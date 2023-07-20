package com.example.ladiworkservice.configurations.global_variable;

import java.util.Arrays;
import java.util.List;

public class OrderStatus {
    public static final List<Integer> STATUS_ORDER_ASSIGNED = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);
    public static final List<Integer> STATUS_ORDER_PROCESSED = Arrays.asList(2,3,4,5,6,7,8,10,11,12);
    public static final List<Integer> STATUS_ORDER_SUCCESS= Arrays.asList(7,8,10,11,12);
    public static final List<Integer> STATUS_ORDER_ACTUAL_REVENUE= Arrays.asList(12);
    public static final List<Integer> STATUS_ORDER_ESTIMATED_REVENUE= Arrays.asList(7,8,11,12);

}
