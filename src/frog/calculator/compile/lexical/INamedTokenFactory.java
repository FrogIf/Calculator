package frog.calculator.compile.lexical;

/**
 * 指定名称的token, 非内置token
 */
public interface INamedTokenFactory {

    IToken create(String word);
    
}
