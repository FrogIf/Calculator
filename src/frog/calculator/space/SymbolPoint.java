package frog.calculator.space;

public class SymbolPoint extends AbstractPoint<String> {

    private String symbol;

    public SymbolPoint(String symbol, ICoordinate coordinate) {
        this.symbol = symbol;
        this.coordinate = coordinate;
    }

    @Override
    public String intrinsic() {
        return this.symbol;
    }

    @Override
    public ICoordinate getCoordinate() {
        return this.coordinate;
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
