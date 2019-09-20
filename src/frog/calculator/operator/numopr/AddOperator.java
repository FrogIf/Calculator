package frog.calculator.operator.numopr;

import frog.calculator.exception.UnrightExpressionException;
import frog.calculator.exception.UnsupportDimensionException;
import frog.calculator.express.IExpression;
import frog.calculator.math.INumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.space.FixedAlignSpaceBuilder;
import frog.calculator.space.IPoint;
import frog.calculator.space.ISpace;

public class AddOperator extends AbstractOperator {

    @Override
    public ISpace<IPoint<INumber>> operate(IExpression exp) {
        IExpression left = exp.nextChild();
        IExpression right = exp.nextChild();

        if(right == null){
            throw new UnrightExpressionException();
        }

        if(left == null){
            return right.interpret();
        }else{
            ISpace<IPoint<INumber>> ls = left.interpret();
            ISpace<IPoint<INumber>> rs = right.interpret();

            if(ls.dimension() != rs.dimension()){
                throw new UnsupportDimensionException(ls.toString(), ls.dimension());
            }

            FixedAlignSpaceBuilder<INumber> builder = new FixedAlignSpaceBuilder<>();
            builder.setDimension(ls.dimension());

            for(int i = 0, len = ls.dimension(); i < len; i++){

            }

        }

        return null;
    }

}
