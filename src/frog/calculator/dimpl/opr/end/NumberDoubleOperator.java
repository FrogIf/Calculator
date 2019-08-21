package frog.calculator.dimpl.opr.end;

import frog.calculator.dimpl.opr.AbstractOperator;
import frog.calculator.express.IExpression;
import frog.calculator.space.*;

public class NumberDoubleOperator extends AbstractOperator {

    private static final int DIMENSION = 1;

    @Override
    public ISpace operate(IExpression expression) {
        CommonSpaceBuilder builder = new CommonSpaceBuilder();
        builder.setDimension(DIMENSION);
        builder.setWidth(0, 1);
        ISpace space = builder.build();

        space.addValue(new CommonCoordinate(0), new CommonLiteral(expression.symbol()));

        return space;
    }

}
