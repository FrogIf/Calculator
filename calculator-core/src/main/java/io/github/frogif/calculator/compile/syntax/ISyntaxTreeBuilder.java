package io.github.frogif.calculator.compile.syntax;

import io.github.frogif.calculator.compile.lexical.IToken;
import io.github.frogif.calculator.exception.CompileException;
import io.github.frogif.calculator.util.collection.IList;

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
