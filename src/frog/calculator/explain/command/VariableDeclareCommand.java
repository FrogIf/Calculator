package frog.calculator.explain.command;

import frog.calculator.exception.BuildException;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.explain.IExpressionBuilder;
import frog.calculator.explain.register.IRegister;
import frog.calculator.explain.resolve.IResolverResult;
import frog.calculator.explain.resolve.IResolverResultFactory;
import frog.calculator.explain.resolve.TruncateResolver;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionHolder;
import frog.calculator.express.template.VariableExpression;
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

    private String assign;

    private String blockClose;

    private boolean declareFunction;    // 指示是否是函数声明

    private String over;

    private IRegister<IExpression> localRegister;   // 供函数调用的局部变量注册表

    private TruncateResolver variableResolver;

    private IResolverResultFactory resolverResultFactory;

    public VariableDeclareCommand(String command, IExpressionHolder holder, IResolverResultFactory resolverResultFactory) {
        this.command = command;
        this.resolverResultFactory = resolverResultFactory;
        this.assign = this.over = holder.getAssign().symbol();

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
        this.blockClose = holder.getBlockEnd().symbol();

        this.variableResolver = new TruncateResolver((symbol) -> new VariableExpression(symbol, this.over), truncateSymbol, this.resolverResultFactory);
    }

    @Override
    public int init(IExpressionBuilder builder) {
        return command.length();
    }

    @Override
    public void beforeResolve(char[] chars, int startIndex, IExpressionBuilder builder) throws BuildException {
        if(this.variableResolver != null){
            // 执行变量解析
            IResolverResult result = this.variableResolver.resolve(chars, startIndex);
            if(result != null){
                builder.addVariable(result.getExpression());
            }
        }
    }

    @Override
    public IResolverResult afterResolve(IResolverResult result, IExpressionBuilder builder) {
        if(result != null){
            String symbol = result.getExpression().symbol();
            if(this.funOpen.equals(symbol)){
                this.declareFunction = true;
                builder.createLocalVariableTable();
            }else if(this.declareFunction && this.funClose.equals(symbol)){ // 说明是函数声明
                this.localRegister = builder.popLocalVariableTable();
                this.over = this.blockClose;
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
        if(this.over.equals(symbol)){
            return true;
        }else if(this.assign.equals(symbol)){   // TODO 需要注意下声明的嵌套时会不会有什么影响
            this.variableResolver = null;
        }
        return false;
    }

    @Override
    public String symbol() {
        return command;
    }

}
