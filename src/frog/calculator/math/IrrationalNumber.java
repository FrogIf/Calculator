package frog.calculator.math;

public interface IrrationalNumber {

    AbstractRealNumber tryAdd(AbstractIrrationalNumber num);

    AbstractRealNumber trySub(AbstractIrrationalNumber num);

    AbstractRealNumber tryMult(AbstractIrrationalNumber num);

    AbstractRealNumber tryDiv(AbstractIrrationalNumber num);

}
