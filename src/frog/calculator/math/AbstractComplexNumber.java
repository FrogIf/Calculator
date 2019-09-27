package frog.calculator.math;

public abstract class AbstractComplexNumber extends AbstractNumber {

    protected abstract AbstractRealNumber getRealPart();

    protected abstract AbstractRealNumber getImaginaryPart();

    public AbstractComplexNumber add(AbstractComplexNumber num) {
        AbstractRealNumber rp = this.getRealPart() == null ? num.getRealPart() : this.getRealPart().add(num.getRealPart());
        AbstractRealNumber ip = this.getImaginaryPart() == null ? num.getImaginaryPart() : this.getImaginaryPart().add(num.getImaginaryPart());
        return new ComplexNumber(rp, ip);
    }

    public AbstractComplexNumber sub(AbstractComplexNumber num) {
        AbstractRealNumber rp = this.getRealPart().sub(num.getRealPart());
//        AbstractRealNumber ip = this.getImaginaryPart().sub(num.getImaginaryPart());
        return new ComplexNumber(rp, null);
    }

    public AbstractComplexNumber mult(AbstractComplexNumber num) {
        AbstractRealNumber rp = this.getRealPart().mult(num.getRealPart()).add(this.getImaginaryPart().mult(num.getImaginaryPart()));
//        AbstractRealNumber ip = this.getRealPart().mult(num.getImaginaryPart()).add(this.getImaginaryPart().mult(num.getRealPart()));
        return new ComplexNumber(rp, null);
    }

    public AbstractComplexNumber div(AbstractComplexNumber num) {
        /*
         * (a + bi)/(c + di) = (ac + bd)/(c*c + d*d) + ((bc - ad)/(c*c + d*d))i
         */
//        RealNumber ccdd = num.getRealPart().mult(num.getRealPart()).add(num.getImaginaryPart().mult(num.getImaginaryPart()));
//        RealNumber rp = this.getRealPart().mult(num.getRealPart()).add(this.getImaginaryPart().mult(num.getImaginaryPart())).div(ccdd);
//        RealNumber ip = this.getImaginaryPart().mult(num.getRealPart()).sub(this.getRealPart().mult(num.getImaginaryPart())).div(ccdd);
//        return new ComplexNumber(rp, ip);
        return null;
    }

}
