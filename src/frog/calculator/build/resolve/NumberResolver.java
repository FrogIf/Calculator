package frog.calculator.build.resolve;

import frog.calculator.execute.IOperator;
import frog.calculator.execute.base.NumberOpr;
import frog.calculator.express.EndPointExpression;
import frog.calculator.express.IExpression;

/**
 * 数字表达式解析器
 */
public final class NumberResolver extends AbstractResolver {

    private final IOperator operator = new NumberOpr();

    @Override
    public IResolveResult resolve(char[] chars, int startIndex) {
        StringBuilder numberBuilder = new StringBuilder();

        int oldStart = startIndex;
        boolean hasDot = false; // 记录是否已经找到小数点
        for(; startIndex < chars.length; startIndex++){
            char ch = chars[startIndex];

            if(ch >= '0' && ch <= '9'){
                numberBuilder.append(ch);
            }else if(ch == '.' && !hasDot){
                hasDot = true;
                numberBuilder.append(ch);
            }else if(ch == '_' && hasDot){
                numberBuilder.append(ch);
            }else{
                break;
            }
        }

        if(numberBuilder.length() != 0){
            IExpression numberExpression = new EndPointExpression(numberBuilder.toString(), operator);
            NumberResolveResult resolveResult = new NumberResolveResult();
            resolveResult.expression = numberExpression;
            resolveResult.offset = startIndex - oldStart;
            return resolveResult;
        }
        return EMPTY_RESULT;
    }

    private static class NumberResolveResult implements IResolveResult {

        private IExpression expression;

        private int offset;

        @Override
        public IExpression getExpression() {
            return this.expression;
        }

        @Override
        public int offset() {
            return this.offset;
        }

        @Override
        public boolean success() {
            return true;
        }
    }

}
