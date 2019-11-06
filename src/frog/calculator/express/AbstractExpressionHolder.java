package frog.calculator.express;

public abstract class AbstractExpressionHolder implements IExpressionHolder {

    // 右括号
    private IExpression bracketClose = new MarkExpression(")");

    // 分割符
    private IExpression separator = new MarkExpression(",");

    // 左括号
    private IExpression bracketOpen = new SurroundExpression("(", separator.symbol(), bracketClose.symbol());

    // list 结束
    private IExpression listEnd = new MarkExpression("]");

    // 转list函数
    private IExpression listFun = new SurroundExpression("[", separator.symbol(), listEnd.symbol());

    // 表达式终结符
    private IExpression end = new MarkExpression(";");

    // 变量赋值符
    private IExpression assign = new AssignExpression("=", end.symbol());

    // 代码块起始表达式
    private IExpression blockClose = new MarkExpression("}");

    // 代码块终止表达式
    private IExpression blockOpen = new SurroundExpression("{", separator.symbol(), blockClose.symbol());

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
                end,    // 表达式结束符
                assign,  // 赋值表达式
                blockOpen,  // 块起始
                blockClose  // 块终止
        };
    }

    protected abstract IExpression[] getRunnableExpression();

    public IExpression getEndExpression(){
        return this.end;
    }

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
    public IExpression getBlockOpen() {
        return this.blockOpen;
    }

    @Override
    public IExpression getBlockClose() {
        return this.blockClose;
    }
}
