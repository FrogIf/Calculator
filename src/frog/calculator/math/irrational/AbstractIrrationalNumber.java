package frog.calculator.math.irrational;

import frog.calculator.math.INumber;
import frog.calculator.math.real.PolynomialNumber;

public abstract class AbstractIrrationalNumber implements INumber {

    /*
     * 这里的tryAdd, trySub, tryMult, tryDiv均是尝试运算, 如果运算失败, 则返回null
     */

    public abstract PolynomialNumber tryAdd(AbstractIrrationalNumber num);

    public abstract PolynomialNumber trySub(AbstractIrrationalNumber num);

    public abstract PolynomialNumber tryMult(AbstractIrrationalNumber num);

    public abstract PolynomialNumber tryDiv(AbstractIrrationalNumber num);

}
