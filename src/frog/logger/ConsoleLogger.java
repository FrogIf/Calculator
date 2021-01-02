package frog.logger;

public class ConsoleLogger extends AbstraceLogger {

    private static final String HOLDER_REGEX = "\\{}";

    public ConsoleLogger(LogLevel level, String name) {
        super(level, name);
    }

    @Override
    protected void outputLog(String msg, Object... objs) {
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
    
}
