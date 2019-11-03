package frog.calculator.command;

import frog.calculator.ICalculatorManager;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;
import frog.calculator.express.VariableExpression;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.resolve.TruncateResolver;

public class DeclareCommand extends AbstractCommand {

    private String command;

    private String over;

    private char endPrefix;

    private TruncateResolver truncateResolver;

    public DeclareCommand(String command, String over, ICalculatorManager manager, IExpression[] structExpressions) {
        this.command = command;
        this.over = over;
        this.endPrefix = this.over.charAt(0);

        boolean haveOver = false;
        for (IExpression structExpression : structExpressions) {
            if(over.equals(structExpression.symbol())){
                haveOver = true;
                break;
            }
        }

        String[] truncateSymbol;
        if(!haveOver){
            truncateSymbol = new String[structExpressions.length + 1];
            truncateSymbol[structExpressions.length] = over;
        }else{
            truncateSymbol = new String[structExpressions.length];
        }
        for (int i = 0; i < structExpressions.length; i++) {
            truncateSymbol[i] = structExpressions[i].symbol();
        }

        this.truncateResolver = new TruncateResolver(manager, (symbol) -> new VariableExpression(symbol, this.over), truncateSymbol);
    }

    @Override
    public int init(ICalculatorSession session) {
        return command.length();
    }

    @Override
    public void beforeResolve(char[] chars, int startIndex, ICalculatorSession session) {
        // 执行变量解析
        IResolverResult result = this.truncateResolver.resolve(chars, startIndex);
        if(result == null){
            throw new IllegalStateException("truncate resolve failed.");
        }
        session.addVariable(result.getExpression());
    }

    @Override
    public IResolverResult afterResolve(IResolverResult result, ICalculatorSession session) {
        return result;
    }

    @Override
    public boolean over(char[] chars, int startIndex) {
        if(startIndex >= chars.length){
            return true;
        }
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
}
