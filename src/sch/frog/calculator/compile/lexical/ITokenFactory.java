package sch.frog.calculator.compile.lexical;

/**
 * koken工厂
 */
public interface ITokenFactory {
    
    /**
     * token
     * @param word
     * @return
     */
    IToken create(String word);

}
