package sch.frog.calculator.math.cas;

/**
 * 逆元求取
 */
public interface IInverseElementTransformer<E extends IElement> {

    E transformToInverse(E source);
    
}
