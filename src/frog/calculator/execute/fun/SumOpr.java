package frog.calculator.execute.fun;

import frog.calculator.execute.exception.IncorrectStructureException;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.execute.AbstractOperator;
import frog.calculator.execute.base.AddOpr;
import frog.calculator.execute.space.ISpace;

public class SumOpr extends AbstractOperator {
    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression child = exp.nextChild();
        if(child == null){
            throw new IncorrectStructureException("sum", "child is null.");
        }else{
            if(!child.hasNextChild()){
                throw new IncorrectStructureException("sum", "empty argument.");
            }
            ISpace<BaseNumber> sumSpace = child.nextChild().interpret();
            while(child.hasNextChild()){
                sumSpace = AddOpr.addSpace(child.nextChild().interpret(), sumSpace);
            }

            return sumSpace;
        }
    }
}
