package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.ListLogWork;
import com.example.ladiworkservice.model.Log_work;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.ListLogWorkRepository;
import com.example.ladiworkservice.service.ListLogWorkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListLogWorkServiceImpl extends BaseServiceImpl<ListLogWork> implements ListLogWorkService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ListLogWorkRepository listLogWorkRepository;

    @Override
    protected BaseRepository<ListLogWork> getRepository() { return listLogWorkRepository; }

    @Override
    public BaseResponse findDataByDate(Long startDate, Long endDate, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        List<ListLogWork> listLogWorks = listLogWorkRepository.findDataByDate(startDate, endDate);
        if (!listLogWorks.isEmpty()) {
            // Thực hiện phân trang dựa trên danh sách kết quả và thông tin phân trang đã có
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), listLogWorks.size());
            Page<ListLogWork> listLogWorkPage = new PageImpl<>(listLogWorks.subList(start, end), pageable, listLogWorks.size());
            return new BaseResponse(200, "OK", listLogWorkPage.getContent());
        } else {
            return new BaseResponse(500, "Không tìm thấy dữ liệu phù hợp", null);
        }
    }
}
