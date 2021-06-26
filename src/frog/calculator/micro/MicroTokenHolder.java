package frog.calculator.micro;

import frog.calculator.compile.lexical.CommonToken;
import frog.calculator.compile.lexical.IToken;
import frog.calculator.compile.syntax.ContainerNode;
import frog.calculator.compile.syntax.ISyntaxNodeGenerator;
import frog.calculator.compile.syntax.NonterminalNode;
import frog.calculator.compile.syntax.TerminalNode;
import frog.calculator.compile.syntax.NonterminalNode.AssociateType;
import frog.calculator.micro.exec.impl.base.AddExecutor;
import frog.calculator.micro.exec.impl.base.BracketExecutor;
import frog.calculator.micro.exec.impl.base.ComplexMarkExecutor;
import frog.calculator.micro.exec.impl.base.DeclareExecutor;
import frog.calculator.micro.exec.impl.base.DivExecutor;
import frog.calculator.micro.exec.impl.base.DotExecutor;
import frog.calculator.micro.exec.impl.base.EqualExecutor;
import frog.calculator.micro.exec.impl.base.MultExecutor;
import frog.calculator.micro.exec.impl.base.PowerExecutor;
import frog.calculator.micro.exec.impl.base.SubExecutor;
import frog.calculator.micro.exec.impl.ext.FactorialExecutor;
import frog.calculator.micro.exec.impl.ext.PercentExecutor;
import frog.calculator.micro.exec.impl.fun.AverageExecutor;
import frog.calculator.micro.exec.impl.fun.SumExecutor;

class MicroTokenHolder {

    private MicroTokenHolder(){
        // do nothing
    }

    private static final ISyntaxNodeGenerator[] builders = new ISyntaxNodeGenerator[] { 
        new NonterminalNode("=", 5, new EqualExecutor()),
        new NonterminalNode("*", 20, new MultExecutor()), 
        new NonterminalNode("/", 20, new DivExecutor()),
        new NonterminalNode("^", 30, new PowerExecutor()), 
        new NonterminalNode("!", 40, AssociateType.LEFT, new FactorialExecutor()),
        new NonterminalNode("%", 40, AssociateType.LEFT, new PercentExecutor()),
        new NonterminalNode("i", 40, AssociateType.LEFT, new ComplexMarkExecutor()),
        new NonterminalNode("sum", 50, AssociateType.RIGHT, new SumExecutor()),
        new NonterminalNode("avg", 50, AssociateType.RIGHT, new AverageExecutor()),
        new NonterminalNode("@", 60, AssociateType.RIGHT, new DeclareExecutor()),
        new ContainerNode("(", ")", new BracketExecutor()),
        new NonterminalNode(",", 0, new DotExecutor()),
        new TerminalNode(")", null)
    };

    public static final IToken ADD_TOKEN =  new CommonToken("+", new NonterminalNode("+", 10, new AddExecutor()));

    public static final IToken SUB_TOKEN =  new CommonToken("-", new NonterminalNode("-", 10, new SubExecutor()));

    public static IToken[] getTokens() {
        IToken[] tokens = new IToken[builders.length];
        for(int i = 0, len = builders.length; i < len; i++){
            ISyntaxNodeGenerator b = builders[i];
            tokens[i] = new CommonToken(b.word(), b);
        }
        return tokens;
    }
    
}
