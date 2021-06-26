package frog.calculator.compile.syntax;

import frog.calculator.compile.exception.CompileException;
import frog.calculator.compile.lexical.IScannerOperator;

/**
 * 语法树构建器
 */
public interface ISyntaxTreeBuilder {

    /**
     * 将表达式构建为语法树
     * @param scanner 表达式扫描器
     * @return 语法树
     */
    ISyntaxNode build(IScannerOperator scannerOperator) throws CompileException;

}
