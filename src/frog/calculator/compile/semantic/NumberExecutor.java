package frog.calculator.compile.semantic;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.math.number.RationalNumber;

/**
 * 数字执行器
 */
public class NumberExecutor implements IExecutor {

    private static final NumberExecutor INSTANCE = new NumberExecutor();

    private NumberExecutor(){
        // hide constructor
    }

    public static NumberExecutor getInstance() {
        return INSTANCE;
    }

    @Override
    public IResult execute(ISyntaxNode token, IExecuteContext context) {
        return null;
    }

    private static BaseNumber parse(String word){
        int dot1 = word.indexOf('.');
        int dot2 = word.indexOf('_');
        if(dot2 == -1){ // 没有循环节
            return BaseNumber.valueOf(word);
        }else{  // 有循环节
            word = word.replace("_", "");
            RationalNumber resultRational = new RationalNumber(word, dot2 - dot1 - 1);
            return BaseNumber.valueOf(resultRational);
        }
    }
    
}
