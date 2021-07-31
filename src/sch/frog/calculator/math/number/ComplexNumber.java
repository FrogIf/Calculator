package sch.frog.calculator.math.number;

/**
 * 复数
 */
public final class ComplexNumber extends AbstractBaseNumber implements Comparable<ComplexNumber> {

    public static final ComplexNumber I = new ComplexNumber(null, RealNumber.ONE);

    public static final ComplexNumber ONE = new ComplexNumber(RealNumber.ONE, null);

    public static final ComplexNumber ZERO = new ComplexNumber(RealNumber.ZERO, null);

    private final RealNumber realPart;

    private final RealNumber imaginaryPart;

    public ComplexNumber(RealNumber realPart, RealNumber imaginaryPart) {
        if(realPart == null && imaginaryPart == null){
            throw new IllegalArgumentException("can't init a empty object.");
        }
        this.realPart = realPart == null ? RealNumber.ZERO : realPart;
        this.imaginaryPart = imaginaryPart == null ? RealNumber.ZERO : imaginaryPart;
    }

    public ComplexNumber(RealNumber realPart){
        if(realPart == null){
            throw new IllegalArgumentException("can't init a empty object.");
        }
        this.realPart = realPart;
        this.imaginaryPart = RealNumber.ZERO;
    }

    public ComplexNumber(IntegerNumber realPart){
        if(realPart == null){
            throw new IllegalArgumentException("can't init a empty object.");
        }
        this.realPart = new RealNumber(realPart);
        this.imaginaryPart = RealNumber.ZERO;
    }

    public ComplexNumber(RationalNumber realPart){
        if(realPart == null){
            throw new IllegalArgumentException("can't init a empty object.");
        }
        this.realPart = new RealNumber(realPart);
        this.imaginaryPart = RealNumber.ZERO;
    }

    public ComplexNumber add(ComplexNumber num){
        return new ComplexNumber(this.realPart.add(num.realPart), this.imaginaryPart.add(num.imaginaryPart));
    }

    public ComplexNumber sub(ComplexNumber num){
        return new ComplexNumber(this.realPart.sub(num.realPart), this.imaginaryPart.sub(num.imaginaryPart));
    }

    public ComplexNumber mult(ComplexNumber num){
        /*
         * (a + bi) * (c + di) = ac + bd + (ad + bc)i
         */
        return new ComplexNumber(this.realPart.mult(num.realPart).sub(this.imaginaryPart.mult(num.imaginaryPart)),
                this.realPart.mult(num.imaginaryPart).add(this.imaginaryPart.mult(num.realPart)));
    }

    public ComplexNumber div(ComplexNumber num){
        /*
         * (a + bi)/(c + di) = (ac + bd)/(c*c + d*d) + ((bc - ad)/(c*c + d*d))i
         */
        RealNumber cd = num.realPart.mult(num.realPart).add(num.imaginaryPart.mult(num.imaginaryPart));
        RealNumber rp = this.realPart.mult(num.realPart).add(this.imaginaryPart.mult(num.imaginaryPart)).div(cd);
        RealNumber ip = this.imaginaryPart.mult(num.realPart).sub(this.realPart.mult(num.imaginaryPart)).div(cd);
        return new ComplexNumber(rp, ip);
    }

    public ComplexNumber not() {
        return new ComplexNumber(realPart.not(), imaginaryPart.not());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean ipZero = RealNumber.ZERO.equals(this.imaginaryPart);
        if(ipZero || !RealNumber.ZERO.equals(this.realPart)){
            sb.append(this.realPart.toString());
        }
        if(!ipZero){
            if(sb.length() > 0){
                sb.append('+');
            }
            sb.append(this.imaginaryPart.toString());
            sb.append('i');
        }
        return sb.toString();
    }

    public RealNumber getRealPart() {
        return realPart;
    }

    public RealNumber getImaginaryPart() {
        return imaginaryPart;
    }

    /**
     * 该比较实际比较的是两个复数的模
     * @param o 比较的对象
     * @return 比较结果, 0 - 相等, 大于0则this大, 小于0则that大
     */
    @Override
    public int compareTo(ComplexNumber o) {
        // TODO 复数的比较
        return 0;
    }

    /**
     * 转换为IntegerNumber, 如果转换失败, 返回null
     */
    public IntegerNumber toInteger(){
        if(!RealNumber.ZERO.equals(this.imaginaryPart)){
            return null;
        }else{
            return this.realPart.toInteger();
        }
    }

    /**
     * 转换为整数, 如果转换失败, 则返回null
     */
    public RationalNumber toRational(){
        if(!RealNumber.ZERO.equals(this.imaginaryPart)){
            return null;
        }else{
            return this.realPart.toRational();
        }
    }
}
