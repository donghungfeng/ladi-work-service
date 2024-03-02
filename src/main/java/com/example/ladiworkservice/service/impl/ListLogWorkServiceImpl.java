package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.model.ListLogWork;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.ListLogWorkRepository;
import com.example.ladiworkservice.service.ListLogWorkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListLogWorkServiceImpl extends BaseServiceImpl<ListLogWork> implements ListLogWorkService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ListLogWorkRepository listLogWorkRepository;

    @Override
    protected BaseRepository<ListLogWork> getRepository() { return listLogWorkRepository; }


}
