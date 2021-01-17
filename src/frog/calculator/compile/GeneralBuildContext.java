package frog.calculator.compile;

public class GeneralBuildContext implements IBuildContext {

    private final ISyntaxTreeBuilder builder;

    public GeneralBuildContext(ISyntaxTreeBuilder builder){
        this.builder = builder;
    }

    @Override
    public ISyntaxTreeBuilder getBuilder() {
        return this.builder;
    }
    
}
