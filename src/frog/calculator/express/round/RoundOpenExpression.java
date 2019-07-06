package frog.calculator.express.round;

import frog.calculator.express.APriorityExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.context.IExpressContext;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.operate.IOperator;

public class RoundOpenExpression extends APriorityExpression {

    private IExpression suspendExp;

    private IExpression content;

    private IExpression closeExp;

    private String closeSymbol; // 监测又半表达式的符号

    private IExpressContext context;

    public RoundOpenExpression(IOperator operator, int priority, String symbol, String closeSymbol, IExpressContext context) {
        super(operator, priority, symbol);
        if(closeSymbol == null){
            throw new IllegalArgumentException("closeSymbol can not be null.");
        }
        if(context == null){
            throw new IllegalArgumentException("environment object is null.");
        }
        this.context = context;
        this.closeSymbol = closeSymbol;
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        IExpression root = this;

        if (this.closeExp != null) {
            return super.assembleTree(expression);
        }else{
            if(this.closeSymbol.equals(expression.symbol())){
                this.closeExp = expression;
                this.isLeaf = true;
                if(this.suspendExp != null){
                    root = this.suspendExp;
                    this.suspendExp = null;
                    if(!root.createBranch(this)){
                        throw new IllegalStateException("the expression can't put into this tree.");
                    }
                }
            }else{
                if(this.content == null){
                    this.content = expression;
                }else{
                    this.content = this.content.assembleTree(expression);
                    if(this.content == null){
                        throw new IllegalStateException("tree root lost.");
                    }
                }
            }
        }

        return root;
    }

    @Override
    public boolean createBranch(IExpression expression) {
        if(this.closeExp != null){
            return false;
        }

        if(this.closeSymbol.equals(expression.symbol())){
            throw new IllegalArgumentException("expression error.");
        }

        if(this.suspendExp == null){
            this.suspendExp = expression;
        }else{
            if(this.content == null){
                this.content = expression;
            }else{
                IExpression root = this.content.assembleTree(expression);
                if(root != null){
                    this.content = root;
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public AResultExpression interpret() {
        if(this.content  == null){
            throw new IllegalStateException("invalid expression.");
        }
        return this.content.interpret();
    }

    @Override
    public IExpression clone(){
        return super.clone();
    }

    private boolean isLeaf = false;

    @Override
    public boolean leaf(){
        return this.isLeaf;
    }

    @Override
    public int priority() {
        if(this.closeExp != null){
            return this.context.getMaxPriority();
        }else{
            return this.context.getMinPriority();
        }
    }

}