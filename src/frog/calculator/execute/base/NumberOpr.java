package frog.calculator.execute.base;

import frog.calculator.execute.AbstractOperator;
import frog.calculator.execute.space.AtomSpace;
import frog.calculator.execute.space.ISpace;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.math.number.RationalNumber;
import frog.calculator.util.StringUtils;

public class NumberOpr extends AbstractOperator {
    @Override
    public ISpace<BaseNumber> evaluate(IExpression exp) {
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
