package frog.calculator.dimpl.opr.two;

import frog.calculator.exception.UnsupportDimensionException;
import frog.calculator.exception.UnsupportOperateException;
import frog.calculator.space.FixedAlignSpaceBuilder;
import frog.calculator.space.IPoint;
import frog.calculator.space.ISpace;
import frog.calculator.space.SymbolPoint;
import frog.calculator.util.StringUtils;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;

public class AddOperator extends LeftNullableOperator{

    @Override
    protected ISpace doubleCalculate(ISpace left, ISpace right) {
        if(left.dimension() != right.dimension()){
            throw new UnsupportDimensionException(left.toString(), left.dimension());
        }

        FixedAlignSpaceBuilder builder = new FixedAlignSpaceBuilder();
        builder.setDimension(left.dimension());

        for(int i = 0, len = left.dimension(); i < len; i++){
            int lw = left.width(null);
            if(lw != right.width(null)){
                throw new UnsupportOperateException(StringUtils.concat("width is not equal in a dimension. dimension : ",
                        String.valueOf(left.dimension()), ", left width : ",
                        String.valueOf(left.width(null)), ", right width",
                        String.valueOf(right.width(null))));
            }else{
                builder.setWidth(i, lw);
            }
        }

        IList<IPoint> lvs = left.getPoints();
        IList<IPoint> rvs = right.getPoints();

        IList<IPoint> result = new ArrayList<>(lvs.size());
        builder.initElements(result);

        for(int i = 0; i < lvs.size(); i++){
            IPoint lv = lvs.get(i);
            IPoint rv = rvs.get(i);

            if(lv == null && rv == null) { continue; }

            double lvv = lv == null ? 0 : Double.parseDouble((String) lv.intrinsic());
            double rvv = rv == null ? 0 : Double.parseDouble((String) rv.intrinsic());

            result.add(i, new SymbolPoint(String.valueOf(lvv + rvv)));
        }

        return builder.build();
    }

}
