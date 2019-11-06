package frog.calculator.command;

import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpressionHolder;
import frog.calculator.resolver.IResolverResult;
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
        session.createLocalVariableRegion();    // 创建局部变量表
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
            session.popLocalVariableRegion();   // 销毁局部变量表
        }
        return isOver;
    }

    @Override
    public String symbol() {
        return this.blockOpen;
    }
}
