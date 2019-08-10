package frog.calculator.resolver.util;

import frog.calculator.express.IExpression;
import frog.calculator.register.IRegister;
import frog.calculator.resolver.IResolverResult;
import frog.calculator.resolver.IResolverResultFactory;

public class CommonSymbolParse {

    /**
     * @param chars 待解析char数组
     * @param startIndex 解析起始位置
     * @param register 解析器
     * @param resolverResultFactory 解析结果工厂
     * @return 解析结果对象(不为null)
     */
    public static IResolverResult parseExpression(char[] chars, int startIndex, IRegister register, IResolverResultFactory resolverResultFactory){
        if(register == null){
            throw new IllegalArgumentException("there is no register.");
        }

        IExpression expression = register.retrieve(chars, startIndex);
        if(expression != null){
            IExpression exp = expression.clone();
            return generateResult(exp, startIndex, resolverResultFactory);
        }
        return resolverResultFactory.createResolverResultBean();
    }

    /**
     * 将指定的表达是包装成解析结果
     * @param expression
     * @param startIndex
     * @param resolverResultFactory
     * @return
     */
    public static IResolverResult generateResult(IExpression expression, int startIndex, IResolverResultFactory resolverResultFactory){
        IResolverResult result = resolverResultFactory.createResolverResultBean();
        if(expression != null) {
            result.setEndIndex(startIndex + expression.symbol().length() - 1);
            result.setExpression(expression);
            result.setSymbol(expression.symbol());
        }
        return result;
    }

}
