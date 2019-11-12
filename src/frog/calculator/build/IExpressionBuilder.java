package frog.calculator.build;

import frog.calculator.build.command.ICommand;
import frog.calculator.build.region.IBuildRegion;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.express.IExpression;
import frog.calculator.util.collection.ITraveller;

public interface IExpressionBuilder {

    IExpression getRoot();

    void finishBuild();

    void buildFail();

    void addBuildFinishListener(IBuildFinishListener listener);

    IExpression append(IExpression expression);

    void setBuildRegion(IBuildRegion region);

    ICalculatorSession getSession();

    /**
     * 向会话中添加command
     * @param command 待添加的命令
     */
    void pushCommand(ICommand command);

    /**
     * 销毁会话顶部命令<br/>
     * 为安全起见, 必须传入待销毁命令本身方可销毁该命令
     * @param command 指定待销毁的命令
     */
    void popCommand(ICommand command);

    /**
     * 清除session会话中的所有命令
     */
    void clearCommand();

    /**
     * 获取session的命令traveller
     * @return traveller
     */
    ITraveller<ICommand> commandTraveller();

}
