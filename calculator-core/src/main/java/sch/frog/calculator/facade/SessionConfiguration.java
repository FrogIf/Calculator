package sch.frog.calculator.facade;

public class SessionConfiguration {

    /**
     * 是否显示抽象语法树
     */
    private boolean showAST;

    /**
     * 指定舍入模式
     */
    private NumberMode numberMode = new NumberMode(NumberMode.Mode.PLAIN, 10);


    public boolean isShowAST() {
        return showAST;
    }

    public void setShowAST(boolean showAST) {
        this.showAST = showAST;
    }

    public NumberMode getNumberMode() {
        return numberMode;
    }

    public void setNumberMode(NumberMode numberMode) {
        this.numberMode = numberMode;
    }
}
