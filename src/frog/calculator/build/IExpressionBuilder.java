package frog.calculator.build;

import frog.calculator.build.region.IBuildRegion;
import frog.calculator.express.IExpression;

public interface IExpressionBuilder {

    IExpression getRoot();

    void finishBuild();

    void addBuildFinishListener(IBuildFinishListener listener);

    IExpression append(IExpression expression);

    void setBuildRegion(IBuildRegion region);
}
