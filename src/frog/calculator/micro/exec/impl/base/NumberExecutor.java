package frog.calculator.micro.exec.impl.base;

import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.math.number.RationalNumber;
import frog.calculator.micro.exec.AbstractMicroExecutor;
import frog.calculator.micro.exec.MicroExecuteContext;
import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;

/**
 * 数字执行器
 */
public class NumberExecutor extends AbstractMicroExecutor {

    @Override
    protected IList<ComplexNumber> evaluate(ISyntaxNode self, IList<ComplexNumber> children, MicroExecuteContext context) {
        ComplexNumber number = parse(self.word());
        ArrayList<ComplexNumber> result = new ArrayList<>(1);
        result.add(number);
        return result;
    }

    private static ComplexNumber parse(String word){
        int dot1 = word.indexOf('.');
        int dot2 = word.indexOf('_');
        RationalNumber rationalNumber;
        if(dot2 == -1){ // 没有循环节
            rationalNumber = new RationalNumber(word);
        }else{  // 有循环节
            word = word.replace("_", "");
            rationalNumber = new RationalNumber(word, dot2 - dot1 - 1);
        }
        return new ComplexNumber(rationalNumber);
    }
}
