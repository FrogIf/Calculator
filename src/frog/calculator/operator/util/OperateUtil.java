package frog.calculator.operator.util;

import frog.calculator.exception.UnsupportDimensionException;
import frog.calculator.math.INumber;
import frog.calculator.space.Coordinate;
import frog.calculator.space.FixedAlignSpace;
import frog.calculator.space.IRange;
import frog.calculator.space.ISpace;

public class OperateUtil {

    public static ISpace<INumber> operate(ISpace<INumber> left, ISpace<INumber> right, ISingleElementDealer dealer){
        IRange lr = left.getRange();
        IRange rr = right.getRange();

        if(!lr.equals(rr)){
            throw new UnsupportDimensionException(lr.toString(), 12);
        }

        FixedAlignSpace<INumber> result = new FixedAlignSpace<>(lr);

        int[] coordinateArr = new int[lr.dimension()];
        int[] widths = lr.maxWidths();
        for(int i = coordinateArr.length - 1; i >= 0; i--){
            for(int j = 0; j < widths[i]; j++){
                coordinateArr[i] = j;
                Coordinate coordinate = new Coordinate(coordinateArr);
                INumber ln = left.get(coordinate);
                INumber rn = right.get(coordinate);
                result.add(dealer.deal(ln, rn), coordinate);
            }
        }

        return result;
    }

}