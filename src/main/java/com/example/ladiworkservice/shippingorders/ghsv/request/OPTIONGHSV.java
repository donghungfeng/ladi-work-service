package com.example.ladiworkservice.shippingorders.ghsv.request;

import java.util.HashMap;
import java.util.Map;

public class OPTIONGHSV {
    public static final Map<Integer, Integer> OPTIONDELIVERYGHN = OPTIONGHSV.OptionDelivery.optionDelivery();
    public static class OptionDelivery{
        public static Map<Integer, Integer> optionDelivery(){
            Map<Integer, Integer> optionDeliveryGHN = new HashMap<>();
            optionDeliveryGHN.put(1,1);
            optionDeliveryGHN.put(2,2);
            optionDeliveryGHN.put(3,3);
            return optionDeliveryGHN;
        }

    }


}
