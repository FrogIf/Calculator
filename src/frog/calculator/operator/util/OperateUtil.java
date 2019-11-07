package frog.calculator.operator.util;

import frog.calculator.operator.exception.IncorrectStructureException;
import frog.calculator.operator.exception.NonsupportRangeException;
import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.space.Coordinate;
import frog.calculator.space.FixedAlignSpace;
import frog.calculator.space.IRange;
import frog.calculator.space.ISpace;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.LinkedList;

public class OperateUtil {

    public static ISpace<BaseNumber> transform(ISpace<BaseNumber> left, ISpace<BaseNumber> right, ILeftRightMapDealer dealer){
        IRange lr = left.getRange();
        IRange rr = right.getRange();

        if(!lr.equals(rr)){
            throw new NonsupportRangeException(dealer.getClass().toString(), "left range not equals right range : left : " + lr.toString() + ", right : " + rr.toString());
        }

        FixedAlignSpace<BaseNumber> result = new FixedAlignSpace<>(lr);

        int[] coordinateArr = new int[lr.dimension()];
        int[] widths = lr.maxWidths();
        for(int i = coordinateArr.length - 1; i >= 0; i--){
            for(int j = 0; j < widths[i]; j++){
                coordinateArr[i] = j;
                Coordinate coordinate = new Coordinate(coordinateArr);
                BaseNumber ln = left.get(coordinate);
                BaseNumber rn = right.get(coordinate);
                result.add(dealer.deal(ln, rn), coordinate);
            }
        }

        return result;
    }

    public static ISpace<BaseNumber> transform(ISpace<BaseNumber> space, IOneElementDealer dealer){
        IRange range = space.getRange();
        FixedAlignSpace<BaseNumber> result = new FixedAlignSpace<>(range);

        int[] coordinateArr = new int[range.dimension()];
        int[] widths = range.maxWidths();
        for(int i = coordinateArr.length - 1; i >= 0; i--){
            for(int j = 0; j < widths[i]; j++){
                coordinateArr[i] = j;
                Coordinate coordinate = new Coordinate(coordinateArr);
                BaseNumber num = space.get(coordinate);
                result.add(dealer.deal(num), coordinate);
            }
        }

        return result;
    }

    public static IList<IExpression> getFunctionArgumentList(IExpression fun){
        IExpression surround = fun.nextChild();
        if(surround == null){
            throw new IncorrectStructureException("average", "no argument.");
        }else{
            IList<IExpression> argumentExpressions = new LinkedList<>();
            while(surround.hasNextChild()){
                argumentExpressions.add(surround.nextChild());
            }
            return argumentExpressions;
        }
    }

}
