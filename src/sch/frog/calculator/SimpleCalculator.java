package sch.frog.calculator;

import sch.frog.calculator.compile.lexical.GeneralLexer;
import sch.frog.calculator.compile.lexical.ILexer;
import sch.frog.calculator.compile.lexical.TextScannerOperator;
import sch.frog.calculator.compile.semantic.GeneralExecuteContext;
import sch.frog.calculator.compile.semantic.result.IResult;
import sch.frog.calculator.compile.semantic.result.IValue;
import sch.frog.calculator.compile.semantic.result.IResult.ResultType;
import sch.frog.calculator.compile.syntax.GeneralSyntaxTreeBuilder;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.compile.syntax.ISyntaxTreeBuilder;
import sch.frog.calculator.connect.ICalculatorSession;
import sch.frog.calculator.math.number.ComplexNumber;
import sch.frog.calculator.micro.MicroCompileManager;
import sch.frog.calculator.micro.MicroUtil;
import sch.frog.calculator.util.collection.IList;
import sch.frog.test.util.TreeDisplayUtil;
import sch.frog.test.util.TreeDisplayUtil.ITreeNodeReader;

public class SimpleCalculator implements ICalculator<ComplexNumber> {

    private final ISyntaxTreeBuilder builder;

    public SimpleCalculator(){
        ILexer lexer = GeneralLexer.build(new MicroCompileManager().getTokenFetchers());
        builder = new GeneralSyntaxTreeBuilder(lexer);
    }

    public ComplexNumber calculate(String expression, ICalculatorSession session) {
        ISyntaxNode expRoot = this.builder.build(new TextScannerOperator(expression));
        try{
            String treeDisplayStr = TreeDisplayUtil.drawTree(expRoot, new ITreeNodeReader<ISyntaxNode>(){
    
                @Override
                public String label(ISyntaxNode node) {
                    return node.word();
                }
    
                @Override
                public IList<ISyntaxNode> children(ISyntaxNode parent) {
                    return parent.children();
                }
    
            });
            System.out.println(treeDisplayStr);
        }catch(Exception e){
            e.printStackTrace();
        }
        GeneralExecuteContext context = new GeneralExecuteContext(session);
        IResult result = expRoot.execute(context);
        if(result.getResultType() == ResultType.VALUE){
            IValue value = result.getValue();
            return MicroUtil.getNumber(value, context);
        }else{
            return null;
        }
    }
    
    
    
}
