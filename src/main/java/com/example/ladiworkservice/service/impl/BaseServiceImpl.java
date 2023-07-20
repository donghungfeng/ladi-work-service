package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.exceptions.FindByIdNullException;
import com.example.ladiworkservice.query.CustomRsqlVisitor;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.service.BaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.security.NoSuchAlgorithmException;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected abstract BaseRepository<T> getRepository();
    Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);


    @Override
    public BaseResponse getAll() {
        return new BaseResponse(200, "Ok", this.getRepository().findAllByOrderByIdDesc());
    }

    @Override
    public BaseResponse search(String filter, String sort, int size, int page){
        Node rootNode = new RSQLParser().parse(filter);
        Specification<T> spec = rootNode.accept(new CustomRsqlVisitor<T>());
        String[] sortList = sort.split(",");
        Sort.Direction direction = sortList[1].equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, direction, sortList[0]);
        return new BaseResponse(200, "Ok", this.getRepository().findAll(spec, pageable));
    }

    @Override
    public BaseResponse create(T t) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return new BaseResponse(200,"Ok", this.getRepository().save(t));
        }
        catch (Exception ex) {
            logger.error(String.format("BaseServiceImpl|create|Error: %s|INPUT: %s", ex.getMessage(), mapper.writeValueAsString(t)));
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public BaseResponse update(Long id,T t) throws NoSuchAlgorithmException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        try {
            if (this.getRepository().findAllById(id) == null)
                throw new FindByIdNullException(String.format("Không tồn tại bản ghi với id: %s", id));


            return new BaseResponse(200, "Ok", this.getRepository().save(t));
        }
        catch (Exception ex) {
            logger.error(String.format("BaseServiceImpl|update|Error: %s|INPUT: %s", ex.getMessage(), mapper.writeValueAsString(t)));
            throw new RuntimeException(ex.getMessage());
        }
    }
    @Override
    public BaseResponse getById(Long id) {
        try {
            if (this.getRepository().findAllById(id) == null)
                throw new FindByIdNullException(String.format("Không tồn tại bản ghi với id: %s", id));

            return new BaseResponse(200, "Ok", this.getRepository().findAllById(id));
        }
        catch (Exception ex) {
            logger.error(String.format("BaseServiceImpl|getById|Error: %s|INPUT: %s", ex.getMessage(), id));
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public BaseResponse deleteById(Long id) {
        try {
            T t = this.getRepository().findAllById(id);

            if (this.getRepository().findAllById(id) == null)
                throw new FindByIdNullException(String.format("Không tồn tại bản ghi với id: %s", id));

            this.getRepository().delete(t);
            return new BaseResponse(200, "Ok", this.getRepository().findAllById(id));
        }
        catch (Exception ex) {
            logger.error(String.format("BaseServiceImpl|getById|Error: %s|INPUT: %s", ex.getMessage(), id));
            throw new RuntimeException(ex.getMessage());
        }
    }
}
