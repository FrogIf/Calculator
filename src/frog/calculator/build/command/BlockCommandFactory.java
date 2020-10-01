package frog.calculator.build.command;

public class BlockCommandFactory extends AbstractCommandFactory{

    private String blockStart;

    private String blockEnd;

    public BlockCommandFactory(String blockStart, String blockEnd) {
        super(blockStart);
        this.blockStart = blockStart;
        this.blockEnd = blockEnd;
    }

    @Override
    public ICommand instance() {
        return new BlockCommand();
    }
}
