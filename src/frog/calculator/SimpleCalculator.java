package frog.calculator;

import frog.calculator.compile.lexical.GeneralLexer;
import frog.calculator.compile.lexical.ILexer;
import frog.calculator.compile.lexical.TextScannerOperator;
import frog.calculator.compile.semantic.GeneralExecuteContext;
import frog.calculator.compile.semantic.result.IResult;
import frog.calculator.compile.semantic.result.IValue;
import frog.calculator.compile.semantic.result.IResult.ResultType;
import frog.calculator.compile.syntax.GeneralSyntaxTreeBuilder;
import frog.calculator.compile.syntax.ISyntaxNode;
import frog.calculator.compile.syntax.ISyntaxTreeBuilder;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.math.number.ComplexNumber;
import frog.calculator.micro.MicroCompileManager;
import frog.calculator.micro.MicroUtil;
import frog.calculator.util.collection.IList;
import frog.test.util.tree.SyntaxTreeDisplayUtil;
import frog.test.util.tree.SyntaxTreeDisplayUtil.ITreeNodeReader;

public class SimpleCalculator implements ICalculator<ComplexNumber> {

    private final ISyntaxTreeBuilder builder;

    public SimpleCalculator(){
        ILexer lexer = GeneralLexer.build(new MicroCompileManager().getTokenFetchers());
        builder = new GeneralSyntaxTreeBuilder(lexer);
    }

    public ComplexNumber calculate(String expression, ICalculatorSession session) {
        ISyntaxNode expRoot = this.builder.build(new TextScannerOperator(expression));
        try{
            String treeDisplayStr = SyntaxTreeDisplayUtil.drawTree(expRoot, new ITreeNodeReader<ISyntaxNode>(){
    
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
