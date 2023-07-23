package sch.frog.calculator.cell;

import sch.frog.calculator.compile.lexical.IToken;
import sch.frog.calculator.compile.lexical.TextScanner;
import sch.frog.calculator.compile.semantic.DoNothingExecuteContext;
import sch.frog.calculator.compile.semantic.result.IResult;
import sch.frog.calculator.compile.semantic.result.IValue;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.number.ComplexNumber;
import sch.frog.calculator.platform.GeneralCompileManager;
import sch.frog.calculator.util.collection.IList;

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
