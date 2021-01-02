package frog.math.operation;

/**
 * 求逆
 */
public interface Inv {
    /**
     * 求逆元
     * @return 逆元
     */
    Inv inv();
    
    /**
     * 单位元(幺元)
     * @return 单位元
     */
    Inv getIdentityElement();
}
