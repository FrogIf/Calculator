package frog.calculator.exec.fun;

import frog.calculator.exec.exception.IncorrectStructureException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.exec.AbstractOperator;
import frog.calculator.exec.base.AddOpr;
import frog.calculator.exec.space.ISpace;

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
