package sch.frog.calculator.math.number;

/**
 * 实数
 */
public class RealNumber extends AbstractBaseNumber implements Comparable<RealNumber> {

    public static final RealNumber ZERO = new RealNumber(IntegerNumber.ZERO);

    public static final RealNumber ONE = new RealNumber(IntegerNumber.ONE);

    private final IrrationalNumber irrationalNumber;

    private final RationalNumber rationalNumber;

    public RealNumber(IrrationalNumber irrationalNumber) {
        if (irrationalNumber == null) {
            throw new IllegalArgumentException("irrational is null");
        }
        this.irrationalNumber = irrationalNumber;
        this.rationalNumber = null;
    }

    public RealNumber(RationalNumber rationalNumber) {
        if (rationalNumber == null) {
            throw new IllegalArgumentException("rational is null");
        }
        this.irrationalNumber = null;
        this.rationalNumber = rationalNumber;
    }

    public RealNumber(IntegerNumber integerNumber) {
        if(integerNumber == null){
            throw new IllegalArgumentException("integer is null");
        }
        this.irrationalNumber = null;
        this.rationalNumber = new RationalNumber(integerNumber);
    }

    public RealNumber add(RealNumber num) {
        if (this.rationalNumber != null && num.rationalNumber != null) {  // 两个有理数的运算
            return new RealNumber(this.rationalNumber.add(num.rationalNumber));
        } else if (this.irrationalNumber != null && num.irrationalNumber != null) {
            return this.irrationalNumber.add(num.irrationalNumber);
        } else {
            // 一个有理数, 一个无理数
            RationalNumber rationalNumber;
            IrrationalNumber irrationalNumber;
            if (this.irrationalNumber != null) {
                irrationalNumber = this.irrationalNumber;
                rationalNumber = num.rationalNumber;
            } else {
                irrationalNumber = num.irrationalNumber;
                rationalNumber = this.rationalNumber;
            }
            assert irrationalNumber != null;
            return irrationalNumber.add(rationalNumber);
        }
    }

    public RealNumber sub(RealNumber num) {
        if (this.rationalNumber != null && num.rationalNumber != null) {
            return new RealNumber(this.rationalNumber.sub(num.rationalNumber));
        } else if (this.irrationalNumber != null && num.irrationalNumber != null) {
            return this.irrationalNumber.sub(num.irrationalNumber);
        } else {
            if (this.irrationalNumber != null) {
                return this.irrationalNumber.sub(num.rationalNumber);
            } else {
                assert this.rationalNumber != null;
                assert num.irrationalNumber != null;
                return num.irrationalNumber.add(this.rationalNumber.not());
            }
        }
    }

    public RealNumber mult(RealNumber num) {
        if (this.rationalNumber != null && num.rationalNumber != null) {
            return new RealNumber(this.rationalNumber.mult(num.rationalNumber));
        } else if (this.irrationalNumber != null && num.irrationalNumber != null) {
            return this.irrationalNumber.mult(num.irrationalNumber);
        } else {
            RationalNumber rationalNumber;
            IrrationalNumber irrationalNumber;
            if (this.irrationalNumber != null) {
                irrationalNumber = this.irrationalNumber;
                rationalNumber = num.rationalNumber;
            } else {
                irrationalNumber = num.irrationalNumber;
                rationalNumber = this.rationalNumber;
            }
            assert irrationalNumber != null;
            return irrationalNumber.mult(rationalNumber);
        }
    }

    public RealNumber div(RealNumber num) {
        if (this.rationalNumber != null && num.rationalNumber != null) {
            return new RealNumber(this.rationalNumber.div(num.rationalNumber));
        } else if (this.irrationalNumber != null && num.irrationalNumber != null) {
            return this.irrationalNumber.div(num.irrationalNumber);
        } else {
            if (this.irrationalNumber != null) {
                return this.irrationalNumber.div(num.rationalNumber);
            } else {
                assert num.irrationalNumber != null;
                assert this.rationalNumber != null;
                return num.irrationalNumber.mult(this.rationalNumber.upend());
            }
        }
    }

    public RealNumber not(){
        if(this.rationalNumber != null){
            return new RealNumber(this.rationalNumber.not());
        }else{
            assert this.irrationalNumber != null;
            return new RealNumber(this.irrationalNumber.not());
        }
    }

    @Override
    public String toString() {
        if(this.rationalNumber != null){
            return this.rationalNumber.toString();
        }else{
            assert this.irrationalNumber != null;
            return this.irrationalNumber.toString();
        }
    }

    /**
     * 转换为整数, 如果转换失败, 返回null
     */
    public IntegerNumber toInteger() {
        if(this.rationalNumber != null){
            return this.rationalNumber.toInteger();
        }else{
            return null;
        }
    }

    /**
     * 转换为有理数, 如果转换失败, 返回null
     */
    public RationalNumber toRational() {
        if(this.rationalNumber != null){
            return this.rationalNumber;
        }else{
            return null;
        }
    }

    @Override
    public int compareTo(RealNumber that) {
        if(that.irrationalNumber == null && this.irrationalNumber == null){ // 两者都是有理数
            return this.rationalNumber.compareTo(that.rationalNumber);
        }else if(that.irrationalNumber != null && that.irrationalNumber != null){
            // TODO 无理数之间的比较
        }else{
            // TODO 无理数与有理数之间的比较
        }
        throw new UnsupportedOperationException("can't compart for the moment.");
    }

    @Override
    public boolean equals(Object that) {
        if(this == that){ return true; }
        if(that instanceof RealNumber){
            return this.compareTo((RealNumber) that) == 0;
        }else{
            return false;
        }
    }

    @Override
    public String decimal(int scale, NumberRoundingMode roundingMode, boolean fillWithZero) {
        return null;
    }

    @Override
    public String scientificNotation(int scale, NumberRoundingMode roundingMode, boolean fillWithZero) {
        return null;
    }
}
