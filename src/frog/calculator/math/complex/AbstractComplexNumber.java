package frog.calculator.math.complex;

public abstract class AbstractComplexNumber {

//    protected abstract AbstractRealNumber getRealPart();
//
//    protected abstract AbstractRealNumber getImaginaryPart();
//
//    public abstract AbstractComplexNumber not();
//
//    public INumber add(AbstractComplexNumber num) {
//        AbstractRealNumber rp = this.getRealPart() == null ? num.getRealPart() : this.getRealPart().add(num.getRealPart());
//        AbstractRealNumber ip = this.getImaginaryPart() == null ? num.getImaginaryPart() : this.getImaginaryPart().add(num.getImaginaryPart());
//        return this.dealResult(rp, ip);
//    }
//
//    public INumber sub(AbstractComplexNumber num) {
//        AbstractRealNumber rp = this.getRealPart() == null ? num.getRealPart() : this.getRealPart().sub(num.getRealPart());
//        AbstractRealNumber ip = this.getImaginaryPart() == null ? num.getImaginaryPart() : this.getImaginaryPart().sub(num.getImaginaryPart());
//        return this.dealResult(rp, ip);
//    }
//
//    public INumber mult(AbstractComplexNumber num) {
//        AbstractRealNumber rp = this.getRealPart().mult(num.getRealPart()).add(this.getImaginaryPart().mult(num.getImaginaryPart()));
//        AbstractRealNumber ip = this.getRealPart().mult(num.getImaginaryPart()).add(this.getImaginaryPart().mult(num.getRealPart()));
//        return this.dealResult(rp, ip);
//    }
//
//    public INumber div(AbstractComplexNumber num) {
//        /*
//         * (a + bi)/(c + di) = (ac + bd)/(c*c + d*d) + ((bc - ad)/(c*c + d*d))i
//         */
//        AbstractRealNumber cd = num.getRealPart().mult(num.getRealPart()).add(num.getImaginaryPart().mult(num.getImaginaryPart()));
//        AbstractRealNumber rp = this.getRealPart().mult(num.getRealPart()).add(this.getImaginaryPart().mult(num.getImaginaryPart())).div(cd);
//        AbstractRealNumber ip = this.getImaginaryPart().mult(num.getRealPart()).sub(this.getRealPart().mult(num.getImaginaryPart())).div(cd);
//        return this.dealResult(rp, ip);
//    }
//
//    private AbstractNumber dealResult(AbstractRealNumber rp, AbstractRealNumber ip){
//        if(ip == null){
//            return rp;
//        }else if(rp == null){
//            return ip;
//        }else{
//            return new ComplexNumber(rp, ip);
//        }
//    }

}
