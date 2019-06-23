package frog.calculator.resolve;

/**
 * 解析器, 将字符串解析为一个个IExpression对象
 */
public interface IResolver {

    /**
     * @param chars 待解析的字符数组
     * @param startIndex 指定解析开始的位置
     * @return 返回解析结果对象, 不会返回null
     */
    IResolveResult resolve(char[] chars, int startIndex);

}
