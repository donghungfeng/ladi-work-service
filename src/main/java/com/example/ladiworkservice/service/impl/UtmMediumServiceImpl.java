package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.configurations.JwtTokenProvider;
import com.example.ladiworkservice.dto.UtmMediumDto;
import com.example.ladiworkservice.model.Account;
import com.example.ladiworkservice.model.UtmMedium;
import com.example.ladiworkservice.repository.AccountRepository;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.UtmMediumRepository;
import com.example.ladiworkservice.service.UtmMediumService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class UtmMediumServiceImpl extends BaseServiceImpl<UtmMedium> implements UtmMediumService {

    @Autowired
    UtmMediumRepository utmMediumRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AccountRepository accountRepository;
    @Override
    protected BaseRepository<UtmMedium> getRepository() {
        return utmMediumRepository;
    }

    public List<UtmMediumDto> getAllData(String jwt){
        List<UtmMediumDto> utmMediumDtoList= new ArrayList<>();
        List<UtmMedium> utmMediumList = new ArrayList<>();
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String bearerToken = getJwtFromRequest(jwt);
        String userName = jwtTokenProvider.getAccountUserNameFromJWT(bearerToken);
        Account account = accountRepository.findByUserName(userName);
        if(account.getRole().equals("admin")){
            utmMediumList = utmMediumRepository.findAll();
        }
        else if(account.getRole().equals("marketing")){
            utmMediumList = utmMediumRepository.findAllByAccount(account);
        }
        for (int i = 0; i < utmMediumList.size(); i++) {
            UtmMediumDto utmMediumDto = modelMapper.map(utmMediumList.get(i), UtmMediumDto.class);
            utmMediumDtoList.add(utmMediumDto);
        }
        return utmMediumDtoList;
    }


    private String getJwtFromRequest(String bearerToken) {
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
