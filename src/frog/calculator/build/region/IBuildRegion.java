package frog.calculator.build.region;

import frog.calculator.express.IExpression;

public interface IBuildRegion {

    boolean match(IExpression expression);

}
