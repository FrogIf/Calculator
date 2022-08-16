package sch.frog.calculator.math.number;

public class NumberUtil {

    private NumberUtil(){
        // do nothing
    }

    private static final IntegerNumber TWO = IntegerNumber.valueOf(2);

    /**
     * 幂计算
     * @param base 底数
     * @param pow 指数
     * @return 结果
     */
    public static ComplexNumber power(ComplexNumber base, ComplexNumber pow){
        IntegerNumber intPow = pow.toInteger();
        if(intPow != null){ // 整数指数的幂运算
            ComplexNumber result = ComplexNumber.ONE;
            IntegerNumber positiveIntPow = intPow.abs();
            while(!positiveIntPow.equals(IntegerNumber.ZERO)){
                if(positiveIntPow.isOdd()){
                    result = result.mult(base);
                }
                base = base.mult(base);
                positiveIntPow = positiveIntPow.div(TWO);
            }
            if(intPow.getSign() == NumberSign.NEGATIVE){  // 负指数
                return ComplexNumber.ONE.div(result);
            }else{  // 正指数
                return result;
            }
        }else{
            // TODO 暂时不支持
            throw new UnsupportedOperationException("power operation is unsupported for [" + pow.toString() + "] for the moment");
        }
    }

    /**
     * 阶乘计算
     * @param num 待计算的数
     * @return 计算结果
     */
    public static ComplexNumber factorial(ComplexNumber num){
        IntegerNumber intNum = num.toInteger();
        if(intNum == null){
            throw new IllegalArgumentException(num.toString() + " is not a rational number.");
        }

        IntegerNumber result = IntegerNumber.ONE;
        while(IntegerNumber.ONE.compareTo(intNum) < 0){
            result = result.mult(intNum);
            intNum = intNum.sub(1);
        }

        return new ComplexNumber(result);
    }

}
