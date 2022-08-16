package sch.frog.calculator.facade.commands;

import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.facade.ExecResult;
import sch.frog.calculator.facade.ExecuteSession;
import sch.frog.calculator.facade.ICommand;

public class ShowASTCommand implements ICommand {

    @Override
    public String literal() {
        return "show_ast";
    }

    @Override
    public ExecResult execute(ExecuteSession session, IList<String> args) {
        ExecResult result = new ExecResult();
        result.setSuccess(false);
        if(args.isEmpty()){
            result.setErrorMsg("no args assign.");
        }else{
            String arg = args.get(0);
            arg = arg.toLowerCase();
            if("on".equals(arg)){
                session.setShowAST(true);
                result.setSuccess(true);
            }else if("off".equals(arg)){
                session.setShowAST(false);
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
                result.setErrorMsg("unrecognized args : " + arg);
            }
            if(result.getSuccess() && args.size() > 1){
                result.setWarnMsg("only one args needed, but provide " + args.size());
            }
        }
        return result;
    }
    
}
