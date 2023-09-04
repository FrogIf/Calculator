package io.github.frogif.calculator.facade;

import io.github.frogif.calculator.platform.GeneralCompileManager;
import io.github.frogif.calculator.compile.lexical.IToken;
import io.github.frogif.calculator.compile.lexical.TextScanner;
import io.github.frogif.calculator.compile.semantic.GeneralExecuteContext;
import io.github.frogif.calculator.compile.semantic.result.IResult;
import io.github.frogif.calculator.compile.semantic.result.IValue;
import io.github.frogif.calculator.compile.syntax.ISyntaxNode;
import io.github.frogif.calculator.facade.NumberMode.Mode;
import io.github.frogif.calculator.io.IInputStream;
import io.github.frogif.calculator.io.IOutputStream;
import io.github.frogif.calculator.number.impl.ComplexNumber;
import io.github.frogif.calculator.number.impl.IBaseNumber;
import io.github.frogif.calculator.cell.CellUtil;
import io.github.frogif.calculator.util.TreeDisplayUtil;
import io.github.frogif.calculator.util.TreeDisplayUtil.ITreeNodeReader;
import io.github.frogif.calculator.util.collection.IList;

public class Calculator {

    private final ITreeNodeReader<ISyntaxNode> treeNodeReader = new ITreeNodeReader<ISyntaxNode>() {
        @Override
        public String label(ISyntaxNode node) {
            return node.word();
        }

        @Override
        public IList<ISyntaxNode> children(ISyntaxNode parent) {
            return parent.children();
        }
    };

    private final GeneralCompileManager manager = new GeneralCompileManager();

    public void calculate(IInputStream in, IOutputStream out, ExecuteSession session, IFallback fallback){
        String line;
        SessionConfiguration configuration = session.getSessionConfiguration();
        boolean showAST = configuration.isShowAST();
        while((line = in.readLine()) != null){
            try{
                TextScanner textScanner = new TextScanner(line);
                IList<IToken> tokens = manager.getLexer().tokenization(textScanner);
                ISyntaxNode expRoot = manager.getAstTreeBuilder().build(tokens);
                if(showAST){
                    String treeDisplayStr = TreeDisplayUtil.drawTree(expRoot, treeNodeReader);
                    out.println(treeDisplayStr);
                }

                GeneralExecuteContext context = new GeneralExecuteContext(session.getCalculatorSession());
                IResult result = expRoot.execute(context);
                if(result.getResultType() == IResult.ResultType.VALUE){
                    IValue value = result.getValue();
                    ComplexNumber number = CellUtil.getNumber(value, context);
                    out.println(numberToString(number, session));
                }
            }catch (Exception e){
                if(fallback == null){
                    break;
                }else{
                    fallback.handle(e);
                }
            }
        }
    }

    private String numberToString(IBaseNumber number, ExecuteSession session){
        NumberMode numberMode = session.getSessionConfiguration().getNumberMode();
        int scale = numberMode.getScale();
        Mode mode = numberMode.getMode();
        return mode.hanle(number, scale);
    }

    
    
}
