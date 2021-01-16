package frog.calculator.config;

import frog.calculator.compile.lexical.CommonToken;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.syntax.OpenSyntaxNode;

public class MathTokenHolder implements ITokenHolder {

    private static final IToken[] tokens = new IToken[]{
        new CommonToken("+", new OpenSyntaxNode(null, "+", 10)),
        new CommonToken("-", new OpenSyntaxNode(null, "-", 10))
    };

    @Override
    public IToken[] getTokens() {
        return tokens;
    }
    
}
