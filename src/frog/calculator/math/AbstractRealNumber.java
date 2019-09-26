package frog.calculator.math;

public abstract class AbstractRealNumber extends AbstractComplexNumber {

    protected abstract RationalNumber getRationalPart();

    protected abstract AbstractIrrationalNumber getIrrationalPart();

    protected abstract AbstractRealNumber getNext();

    public final AbstractRealNumber add(AbstractRealNumber num) {
        AbstractRealNumber next;
        if(this.getNext() == null){
            next = num.getNext();
        }else if(num.getNext() == null){
            next = this.getNext();
        }else{
            next = this.getNext().add(num.getNext());
        }

        if(this.getIrrationalPart() == null && num.getIrrationalPart() == null){
            RationalNumber rationalNumber;
            if(this.getRationalPart() == null){
                rationalNumber = num.getRationalPart();
            }else if(num.getRationalPart() == null){
                rationalNumber = this.getRationalPart();
            }else{
                rationalNumber = this.getRationalPart().add(num.getRationalPart());
            }
            return new RealNumber(rationalNumber, next);
        }else if(this.getRationalPart().equals(num.getRationalPart())){
            INumber irrational;
            if(this.getIrrationalPart() == null){
                irrational = num.getIrrationalPart();
            }else if(num.getRationalPart() == null){
                irrational = this.getIrrationalPart();
            }else{
                irrational = this.getIrrationalPart().add(num.getIrrationalPart());
            }
            if(irrational instanceof AbstractIrrationalNumber){
                return new RealNumber(this.getRationalPart(), (AbstractIrrationalNumber) irrational, next);
            }else{
                RationalNumber rationalNumber = irrational == null ? this.getRationalPart() : this.getRationalPart().mult((RationalNumber) irrational);
                return new RealNumber(rationalNumber, null, next);
            }
        }else{
            return new RealNumber(this.getRationalPart(), this.getIrrationalPart(), num);
        }
    }

    public final AbstractRealNumber sub(AbstractRealNumber num) {

        return null;
    }

    public final AbstractRealNumber mult(AbstractRealNumber number) {
        return null;
    }

    public final AbstractRealNumber div(AbstractRealNumber number) {
        return null;
    }

    @Override
    public final AbstractRealNumber getRealPart() {
        return this;
    }

    @Override
    public final AbstractRealNumber getImaginaryPart() {
        return null;
    }

}
