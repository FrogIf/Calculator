package sch.frog.calculator.facade;

import sch.frog.calculator.math.number.IBaseNumber;
import sch.frog.calculator.math.number.NumberRoundingMode;

public class NumberMode {
    
    private final int scale;

    private Mode mode;

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
        CELING((number, scale) -> number.decimal(scale, NumberRoundingMode.CEILING)),
        FLOOR((number, scale) -> number.decimal(scale, NumberRoundingMode.FLOOR)),
        SCIENFIFIC((number, scale) -> number.scientificNotation(scale, NumberRoundingMode.HALF_UP));

        private IModePolicy policy;

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

}
