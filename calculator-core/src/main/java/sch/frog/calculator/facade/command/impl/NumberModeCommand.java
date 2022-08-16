package sch.frog.calculator.facade.command.impl;

import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.facade.command.CommandExecuteResult;
import sch.frog.calculator.facade.ExecuteSession;
import sch.frog.calculator.facade.command.ICommand;
import sch.frog.calculator.facade.NumberMode;

public class NumberModeCommand implements ICommand {

    @Override
    public String literal() {
        return "nummode";
    }

    @Override
    public CommandExecuteResult execute(ExecuteSession session, IList<String> args) {
        CommandExecuteResult result = new CommandExecuteResult();
        result.setSuccess(false);
        if(args.isEmpty()){
            result.setErrorMsg("no args can be used.");
            result.setSuccess(false);
        }else{
            String mode = args.get(0);
            mode = mode.toLowerCase();
            if("none".equals(mode)){
                session.setNumberMode(new NumberMode(NumberMode.Mode.NONE, 0));
                result.setSuccess(true);
                if(args.size() > 1){
                    result.setWarnMsg("only one args needed, but provide " + args.size());
                }
            }else {
                if(args.size() == 1){
                    result.setErrorMsg("2 args required.");
                }else{
                    String scaleStr = args.get(1);
                    int scale = -1;
                    try{
                        scale = Integer.parseInt(scaleStr);
                    }catch(NumberFormatException e){
                        result.setErrorMsg("arg2 must be number.");
                        return result;
                    }
                    if(scale < 0){
                        result.setErrorMsg("args must positive number.");
                    }else{
                        result.setSuccess(true);
                        if("up".equals(mode)){
                            session.setNumberMode(new NumberMode(NumberMode.Mode.UP, scale));
                        }else if("down".equals(mode)){
                            session.setNumberMode(new NumberMode(NumberMode.Mode.DOWN, scale));
                        }else if("half_up".equals(mode)){
                            session.setNumberMode(new NumberMode(NumberMode.Mode.HALF_UP, scale));
                        }else if("half_down".equals(mode)){
                            session.setNumberMode(new NumberMode(NumberMode.Mode.HALF_DOWN, scale));
                        }else if("half_even".equals(mode)){
                            session.setNumberMode(new NumberMode(NumberMode.Mode.HALF_EVEN, scale));
                        }else if("ceiling".equals(mode)){
                            session.setNumberMode(new NumberMode(NumberMode.Mode.CEILING, scale));
                        }else if("floor".equals(mode)){
                            session.setNumberMode(new NumberMode(NumberMode.Mode.FLOOR, scale));
                        }else if("scientific".equals(mode) || "e".equals(mode)){
                            session.setNumberMode(new NumberMode(NumberMode.Mode.SCIENTIFIC, scale));
                        }else if("plain".equals(mode)){
                            session.setNumberMode(new NumberMode(NumberMode.Mode.PLAIN, scale));
                        }else{
                            result.setSuccess(false);                            
                            result.setErrorMsg("unrecognized mode : " + mode);
                        }
                    }
                }
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
