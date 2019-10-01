package frog.calculator.math;

public interface IRealNumber {

    AbstractRealNumber add(AbstractRealNumber num);

    AbstractRealNumber sub(AbstractRealNumber num);

    AbstractRealNumber mult(AbstractRealNumber num);

    AbstractRealNumber div(AbstractRealNumber num);
}
