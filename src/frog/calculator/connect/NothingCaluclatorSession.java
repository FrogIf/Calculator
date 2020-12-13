package frog.calculator.connect;

import frog.calculator.build.resolve.IResolveResult;
import frog.calculator.exception.DuplicateSymbolException;
import frog.calculator.express.IExpression;

/**
 * 不起任何作用的calculator session
 * 由于整个计算过程中是有session参与的, 但是有时候, 又没有变量的需求, 也
 * 就没有必要提供session, 只想进行简单的计算, 这时, 可以使用这个session占位
 */
public class NothingCaluclatorSession extends AbstractCalculatorSession {

    private static final NothingResovleResult result = new NothingResovleResult();

    @Override
    public void addVariable(IExpression expression) throws DuplicateSymbolException {
        throw new UnsupportedOperationException("this session can't store vairable.");
    }

    @Override
    public IResolveResult resolve(char[] chars, int startIndex) {
        return result;
    }

    private static class NothingResovleResult implements IResolveResult{

        @Override
        public IExpression getExpression() {
            return null;
        }

        @Override
        public int offset() {
            return 0;
        }

        @Override
        public boolean success() {
            return false;
        }

    }
    
}
