package com.example.ladiworkservice.configurations;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class InetAddressUtils {
    public static InetAddress getInetAddress() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface interface1 = interfaces.nextElement();
            Enumeration<InetAddress> addresses = interface1.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
                    return address;
                }
            }
        }
        return null;
    }
}
