package frog.calculator.operator.util;

import frog.calculator.exception.UnsupportDimensionException;
import frog.calculator.math.BaseNumber;
import frog.calculator.space.Coordinate;
import frog.calculator.space.FixedAlignSpace;
import frog.calculator.space.IRange;
import frog.calculator.space.ISpace;

public class OperateUtil {

    public static ISpace<BaseNumber> transform(ISpace<BaseNumber> left, ISpace<BaseNumber> right, ILeftRightMapDealer dealer){
        IRange lr = left.getRange();
        IRange rr = right.getRange();

        if(!lr.equals(rr)){
            throw new UnsupportDimensionException(lr.toString(), 12);
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

}
