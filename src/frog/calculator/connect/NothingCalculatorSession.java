package frog.calculator.connect;

import frog.calculator.compile.syntax.ISyntaxNode;

/**
 * 不起任何作用的calculator session
 * 由于整个计算过程中是有session参与的, 但是有时候, 又没有变量的需求, 也
 * 就没有必要提供session, 只想进行简单的计算, 这时, 可以使用这个session占位
 */
public class NothingCalculatorSession implements ICalculatorSession {

    @Override
    public void addVariable(ISyntaxNode node) {
        throw new UnsupportedOperationException("this session can't store vairable.");
    }

    @Override
    public ISyntaxNode getVariable(String name) {
        return null;
    }

}
