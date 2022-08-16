package sch.frog.calculator.runtime;

import sch.frog.calculator.math.number.NumberRoundingMode;

/**
 * 运行时配置, 可能动态的修改, 并随时生效
 */
public class RuntimeConfiguration {

    /**
     * 输出结果保留的小数位数, 默认保留小数点后10位
     */
    private int scale = 10;

    /**
     * 输出的小数舍入方式, 默认采用四舍五入, 详见NumberRoundingMode
     */
    private String roundMode = NumberRoundingMode.HALF_UP.name();

    /**
     * 小数位填充
     */
    private boolean fillWithZero = false;

    /**
     * 是否输出AST树, true - 输出, false - 不输出
     */
    private boolean outputASTTree = false;

    public void setScale(int scale){
        this.scale = scale;
    }

    public int getScale(){
        return this.scale;
    }

    public String getRoundMode(){
        return this.roundMode;
    }
    
    public void setRoundMode(String roundMode){
        this.roundMode = roundMode;
    }

    public boolean getFillWithZero(){
        return this.fillWithZero;
    }

    public void setFillWithZero(boolean fillWithZero){
        this.fillWithZero = fillWithZero;
    }

    public void setOutputASTTree(boolean outputASTTree){
        this.outputASTTree = outputASTTree;
    }

    public boolean getOutputASTTree(){
        return this.outputASTTree;
    }
}
