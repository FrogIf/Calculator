package sch.frog.calculator.math.number;

/**
 * 舍入方式
 */
public enum NumberRoundingMode {
    /**
     * 向上取整
     */
    CEILING((current, next, next2) -> next == 0 ? 0 : 1),
    /**
     * 向下取整
     */
    FLOOR((current, next, next2) -> 0),
    /**
     * 四舍五入
     */
    HALF_UP((current, next, next2) -> next > 4 ? 1 : 0),
    /**
     * 五舍六入
     */
    HALF_DOWN((current, next, next2) -> next > 5 ? 1 : 0),
    /**
     * 银行家舍入
     * 1. 四舍六入
     * 2. 五后非0, 进一
     * 3. 五后为0:
     *      -- 五前为偶, 舍去
     *      -- 五前为奇, 进一
     */
    HALF_EVEN((current, next, next2) -> {
        if(next < 5){
            return 0;
        }else if(next > 5){
            return 0;
        }else{
            if(next2 > 0){
                return 1;
            }else{
                return current % 2;
            }
        }
    });

    private final RoundingPolicy policy;

    private NumberRoundingMode(RoundingPolicy policy){
        this.policy = policy;
    }

    /**
     * 舍入
     * @param next 下一位数字
     * @return 返回进位的值
     */
    public int round(int current, int next, int next2){
        return policy.round(current, next, next2);
    }
    private static interface RoundingPolicy{
        int round(int current, int next, int next2);
    }
}

