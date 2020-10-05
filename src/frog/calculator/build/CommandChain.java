package frog.calculator.build;

import frog.calculator.build.command.ICommand;
import frog.calculator.build.command.ICommandDetector;
import frog.calculator.exception.BuildException;

class CommandChain {

    private final ICommandDetector commandDetector;

    private CommandNode head;

    private CommandNode tail;

    public CommandChain(ICommandDetector commandDetector) {
        if(commandDetector == null){
            throw new IllegalArgumentException("command detector is null");
        }
        this.commandDetector = commandDetector;
    }

    public int preBuild(char[] chars, int startIndex, IBuildContext buildContext) throws BuildException {
        CommandNode cursor = head;
        while(cursor != null){
            cursor.command.preBuild(chars, startIndex, buildContext);
            cursor = cursor.next;
        }

        return commandDetect(chars, startIndex, buildContext);
    }

    public void postBuild(IBuildContext buildContext){
        CommandNode cursor = tail;
        while(cursor != null){
            boolean valid = cursor.command.postBuild(buildContext);

            // 如果当前命令已失效, 则从chain中删除
            if(!valid){
                if(cursor.previous != null){
                    cursor.previous.next = cursor.next;
                }
                if(cursor.next != null){
                    cursor.next.previous = cursor.previous;
                }
                if(cursor == tail){
                    tail = cursor.previous;
                }
                if(cursor == head){
                    head = cursor.next;
                }
            }

            cursor = cursor.previous;
        }
    }

    /**
     * 探查是否有新的命令, 如果有, 则插入命令调用链, 插入位置, 当前命令后, 下一条命令之前
     * 探查之后, 返回偏移量
     * @param chars 探查字符数组
     * @param startIndex 探查起始位置
     * @param builder builder
     * @return 偏移量
     */
    private int commandDetect(char[] chars, int startIndex, IBuildContext buildContext) throws BuildException {
        ICommand newCmd;
        int commandOffset = 0;
        do{
            newCmd = commandDetector.detect(chars, startIndex);
            if(newCmd == null){ break; }

            CommandNode node = new CommandNode(newCmd);
            if (tail != null) {
                tail.next = node;
                node.previous = tail;
            }
            tail = node;
            if(head == null){
                head = tail;
            }

            commandOffset += newCmd.offset();
            startIndex += newCmd.symbol().length();

            newCmd.preBuild(chars, startIndex, buildContext);
        }while (startIndex < chars.length);

        return commandOffset;
    }

    /**
     * 清理, 重回初始状态
     */
    public void clear(){
        this.head = this.tail = null;
    }

    private static class CommandNode {
        private final ICommand command;
        public CommandNode(ICommand command) {
            this.command = command;
        }
        private CommandNode next;
        private CommandNode previous;
    }
}
