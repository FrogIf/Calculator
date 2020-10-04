package frog.calculator.build.resolve;

/**
 * 解析器, 将字符串解析为一个IExpression对象
 */
public interface IResolver {

    /**
     * @param chars 待解析的字符数组
     * @param startIndex 指定解析开始的位置
     * @return 返回解析结果, 永远不会返回null, 如果解析失败, 返回空结果
     * FIXME 解析失败之后, 仍返回对象, 只不过其中的expression为null
     */
    IResolveResult resolve(char[] chars, int startIndex);
}
