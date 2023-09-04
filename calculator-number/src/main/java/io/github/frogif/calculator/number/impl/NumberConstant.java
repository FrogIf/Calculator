package io.github.frogif.calculator.number.impl;

public class NumberConstant {

    private NumberConstant(){
        // do nothing
    }

    /**
     * 使用科学计数法表示的阈值, 整数部分如果超出这个长度, 则整体使用科学计数法进行表示
     */
    public static final int SCIENTIFIC_THRESHOLD = 1000;

    /**
     * 
     */
    public static final char SCIENTIFIC_MARK = 'E';

}
