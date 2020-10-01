package frog.calculator.build.resolve;

import frog.calculator.express.IExpression;

/**
 * 数字表达式解析器
 */
public class NumberResolver extends AbstractResolver {

    private ISymbolExpressionFactory numberExpressionFactory;

    public NumberResolver(ISymbolExpressionFactory numberExpressionFactory, IResolverResultFactory resolverResultFactory) {
        super(resolverResultFactory);
        this.numberExpressionFactory = numberExpressionFactory;
    }

    @Override
    public IResolverResult resolve(char[] chars, int startIndex) {
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
            IExpression numberExpression = numberExpressionFactory.createExpression(numberBuilder.toString());
            IResolverResult resolveResult = this.resolverResultFactory.createResolverResultBean(numberExpression);
            resolveResult.setOffset(startIndex - oldStart);
            return resolveResult;
        }
        return null;
    }

}
