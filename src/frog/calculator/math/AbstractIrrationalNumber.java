package frog.calculator.math;

public abstract class AbstractIrrationalNumber extends AbstractRealNumber{

    /*
     * 一下运算结果一定是实数, 因为加减乘除在实数域范围内闭包
     */

    public abstract AbstractRealNumber tryAdd(AbstractIrrationalNumber num);

    public abstract AbstractRealNumber trySub(AbstractIrrationalNumber num);

    public abstract AbstractRealNumber tryMult(AbstractIrrationalNumber num);

    public abstract AbstractRealNumber tryDiv(AbstractIrrationalNumber num);

}
