package frog.calculator.compile.lexical;

/**
 * 数字koken工厂
 */
public interface INumberTokenFactory {
    
    /**
     * 创建一个数字token
     * @param word
     * @return
     */
    IToken create(String word);

}
