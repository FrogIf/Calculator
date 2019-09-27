package frog.calculator.math;

public class RealNumber extends AbstractRealNumber {

    /*
     * 一个实数 = 有理数 * 无理数
     * 也就是一个多项式
     */

    private final RationalNumber rationalPart;

    private final AbstractIrrationalNumber irrationalPart;

    RealNumber(RationalNumber rationalPart, AbstractIrrationalNumber irrationalNumber) {
        this.rationalPart = rationalPart;
        this.irrationalPart = irrationalNumber;
    }

    protected RealNumber() {
        this.rationalPart = null;
        this.irrationalPart = null;
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

    public AbstractIrrationalNumber getIrrationalPart() {
        return irrationalPart;
    }

    @Override
    public AbstractRealNumber add(AbstractRealNumber num) {
        return PolynomialNumber.createPolynomial(this).add(num);
    }

    @Override
    public AbstractRealNumber sub(AbstractRealNumber num) {
        return null;
    }

    @Override
    public AbstractRealNumber mult(AbstractRealNumber num) {
        return null;
    }

    @Override
    public AbstractRealNumber div(AbstractRealNumber num) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if(this.rationalPart != null){
            result.append(this.rationalPart.toString());
        }
        if(this.irrationalPart != null){
            if(result.length() > 0) { result.append('*'); }
            result.append(this.irrationalPart.toString());
        }
        return result.toString();
    }
}
