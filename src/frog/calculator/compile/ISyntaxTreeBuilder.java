package frog.calculator.compile;

import frog.calculator.compile.exception.CompileException;
import frog.calculator.compile.lexical.IScanner;
import frog.calculator.compile.syntax.ISyntaxNode;

public interface ISyntaxTreeBuilder {

    /**
     * 将表达式构建为语法树
     * @param scanner 表达式扫描器
     * @return 语法树
     */
    ISyntaxNode build(IScanner scanner) throws CompileException;

}
