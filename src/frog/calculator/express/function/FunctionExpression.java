package frog.calculator.express.function;

import frog.calculator.express.ANaturalExpression;
import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.operater.IOperator;

/**
 * 函数表达式
 */
public class FunctionExpression extends ANaturalExpression {

    private Arguement arguementChain = null;

    private Arguement unFull = null;

    /**
     * 带参函数表达式
     * @param operator
     * @param priority
     * @param symbol
     * @param funcBody
     * @param variableExpression
     */
    public FunctionExpression(IOperator operator, int priority, String symbol, IExpression funcBody, VariableExpression[] variableExpression) {
        super(operator, priority, symbol);
        this.funcBody = funcBody;

        if(variableExpression != null) initFunctionArguement(variableExpression);
    }

    /**
     * 创建一个空参函数
     * @param operator
     * @param priority
     * @param symbol
     * @param funcBody
     */
    public FunctionExpression(IOperator operator, int priority, String symbol, IExpression funcBody) {
        this(operator, priority, symbol, funcBody, null);
        this.funcBody = funcBody;
    }

    private void initFunctionArguement(VariableExpression[] variableExpression){
        if(variableExpression.length > 0){
            arguementChain = new Arguement(variableExpression[0]);
            unFull = arguementChain;
            Arguement currentActive = arguementChain;
            for(int i = 1; i < variableExpression.length; i++){
                Arguement argExp = new Arguement(variableExpression[i]);
                currentActive.next = argExp;
                currentActive = currentActive.next;
            }
        }
    }

    private IExpression funcBody;   // 函数体

    @Override
    public boolean createBranch(IExpression expression) {
        if(unFull != null){
            VariableExpression varExp = unFull.argExpression;
            while (!varExp.createBranch(expression)){
                unFull = unFull.next;
                if(unFull == null) return false;
                varExp = unFull.argExpression;
            }
            return true;
        }
        return false;
    }


    @Override
    public AResultExpression interpret() {
        return funcBody.interpret();
    }

    /**
     * 参数
     */
    private static class Arguement {

        private VariableExpression argExpression;

        private Arguement next;

        public Arguement(VariableExpression argExpression) {
            this.argExpression = argExpression;
        }
    }



}
