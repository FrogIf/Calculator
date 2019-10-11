package frog.calculator.math.irrational;

import frog.calculator.math.INumber;
import frog.calculator.math.rational.RationalNumber;
import frog.calculator.math.real.FactorNumber;

public abstract class AbstractIrrationalNumber implements INumber {

    /*
     * 这里的try均是尝试运算, 如果运算失败, 则返回null
     */

    public abstract FactorNumber tryAdd(AbstractIrrationalNumber num);

    public abstract FactorNumber trySub(AbstractIrrationalNumber num);

    public abstract FactorNumber mult(AbstractIrrationalNumber num);

    public abstract FactorNumber tryDiv(AbstractIrrationalNumber num);

    public abstract AbstractIrrationalNumber tryAbsorb(RationalNumber rational);
}
