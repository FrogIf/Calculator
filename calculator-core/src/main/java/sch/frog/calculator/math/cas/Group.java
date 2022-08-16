package sch.frog.calculator.math.cas;

/**
 * 群:
 * 1. 二元运算
 * 2. 结合律
 * 3. 幺元
 * 4. 逆元
 */
public class Group<E extends IElement> extends HalfGroup<E> {

    // 单位元
    private final E identityElement;

    // 逆元求取
    private final IInverseElementTransformer<E> inverseElementTransformer;

    // 是否为交换群
    private final boolean commutative;

    public Group(IOperator<E> operator, E identityElement, IInverseElementTransformer<E> inverseElementTransformer) {
        super(operator);
        this.identityElement = identityElement;
        this.inverseElementTransformer = inverseElementTransformer;
        this.commutative = false;
    }

    public Group(IOperator<E> operator, E identityElement, IInverseElementTransformer<E> inverseElementTransformer, boolean commutative) {
        super(operator);
        this.identityElement = identityElement;
        this.inverseElementTransformer = inverseElementTransformer;
        this.commutative = commutative;
    }

    /**
     * 获取指定元在该群中的逆元
     */
    public E getInverseElement(E source){
        return inverseElementTransformer.transformToInverse(source);
    }

    /**
     * 获取该群的单位元
     */
    public E getIdentityElement(){
        return this.identityElement;
    }

    /**
     * 是否是交换群
     * @return true - 是; false - 不是
     */
    public boolean isCommutative(){
        return this.commutative;
    }
    
}
