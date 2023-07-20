package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.CallInfo;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.CallInfoRepository;
import com.example.ladiworkservice.service.CallInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallInfoServiceImpl extends BaseServiceImpl<CallInfo> implements CallInfoService {
    @Autowired
    CallInfoRepository callInfoRepository;
    @Override
    protected BaseRepository<CallInfo> getRepository() {
        return callInfoRepository;
    }

    @Override
    public BaseResponse getCallActive() {
        Pageable pageable = PageRequest.of(0,100);
        List<CallInfo> callList = callInfoRepository.findAllByIsActive(1);

        if (callList.isEmpty()){
            return new BaseResponse(500, "FAIL", "Không có ext rảnh rỗi!");
        }
        double randomDouble = Math.random();
        randomDouble = randomDouble * callList.size() ;
        int index = (int) randomDouble;

        CallInfo callInfo = callInfoRepository.findAllByIsActive(1).get(index);
        callInfo.setIsActive(0);
        callInfoRepository.save(callInfo);
        return new BaseResponse(200, "OK", callInfo );
    }

    @Override
    public BaseResponse active(Long id) {
        CallInfo callInfo = callInfoRepository.findAllById(id);
        if (callInfo == null){
            return new BaseResponse(500, "FAIL", "Không tồn tại bản ghi!");
        }
        callInfo.setIsActive(1);
        callInfoRepository.save(callInfo);
        return new BaseResponse(200, "OK", callInfo);
    }

    @Override
    public BaseResponse deActive(Long id) {
        CallInfo callInfo = callInfoRepository.findAllById(id);
        if (callInfo == null){
            return new BaseResponse(500, "FAIL", "Không tồn tại bản ghi!");
        }
        callInfo.setIsActive(0);
        callInfoRepository.save(callInfo);
        return new BaseResponse(200, "OK", callInfo);
    }

    @Override
    public BaseResponse heathCheck() {
        if (callInfoRepository.heathCheck() == 1){
            return new BaseResponse(200, "Ok", "Kết nối thành công!" );
        }else {
            return new BaseResponse(500, "FAIL", "Kết nối thất bại!");
        }
    }
}
