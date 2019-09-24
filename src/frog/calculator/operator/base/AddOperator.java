package frog.calculator.operator.base;

import frog.calculator.exception.UnrightExpressionException;
import frog.calculator.exception.UnsupportDimensionException;
import frog.calculator.express.IExpression;
import frog.calculator.math.INumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.space.FixedAlignSpace;
import frog.calculator.space.IRange;
import frog.calculator.space.ISpace;
import frog.calculator.util.collection.IList;

public class AddOperator extends AbstractOperator {

    @Override
    public ISpace<INumber> operate(IExpression exp) {
        IExpression left = exp.nextChild();
        IExpression right = exp.nextChild();

        if(right == null){
            throw new UnrightExpressionException();
        }

        if(left == null){
            return right.interpret();
        }else{
            ISpace<INumber> ls = left.interpret();
            ISpace<INumber> rs = right.interpret();

            IRange lr = ls.getRange();
            IRange rr = rs.getRange();

            if(!lr.equals(rr)){
                throw new UnsupportDimensionException(ls.toString(), 12);
            }

            FixedAlignSpace<INumber> result = new FixedAlignSpace<>(lr);

            IList<INumber> lpoints = ls.getElements();
            IList<INumber> rpoints = rs.getElements();



            return result;
        }
    }
}
