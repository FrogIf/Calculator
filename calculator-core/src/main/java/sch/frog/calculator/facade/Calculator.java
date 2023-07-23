package sch.frog.calculator.facade;

import sch.frog.calculator.compile.lexical.IToken;
import sch.frog.calculator.compile.lexical.TextScanner;
import sch.frog.calculator.compile.semantic.GeneralExecuteContext;
import sch.frog.calculator.compile.semantic.result.IResult;
import sch.frog.calculator.compile.semantic.result.IValue;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.facade.NumberMode.Mode;
import sch.frog.calculator.io.IInputStream;
import sch.frog.calculator.io.IOutputStream;
import sch.frog.calculator.number.ComplexNumber;
import sch.frog.calculator.number.IBaseNumber;
import sch.frog.calculator.cell.CellUtil;
import sch.frog.calculator.platform.GeneralCompileManager;
import sch.frog.calculator.util.TreeDisplayUtil;
import sch.frog.calculator.util.TreeDisplayUtil.ITreeNodeReader;
import sch.frog.calculator.util.collection.IList;

public class Calculator {

    private final ITreeNodeReader<ISyntaxNode> treeNodeReader = new ITreeNodeReader<>() {
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
