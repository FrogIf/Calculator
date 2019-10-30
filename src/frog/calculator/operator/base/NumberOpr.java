package frog.calculator.operator.base;

import frog.calculator.express.IExpression;
import frog.calculator.math.BaseNumber;
import frog.calculator.math.rational.RationalNumber;
import frog.calculator.operator.AbstractOperator;
import frog.calculator.space.AtomSpace;
import frog.calculator.space.ISpace;
import frog.calculator.util.StringUtils;

public class NumberOpr extends AbstractOperator {
    @Override
    public ISpace<BaseNumber> operate(IExpression exp) {
        BaseNumber number = parse(exp.symbol());
        return new AtomSpace<>(number);
    }

    private static BaseNumber parse(String symbol){
        if(StringUtils.isBlank(symbol)){
            throw new IllegalArgumentException("can't parse this number.");
        }

        int dot1 = symbol.indexOf('.');
        int dot2 = symbol.indexOf('_');
        if(dot2 == -1){ // 没有循环节
            return BaseNumber.valueOf(symbol);
        }else{  // 有循环节
            symbol = symbol.replace("_", "");
            RationalNumber resultRational = new RationalNumber(symbol, dot2 - dot1 - 1);
            return BaseNumber.valueOf(resultRational);
        }
    }
}
