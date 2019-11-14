package frog.calculator.build.command;

import frog.calculator.ICalculatorManager;
import frog.calculator.build.IExpressionBuilder;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.build.resolve.TruncateResolver;
import frog.calculator.exception.BuildException;
import frog.calculator.express.FunctionExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionHolder;
import frog.calculator.express.VariableExpression;
import frog.calculator.util.StringUtils;

/**
 * 声明命令<br/>
 * 用于定义变量以及函数
 */
public class DeclareCommand extends AbstractCommand {

    private String command;

    private String over;

    private String funOpen;

    private TruncateResolver variableResolver;

    public DeclareCommand(String command, ICalculatorManager manager, IExpressionHolder holder) {
        this.command = command;
        this.over = holder.getAssign().symbol();

        IExpression[] structExpressions = holder.getStructureExpression();
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

        this.funOpen = holder.getBracketOpen().symbol();

        this.variableResolver = new TruncateResolver(manager, (symbol) -> VariableExpression.createVariableExpression(symbol, this.over), truncateSymbol);
    }

    @Override
    public int init(IExpressionBuilder builder) {
        return command.length();
    }

    @Override
    public void beforeResolve(char[] chars, int startIndex, IExpressionBuilder builder) throws BuildException {
        // 执行变量解析
        IResolverResult result = this.variableResolver.resolve(chars, startIndex);
        if(result != null){
            int truncate = startIndex + result.offset();
            if(StringUtils.startWith(truncate, chars, this.funOpen)){ // 如果是函数
                FunctionExpression functionExpression = new FunctionExpression(result.getExpression().symbol());
                builder.addVariable(functionExpression);
                builder.createLocalVariableTable();    // 遇到"="释放
            }else{  // 如果是变量
                builder.addVariable(result.getExpression());
            }
        }
    }

    @Override
    public IResolverResult afterResolve(IResolverResult result, IExpressionBuilder builder) {
        return result;
    }

    @Override
    public boolean over(char[] chars, int startIndex, IExpressionBuilder builder) {
        if(startIndex >= chars.length){
            return true;
        }
        boolean isOver = StringUtils.startWith(startIndex, chars, this.over);
        if(isOver){
            // TODO 判断刚刚声明的是变量还是函数, 如果是函数, 销毁上层局部变量栈
//            IRegister<IExpression> register = builder.popLocalVariableTable();
//            FunctionBuildPipe pipe = new FunctionBuildPipe(new String[]{"{"});
//            pipe.setRegister(register);
//            builder.setBuildPipe(pipe);
        }
        return isOver;
    }

    @Override
    public String symbol() {
        return command;
    }
}
