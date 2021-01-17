package frog.calculator.compile;

public interface IBuildContext {

    /**
     * 获取语法树构建器
     */
    ISyntaxTreeBuilder getBuilder();
    
}
