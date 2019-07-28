package frog.calculator.express.variable;

import frog.calculator.express.AbstractExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.IExpressionContext;
import frog.calculator.register.IRegister;
import frog.calculator.util.LinkedList;

public class VariableExpression extends AbstractExpression {

    private IExpression valueExpression;    // 变量值表达式, 赋值符号右边的表达式

    private IExpressionContext context;

    protected String assignSymbol;

    // 标记该变量是否可以被赋值
    protected boolean assignable = false;

    protected boolean assignFinish = false;

    /**
     * 赋值操作符
     * @param variableName  变量名
     * @param assignSymbol  赋值运算符
     */
    public VariableExpression(String variableName, String assignSymbol) {
        super(variableName, null);
        this.assignSymbol = assignSymbol;
    }

    @Override
    public boolean createBranch(IExpression childExpression) {
        if(assignable){
            if(valueExpression == null){
                valueExpression = childExpression;
                return true;
            }else{
                IExpression root = valueExpression.assembleTree(childExpression);
                if(root == null){
                    return false;
                }else{
                    valueExpression = root;
                    return true;
                }
            }
        }else{
            // 当赋值符号出现, 将该变量指定为可以赋值
            this.assignable = this.assignSymbol.equals(childExpression.symbol());
            return this.assignable;
        }
    }

    @Override
    public IExpression assembleTree(IExpression expression) {
        if(assignFinish){
            return expression.assembleTree(this);
        }else{
            if(this.createBranch(expression)){
                return this;
            }else{
                if(!this.assignable){
                    return expression.assembleTree(this);
                }else{
                    return null;
                }
            }
        }
    }

    @Override
    public IExpression interpret() {
        this.assignFinish = true;
        if(valueExpression == null){
            LinkedList<IExpression> variables = context.getVariables();
            LinkedList<IExpression>.Iterator iterator = variables.getIterator();
            while(iterator.hasNext()){
                IExpression variable = iterator.next();
                if(variable.symbol().equals(this.symbol)){
                    this.valueExpression = variable.interpret();
                    return this.valueExpression;
                }
            }
            IRegister iRegister = context.getSession().getUserRegister().retrieveRegistryInfo(this.symbol().toCharArray(), 0);
            if(iRegister != null){
                this.valueExpression = iRegister.getExpression();
            }
            if(this.valueExpression == null){
                this.assignFinish = false;
                throw new IllegalArgumentException("undefine variables");
            }else{
                this.valueExpression = this.valueExpression.interpret();
                return this.valueExpression;
            }
        }else{
            this.valueExpression = valueExpression.interpret();
            return valueExpression;
        }
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public int buildFactor() {
        return 1;
    }

    @Override
    public void setExpressionContext(IExpressionContext context) {
        this.context = context;
        if(this.valueExpression != null){
            this.valueExpression.setExpressionContext(context);
        }
    }

    @Override
    public IExpression clone() {
        VariableExpression expression = (VariableExpression) super.clone();
        if(this.valueExpression != null && assignFinish){
            expression.valueExpression = valueExpression.clone();
        }else{
            expression.valueExpression = null;
        }
        expression.assignable = false;
        expression.assignFinish = false;
        return expression;
    }
}
