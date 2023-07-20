package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.constants.GhnCellIndex;
import com.example.ladiworkservice.constants.GhsvCellIndex;
import com.example.ladiworkservice.controller.SessionData;
import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.FinanceRepository;
import com.example.ladiworkservice.repository.OrderReponsitory;
import com.example.ladiworkservice.repository.ShopRepository;
import com.example.ladiworkservice.service.*;
import com.example.ladiworkservice.service.*;
import com.example.ladiworkservice.util.DateUtil;
import com.example.ladiworkservice.util.ExcelUtils;
import com.example.ladiworkservice.model.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinanceServiceImpl extends BaseServiceImpl<Finance> implements FinanceService {

    private static final String GHSV = "GHSV";
    private static final String GHN = "GHN";

    @Autowired
    FinanceRepository financeRepository;

    @Autowired
    OrderReponsitory orderReponsitory;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    private AccountShippingTypeService accountShippingTypeService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private WebhookOrderService webhookOrderService;

    @Override

    protected BaseRepository<Finance> getRepository() {
        return financeRepository;
    }

    @Override
    public BaseResponse create(Finance finance) {
        if (finance.getShop() == null) {
            return new BaseResponse().fail("Shop không được để trống!");
        }
        if (finance.getDateFrom() == null || finance.getDateTo() == null) {
            return new BaseResponse().fail("From date hoặc to date đang trống!");
        }
        Shop shop = shopRepository.findAllById(finance.getShop().getId());
        if (shop == null) {
            return new BaseResponse().fail("shop không tồn tại!");
        }
        finance.setShop(shop);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        Date tempDate = new Date();
        LocalDateTime currentDate = tempDate.toInstant().atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime();
        Long now = Long.parseLong(currentDate.format(formatter));
        finance.setCode("FE" + now + "_" + finance.getShop().getId());
        finance.setShop(shop);
        finance.setDatePaid(finance.getDateFrom());
        finance.setTransferFee(0L);
        finance.setDiscount(0L);
        finance = financeRepository.save(finance);

        Long totalFee = 0L;
        Long totalOrder = 0L;
        Long ammountReceived = 0L;
        Long amountCollected = 0L;
        List<Order> ordersList = orderReponsitory.findOrderWithDate(Long.parseLong(finance.getDateFrom() + "000000"), Long.parseLong(finance.getDateTo() + "235959"));
        for (Order item : ordersList) {
            item.setDateFinanced(now);
            item.setFinance(finance);
            totalFee = totalFee + item.getFee();
            totalOrder++;
            amountCollected = amountCollected + item.getCod();
        }
        finance.setTotalFee(totalFee);
        finance.setTotalOrder(totalOrder);
        finance.setAmountCollected(amountCollected);
        finance.setAmountReceived(amountCollected - totalFee);
        orderReponsitory.saveAll(ordersList);
        return new BaseResponse().success(financeRepository.save(finance));
    }

    @Override
    public void session(Long shippingTypeId, MultipartFile file) throws Exception {
        AccountShippingType shippingType = accountShippingTypeService.findById(shippingTypeId);
        SessionData sessionData = readFile(file, shippingType.getCode());
        processData(sessionData);
    }

    private Iterator<Row> getRows(MultipartFile file) throws IOException {
        Workbook excel = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = excel.getSheetAt(0);
        return sheet.iterator();
    }

    private SessionData readFile(MultipartFile file, String shippingCode) throws Exception {
        Iterator<Row> iRow = this.getRows(file);
        switch (shippingCode) {
            case GHN:
                return getGhnData(iRow);
            case GHSV:
                return getGhsvData(iRow);
            default:
                throw new RuntimeException(String.format("Hệ thống chưa hỗ trợ đơn vị giao hàng: %s", shippingCode));
        }

    }

    private void processData(SessionData data) {
        Long fee = configService.getChiPhiVanChuyen();

        List<String> orderCodes = this.getOrderCode(data.getOrders());
        List<WebhookOrder> savedOrders = webhookOrderService.getByOrderCodes(orderCodes);

        savedOrders.forEach(e -> {
            e.setStatusText(this.getStatusText(data.getOrders(), e.getOrderCode()));
        });

        savedOrders.addAll(this.getUnsavedOrder(savedOrders, data.getOrders()));

        savedOrders.forEach(e -> {
            e.setFee(fee);
        });

        Long currentDate = DateUtil.getCurrenDate();
        Finance finance = this.saveFinance(data.getFinance(), savedOrders, currentDate);

        for (WebhookOrder savedOrder : savedOrders) {
            savedOrder.setFinanceId(finance.getId());
            savedOrder.setDateFinanced(currentDate);
            if (StringUtils.isEmpty(savedOrder.getProduct())) {
                savedOrder.setProduct("");
            }
        }
        webhookOrderService.saveAll(savedOrders);
    }

    private Finance saveFinance(Finance req, List<WebhookOrder> savedOrders, Long currentDate) {
        req.setTotalFee(this.sumFee(savedOrders));
        req.setAmountReceived(req.getAmountCollected() - req.getTotalFee());
        req.setDateFrom(currentDate);
        req.setDateTo(currentDate);
        if (req.getShop() == null)
            req.setShop(shopService.findById(savedOrders.get(0).getShop()));
        return financeRepository.save(req);
    }

    private List<WebhookOrder> getUnsavedOrder(List<WebhookOrder> savedOrders, List<WebhookOrder> reqOrder) {
        List<String> savedOrderCodes = this.getOrderCode(savedOrders);
        return reqOrder.stream()
                .filter(e -> !savedOrderCodes.contains(e.getOrderCode()))
                .collect(Collectors.toList());
    }

    private String getStatusText(List<WebhookOrder> reqOrders, String orderCode) {
        return reqOrders.stream().filter(
                e -> e.getOrderCode().equalsIgnoreCase(orderCode)
        ).findFirst().get().getStatusText();
    }

    private List<String> getOrderCode(List<WebhookOrder> webhookOrders) {
        return webhookOrders.stream().map(WebhookOrder::getOrderCode).collect(Collectors.toList());
    }

    private SessionData getGhnData(Iterator<Row> iRow) throws Exception {
        List<WebhookOrder> webhookOrders = this.readGhnWebHookOrder(iRow);
        Finance finance = this.getGhnFinance(webhookOrders);
        return SessionData.builder()
                .finance(finance)
                .orders(webhookOrders)
                .build();
    }

    private SessionData getGhsvData(Iterator<Row> iRow) throws Exception {
        Finance finance = this.readFinanceGhsv(iRow);
        List<WebhookOrder> webhookOrders = this.readGhsvWebHookOrder(iRow);
        webhookOrders.forEach(e -> e.setShop(finance.getShop().getId()));
        return SessionData.builder()
                .finance(finance)
                .orders(webhookOrders)
                .build();
    }

    private List<WebhookOrder> readGhnWebHookOrder(Iterator<Row> iRow) throws ParseException {
        boolean isStartCollect = false;
        List<WebhookOrder> webhookOrders = new ArrayList<>();

        String shopExcel = "";

        while (iRow.hasNext()) {
            Row row = iRow.next();
            String firstCellValue = ExcelUtils.getStringCell(row, 0);

            if (Objects.equals(firstCellValue, "STT")) {
                isStartCollect = true;
                continue;
            }
            if (!isStartCollect) continue;
            if (Objects.equals(firstCellValue, "Tổng cộng")) break;

            if (StringUtils.isEmpty(shopExcel)) {
                shopExcel = ExcelUtils.getStringCell(row, GhnCellIndex.CUA_HANG);
            }

            WebhookOrder webhookOrder = new WebhookOrder();
            webhookOrder.setOrderCode(ExcelUtils.getStringCell(row, GhnCellIndex.MA_DON_HANG));
            webhookOrder.setClientCode(ExcelUtils.getStringCell(row, GhnCellIndex.MA_DON_KHACHHANG));

            webhookOrder.setToName(ExcelUtils.getStringCell(row, GhnCellIndex.NGUOI_NHAN));

            String diaChi = ExcelUtils.getStringCell(row, GhnCellIndex.DIA_CHI);
            String[] diaChis = diaChi.split(",");
            webhookOrder.setToAddress(getByIndex(diaChis, 0));
            webhookOrder.setToWard(getByIndex(diaChis, 1));
            webhookOrder.setToDistrict(getByIndex(diaChis, 2));
            webhookOrder.setToProvince(getByIndex(diaChis, 3));

            webhookOrder.setDateCreate(ExcelUtils.getDateCell(row, GhnCellIndex.NGAY_TAO));
            webhookOrder.setDateChanged(ExcelUtils.getDateCell(row, GhnCellIndex.NGAY_GIAO_TRA));
            webhookOrder.setStatusText(ExcelUtils.getStringCell(row, GhnCellIndex.THU_HO));
            webhookOrder.setCod(ExcelUtils.getLongCell(row, GhnCellIndex.COD));

            webhookOrders.add(webhookOrder);
        }

        Shop shop = this.getShop(shopExcel);
        for (WebhookOrder webhookOrder : webhookOrders) {
            webhookOrder.setShop(shop.getId());
        }

        return webhookOrders;
    }

    private Finance getGhnFinance(List<WebhookOrder> webhookOrders) {
        Finance finance = new Finance();
        finance.setTotalOrder(Long.valueOf(webhookOrders.size()));
        finance.setAmountCollected(this.sumCod(webhookOrders));
        return finance;
    }


    private Long sumFee(List<WebhookOrder> webhookOrders) {
        return webhookOrders.stream().mapToLong(WebhookOrder::getFee).sum();
    }

    private Long sumCod(List<WebhookOrder> webhookOrders) {
        return webhookOrders.stream().mapToLong(WebhookOrder::getCod).sum();
    }

    private String getByIndex(String[] list, Integer index) {
        try {
            return list[index];
        } catch (Exception e) {
            return "";
        }
    }

    private List<WebhookOrder> readGhsvWebHookOrder(Iterator<Row> iRow) throws ParseException {
        boolean isStartCollect = false;
        List<WebhookOrder> webhookOrders = new ArrayList<>();

        while (iRow.hasNext()) {
            Row row = iRow.next();

            String firstCellValue = ExcelUtils.getStringCell(row, 0);
            if (Objects.equals(firstCellValue, "STT")) {
                isStartCollect = true;
                continue;
            }

            if (!isStartCollect) continue;
            WebhookOrder webhookOrder = new WebhookOrder();
            webhookOrder.setDateFinanced(ExcelUtils.getDateCell(row, GhsvCellIndex.NGAY_DOI_SOAT));
            webhookOrder.setDateCreate(ExcelUtils.getDateCell(row, GhsvCellIndex.NGAY_TAO));
            webhookOrder.setOrderCode(ExcelUtils.getStringCell(row, GhsvCellIndex.MA_DON_HANG));
            webhookOrder.setRequiredCode(ExcelUtils.getStringCell(row, GhsvCellIndex.MA_YEU_CAU));
            webhookOrder.setClientCode(ExcelUtils.getStringCell(row, GhsvCellIndex.MA_DON_KHACHHANG));
            webhookOrder.setStatusText(ExcelUtils.getStringCell(row, GhsvCellIndex.TRANG_THAI));
            webhookOrder.setToPhone(ExcelUtils.getStringCell(row, GhsvCellIndex.SDT_NGUOINHAN));
            webhookOrder.setToAddress(ExcelUtils.getStringCell(row, GhsvCellIndex.NOI_DUNG));
            webhookOrder.setProduct(ExcelUtils.getStringCell(row, GhsvCellIndex.TEN_SAN_PHAM));
            webhookOrder.setWeight(ExcelUtils.getDoubleCell(row, GhsvCellIndex.KHOI_LUONG));
            webhookOrder.setCod(ExcelUtils.getLongCell(row, GhsvCellIndex.THU_HO));
            webhookOrder.setNote(ExcelUtils.getStringCell(row, GhsvCellIndex.GHI_CHU));
            webhookOrders.add(webhookOrder);
        }

        return webhookOrders;
    }

    private Finance readFinanceGhsv(Iterator<Row> iRow) {
        Finance finance = new Finance();
        while (iRow.hasNext()) {
            Row row = iRow.next();

            if (row.getRowNum() == 0) {
                finance.setShop(this.getShop(row));
                continue;
            }

            if (row.getRowNum() == 1) {
                finance.setTotalOrder(ExcelUtils.getLongCell(row, GhsvCellIndex.FINANCE_VALUE));
                continue;
            }

            if (row.getRowNum() == 5) {
                finance.setTotalFee(ExcelUtils.getLongCell(row, GhsvCellIndex.FINANCE_VALUE));
                continue;
            }

            if (row.getRowNum() == 4) {
                finance.setAmountCollected(ExcelUtils.getLongCell(row, GhsvCellIndex.FINANCE_VALUE));
                continue;
            }

            if (row.getRowNum() == 7) {
                finance.setAmountReceived(ExcelUtils.getLongCell(row, GhsvCellIndex.FINANCE_VALUE));
                break;
            }
        }
        return finance;
    }

    private Shop getShop(String shopExcel) {
        String[] data = shopExcel.split("-");
        Long id = Long.valueOf(data[0].trim());
        String name = data[1].trim();
        Shop shop = shopService.findById(id);
        if (name.equalsIgnoreCase(shop.getName())) return shop;

        throw new RuntimeException(String.format("Shop có Id: %s và tên: %s không tồn tại", id, name));
    }

    private Shop getShop(Row row) {
        String value = ExcelUtils.getStringCell(row, 0);
        String shopName = value.split(",")[0].split(":")[1].trim();
        return shopService.getByName(shopName);
    }
}
