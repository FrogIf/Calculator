package frog.calculator.execute.holder;

import frog.calculator.execute.base.BracketOpr;
import frog.calculator.execute.base.RegionOpr;
import frog.calculator.express.*;

public abstract class AbstractExpressionHolder implements IExpressionHolder {

    // 右括号
    private final IExpression bracketClose = new SignalExpression(")");

    // 分割符
    private final IExpression separator = new SignalExpression(",");

    // 左括号
    private final IExpression bracketOpen = new ContainerExpression("(", separator.symbol(), bracketClose.symbol(), new BracketOpr());

    // list 结束
    private final IExpression listEnd = new SignalExpression("]");

    // 转list函数
    private final IExpression listFun = new ContainerExpression("[", separator.symbol(), listEnd.symbol(), new BracketOpr());

    // 变量赋值符
    private final IExpression assign = new AssignExpression("=");

    // 代码块起始表达式
    private final IExpression blockEnd = new SignalExpression("}");

    // 代码块终止表达式
    private final IExpression blockStart = new ContainerExpression("{", separator.symbol(), blockEnd.symbol(), new RegionOpr());

    @Override
    public IExpression[] getBuiltInExpression() {
        IExpression[] runnableExpression = this.getRunnableExpression();
        IExpression[] structureExpression = this.getStructureExpression();
        int total = runnableExpression.length + structureExpression.length;

        IExpression[] builtInExpression = new IExpression[total];
        int i = 0;
        for(; i < runnableExpression.length; i++){
            builtInExpression[i] = runnableExpression[i];
        }

        for(int j = 0; j < structureExpression.length; j++){
            builtInExpression[i + j] = structureExpression[j];
        }

        return builtInExpression;
    }

    @Override
    public IExpression[] getStructureExpression() {
        return new IExpression[]{
                bracketOpen,    // 左括号
                bracketClose,   // 右括号
                separator,      // 逗号
                listFun,        // 集合左
                listEnd,         // 集合右
                assign,  // 赋值表达式
                blockEnd,  // 块起始
                blockStart  // 块终止
        };
    }

    protected abstract IExpression[] getRunnableExpression();

    @Override
    public IExpression getBracketOpen() {
        return this.bracketOpen;
    }

    @Override
    public IExpression getBracketClose() {
        return this.bracketClose;
    }

    @Override
    public IExpression getAssign() {
        return this.assign;
    }

    @Override
    public IExpression getBlockStart() {
        return this.blockStart;
    }

    @Override
    public IExpression getBlockEnd() {
        return this.blockEnd;
    }
}
