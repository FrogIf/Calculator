package frog.calculator.resolver;

/**
 * 解析器, 将字符串解析为一个IExpression对象
 */
public interface IResolver {

    /**
     * @param chars 待解析的字符数组
     * @param startIndex 指定解析开始的位置
     * @return 返回解析结果, 如果解析失败, 返回null
     */
    IResolverResult resolve(char[] chars, int startIndex);
}
