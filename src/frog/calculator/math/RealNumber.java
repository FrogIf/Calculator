package frog.calculator.math;

public class RealNumber extends ComplexNumber implements IRealNumber {

    /*
     * 一个实数 = 有理数 * 无理数 + 另一个实数
     * 也就是一个多项式
     */

    private RationalNumber rationalPart;

    private IrrationalNumber irrationalPart;

    private RealNumber next;

    protected RealNumber(){ }

    public RealNumber(RealNumber realPart) {

    }

    @Override
    public byte getSign() {
        return 0;
    }

    public RealNumber add(RealNumber num) {
        return null;
    }

    public RealNumber sub(RealNumber number) {
        return null;
    }

    public RealNumber mult(RealNumber number) {
        return null;
    }

    public RealNumber div(RealNumber number) {
        return null;
    }

    @Override
    public RealNumber getRealPart() {
        return this;
    }

    @Override
    public RealNumber getImaginaryPart() {
        return IntegerNumber.ZERO;
    }

    @Override
    public String toDecimal(int count) {
        return null;
    }

    @Override
    public int compareTo(INumber o) {
        return 0;
    }

    public RationalNumber getRationalPart() {
        return rationalPart;
    }

    public IrrationalNumber getIrrationalPart() {
        return irrationalPart;
    }

    public RealNumber getNext() {
        return next;
    }
}
