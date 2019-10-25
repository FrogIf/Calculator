package frog.calculator.math;

import frog.calculator.math.complex.ComplexNumber;
import frog.calculator.math.rational.IntegerNumber;
import frog.calculator.math.rational.RationalNumber;

public class MathUtil {

    private static final IntegerNumber TWO = IntegerNumber.valueOf(2);

    public static BaseNumber power(BaseNumber base, BaseNumber pow){
        RationalNumber rational = pow.tryToConvertToRational();
        IntegerNumber intPow = null;
        if(rational != null){
            intPow = rational.convertToInteger();
        }
        BaseNumber result = BaseNumber.ONE;
        if(intPow != null){   // 说明是整数幂
            while(!intPow.equals(IntegerNumber.ZERO)){
                if(intPow.isOdd()){
                    result = result.mult(base);
                }
                base = base.mult(base);
                intPow = intPow.div(TWO);
            }
            if(intPow.getSign() == IntegerNumber.NEGATIVE){  // 负指数
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

    private static final BaseNumber IMAGINARY_MARK = new BaseNumber(ComplexNumber.I);

    public static BaseNumber multiplyI(BaseNumber num){
        return num.mult(IMAGINARY_MARK);
    }

    public static BaseNumber factorial(BaseNumber num){
        RationalNumber rational = num.tryToConvertToRational();
        if(rational == null){
            throw new IllegalArgumentException(num.toString() + " is not a rational number.");
        }
        IntegerNumber integer = rational.convertToInteger();
        if(integer == null){
            throw new IllegalArgumentException("factorial can't support this number : " + num.toString());
        }

        IntegerNumber result = integer;

        while(IntegerNumber.ONE.compareTo(integer) < 0){
            integer = integer.decrease();
            result = result.mult(integer);
        }

        return BaseNumber.valueOf(result);
    }

}
