package frog.calculator.build.command;

import frog.calculator.ICalculatorManager;
import frog.calculator.build.IExpressionBuilder;
import frog.calculator.build.register.IRegister;
import frog.calculator.build.resolve.IResolverResult;
import frog.calculator.build.resolve.TruncateResolver;
import frog.calculator.exception.BuildException;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionHolder;
import frog.calculator.express.VariableExpression;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;

/**
 * 声明命令<br/>
 * 用于定义变量以及函数<br/>
 * 变量定义:<br/>
 * [command][var][assign][value]<br/>
 * 函数定义:<br/>
 * [command][var][funOpen][var][var]...[funClose][assign][blockOpen]...[blockClose]
 */
public class VariableDeclareCommand extends AbstractCommand {

    private String command;

    private String funOpen;

    private String funClose;

    private String blockOpen;

    private boolean declareFunction;    // 指示是否是函数声明

    private String over;

    private IRegister<IExpression> localRegister;   // 供函数调用的局部变量注册表

    private TruncateResolver variableResolver;

    public VariableDeclareCommand(String command, ICalculatorManager manager, IExpressionHolder holder) {
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
        this.funClose = holder.getBracketClose().symbol();
        this.blockOpen = holder.getBlockStart().symbol();

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
            builder.addVariable(result.getExpression());
        }
    }

    @Override
    public IResolverResult afterResolve(IResolverResult result, IExpressionBuilder builder) {
        if(result != null){
            String symbol = result.getExpression().symbol();
            if(this.funOpen.equals(symbol)){
                this.declareFunction = true;
                builder.createLocalVariableTable();
            }else if(this.declareFunction && this.funClose.equals(symbol)){
                this.localRegister = builder.popLocalVariableTable();
                this.over = this.blockOpen;
            }else if(this.blockOpen.equals(symbol)){
                IList<IExpression> elements = this.localRegister.getElements();
                Iterator<IExpression> itr = elements.iterator();
                while(itr.hasNext()){
                    try {
                        builder.addVariable(itr.next());
                    } catch (DuplicateSymbolException e) {
                        // TODO 异常抛出
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean over(String symbol, IExpressionBuilder builder) {
        return this.over.equals(symbol);
    }

    @Override
    public String symbol() {
        return command;
    }

}
