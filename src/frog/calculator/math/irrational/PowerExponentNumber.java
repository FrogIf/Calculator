package frog.calculator.math.irrational;

import frog.calculator.math.INumber;
import frog.calculator.math.rational.RationalNumber;
import frog.calculator.math.real.FactorNumber;

/**
 * 幂指数 z = x ^ y
 */
public class PowerExponentNumber extends AbstractIrrationalNumber {

    @Override
    public FactorNumber tryAdd(AbstractIrrationalNumber num) {
        return null;
    }

    @Override
    public FactorNumber trySub(AbstractIrrationalNumber num) {
        return null;
    }

    @Override
    public FactorNumber tryDiv(AbstractIrrationalNumber num) {
        return null;
    }

    @Override
    public FactorNumber mult(AbstractIrrationalNumber num) {
        return null;
    }

    @Override
    public AbstractIrrationalNumber tryAbsorb(RationalNumber rational) {
        return null;
    }

    @Override
    public INumber not() {
        return null;
    }

    @Override
    public String toDecimal(int count) {
        return null;
    }
}
