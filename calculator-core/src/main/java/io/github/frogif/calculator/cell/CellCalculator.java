package io.github.frogif.calculator.cell;

import io.github.frogif.calculator.platform.GeneralCompileManager;
import io.github.frogif.calculator.util.collection.IList;
import io.github.frogif.calculator.compile.lexical.IToken;
import io.github.frogif.calculator.compile.lexical.TextScanner;
import io.github.frogif.calculator.compile.semantic.DoNothingExecuteContext;
import io.github.frogif.calculator.compile.semantic.result.IResult;
import io.github.frogif.calculator.compile.semantic.result.IValue;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.number.impl.ComplexNumber;

public class CellCalculator {

    private final GeneralCompileManager manager = new GeneralCompileManager();

    public ComplexNumber calculate(String expression){
        TextScanner textScanner = new TextScanner(expression);
        IList<IToken> tokens = manager.getLexer().tokenization(textScanner);
        ISyntaxNode expRoot = manager.getAstTreeBuilder().build(tokens);

        DoNothingExecuteContext context = new DoNothingExecuteContext();
        IResult result = expRoot.execute(context);
        if(result.getResultType() == IResult.ResultType.VALUE){
            IValue value = result.getValue();
            return CellUtil.getNumber(value, context);
        }else{
            return null;
        }
    }

}
