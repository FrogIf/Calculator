package frog.calculator.exec.base;

import frog.calculator.exec.exception.NonsupportOperateException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.math.MathUtil;
import frog.calculator.exec.AbstractOperator;
import frog.calculator.exec.space.Coordinate;
import frog.calculator.exec.space.FixedAlignSpace;
import frog.calculator.exec.space.IRange;
import frog.calculator.exec.space.ISpace;

public class PowerOpr extends AbstractOperator {
    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        IExpression base = exp.nextChild();

        IExpression index = exp.nextChild();
        ISpace<BaseNumber> indexSpace = index.interpret();
        IRange indexRange = indexSpace.getRange();
        if(indexRange.dimension() != 1){
            throw new NonsupportOperateException("power", "the index require dimension 1 but " + indexRange.dimension());
        }else if(indexRange.maxWidths()[0] != 1){
            throw new NonsupportOperateException("power", "the index require number 1 but " + indexRange.maxWidths()[0]);
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
