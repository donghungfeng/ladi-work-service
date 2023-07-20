package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.controller.reponse.AddressReponse;
import com.example.ladiworkservice.model.Address;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository  extends BaseRepository<Address> {
    @Query("SELECT DISTINCT new com.example.ladiworkservice.controller.reponse.AddressReponse(a.provinceId, a.province) from Address a")
    List<AddressReponse> findProvinces();

    @Query("SELECT DISTINCT new com.example.ladiworkservice.controller.reponse.AddressReponse(a.districtId, a.district) from Address a where a.provinceId = :provinceId")
    List<AddressReponse> findDistinctByProvinceId(Long provinceId);

    @Query("SELECT DISTINCT new com.example.ladiworkservice.controller.reponse.AddressReponse(a.communeId, a.commune) from Address a where a.provinceId = :provinceId and a.districtId = :districtId")
    List<AddressReponse> findCommuneByProvinceIdAndDistrictId(Long provinceId, Long districtId);

    Address findAllByProvinceIdAndDistrictIdAndCommuneId(Long provinceId, Long districtId, Long communeId);
}
