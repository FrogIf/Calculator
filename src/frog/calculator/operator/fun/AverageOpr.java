package frog.calculator.operator.fun;

import frog.calculator.exception.UnrightExpressionException;
import frog.calculator.exception.UnsupportDimensionException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.space.*;

public class AverageOpr extends AbstractOperator {
    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression child = exp.nextChild();
        if(child == null){
            throw new UnrightExpressionException();
        }else{
            ISpace<BaseNumber> space = child.interpret();

            IRange range = space.getRange();
            if(range.dimension() != 1){
                throw new UnsupportDimensionException(exp.symbol(), range.dimension());
            }else{
                int[] widths = range.maxWidths();
                int w = widths[0];
                BaseNumber sum = BaseNumber.ZERO;
                for(int i = 0; i < w; i++){
                    sum = sum.add(space.get(new Coordinate(i)));
                }
                sum = sum.div(BaseNumber.valueOf(String.valueOf(w)));

                SpaceRange rRange = new SpaceRange();
                int[] widthArr = new int[1];
                widthArr[0] = 1;
                rRange.setMaxWidths(widthArr);
                FixedAlignSpace<BaseNumber> resultSpace = new FixedAlignSpace<>(rRange);
                resultSpace.add(sum,  new Coordinate(0));
                return resultSpace;
            }
        }
    }
}
