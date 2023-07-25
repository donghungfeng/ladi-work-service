package com.example.ladiworkservice.controller;


import com.example.ladiworkservice.controller.reponse.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.*;
import java.util.Enumeration;


@RestController
@RequestMapping("/ip")
public class IPController {
    @GetMapping(value = "", produces = "application/json")
    public BaseResponse getIPv4Address() throws UnknownHostException, SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
//            if (networkInterface.getName().startsWith("Wireless LAN adapter Wi-Fi")) {
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && address.getHostAddress().contains(".")) {
                        String wifiName = networkInterface.getDisplayName();
                        String ssid = getSSID(networkInterface);
                        return new BaseResponse(200, null, address.getHostAddress() + wifiName+ssid);
                    }
                }
            }
//        }
        return new BaseResponse(500, "không ket noi wifi", null);
    }
    private String getSSID(NetworkInterface networkInterface) throws SocketException {
        byte[] ssidBytes = networkInterface.getHardwareAddress();
        String ssid = "";
        for (int i = 0; i < ssidBytes.length; i++) {
            ssid += String.format("%02X:", ssidBytes[i]);
        }
        return ssid.substring(0, ssid.length() - 1);
    }
}

