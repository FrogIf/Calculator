package frog.calculator.math.number;

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

    @Override
    public int compareTo(RealNumber o) {
        // TODO 比较大小
        return 0;
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
}
