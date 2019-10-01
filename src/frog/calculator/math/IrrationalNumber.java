package frog.calculator.math;

public interface IrrationalNumber {

    AbstractRealNumber tryAdd(AbstractIrrationalNumber num);

    AbstractRealNumber trySub(AbstractIrrationalNumber num);

    AbstractRealNumber tryMult(AbstractIrrationalNumber num);

    AbstractRealNumber tryDiv(AbstractIrrationalNumber num);

    // 尝试提取无理数中的有理数因子
//    RationalNumber tryGetRationalFactor();
//
//    AbstractIrrationalNumber tryGetIrrationalFactor();
}
