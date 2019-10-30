package frog.calculator.operator.fun;

import frog.calculator.exception.UnrightExpressionException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.operator.base.AddOpr;
import frog.calculator.space.ISpace;

public class SumOpr extends AbstractOperator {
    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression child = exp.nextChild();
        if(child == null){
            throw new UnrightExpressionException();
        }else{
            if(!child.hasNextChild()){
                throw new IllegalArgumentException("empty argument.");
            }
            ISpace<BaseNumber> sumSpace = child.nextChild().interpret();
            while(child.hasNextChild()){
                sumSpace = AddOpr.addSpace(child.nextChild().interpret(), sumSpace);
            }

            return sumSpace;
        }
    }
}
