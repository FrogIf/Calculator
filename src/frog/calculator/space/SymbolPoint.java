package frog.calculator.space;

public class SymbolPoint extends AbstractPoint<String> {

    private String symbol;

    public SymbolPoint(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String intrinsic() {
        return this.symbol;
    }

    @Override
    public int getAxialValue() {
        return this.axialValue;
    }

    @Override
    public void setAxialValue(int val) {
        this.axialValue = val;
    }

    @Override
    public IPoint clone() {
        return super.clone();
    }

    @Override
    public String toString() {
        return symbol;
    }

}
