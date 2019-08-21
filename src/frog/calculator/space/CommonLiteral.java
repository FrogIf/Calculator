package frog.calculator.space;

public class CommonLiteral implements ILiteral {

    private String realValue;

    public CommonLiteral(String value) {
        this.realValue = value;
    }

    @Override
    public String value() {
        return this.realValue;
    }

    @Override
    public String toString() {
        return realValue;
    }
}
