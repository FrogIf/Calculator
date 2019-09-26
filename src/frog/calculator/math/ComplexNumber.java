package frog.calculator.math;

/**
 * 复数
 */
public class ComplexNumber extends AbstractComplexNumber {

    private final AbstractRealNumber realPart;

    private final AbstractRealNumber imaginaryPart;

    public AbstractRealNumber getRealPart(){
        return this.realPart;
    }

    public AbstractRealNumber getImaginaryPart(){
        return this.imaginaryPart;
    }

    public ComplexNumber(AbstractRealNumber realPart, AbstractRealNumber imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    @Override
    public byte getSign() {
        return 0;
    }

    @Override
    public String toDecimal(int count) {
        return this.getRealPart().toDecimal(count) + "+" + this.getRealPart().toDecimal(count) + "i";
    }

    @Override
    public int compareTo(INumber o) {
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if(this.realPart != null){
            result.append(this.realPart.toString());
            if(this.imaginaryPart != null){
                result.append('+');
            }
        }
        if(this.imaginaryPart != null){
            result.append(this.imaginaryPart.toString());
            result.append('i');
        }
        return result.toString();
    }
}
