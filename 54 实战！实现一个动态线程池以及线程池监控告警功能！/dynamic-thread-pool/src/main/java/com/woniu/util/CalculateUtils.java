package com.woniu.util;

import java.math.BigDecimal;

public class CalculateUtils {

    /**
     * 数据相除运算
     *
     * @param num1
     * @param num2
     * @return
     */
    public static double divide(int num1, int num2) {
        BigDecimal divide = new BigDecimal(num1).divide(new BigDecimal(num2), 2, BigDecimal.ROUND_UP);
        return divide.doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(divide(8, 63));
    }
}
