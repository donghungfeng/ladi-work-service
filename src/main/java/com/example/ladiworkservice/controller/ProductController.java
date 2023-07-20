package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.CreateProductRequest;
import com.example.ladiworkservice.controller.request.GetPriceImportRequest;
import com.example.ladiworkservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping()
    public BaseResponse getAllByShopcodeAndStatus(@RequestParam(name = "shopCode") String  shopcode, @RequestParam(name = "status") int status){
        return productService.getAllByShopcodeAndStatus(shopcode, status);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('admin')")
    public BaseResponse create(@RequestBody CreateProductRequest createProductRequest){
        return productService.create(createProductRequest);
    }

    @GetMapping("/search")
    public BaseResponse search(@RequestParam String filter, @RequestParam String sort, @RequestParam int size, @RequestParam int page){
        return productService.search(filter, sort, size, page);
    }

    @PostMapping("/price-import")
    public BaseResponse getPridceImport(@RequestBody GetPriceImportRequest getPriceImportRequest){
        return productService.updateSoldProduct(getPriceImportRequest.getList());
    }

    @GetMapping("statistic-product")
    public BaseResponse statisticProduct(@RequestParam String shopId){
        return productService.statisticProduct(Long.parseLong(shopId));
    }
}
