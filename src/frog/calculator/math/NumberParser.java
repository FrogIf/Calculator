package frog.calculator.math;

import frog.calculator.math.rational.RationalNumber;
import frog.calculator.util.StringUtils;

public class NumberParser {

    public static BaseNumber parseNumber(String symbol){
        if(StringUtils.isBlank(symbol)){
            throw new IllegalArgumentException("can't parse this number.");
        }

        int dot1 = symbol.indexOf('.');
        int dot2 = symbol.indexOf('_');
        if(dot2 == -1){ // 没有循环节
            return BaseNumber.valueOf(symbol);
        }else{  // 有循环节
            symbol = symbol.replace("_", "");
            RationalNumber resultRational = new RationalNumber(symbol, dot2 - dot1 - 1);
            return BaseNumber.valueOf(resultRational);
        }
    }

}
