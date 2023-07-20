package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.dto.DataConfigInfo;
import com.example.ladiworkservice.exceptions.FindByIdNullException;
import com.example.ladiworkservice.model.DataConfig;
import com.example.ladiworkservice.model.Shop;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.DataConfigRepository;
import com.example.ladiworkservice.service.DataConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class DataConfigServiceImpl extends BaseServiceImpl<DataConfig> implements DataConfigService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DataConfigRepository dataConfigRepository;

    public DataConfigServiceImpl(DataConfigRepository dataConfigRepository) {
        this.dataConfigRepository = dataConfigRepository;
    }

    @Override
    protected BaseRepository<DataConfig> getRepository() {
        return dataConfigRepository;
    }

    @Override
    public void initDefault() throws JsonProcessingException {
        List<DataConfig> dataConfigs = dataConfigRepository.getByShop(null);
        if (!CollectionUtils.isEmpty(dataConfigs)) return;
        createDefault();
    }

    private DataConfig createDefault() {
        try {
            DataConfig defaultConfig = DataConfig.builder()
                    .addressInfo(objectMapper.writeValueAsString(DataConfigInfo.getDefaultAddress()))
                    .customerInfo(objectMapper.writeValueAsString(DataConfigInfo.getDefaultCustomer()))
                    .build();
            return dataConfigRepository.save(defaultConfig);
        } catch (Exception e) {
            logger.error(String.format("createDefault: ERROR: %s", e.getMessage()));
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void createForShop(Shop shop) {
        List<DataConfig> shopConfigs = dataConfigRepository.getByShop(shop);
        if (!CollectionUtils.isEmpty(shopConfigs)) return;

        DataConfig defaultConfig = this.getDefault();
        DataConfig shopConfig = new DataConfig();
        BeanUtils.copyProperties(defaultConfig, shopConfig);

        shopConfig.setId(null);
        shopConfig.setShop(shop);
        dataConfigRepository.save(shopConfig);
    }

    @Override
    public DataConfig findById(Long id) {
        return dataConfigRepository.findById(id).orElseThrow(
                () -> new RuntimeException("DataConfig không tồn tại!")
        );
    }

    private DataConfig getDefault() {
        List<DataConfig> dataConfigs = dataConfigRepository.getByShop(null);
        if (!CollectionUtils.isEmpty(dataConfigs)) return dataConfigs.get(0);
        return createDefault();
    }

    @Override
    public BaseResponse create(DataConfig dataConfig) throws JsonProcessingException {
        if (dataConfig.getShop() == null)
            throw new RuntimeException("Vui lòng chọn shop!");
        List<DataConfig> dataConfigs = dataConfigRepository.getByShop_Id(dataConfig.getShop().getId());
        if (!CollectionUtils.isEmpty(dataConfigs))
            throw new RuntimeException("Shop đã tồn tại cấu hình, không thể tạo thêm!");
        return new BaseResponse(200, "Lưu cấu hình thành công!", this.getRepository().save(dataConfig));
    }

    @Override
    public BaseResponse update(Long id, DataConfig dataConfig) throws NoSuchAlgorithmException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            if (this.getRepository().findAllById(id) == null)
                throw new FindByIdNullException(String.format("Không tồn tại bản ghi với id: %s", id));


            return new BaseResponse(200, "Lưu cấu hình thành công!", this.getRepository().save(dataConfig));
        } catch (Exception ex) {
            logger.error(String.format("BaseServiceImpl|update|Error: %s|INPUT: %s", ex.getMessage(), mapper.writeValueAsString(dataConfig)));
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public BaseResponse deleteById(Long id) {
        DataConfig dataConfig = this.findById(id);
        if (dataConfig.getShop() == null)
            throw new RuntimeException("Không được xoá DataConfig mặc định của hệ thống!");

        List<DataConfig> dataConfigs = dataConfigRepository.getByShop(dataConfig.getShop());
        //Chỉ được xoá khi Shop có nhiều hơn 1 DataConfig
        if (dataConfigs.size() > 1)
            return super.deleteById(id);

        throw new RuntimeException("Không được xoá DataConfig của Shop!");
    }
}
