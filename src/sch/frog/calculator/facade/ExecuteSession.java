package sch.frog.calculator.facade;

import sch.frog.calculator.runtime.ICalculatorSession;
import sch.frog.calculator.runtime.GeneralCalculatorSession;

public class ExecuteSession {

    private final ICalculatorSession calculateSession;

    /**
     * 是否显示抽象语法树
     */
    private boolean showAST = false;

    /**
     * 指定舍入模式
     */
    private NumberMode numberMode = new NumberMode(NumberMode.Mode.NONE, 0);

    public ExecuteSession(){
        this.calculateSession = new GeneralCalculatorSession();
    }

    public ICalculatorSession getCalculatorSession(){
        return this.calculateSession;
    }

    public boolean showAST(){
        return this.showAST;
    }

    public void setShowAST(boolean show){
        this.showAST = show;
    }

    public void setNumberMode(NumberMode mode){
        this.numberMode = mode;
    }

    public NumberMode getNumberMode(){
        return this.numberMode;
    }
    
}
