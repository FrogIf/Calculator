package frog.calculator.resolver.util;

import frog.calculator.ICalculatorManager;
import frog.calculator.express.IExpression;
import frog.calculator.register.IRegister;
import frog.calculator.resolver.IResolverResult;

public class CommonSymbolParse {

    /**
     * @param chars 待解析char数组
     * @param startIndex 解析起始位置
     * @param register 解析器
     * @param manager 计算器管理器
     * @return 解析结果对象(不为null)
     */
    public static IResolverResult parseExpression(char[] chars, int startIndex, IRegister<IExpression> register, ICalculatorManager manager){
        if(register == null){
            throw new IllegalArgumentException("there is no register.");
        }

        IExpression expression = register.retrieve(chars, startIndex);
        if(expression != null){
            IExpression exp = expression.clone();
            return manager.createResolverResult(exp);
        }
        return null;
    }
}
