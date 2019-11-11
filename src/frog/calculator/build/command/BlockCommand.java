package frog.calculator.build.command;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpressionHolder;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.util.StringUtils;

public class BlockCommand extends AbstractCommand {

    private String blockOpen;

    private String blockClose;

    public BlockCommand(IExpressionHolder holder) {
        this.blockOpen = holder.getBlockOpen().symbol();
        this.blockClose = holder.getBlockClose().symbol();
    }

    @Override
    public int init(ICalculatorSession session) {
        session.createLocalVariableTable();    // 创建局部变量表
        return 0;
    }

    @Override
    public void beforeResolve(char[] chars, int startIndex, ICalculatorSession session) {
        // do nothing
    }

    @Override
    public IResolverResult afterResolve(IResolverResult result, ICalculatorSession session) {
        // do nothing
        return result;
    }

    @Override
    public boolean over(char[] chars, int startIndex, ICalculatorSession session) {
        boolean isOver = StringUtils.startWith(startIndex, chars, this.blockClose);
        if(isOver){
            session.popLocalVariableTable();   // 销毁局部变量表
        }
        return isOver;
    }

    @Override
    public void buildFailedCallback(ICalculatorSession session) {
        session.popLocalVariableTable();
        session.popCommand(this);
    }

    @Override
    public String symbol() {
        return this.blockOpen;
    }
}
