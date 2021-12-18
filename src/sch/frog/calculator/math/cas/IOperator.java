package sch.frog.calculator.math.cas;

/**
 * 二元运算
 */
public interface IOperator<E extends IElement> {
    
    public E operate(E left, E right);

}
