package com.example.ladiworkservice.shippingorders.ghn.request;

import java.util.HashMap;
import java.util.Map;

public class OPTIONGHN {
    public static final Map<Integer, String> OPTIONDELIVERYGHN = OptionDelivery.optionDelivery();
    public static class OptionDelivery{
        public static Map<Integer, String> optionDelivery(){
            Map<Integer, String> optionDeliveryGHN = new HashMap<>();
            optionDeliveryGHN.put(1,"CHOXEMHANGKHONGTHU");
            optionDeliveryGHN.put(2,"CHOTHUHANG");
            optionDeliveryGHN.put(3,"KHONGCHOXEMHANG");
            return optionDeliveryGHN;
        }

    }

}
