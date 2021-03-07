package frog.calculator.math.number;

public abstract class AbstractBaseNumber implements IBaseNumber{

    protected static final int RESERVE_STRUCTURE = -1;

    /**
     * 保留小数位数
     * 默认: -1, 不以小数形式展示, 即保存原始数的结构
     */
    protected int scale = RESERVE_STRUCTURE;

    /**
     * 默认采用四舍五入的舍入方式
     */
    protected NumberRoundingMode roundingMode = NumberRoundingMode.HALF_UP;

    /**
     * 默认不足位, 不使用0填充
     */
    protected boolean fillWithZero = false;

    protected AbstractBaseNumber(){
        // do nothing
    }

    protected AbstractBaseNumber(int scale, NumberRoundingMode roundingMode, boolean fillWithZero){
        this.setScaleInfo(scale, roundingMode, fillWithZero);
    }

    private void setScaleInfo(int scale, NumberRoundingMode roundingMode, boolean fillWithZero){
        this.scale = scale;
        this.roundingMode = roundingMode;
        this.fillWithZero = fillWithZero;
    }

    @Override
    public void setScale(int scale, NumberRoundingMode roundingMode, boolean fillWithZero){
        this.setScaleInfo(scale, roundingMode, fillWithZero);
    }

    @Override
    public void setScale(int scale, NumberRoundingMode roundingMode) {
        this.setScaleInfo(scale, roundingMode, false);
    }
}
