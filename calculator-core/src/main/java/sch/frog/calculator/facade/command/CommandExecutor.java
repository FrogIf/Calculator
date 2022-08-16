package sch.frog.calculator.facade.command;

import sch.frog.calculator.facade.ExecuteSession;
import sch.frog.calculator.util.IComparator;
import sch.frog.calculator.util.collection.ArrayList;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.IMap;
import sch.frog.calculator.util.collection.TreeMap;

public class CommandExecutor {

    private final IMap<String, ICommand> commandMap = new TreeMap<>(IComparator.STRING_DEFAULT_COMPARATOR);

    public CommandExecuteResult execute(String statement, ExecuteSession session){
        if(!statement.startsWith(CommandConstants.PREFIX_MARK)){
            throw new IllegalArgumentException("[" + statement + "] is not command.");
        }

        // 解析
        char[] charArray = statement.toCharArray();
        int i = 1;
        StringBuilder commandBuilder = new StringBuilder();
        for(; i < charArray.length; i++){
            char ch = charArray[i];
            if(ch == ' '){
                break;
            }
            commandBuilder.append(ch);
        }

        i = skipBlank(i, charArray);

        IList<String> args = new ArrayList<>();
        StringBuilder argBuilder = new StringBuilder();
        for(; i < charArray.length; i++){
            char ch = charArray[i];
            if(ch == ' '){
                args.add(argBuilder.toString());
                argBuilder = new StringBuilder();
                i = skipBlank(i, charArray) - 1;
                continue;
            }
            argBuilder.append(ch);
        }

        if(argBuilder.length() > 0){
            args.add(argBuilder.toString());
        }

        return execute(commandBuilder.toString(), args, session);
    }

    private CommandExecuteResult execute(String command, IList<String> args, ExecuteSession session){
        String commandFix = command.toLowerCase();
        ICommand cmd = commandMap.get(commandFix);
        if(cmd == null){
            CommandExecuteResult commandExecuteResult = new CommandExecuteResult();
            commandExecuteResult.setSuccess(false);
            commandExecuteResult.setErrorMsg("unrecognized command [" + command + "]");
            return commandExecuteResult;
        }
        return cmd.execute(session, args);
    }


    private int skipBlank(int start, char[] chars){
        int i = start;
        while(i < chars.length && chars[i] == ' '){
            i++;
        }
        return i;
    }

    public void regist(ICommand command){
        commandMap.put(command.literal().toLowerCase(), command);
    }

}
