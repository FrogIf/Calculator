package frog.calculator.dimpl.opr.fun;

import frog.calculator.dimpl.opr.AbstractOperator;
import frog.calculator.express.IExpression;
import frog.calculator.space.*;

public class AvgOperator extends AbstractOperator {
    @Override
    public ISpace operate(IExpression exp) {
        IExpression child = exp.nextChild();
        ISpace iSpace = child.interpret();
        int width = iSpace.width(0);
        double sum = 0;
        for(int i = 0; i < width; i++){
            ILiteral value = iSpace.getValue(new CommonCoordinate(i));
            sum += Double.parseDouble(value.value());
        }

        ISpaceBuilder builder = new CommonSpaceBuilder();
        builder.setDimension(1);
        builder.setWidth(0, 1);
        ISpace space = builder.build();
        space.addValue(new CommonCoordinate(0), new CommonLiteral(String.valueOf(sum / width)));
        return space;
    }
}
