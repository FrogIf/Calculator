package frog.calculator.compile.semantic;

/**
 * 执行结果
 */
public interface IResult {

    /**
     * 是否执行成功
     * @return
     */
    boolean success();

    /**
     * 执行结果
     * @return
     */
    IValue value();
    
}
