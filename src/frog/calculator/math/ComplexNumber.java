package frog.calculator.math;

/**
 * 复数
 */
public class ComplexNumber extends AbstractNumber implements IComplexNumber{

    private final RealNumber realPart;

    private final RealNumber imaginaryPart;

    public RealNumber getRealPart(){
        return this.realPart;
    }

    public RealNumber getImaginaryPart(){
        return this.imaginaryPart;
    }

    protected ComplexNumber(){
        this.realPart = null;
        this.imaginaryPart = null;
    }

    public ComplexNumber(RealNumber realPart, RealNumber imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    @Override
    public byte getSign() {
        return 0;
    }

    public IComplexNumber add(IComplexNumber num) {
        RealNumber rp = this.getRealPart().add(num.getRealPart());
        RealNumber ip = this.getImaginaryPart().add(num.getImaginaryPart());
        return new ComplexNumber(rp, ip);
    }

    public IComplexNumber sub(IComplexNumber num) {
        RealNumber rp = this.getRealPart().sub(num.getRealPart());
        RealNumber ip = this.getImaginaryPart().sub(num.getImaginaryPart());
        return new ComplexNumber(rp, ip);
    }

    public IComplexNumber mult(IComplexNumber num) {
        RealNumber rp = this.getRealPart().mult(num.getRealPart()).add(this.getImaginaryPart().mult(num.getImaginaryPart()));
        RealNumber ip = this.getRealPart().mult(num.getImaginaryPart()).add(this.getImaginaryPart().mult(num.getRealPart()));
        return new ComplexNumber(rp, ip);
    }

    public IComplexNumber div(IComplexNumber num) {
        /*
         * (a + bi)/(c + di) = (ac + bd)/(c*c + d*d) + ((bc - ad)/(c*c + d*d))i
         */
        RealNumber ccdd = num.getRealPart().mult(num.getRealPart()).add(num.getImaginaryPart().mult(num.getImaginaryPart()));
        RealNumber rp = this.getRealPart().mult(num.getRealPart()).add(this.getImaginaryPart().mult(num.getImaginaryPart())).div(ccdd);
        RealNumber ip = this.getImaginaryPart().mult(num.getRealPart()).sub(this.getRealPart().mult(num.getImaginaryPart())).div(ccdd);
        return new ComplexNumber(rp, ip);
    }

    @Override
    public String toDecimal(int count) {
        return this.getRealPart().toDecimal(count) + "+" + this.getRealPart().toDecimal(count) + "i";
    }

    @Override
    public int compareTo(INumber o) {
        return 0;
    }
}
