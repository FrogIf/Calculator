package frog.calculator.math;

public abstract class AbstractIrrationalNumber extends AbstractRealNumber{

    public abstract INumber add(AbstractIrrationalNumber num);

    public abstract INumber sub(AbstractIrrationalNumber num);

    public abstract INumber mult(AbstractIrrationalNumber num);

    public abstract INumber div(AbstractIrrationalNumber num);

}
