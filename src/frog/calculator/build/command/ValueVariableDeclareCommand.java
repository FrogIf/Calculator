package frog.calculator.build.command;

import frog.calculator.build.IBuildContext;
import frog.calculator.build.IVariableTableManager;
import frog.calculator.build.resolve.IResolveResult;
import frog.calculator.build.resolve.TruncateResolver;
import frog.calculator.exception.BuildException;
import frog.calculator.express.ValueVariableExpression;

/**
 * 声明命令<br/>
 * 用于定义变量以及函数<br/>
 * 变量定义:<br/>
 * [command][var][assign][value]<br/>
 */
public class ValueVariableDeclareCommand extends AbstractCommand {

    private final String command;

    private final int offset;

    private final TruncateResolver variableResolver;

    public ValueVariableDeclareCommand(String command, String assign) {
        this.command = command;
        this.offset = command.length();
        this.variableResolver = new TruncateResolver(ValueVariableExpression::new, new String[] { assign });
    }

    @Override
    public int offset() {
        return this.offset;
    }

    @Override
    public void preBuild(char[] chars, int startIndex, IBuildContext buildContext) throws BuildException {
        IResolveResult result = this.variableResolver.resolve(chars, startIndex);
        if(result.success()){
            /*
             * if the local variable table is empty, add the variable to session.
             * this command can't create local variable table by itself.
             */
            IVariableTableManager localVariableTableManager = buildContext.getLocalVariableTableManager();
            if(localVariableTableManager.isEmpty()){
                buildContext.getSession().addVariable(result.getExpression());
            }else{
                localVariableTableManager.addVariable(result.getExpression());
            }
        }
    }

    @Override
    public boolean postBuild(IBuildContext buildContext) {
        // do nothing
        return false;
    }

    @Override
    public String symbol() {
        return command;
    }

}
