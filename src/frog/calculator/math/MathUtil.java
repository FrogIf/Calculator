package frog.calculator.math;

import frog.calculator.math.rational.IntegerNumber;

public class MathUtil {

    private static final IntegerNumber TWO = IntegerNumber.valueOf(2);

    public static BaseNumber power(BaseNumber base, BaseNumber pow){
        IntegerNumber intPow = pow.convertToInteger();
        BaseNumber result = BaseNumber.ONE;
        if(intPow != null){   // 说明是整数幂
            while(!intPow.equals(IntegerNumber.ZERO)){
                if(intPow.isOdd()){
                    result = result.mult(base);
                }
                base = base.mult(base);
                intPow = intPow.div(TWO);
            }
            if(intPow.getSign() == NumberConstant.NEGATIVE){  // 负指数
                return BaseNumber.ONE.div(result);
            }else{  // 正指数
                return result;
            }
        }else{
            // TODO 非整数指数的运算
            return null;
        }
    }

    public static BaseNumber sqrt(BaseNumber num){
        return num;
    }


}
