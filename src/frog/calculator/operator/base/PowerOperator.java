package frog.calculator.operator.base;

import frog.calculator.exception.UnsupportOperateException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.math.MathUtil;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.space.Coordinate;
import frog.calculator.space.FixedAlignSpace;
import frog.calculator.space.IRange;
import frog.calculator.space.ISpace;

public class PowerOperator extends AbstractOperator {
    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression base = exp.nextChild();

        IExpression index = exp.nextChild();
        ISpace<BaseNumber> indexSpace = index.interpret();
        IRange indexRange = indexSpace.getRange();
        if(indexRange.dimension() != 1 || indexRange.maxWidths()[0] != 1){
            throw new UnsupportOperateException(exp.symbol());
        }else{
            BaseNumber indexNum = indexSpace.get(Coordinate.ORIGIN);

            ISpace<BaseNumber> space = base.interpret();
            IRange baseRange = space.getRange();
            FixedAlignSpace<BaseNumber> result = new FixedAlignSpace<>(baseRange);

            int[] coordinateArr = new int[baseRange.dimension()];
            int[] widths = baseRange.maxWidths();
            for(int i = coordinateArr.length - 1; i >= 0; i--){
                for(int j = 0; j < widths[i]; j++){
                    coordinateArr[i] = j;
                    Coordinate coordinate = new Coordinate(coordinateArr);
                    BaseNumber num = space.get(coordinate);

                    if(num != null){
                        BaseNumber res = MathUtil.power(num, indexNum);
                        result.add(res, coordinate);
                    }
                }
            }

            return result;
        }
    }
}
