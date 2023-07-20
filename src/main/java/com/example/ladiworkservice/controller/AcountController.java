package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.LoginRequest;
import com.example.ladiworkservice.model.Account;
import com.example.ladiworkservice.repository.AccountRepository;
import com.example.ladiworkservice.service.AcountService;
import com.example.ladiworkservice.service.BaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("account")
@CrossOrigin
public class AcountController extends BaseController<Account> {
    @Autowired
    private AcountService acountService;
    @Autowired
    private AccountRepository accountRepository;
    public AcountController(AcountService acountService){
        this.acountService = acountService;
    }
    @Override
    protected BaseService<Account> getService() {
        return acountService;
    }

    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginRequest loginRequest) throws NoSuchAlgorithmException {
        return acountService.login(loginRequest.getUserName(), loginRequest.getPassWord());
    }

    @Override
    public BaseResponse getAll(){
        return new BaseResponse(200, "Lấy dữ liệu thành công!", "Ok");
    }
    @PostMapping("create")
    @Override
    public BaseResponse create(@RequestBody Account account) throws NoSuchAlgorithmException, JsonProcessingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(account.getPassWord().getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        account.setPassWord(myHash);
        System.out.println(account.getUserName());
        if(accountRepository.findByUserName(account.getUserName())!=null){
            return new BaseResponse(500, "Tên tài khoản đã tồn tại",null );
        }

        return new BaseResponse(200, "Tạo thành công!", this.getService().create(account));
    }
}
