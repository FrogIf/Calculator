package frog.calculator.build.command;

import frog.calculator.build.IExpressionBuilder;
import frog.calculator.build.resolve.IResolverResult;

public class BlockCommand extends AbstractCommand {

    private String blockStart;

    private String blockEnd;

    public BlockCommand(String blockStart, String blockEnd) {
        this.blockStart = blockStart;
        this.blockEnd = blockEnd;
    }

    @Override
    public int init(IExpressionBuilder builder) {
        builder.createLocalVariableTable();    // 创建局部变量表
        return 0;
    }

    @Override
    public void beforeResolve(char[] chars, int startIndex, IExpressionBuilder builder) {
        // do nothing
    }

    @Override
    public IResolverResult afterResolve(IResolverResult result, IExpressionBuilder builder) {
        // do nothing
        return result;
    }

    @Override
    public boolean over(String symbol, IExpressionBuilder builder) {
        boolean isOver = this.blockEnd.endsWith(symbol);
        if(isOver){
            builder.popLocalVariableTable();   // 销毁局部变量表
        }
        return isOver;
    }

    @Override
    public String symbol() {
        return this.blockStart;
    }
}
