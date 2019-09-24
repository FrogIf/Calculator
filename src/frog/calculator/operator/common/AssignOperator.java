package frog.calculator.operator.common;

import frog.calculator.exception.StructureErrorException;
import frog.calculator.express.IExpression;
import frog.calculator.express.endpoint.VariableExpression;
import frog.calculator.math.INumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.space.ISpace;
import frog.calculator.util.StringUtils;

public class AssignOperator extends AbstractOperator {

    @Override
    public ISpace<INumber> operate(IExpression exp) {
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
