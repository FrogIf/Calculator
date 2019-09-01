package frog.calculator.dimpl.opr.fun;

import frog.calculator.dimpl.opr.AbstractOperator;
import frog.calculator.express.IExpression;
import frog.calculator.space.*;

public class AvgOperator extends AbstractOperator {
    @Override
    public ISpace operate(IExpression exp) {
        IExpression child = exp.nextChild();
        ISpace iSpace = child.interpret();
        int width = iSpace.width(null);
        double sum = 0;
        for(int i = 0; i < width; i++){
            IPoint value = iSpace.getPoint(new Coordinate(i));
            sum += Double.parseDouble((String) value.intrinsic());
        }

        SpaceBuilder builder = new SpaceBuilder();
        builder.setDimension(1);
        builder.setWidth(0, 1);
        ISpace space = builder.build();
        space.addPoint(new SymbolPoint(String.valueOf(sum / width)), AbstractCoordinate.ORIGIN);
        return space;
    }
}
