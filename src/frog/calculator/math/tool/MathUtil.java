package frog.calculator.math.tool;

import frog.calculator.math.number.BaseNumber;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.math.number.IntegerNumber;
import frog.calculator.math.number.RationalNumber;

public class MathUtil {

    private static final IntegerNumber TWO = IntegerNumber.valueOf(2);

    /**
     * 幂计算
     * @param base 底数
     * @param pow 指数
     * @return 结果
     */
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

    /**
     * 乘以虚数单位
     * @param num 待乘的数
     * @return 相乘后的结果
     */
    public static BaseNumber multiplyI(BaseNumber num){
        return num.mult(IMAGINARY_MARK);
    }

    /**
     * 阶乘计算
     * @param num 待计算的数
     * @return 计算结果
     */
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
