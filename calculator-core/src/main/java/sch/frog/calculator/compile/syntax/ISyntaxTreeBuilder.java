package sch.frog.calculator.compile.syntax;

import sch.frog.calculator.exception.CompileException;
import sch.frog.calculator.compile.lexical.IToken;
import sch.frog.calculator.util.collection.IList;

/**
 * 语法树构建器
 */
public interface ISyntaxTreeBuilder {

    /**
     * 将表达式构建为语法树
     * @param tokens token集合
     * @return 语法树
     */
    ISyntaxNode build(IList<IToken> tokens) throws CompileException;

}
