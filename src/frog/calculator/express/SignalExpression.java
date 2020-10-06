package frog.calculator.express;

import frog.calculator.math.number.BaseNumber;
import frog.calculator.execute.space.ISpace;

/**
 * 信号表达式, 不参与运算, 一般用于指示一个代码块的结束等
 */
public class SignalExpression extends EndPointExpression {

    public SignalExpression(String symbol) {
        super(symbol);
    }

    @Override
    public ISpace<BaseNumber> interpret() {
        throw new IllegalStateException("access illegal.");
    }
}
