package frog.calculator.build.command;

import frog.calculator.build.IBuildContext;
import frog.calculator.exception.BuildException;
import frog.calculator.util.StringUtils;

public class BlockCommand extends AbstractCommand {

    private final String blockStart;

    private final String blockEnd;

    private boolean hasInit = false;

    private int count = 0;

    private boolean hasEnd = false;

    public BlockCommand(String blockStart, String blockEnd) {
        this.blockStart = blockStart;
        this.blockEnd = blockEnd;
    }

    @Override
    public int offset() {
        return 0;
    }

    @Override
    public void preBuild(char[] chars, int startIndex, IBuildContext context) throws BuildException {
        if(!hasInit){
            context.getLocalVariableTableManager().createLocalVariableTable();
            hasInit = true;
        }

        if(StringUtils.startWith(chars, startIndex, this.blockStart)){
            count++;
        }else{
            hasEnd = StringUtils.startWith(chars, startIndex, this.blockEnd) && count-- == 0;
            if(hasEnd){
                context.getLocalVariableTableManager().popLocalVariableTable();
            }
        }
    }

    @Override
    public boolean postBuild(IBuildContext context) {
        return hasEnd;
    }

    @Override
    public String symbol() {
        return this.blockStart;
    }
}
