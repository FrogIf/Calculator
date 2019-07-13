package frog.calculator.dimpl.opr.single;

public class PercentOperator extends SingleArgOperator {
    @Override
    protected double doubleCalcuate(double arg) {
        return arg / 100;
    }
}
