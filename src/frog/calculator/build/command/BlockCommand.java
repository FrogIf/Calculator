package frog.calculator.build.command;

import frog.calculator.build.IBuildContext;
import frog.calculator.exception.BuildException;

public class BlockCommand extends AbstractCommand {

    @Override
    public int offset() {
        return 0;
    }

    @Override
    public void preBuild(char[] chars, int startIndex, IBuildContext context) throws BuildException {

    }

    @Override
    public boolean postBuild(IBuildContext context) {
        return false;
    }

    @Override
    public String symbol() {
        return null;
    }
}
