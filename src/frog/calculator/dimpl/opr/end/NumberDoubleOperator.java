package frog.calculator.dimpl.opr.end;

import frog.calculator.dimpl.opr.AbstractOperator;
import frog.calculator.express.IExpression;
import frog.calculator.space.*;

public class NumberDoubleOperator extends AbstractOperator {

    private static final int DIMENSION = 1;

    @Override
    public ISpace operate(IExpression expression) {
        SpaceBuilder builder = new SpaceBuilder();
        builder.setDimension(DIMENSION);
        builder.setWidth(0, 1);
        ISpace space = builder.build();

        space.addPoint(new SymbolPoint(expression.symbol(), new Coordinate(0)));

        return space;
    }

}
