package frog.calculator;

import frog.calculator.express.IExpression;
import frog.calculator.express.container.ContainerExpression;
import frog.calculator.express.endpoint.MarkExpression;
import frog.calculator.operator.DeclareOperator;
import frog.calculator.operator.StructContainerOperator;

public abstract class AbstractExpressionHolder implements IExpressionHolder {

    // 声明结束
    private IExpression declareEnd = new MarkExpression(";");

    // 声明开始
    private IExpression declareBegin = new ContainerExpression("@", new DeclareOperator(), declareEnd.symbol());

    // 赋值符号
    private IExpression assign = new MarkExpression("=");

    // 右括号
    private IExpression bracketClose = new MarkExpression(")");

    // 左括号
    private IExpression bracketOpen = new ContainerExpression("(", new StructContainerOperator(), bracketClose.symbol());

    // 分割符
    private IExpression separator = new MarkExpression(",");

    @Override
    public IExpression getAssign() {
        return assign;
    }

    @Override
    public IExpression getDeclareBegin() {
        return this.declareBegin;
    }

    @Override
    public IExpression getDelcareEnd() {
        return this.declareEnd;
    }

    @Override
    public IExpression[] getBuiltInExpression() {
        IExpression[] runnableExpression = this.getRunnableExpression();
        int total = runnableExpression.length + 4;

        IExpression[] builtInExpression = new IExpression[total];
        int i = 0;
        for(; i < runnableExpression.length; i++){
            builtInExpression[i] = runnableExpression[i];
        }

        builtInExpression[i++] = this.assign;
        builtInExpression[i++] = this.separator;
        builtInExpression[i++] = this.bracketClose;
        builtInExpression[i] = this.bracketOpen;

        return builtInExpression;
    }

    @Override
    public IExpression[] getDeclareStruct(){
        return new IExpression[]{
                bracketOpen,
                separator,
                bracketClose,
                assign
        };
    }

    protected abstract IExpression[] getRunnableExpression();

}
