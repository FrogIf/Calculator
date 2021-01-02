package frog.math.number;

/**
 * 舍入方式
 */
public enum NumberRoundingMode {
    /**
     * 向远离0的方向舍入
     */
    UP, 
    /**
     * 向接近0的方向舍入
     */
    DOWN,
    /**
     * 向上取整
     */
    CEILING,
    /**
     * 向下取整
     */
    FLOOR,
    /**
     * 四舍五入
     */
    HALF_UP,
    /**
     * 五舍六入
     */
    HALF_DOWN,
    /**
     * 银行家舍入
     * 1. 四舍六入
     * 2. 五后非0, 进一
     * 3. 五后为0:
     *      -- 五前为偶, 舍去
     *      -- 五前为奇, 进一
     */
    HALF_EVEN;
}

