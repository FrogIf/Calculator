package frog.math.number;

// 所有number都应该继承这个抽象类, 以后INumber与Symbol统一之后, 这里可以作为缓冲
public abstract class AbstractNumber implements INumber {

    /**
     * 默认保留10位小数
     */
    protected int scale = 10;

    /**
     * 默认采用四舍五入的舍入方式
     */
    protected NumberRoundingMode roundingMode = NumberRoundingMode.HALF_UP;

    /**
     * 默认不足位, 不使用0填充
     */
    protected boolean fillWithZero = false;

    protected AbstractNumber(){
        // do nothing
    }

    protected AbstractNumber(int scale, NumberRoundingMode roundingMode, boolean fillWithZero){
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
}
