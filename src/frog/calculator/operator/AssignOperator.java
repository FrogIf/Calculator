package frog.calculator.operator;

import frog.calculator.exception.StructureErrorException;
import frog.calculator.express.IExpression;
import frog.calculator.express.endpoint.VariableExpression;
import frog.calculator.space.ISpace;
import frog.calculator.util.StringUtils;

public class AssignOperator implements IOperator {

    @Override
    public ISpace operate(IExpression exp) {
        IExpression variable = exp.nextChild();
        IExpression valueExp = exp.nextChild();
        if(variable == null || valueExp == null){
            throw new StructureErrorException(exp.symbol(), exp.order());
        }
        ISpace value = valueExp.interpret();

        if(variable instanceof VariableExpression){
            ((VariableExpression) variable).assign(value);
        }else{
            throw new UnsupportedOperationException(StringUtils.concat(variable.symbol(), "can't be assign a literal."));
        }

        return value;
    }

}
