package frog.calculator.math.number;

public abstract class AbstractIrrationalNumber implements INumber {

    /*
     * 这里的try均是尝试运算, 如果运算失败, 则返回null
     */

    public FactorNumber tryAdd(AbstractIrrationalNumber num){ return null; }

    public FactorNumber trySub(AbstractIrrationalNumber num){ return null; }

    public FactorNumber tryDiv(AbstractIrrationalNumber num){ return null; }

    public abstract FactorNumber mult(AbstractIrrationalNumber num);

    /**
     * 将一个有理数吸收
     * @param rational
     * @return
     */
    public abstract AbstractIrrationalNumber tryAbsorb(RationalNumber rational);

    public abstract RationalNumber tryConvertToRational();
}
