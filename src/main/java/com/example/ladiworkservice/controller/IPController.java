package com.example.ladiworkservice.controller;


import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.reponse.IPResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.*;
import java.util.Enumeration;


@RestController
@RequestMapping("/ip")
@CrossOrigin
public class IPController {
    @GetMapping(value = "", produces = "application/json")
    public BaseResponse getIPv4Address() throws  SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && address.getHostAddress().contains(".")) {
                        IPResponse ipResponse=new IPResponse();
                        ipResponse.setIp(address.getHostAddress());
                        ipResponse.setWifiName(networkInterface.getDisplayName());
                        return new BaseResponse(200, null, ipResponse);
                    }
                }
        }
        return new BaseResponse(500, "không ket noi wifi", null);
    }
}

