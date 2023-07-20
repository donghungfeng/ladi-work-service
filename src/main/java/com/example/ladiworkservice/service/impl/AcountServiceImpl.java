package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.configurations.AccountDetails;
import com.example.ladiworkservice.configurations.JwtTokenProvider;
import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.reponse.LoginResponse;
import com.example.ladiworkservice.model.Account;
import com.example.ladiworkservice.repository.AccountRepository;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.service.AcountService;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class AcountServiceImpl extends BaseServiceImpl<Account> implements AcountService {
    private AccountRepository acountRepository;

    public AcountServiceImpl(AccountRepository acountRepository) {
        this.acountRepository = acountRepository;
    }

    @Override
    protected BaseRepository<Account> getRepository() {
        return acountRepository;
    }

    @Override
    public BaseResponse login(String userName, String password) throws NoSuchAlgorithmException {
        Account account = acountRepository.findByUserName(userName);
        if (account == null) {
            return new BaseResponse(500, "Account không tồn tại", null);
        }

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();

        String myChecksum = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        if (!account.getPassWord().equals(myChecksum)) {
            return new BaseResponse(500, "Mật khẩu không chính xác", null);
        }

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        LoginResponse loginResponse = new LoginResponse(jwtTokenProvider.generateToken(new AccountDetails(account)), account.getId(), account.getUserName(), account.getFullName(), account.getRole());
        return new BaseResponse(200, "OK", loginResponse);
    }

    @Override
    public List<Account> getMtkByShop(String shopcode) {
        return acountRepository.getMktByShop(shopcode);
    }

    @Override
    public BaseResponse update(Long id, Account account) throws NoSuchAlgorithmException {
        Account account1 = acountRepository.findAllById(id);
        if (account1 == null) {
            return new BaseResponse(200, "message", "Không tồn tại bản ghi với id tương ứng!");
        }
        account1.setFullName(account.getFullName());
        account1.setEmail(account.getEmail());
        account1.setAddress(account.getAddress());
        account1.setPhone(account.getPhone());
        account1.setShop(account.getShop());
        if (account.getPassWord() != "") {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(account.getPassWord().getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
            account1.setPassWord(myHash);
        }
        if (account.getRole() != null) {
            account1.setRole(account.getRole());
        }
        acountRepository.save(account1);
        return new BaseResponse(200, "Ok", account1);
    }

}
