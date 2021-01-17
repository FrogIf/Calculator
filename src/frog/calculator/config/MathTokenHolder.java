package frog.calculator.config;

import frog.calculator.compile.lexical.CommonToken;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.syntax.ISyntaxNodeBuilder;
import frog.calculator.compile.syntax.OpenSyntaxNode;
import frog.calculator.compile.syntax.OpenSyntaxNode.AssociateType;

public class MathTokenHolder implements ITokenHolder {

    private static final ISyntaxNodeBuilder[] builders = new ISyntaxNodeBuilder[]{
        new OpenSyntaxNode(null, "+", 10),
        new OpenSyntaxNode(null, "-", 10),
        new OpenSyntaxNode(null, "*", 20),
        new OpenSyntaxNode(null, "/", 20),
        new OpenSyntaxNode(null, "^", 30),
        new OpenSyntaxNode(null, "!", 40, AssociateType.LEFT),
        new OpenSyntaxNode(null, "%", 40, AssociateType.LEFT)
    };

    @Override
    public IToken[] getTokens() {
        IToken[] tokens = new IToken[builders.length];
        for(int i = 0, len = builders.length; i < len; i++){
            ISyntaxNodeBuilder b = builders[i];
            tokens[i] = new CommonToken(b.word(), b);
        }
        return tokens;
    }
    
}
