package sch.frog.calculator.facade;

import sch.frog.calculator.compile.lexical.GeneralLexer;
import sch.frog.calculator.compile.lexical.ILexer;
import sch.frog.calculator.compile.lexical.TextScannerOperator;
import sch.frog.calculator.compile.semantic.GeneralExecuteContext;
import sch.frog.calculator.compile.semantic.result.IResult;
import sch.frog.calculator.compile.semantic.result.IValue;
import sch.frog.calculator.compile.syntax.GeneralSyntaxTreeBuilder;
import sch.frog.calculator.compile.syntax.ISyntaxNode;
import sch.frog.calculator.compile.syntax.ISyntaxTreeBuilder;
import sch.frog.calculator.facade.NumberMode.Mode;
import sch.frog.calculator.facade.command.CommandExecuteResult;
import sch.frog.calculator.facade.command.CommandHandler;
import sch.frog.calculator.io.IInputStream;
import sch.frog.calculator.io.IOutputStream;
import sch.frog.calculator.math.number.ComplexNumber;
import sch.frog.calculator.math.number.IBaseNumber;
import sch.frog.calculator.micro.MicroCompileManager;
import sch.frog.calculator.micro.MicroUtil;
import sch.frog.calculator.util.StringUtils;
import sch.frog.calculator.util.TreeDisplayUtil;
import sch.frog.calculator.util.TreeDisplayUtil.ITreeNodeReader;
import sch.frog.calculator.util.collection.IList;

public class Calculator {

    private final ISyntaxTreeBuilder builder;

    private final CommandHandler commandHandler = new CommandHandler();

    public Calculator(){
        ILexer lexer = GeneralLexer.build(new MicroCompileManager().getTokenFetchers());
        builder = new GeneralSyntaxTreeBuilder(lexer);
    }

    public void calculate(IInputStream in, IOutputStream out, ExecuteSession session){
        String line;
        while((line = in.readLine()) != null){
            if(commandHandler.isCommand(line)){
                CommandExecuteResult result = commandHandler.execute(line, session);
                if(StringUtils.isBlank(result.getMsg()) && result.getSuccess()){
                    out.println("success");
                }else{
                    out.println(result.getMsg());
                }
            }else{
                ISyntaxNode expRoot = this.builder.build(new TextScannerOperator(line));
                if(session.showAST()){
                    String treeDisplayStr = TreeDisplayUtil.drawTree(expRoot, new ITreeNodeReader<>() {
                        @Override
                        public String label(ISyntaxNode node) {
                            return node.word();
                        }

                        @Override
                        public IList<ISyntaxNode> children(ISyntaxNode parent) {
                            return parent.children();
                        }
                    });
                    out.println(treeDisplayStr);
                }

                GeneralExecuteContext context = new GeneralExecuteContext(session.getCalculatorSession());
                IResult result = expRoot.execute(context);
                if(result.getResultType() == IResult.ResultType.VALUE){
                    IValue value = result.getValue();
                    ComplexNumber number = MicroUtil.getNumber(value, context);
                    out.println(numberToString(number, session));
                }
            }
        }
    }

    private String numberToString(IBaseNumber number, ExecuteSession session){
        NumberMode numberMode = session.getNumberMode();
        int scale = numberMode.getScale();
        Mode mode = numberMode.getMode();
        return mode.hanle(number, scale);
    }

    
    
}
