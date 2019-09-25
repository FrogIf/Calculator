package frog.calculator.operator.base;

import frog.calculator.exception.UnrightExpressionException;
import frog.calculator.exception.UnsupportDimensionException;
import frog.calculator.express.IExpression;
import frog.calculator.math.INumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.space.Coordinate;
import frog.calculator.space.FixedAlignSpace;
import frog.calculator.space.IRange;
import frog.calculator.space.ISpace;

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

            int[] coordinateArr = new int[lr.dimension()];
            int[] widths = lr.maxWidths();
            for(int i = coordinateArr.length - 1; i >= 0; i--){
                for(int j = 0; j < widths[i]; j++){
                    coordinateArr[i] = j;
                    Coordinate coordinate = new Coordinate(coordinateArr);
                    INumber ln = ls.get(coordinate);
                    INumber rn = rs.get(coordinate);

                    if(ln != null || rn != null){
                        if(ln == null){
                            result.add(rn, coordinate);
                        }else if(rn == null){
                            result.add(ln, coordinate);
                        }else{
                            result.add(ln.add(rn), coordinate);
                        }
                    }
                }
            }

            return result;
        }
    }
}
