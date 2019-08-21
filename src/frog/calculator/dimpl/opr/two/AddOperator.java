package frog.calculator.dimpl.opr.two;

import frog.calculator.exception.UnsupportDimensionException;
import frog.calculator.exception.UnsupportOperateException;
import frog.calculator.space.*;
import frog.calculator.util.StringUtils;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;

public class AddOperator extends LeftNullableOperator{

    @Override
    protected ISpace doubleCalculate(ISpace left, ISpace right) {
        if(left.dimension() != right.dimension()){
            throw new UnsupportDimensionException(left.toString(), left.dimension());
        }

        CommonSpaceBuilder builder = new CommonSpaceBuilder();
        builder.setDimension(left.dimension());

        for(int i = 0, len = left.dimension(); i < len; i++){
            int lw = left.width(i);
            if(lw != right.width(i)){
                throw new UnsupportOperateException(StringUtils.concat("width is not equal in a dimension. dimension : ",
                        String.valueOf(left.dimension()), ", left width : ", String.valueOf(left.width(i)), ", right width", String.valueOf(right.width(i))));
            }else{
                builder.setWidth(i, lw);
            }
        }

        IList<ILiteral> lvs = left.getValues();
        IList<ILiteral> rvs = right.getValues();

        IList<ILiteral> result = new ArrayList<>(lvs.size());
        builder.initElements(result);

        for(int i = 0; i < lvs.size(); i++){
            ILiteral lv = lvs.get(i);
            ILiteral rv = rvs.get(i);

            double lvv = lv == null ? 0 : Double.parseDouble(lv.value());
            double rvv = rv == null ? 0 : Double.parseDouble(rv.value());

            result.add(i, new CommonLiteral(String.valueOf(lvv + rvv)));
        }

        return builder.build();
    }

}
