package frog.calculator.command;

import frog.calculator.ICalculatorManager;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.resolve.TruncateResolver;
import frog.calculator.resolver.resolve.factory.ISymbolExpressionFactory;

public class DeclareCommand extends AbstractCommand {

    private String command;

    private String over;

    private char endPrefix;

    private TruncateResolver truncateResolver;

    private static final VariableExpressionFactory expressionFactory = new VariableExpressionFactory();

    public DeclareCommand(String command, String over, ICalculatorManager manager, IExpression[] structExpressions) {
        this.command = command;
        this.over = over;
        this.endPrefix = this.over.charAt(0);

        this.truncateResolver = new TruncateResolver(manager, expressionFactory, structExpressions);
        this.truncateResolver = null;
    }

    @Override
    public int init(ICalculatorSession session) {
        return command.length();
    }

    @Override
    public void beforeResolve(char[] chars, int startIndex, ICalculatorSession session) {
        // 执行变量解析
        IResolverResult result = this.truncateResolver.resolve(chars, startIndex);
        // 如果结果为空, 抛出异常
    }

    @Override
    public IResolverResult afterResolve(IResolverResult result, ICalculatorSession session) {
        return result;
    }

    @Override
    public boolean over(char[] chars, int startIndex) {
        boolean isOver = false;
        if(endPrefix == chars[startIndex]){
            isOver = true;
            for(int i = 1, len = over.length(); i < len && i + startIndex < chars.length; i++){
                if(over.charAt(i) != chars[startIndex + i]){
                    isOver = false;
                    break;
                }
            }
        }
        return isOver;
    }

    @Override
    public String symbol() {
        return command;
    }

    public static class VariableExpressionFactory implements ISymbolExpressionFactory{
        @Override
        public IExpression createExpression(String symbol) {
            return null;
        }
    }
}
