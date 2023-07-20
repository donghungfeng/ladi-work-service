package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.model.Address;
import com.example.ladiworkservice.repository.AddressRepository;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl   extends BaseServiceImpl<Address> implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    protected BaseRepository<Address> getRepository() {
        return addressRepository;
    }
}
