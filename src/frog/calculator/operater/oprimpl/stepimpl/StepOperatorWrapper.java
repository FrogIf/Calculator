package frog.calculator.operater.oprimpl.stepimpl;

import frog.calculator.express.ExpressionType;
import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.operater.IOperator;

public class StepOperatorWrapper implements IOperator{

    private IOperator currentOperator;

    private IOperator mainOperator;

    private IOperator assistOperator;

    private StepMonitorContext context;

    public StepOperatorWrapper(IOperator mainOperator, IOperator assistOperator, StepMonitorContext context) {
        this.currentOperator = this.mainOperator = mainOperator;
        this.assistOperator = assistOperator;
        this.context = context;
    }

    public void restore(){
        this.currentOperator = this.mainOperator;
    }

    @Override
    public AResultExpression operate(IExpression expression) {
        if(expression.type() == ExpressionType.TERMINAL){
            return this.mainOperator.operate(expression);
        }else if(!context.isMainFinish()){
            AResultExpression result = this.mainOperator.operate(expression);
            context.setMainFinish(true);
            return result;
        }else{
            return this.assistOperator.operate(expression);
        }
    }
}
