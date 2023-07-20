package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.configurations.JwtTokenProvider;
import com.example.ladiworkservice.configurations.global_variable.WAREHOUSE;
import com.example.ladiworkservice.controller.request.CreateWarehouseRequest;
import com.example.ladiworkservice.model.Account;
import com.example.ladiworkservice.model.Shop;
import com.example.ladiworkservice.model.Warehouse;
import com.example.ladiworkservice.repository.AccountRepository;
import com.example.ladiworkservice.repository.ShopRepository;
import com.example.ladiworkservice.repository.WarehouseRepository;
import com.example.ladiworkservice.service.DataConfigService;
import com.example.ladiworkservice.service.ShopService;
import com.example.ladiworkservice.controller.reponse.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.ladiworkservice.repository.BaseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

@Service
public class ShopServiceImpl extends BaseServiceImpl<Shop> implements ShopService {

    @Autowired
    ShopRepository shopRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    private DataConfigService dataConfigService;

    @Override
    protected BaseRepository<Shop> getRepository() {
        return shopRepository;
    }

    @Override
    public BaseResponse getAllByStatus(int status, String jwt) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String bearerToken = getJwtFromRequest(jwt);
        String userName = jwtTokenProvider.getAccountUserNameFromJWT(bearerToken);
        Account account = accountRepository.findByUserName(userName);
        List<Shop> shopList = new ArrayList<>();
        if (status == -1) {
            if (account.getRole().equals("admin")) {
                shopList = shopRepository.findAll();
            } else {
                String[] shopCodes = account.getShop().split(",");
                for (String item : shopCodes) {
                    shopList.addAll(shopRepository.findAllByCode(item));
                }
            }
        } else {
            if (account.getRole().equals("admin")) {
                shopList = shopRepository.findAllByStatus(status);
            } else {
                String[] shopCodes = account.getShop().split(",");
                for (String item : shopCodes) {
                    shopList.addAll(shopRepository.findAllByStatusAndCode(status, item));
                }
            }
        }
        return new BaseResponse(200, "OK", shopList);
    }

    @Override
    public Shop findById(Long id) {
        return shopRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Shop Id %s không tồn tại!", id))
        );
    }

    @Override
    public Shop getByName(String name) {
        return shopRepository.findByName(name).orElseThrow(
                () -> new RuntimeException(String.format("Shop có tên: %s không tồn tại!", name))
        );
    }

    private String getJwtFromRequest(String bearerToken) {
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public BaseResponse create(Shop shop) {
        List<Shop> shopList = shopRepository.findAllByCode(shop.getCode());
        if (!shopList.isEmpty()) {
            return new BaseResponse(500, "FAIL", "code shop đã tồn tại");
        }
        shop = shopRepository.save(shop);
        Warehouse warehouse = Warehouse.builder()
                .name("Kho mặc định")
                .flag(WAREHOUSE.FLAG_NOMAL)
                .shop(shop)
                .staus(1)
                .build();
        WarehouseServiceImpl warehouseService = new WarehouseServiceImpl(warehouseRepository);
        CreateWarehouseRequest createWarehouseRequest = new CreateWarehouseRequest(warehouse, new ArrayList<Long>());
        warehouseService.saveWarehouse(createWarehouseRequest);

        dataConfigService.createForShop(shop);

        return new BaseResponse(200, "Ok", shop);
    }


}
