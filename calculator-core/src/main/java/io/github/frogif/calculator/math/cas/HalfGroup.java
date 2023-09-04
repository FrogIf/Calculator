package io.github.frogif.calculator.math.cas;

/**
 * 半群
 * 1. 二元运算
 * 2. 结合律
 */
public class HalfGroup<E extends IElement> {
    
    private final IOperator<E> operator;

    public HalfGroup(IOperator<E> operator){
        this.operator = operator;
    }

    public E operate(E left, E right){
        return operator.operate(left, right);
    }

}
