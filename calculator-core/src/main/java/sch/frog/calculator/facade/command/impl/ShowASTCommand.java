package sch.frog.calculator.facade.command.impl;

import sch.frog.calculator.facade.ExecuteSession;
import sch.frog.calculator.facade.command.CommandExecuteResult;
import sch.frog.calculator.facade.command.ICommand;
import sch.frog.calculator.util.collection.IList;

public class ShowASTCommand implements ICommand {

    @Override
    public String literal() {
        return "showast";
    }

    @Override
    public CommandExecuteResult execute(ExecuteSession session, IList<String> args) {
        CommandExecuteResult result = new CommandExecuteResult();
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

    @Override
    public String help() {
        // TODO 帮助文档
        return null;
    }

}
