package frog.calculator.math;

public class RealNumber extends AbstractRealNumber {

    /*
     * 一个实数 = 有理数 * 无理数 + 另一个实数
     * 也就是一个多项式
     */

    private final RationalNumber rationalPart;

    private final AbstractIrrationalNumber irrationalPart;

    private final AbstractRealNumber next;

    protected RealNumber(){
        this.rationalPart = null;
        this.irrationalPart = null;
        this.next = null;
    }

    RealNumber(RationalNumber rationalPart, AbstractRealNumber next) {
        this.rationalPart = rationalPart;
        this.next = next;
        this.irrationalPart = null;
    }

    RealNumber(RationalNumber rationalPart, AbstractIrrationalNumber irrationalNumber, AbstractRealNumber next) {
        this.rationalPart = rationalPart;
        this.irrationalPart = irrationalNumber;
        this.next = next;
    }

    @Override
    public byte getSign() {
        return 0;
    }

    @Override
    public String toDecimal(int count) {
        return null;
    }

    @Override
    public int compareTo(INumber o) {
        return 0;
    }

    protected RationalNumber getRationalPart() {
        return rationalPart;
    }

    protected AbstractIrrationalNumber getIrrationalPart() {
        return irrationalPart;
    }

    protected AbstractRealNumber getNext() {
        return this.next;
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
        if(this.next != null){
            if(result.length() > 0) { result.append('+'); }
            result.append(this.next.toString());
        }
        return result.toString();
    }
}
