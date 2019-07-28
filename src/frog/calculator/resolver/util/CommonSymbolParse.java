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
        IResolverResult resolveResult = resolverResultFactory.createResolverResultBean();
        IRegister registry = register.retrieveRegistryInfo(chars, startIndex);
        if(registry != null){
            IExpression expression = registry.getExpression();
            if(expression != null){
                IExpression exp = expression.clone();
                resolveResult.setExpression(exp);
                String completeSymbol = exp.symbol();
                if(completeSymbol != null){
                    resolveResult.setSymbol(completeSymbol);
                    resolveResult.setEndIndex(startIndex + completeSymbol.length() - 1);
                }
            }
        }
        return resolveResult;
    }

}
