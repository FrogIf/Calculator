package frog.calculator.math.irrational;

import frog.calculator.math.INumber;
import frog.calculator.math.rational.RationalNumber;
import frog.calculator.math.real.FactorNumber;

/**
 * 幂指数 z = x ^ y
 * 指数是分数
 */
public class PowerNumber extends AbstractIrrationalNumber {

    private RationalNumber exponent;   // 指数

    private RationalNumber base;    // 底数

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
    public RationalNumber tryConvertToRational() {
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
