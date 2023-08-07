package sch.frog.calculator.facade;

import sch.frog.calculator.number.ComplexNumber;
import sch.frog.calculator.number.IBaseNumber;
import sch.frog.calculator.number.IntegerNumber;
import sch.frog.calculator.number.NumberRoundingMode;
import sch.frog.calculator.number.RationalNumber;

public class NumberMode {
    
    private final int scale;

    private final Mode mode;

    public NumberMode(Mode mode, int scale){
        this.scale = scale;
        this.mode = mode;
    }

    public int getScale(){
        return this.scale;
    }

    public Mode getMode(){
        return this.mode;
    }

    public enum Mode{
        NONE((number, scale) -> number.toString()),
        UP((number, scale) -> number.decimal(scale, NumberRoundingMode.UP)),
        DOWN((number, scale) -> number.decimal(scale, NumberRoundingMode.DOWN)),
        HALF_UP((number, scale) -> number.decimal(scale, NumberRoundingMode.HALF_UP)),
        HALF_DOWN((number, scale) -> number.decimal(scale, NumberRoundingMode.HALF_DOWN)),
        HALF_EVEN((number, scale) -> number.decimal(scale, NumberRoundingMode.HALF_EVEN)),
        CEILING((number, scale) -> number.decimal(scale, NumberRoundingMode.CEILING)),
        FLOOR((number, scale) -> number.decimal(scale, NumberRoundingMode.FLOOR)),
        SCIENTIFIC((number, scale) -> number.scientificNotation(scale, NumberRoundingMode.HALF_UP)),
        PLAIN((number, scale) -> {
            if(number instanceof ComplexNumber){
                RationalNumber num = ((ComplexNumber)number).toRational();
                if(num == null){
                    return number.toString();
                }else{
                    return toPlainStringForRationalNumber(num, scale);
                }
            }else if(number instanceof RationalNumber){
                return toPlainStringForRationalNumber((RationalNumber)number, scale);
            }else if(number instanceof IntegerNumber){
                return ((IntegerNumber)number).toPlainString();
            }
            return number.toString();
        });

        private final IModePolicy policy;

        private Mode(IModePolicy policy){
            this.policy = policy;
        }

        public String hanle(IBaseNumber number, int scale){
            return policy.handle(number, scale);
        }
    }

    private interface IModePolicy{
        String handle(IBaseNumber number, int scale);
    }

    private static String toPlainStringForRationalNumber(RationalNumber num, int scale){
        if(num == null){
            return null;
        }else{
            IntegerNumber intNum = num.toInteger();
            if(intNum == null){
                return num.toPlainString(scale, NumberRoundingMode.HALF_UP);
            }else{
                return intNum.toPlainString();
            }
        }
    }

}
