package com.example.ladiworkservice.controller.request;


import com.example.ladiworkservice.controller.reponse.StatisticGeneralResponse;
import com.example.ladiworkservice.model.Account;
import com.example.ladiworkservice.model.BoL;
import com.example.ladiworkservice.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataRequest {

    private Long id;

    private String name;

    private String phone;

    private String street;

    private String country;
    private String productIds;

    private String state;

    private String district;

    private Long deliveryFee;

    private Long discount;
    private String reason;

    private String ward;

    private String product;

    private  int status;

    private Long date;

    private String link;

    private String ipAddress;

    private Long dateChanged;

    private Product productDto;

    private String utm_source;

    private String utm_medium;

    private String utm_campation;

    private String utm_term;

    private String utm_content;

    private String variant_url;

    private Double price;

    private String message;

    private String note;

    private String shopCode;

    private Account account;

    private Long dateOnly;

    private Long dateChangedOnly;
    private Account saleAccount;
    private Long nhanVienId;
    private Double cost;
    private Long cogs;
    private Long totalProductValue;
    private Long timeOrderSuccess;
    private String dataInfo;
    private Long actualFee;
    private BoL boL;
    private StatisticGeneralResponse statisticGeneralResponse;

}
