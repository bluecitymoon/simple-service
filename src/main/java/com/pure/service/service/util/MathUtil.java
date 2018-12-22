package com.pure.service.service.util;

import java.math.BigDecimal;

public class MathUtil {

    public static float division(Integer a, Integer b, Integer decimalCount) {

        Double rate = (new Double(a) ) * 100 / b;

        BigDecimal finishRateDecimal = new BigDecimal(rate);
        BigDecimal roundedDecimal = finishRateDecimal.setScale(decimalCount, BigDecimal.ROUND_HALF_UP);

        return new Float(roundedDecimal.toString());
    }
}
