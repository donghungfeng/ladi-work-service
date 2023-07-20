package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.dto.*;
import com.example.ladiworkservice.repository.*;
import com.example.ladiworkservice.repository.ChartDataRepository;
import com.example.ladiworkservice.service.DashboardService;
import com.example.ladiworkservice.dto.*;
import com.example.ladiworkservice.repository.DashboardRepository;
import com.example.ladiworkservice.repository.DataRepository;
import com.example.ladiworkservice.repository.RefundRateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;
    private final DataRepository dataRepository;
    private final ChartDataRepository chartDataRepository;
    private final RefundRateRepository refundRateRepository;

    public DashboardServiceImpl(DashboardRepository dashboardRepository, DataRepository dataRepository, ChartDataRepository chartDataRepository, RefundRateRepository refundRateRepository) {
        this.dashboardRepository = dashboardRepository;
        this.dataRepository = dataRepository;
        this.chartDataRepository = chartDataRepository;
        this.refundRateRepository = refundRateRepository;
    }

    @Override
    public BaseResponse getDataDashboard(String shopCode, Long fromDate, Long toDate) {
        BaseResponse response = new BaseResponse();

        DashboardDto dashboardDto = new DashboardDto();
        CostDashBoardDto costDashBoardDto = new CostDashBoardDto();
        ProductDashboardDto productDashboardDto = new ProductDashboardDto();


        try {
            var refundRater = refundRateRepository.findById(1L);

            //set revenue data dashboard
            var revenueData = dashboardRepository.getDataRevenueInfoDashboard(shopCode, fromDate, toDate);
            revenueData.getOrderAcceptRate();
            dashboardDto.setRevenueDashboardData(revenueData);


            if(!refundRater.isEmpty())
                revenueData.setRevenueAfterRefund(Math.ceil(revenueData.getRevenue() * ((100 - refundRater.get().getRate()))/100));


            //set cost data dashboard
            var costData = dashboardRepository.getCostDataDashboard(shopCode, fromDate, toDate);

            var totalCost = costData.getTotalCost() + revenueData.getTotalCostPrice() + revenueData.getTotalDeliveryFee();
            var totalCostPercent = revenueData.getRevenue() != 0 ?  (totalCost / revenueData.getRevenue()) * 100 : 0;
            double totalMarketingPercent = revenueData.getRevenue() != 0 ? (costData.getTotalMarketingCost()*1.0  / revenueData.getRevenue()*1.0 ) * 100 : 0;

            double totalDeliveryAfterRefundPercent = revenueData.getRevenue() != 0 ? (revenueData.getTotalDeliveryFeeAfterRefund()*1.0  / revenueData.getRevenue()*1.0) * 100 : 0;
            double totalDeliveryPercent = revenueData.getRevenue() != 0 ? (revenueData.getTotalDeliveryFee() *1.0 / revenueData.getRevenue()*1.0 ) * 100 : 0;

            var totalCostAfterRefund = costData.getTotalCost() + revenueData.getTotalCostPriceAfterRefund() + revenueData.getTotalDeliveryFeeAfterRefund();
            double totalCostAfterRefundPercent = revenueData.getRevenue() != 0 ? (totalCostAfterRefund*1.0 / revenueData.getRevenue()*1.0) * 100 : 0;;

            costDashBoardDto.setTotalCost((long) totalCost);
            costDashBoardDto.setTotalCostAfterRefund(totalCostAfterRefund);
            costDashBoardDto.setTotalMarketingCost(costData.getTotalMarketingCost());
            costDashBoardDto.setTotalShipCost(revenueData.getTotalDeliveryFee());
            costDashBoardDto.setTotalOperationCost(costData.getTotalOperationCost());
            costDashBoardDto.setTotalCostPrice(revenueData.getTotalCostPrice());
            costDashBoardDto.setTotalCostPriceAfterRefund(revenueData.getTotalCostPriceAfterRefund());

            costDashBoardDto.setTotalDeliveryFeeAfterRefund(Long.valueOf(revenueData.getTotalDeliveryFeeAfterRefund()));
            costDashBoardDto.setOtherCost(costData.getOtherCost());

            //cost percent
            costDashBoardDto.setTotalCostMarketingPercent(Math.floor(totalMarketingPercent * 100) / 100);

            costDashBoardDto.setTotalDeliveryPercent(Math.floor(totalDeliveryAfterRefundPercent * 100) / 100);

            costDashBoardDto.setTotalDeliveryAfterRefundPercent(Math.floor(totalDeliveryPercent * 100) / 100);

            costDashBoardDto.setTotalCostAfterRefundPercent(Math.floor(totalCostAfterRefundPercent * 100) / 100);

            costDashBoardDto.setTotalCostPricePercent(Math.floor(totalCostPercent * 100) / 100);


            dashboardDto.setCostDashboardData(costDashBoardDto);

            //set order data dashboard

            var productSoldData = dashboardRepository.getProductSoldDashboard(shopCode, fromDate, toDate);
            productDashboardDto.setProductSoldDashboard(productSoldData.getTotalProductSolded());
            productDashboardDto.setTotalOrderDashboard(revenueData.getTotalOrder());

            dashboardDto.setProductDashboardData(productDashboardDto);

            //set profit data dashboard


            ProfitDashboardDto profitDto = new ProfitDashboardDto();
            var profit = revenueData.getRevenue() != null ? revenueData.getRevenue() - totalCost : 0;
            var profitAfterRefund = revenueData.getRevenueAfterRefund() != null ? revenueData.getRevenueAfterRefund() - totalCostAfterRefund : 0;
            var netProfit = revenueData.getSales() != null ? revenueData.getSales() - totalCost : 0;

            var profitPercent = Math.round((profit/revenueData.getRevenue()) * 100);
            var profitAfterRefundPercent = Math.round((profitAfterRefund/revenueData.getRevenue()) * 100);
            var netProfitPercent = Math.round(netProfit/revenueData.getRevenue() * 100);

            profitDto.setProfit((long) profit);
            profitDto.setProfitAfterRefund(profitAfterRefund);
            profitDto.setNetProfit((long) netProfit);
            profitDto.setProfitPercent((long) profitPercent);
            profitDto.setProfitAfterRefundPercent(profitAfterRefundPercent);
            profitDto.setNetProfitPercent((long) netProfitPercent);

            //productWarehouse
            var productWareHouse = dashboardRepository.getProductWarehouseDto(shopCode, fromDate, toDate);
            dashboardDto.setProductWareHouse(productWareHouse);

            dashboardDto.setProfitData(profitDto);
            response.RESULT = dashboardDto;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        response.MESSAGE = "Thành công";
        response.CODE = HttpStatus.OK.value();

        return response;
    }

    @Override
    public BaseResponse getDataRevenueStatistics(String shopCode, Long fromDate, Long toDate) {
        BaseResponse response = new BaseResponse();
        try {
            List<RevenueStatisticsDto> saleData = dashboardRepository.getRevenueStatisticsData(shopCode, fromDate, toDate);
            List<CostDataDto> costs = dashboardRepository.getCostData(shopCode, fromDate, toDate);



            for (RevenueStatisticsDto sale : saleData)
            {
                for (CostDataDto cost : costs)
                {
                    if(Objects.equals(sale.getDate(), cost.getDate())) {
                        var revenue = sale.getSales() - cost.getTotalCost().doubleValue();
                        var averageRevenue = sale.getAverageRevenue() - cost.getAverageCost().doubleValue();
                        sale.setRevenue(revenue);
                        sale.setAverageRevenue(averageRevenue);
                    }
                }
            }

            response.RESULT = saleData;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        response.MESSAGE = "Thành công";
        response.CODE = HttpStatus.OK.value();

        return response;
    }

    @Override
    public BaseResponse getCostData(String shopCode, Long fromDate, Long toDate) {
        BaseResponse response = new BaseResponse();
        try {
            List<CostDataDto> result = dashboardRepository.getCostData(shopCode, fromDate, toDate);
            response.RESULT = result;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        response.MESSAGE = "Thành công";
        response.CODE = HttpStatus.OK.value();

        return response;
    }

    @Override
    public BaseResponse getSaleUtmDto(String shopCode, Long fromDate, Long toDate) {
        BaseResponse response = new BaseResponse();
        try {
            List<SaleUtmDto> result = dashboardRepository.getSaleUtmDto(shopCode, fromDate, toDate);
            response.RESULT = result;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        response.MESSAGE = "Thành công";
        response.CODE = HttpStatus.OK.value();

        return response;
    }

    @Override
    public BaseResponse getMarketingUtmDto(String shopCode, Long fromDate, Long toDate) {
        BaseResponse response = new BaseResponse();
        try {
            List<MarketingUtmDto> result = dashboardRepository.getMarketingUtmDto(shopCode, fromDate, toDate);

            List<CostMarketingGroupByName>  marketingCosts = dashboardRepository.getCostMarketingGroupByName(shopCode, fromDate, toDate);
            for(var utm : result) {

                for(var cost : marketingCosts) {
                    if(utm.getUtmMedium().equals(cost.getName())) {
                        utm.setCostMarketing(cost.getCostMarketingByName());
                    }
                }
            }

            response.RESULT = result;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        response.MESSAGE = "Thành công";
        response.CODE = HttpStatus.OK.value();

        return response;
    }

    @Override
    public BaseResponse getCostMarketingGroupByName(String shopCode, Long fromDate, Long toDate) {
        BaseResponse response = new BaseResponse();
        try {
            List<CostMarketingGroupByName> result = dashboardRepository.getCostMarketingGroupByName(shopCode, fromDate, toDate);
            response.RESULT = result;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        response.MESSAGE = "Thành công";
        response.CODE = HttpStatus.OK.value();

        return response;
    }

    @Override
    public BaseResponse getProductWareHouseChartDto(Long fromDate, Long toDate) {
        BaseResponse response = new BaseResponse();
        try {
            List<ProductWareHouseChartDto> result = dashboardRepository.getProductWareHouseChartDto(fromDate, toDate);
            response.RESULT = result;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        response.MESSAGE = "Thành công";
        response.CODE = HttpStatus.OK.value();

        return response;
    }


}
