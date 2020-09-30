package frog.calculator.log;

import frog.calculator.express.IExpression;

public class ConsoleCalLogger implements ICalLogger{

    private static final String HOLDER_REGEX = "\\{}";

    @Override
    public void info(String msg, Object... obj) {
        display(msg, obj);
    }

    @Override
    public void debug(String msg, Object... obj) {
        display(msg, obj);
    }

    private void display(String msg, Object[] objs){
        if(objs != null && objs.length > 0){
            String[] fixObjs = new String[objs.length];
            for (int i = 0; i < objs.length; i++){
                Object obj = objs[i];
                if(obj == null){
                    fixObjs[i] = "null";
                }else{
                    fixObjs[i] = obj.toString();
                }
            }
            msg = msg.replaceAll(HOLDER_REGEX, "%s");
            objs = fixObjs;
        }
        System.out.printf(msg + "%n", objs);
    }

    @Override
    public void expression(IExpression expression) {
    }

}
