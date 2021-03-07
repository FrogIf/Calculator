package frog.calculator;

import frog.calculator.build.IExpressionTreeBuilder;
import frog.calculator.build.MathExpressionTreeBuilder;
import frog.calculator.connect.DefaultSessionFactory;
import frog.calculator.connect.ICalculatorSession;
import frog.calculator.connect.ISessionFactory;
import frog.calculator.connect.NothingCalculatorSession;
import frog.calculator.exception.BuildException;
import frog.calculator.execute.space.Coordinate;
import frog.calculator.execute.space.IRange;
import frog.calculator.execute.space.ISpace;
import frog.calculator.express.IExpression;
import frog.calculator.math.number.BaseNumber;
import frog.calculator.util.Arrays;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.LinkedList;

public final class Calculator {

    private final IExpressionTreeBuilder builder = new MathExpressionTreeBuilder();

    private static final NothingCalculatorSession DO_NOTHING_SESSION = new NothingCalculatorSession();

    private final IList<ICalculateListener> listeners = new LinkedList<>();

    private final ISessionFactory sessionFactory = new DefaultSessionFactory();

    // 字符ASCII码在IGNORE_CODE之前的均会被忽略(不包括IGNORE_CODE本身)
    private static final int IGNORE_CODE = 33;

    /**
     * 表达式预处理, 清除无用的字符
     */
    private char[] preprocess(String expression){
        char[] chars = expression.toCharArray();
        char[] fix = new char[chars.length];
        int i = 0;
        for (char c : chars) {
            if (c < IGNORE_CODE) {
                continue;
            }
            fix[i++] = c;
        }
        chars = new char[i];
        Arrays.copy(fix, chars, 0, i);
        return chars;
    }

    public String calculate(String expression) throws BuildException{
        return this.calculate(expression, DO_NOTHING_SESSION);
    }

    /**
     * 执行计算
     * @param expression 待计算的表达式
     * @param session    会话
     * @return 解析结果
     */
    public String calculate(String expression, ICalculatorSession session) throws BuildException {
        char[] expChars = preprocess(expression);

        // 解析
        IExpression expTree = builder.build(expChars, session);

        // 执行
        ISpace<BaseNumber> result;
        boolean success = false;
        try{
            result = expTree.interpret();
            success = true;
            // 触发执行成功监听
            if(!listeners.isEmpty()){
                Iterator<ICalculateListener> iterator = listeners.iterator();
                while (iterator.hasNext()){
                    iterator.next().success();
                }
            }
        }catch (Exception e){
            // 触发执行失败监听
            if(!success && !listeners.isEmpty()){
                Iterator<ICalculateListener> iterator = listeners.iterator();
                while (iterator.hasNext()){
                    iterator.next().failed();
                }
            }
            throw e;
        }

        // TODO 改成直接返回Number对象
        IRange range = result.getRange();
        int[] widths = range.maxWidths();
        if(widths.length == 1 && widths[0] == 1){
            return result.get(new Coordinate(0)).toString();
        }else{
            StringBuilder sb = new StringBuilder("[");
            int[] coordinateArr = new int[range.dimension()];
            for(int i = coordinateArr.length - 1; i >= 0; i--){
                int w = widths[i];
                for(int j = 0; j < w; j++){
                    coordinateArr[i] = j;
                    BaseNumber number = result.get(new Coordinate(coordinateArr));
                    if(j > 0){
                        sb.append(',');
                    }
                    sb.append(number == null ? "null" : number.toString());
                }
                if(i > 0){ sb.append(';'); }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public ICalculatorSession getSession(){
        return this.sessionFactory.createSession();
    }

    public void addCalculateListener(ICalculateListener calculateListener){
        this.listeners.add(calculateListener);
    }

}
