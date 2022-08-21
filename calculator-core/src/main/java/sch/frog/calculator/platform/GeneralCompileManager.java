package sch.frog.calculator.platform;

import sch.frog.calculator.compile.lexical.GeneralLexer;
import sch.frog.calculator.compile.lexical.matcher.IMatcher;
import sch.frog.calculator.compile.lexical.matcher.NumberMatcher;
import sch.frog.calculator.compile.lexical.matcher.PlusMinusMatcher;
import sch.frog.calculator.compile.lexical.matcher.WordMatcher;
import sch.frog.calculator.compile.semantic.exec.DoNothingExecutor;
import sch.frog.calculator.compile.semantic.exec.IExecutor;
import sch.frog.calculator.compile.syntax.DynamicAssociativityNode;
import sch.frog.calculator.micro.exec.impl.base.AssignExecutor;
import sch.frog.calculator.micro.exec.impl.base.BracketExecutor;
import sch.frog.calculator.micro.exec.impl.base.CommaExecutor;
import sch.frog.calculator.micro.exec.impl.base.ComplexMarkExecutor;
import sch.frog.calculator.micro.exec.impl.base.DeclareExecutor;
import sch.frog.calculator.micro.exec.impl.base.DivExecutor;
import sch.frog.calculator.micro.exec.impl.base.MultExecutor;
import sch.frog.calculator.micro.exec.impl.base.NumberExecutor;
import sch.frog.calculator.micro.exec.impl.base.PlusMinusExecutor;
import sch.frog.calculator.micro.exec.impl.base.PowerExecutor;
import sch.frog.calculator.micro.exec.impl.base.VariableExecutor;
import sch.frog.calculator.micro.exec.impl.ext.FactorialExecutor;
import sch.frog.calculator.micro.exec.impl.ext.PercentExecutor;
import sch.frog.calculator.micro.exec.impl.fun.AverageExecutor;
import sch.frog.calculator.micro.exec.impl.fun.SumExecutor;
import sch.frog.calculator.platform.LanguageRule.Type;

public class GeneralCompileManager {

    private final GeneralLexer lexer = new GeneralLexer();

    private static final LanguageRule[] rules = new LanguageRule[]{
            rule(new NumberMatcher(), Integer.MAX_VALUE, Type.TERMINAL, new NumberExecutor()),
            rule(new PlusMinusMatcher(), 10, Type.BOTH_ASSOCIATE, new PlusMinusExecutor()),
            rule("=", 5, Type.BOTH_ASSOCIATE, new AssignExecutor()),
            rule("*", 20, Type.BOTH_ASSOCIATE, new MultExecutor()),
            rule("/", 20, Type.BOTH_ASSOCIATE, new DivExecutor()),
            rule("^", 30, Type.BOTH_ASSOCIATE, new PowerExecutor()),
            rule("!", 40, Type.LEFT_ASSOCIATE, new FactorialExecutor()),
            rule("%", 40, Type.LEFT_ASSOCIATE, new PercentExecutor()),
            rule("i", 40, Type.LEFT_ASSOCIATE, new ComplexMarkExecutor()),
            rule("sum", 50, Type.RIGHT_ASSOCIATE, new SumExecutor()),
            rule("avg", 50, Type.RIGHT_ASSOCIATE, new AverageExecutor()),
            rule("@", 60, Type.RIGHT_ASSOCIATE, new DeclareExecutor()),
            rule(",", 0, Type.BOTH_ASSOCIATE, new CommaExecutor()),
            rule("(", -1, Type.DYNAMIC_ASSOCIATE_AND_NO_LEFT_ASSOCIATE, new BracketExecutor(), new EndTokenAssociativity(")")),
            rule(")", 0, Type.LEFT_ASSOCIATE, new BracketExecutor()),
            rule("{", -1, Type.DYNAMIC_ASSOCIATE_AND_NO_LEFT_ASSOCIATE, new BracketExecutor(), new EndTokenAssociativity("}")),
            rule("}", 0, Type.LEFT_ASSOCIATE, new BracketExecutor()),
            rule(new WordMatcher(), 0, Type.DYNAMIC_ASSOCIATE_AND_NO_LEFT_ASSOCIATE, new VariableExecutor(), new VariableAssociativity()),
            rule("fun", 50, Type.RIGHT_ASSOCIATE, new DoNothingExecutor()),
            rule("funabc", 50, Type.RIGHT_ASSOCIATE, new DoNothingExecutor())
    };

    public static LanguageRule rule(IMatcher matcher, int priority, LanguageRule.Type type, IExecutor executor){
        return new LanguageRule(null, matcher, priority, type, executor,null);
    }

    public static LanguageRule rule(String word, int priority, LanguageRule.Type type, IExecutor executor){
        return new LanguageRule(word, null, priority, type, executor, null);
    }

    public static LanguageRule rule(IMatcher matcher, int priority, LanguageRule.Type type, IExecutor executor, DynamicAssociativityNode.IAssociativity associativity){
        return new LanguageRule(null, matcher, priority, type, executor, associativity);
    }

    public static LanguageRule rule(String word, int priority, LanguageRule.Type type, IExecutor executor, DynamicAssociativityNode.IAssociativity associativity){
        return new LanguageRule(word, null, priority, type, executor, associativity);
    }

    public GeneralCompileManager() {
        init();
    }

    private void init(){
        for (LanguageRule rule : rules) {
            String word = rule.getWord();
            if(word != null){
                lexer.register(word, rule.generator());
            }else{
                lexer.register(rule.getMatcher(), rule.generator());
            }
        }
    }

    public GeneralLexer getLexer() {
        return lexer;
    }

    private static class VariableAssociativity implements DynamicAssociativityNode.IAssociativity{

        private boolean access = false;
        @Override
        public boolean peek(String word) {
            if(access){ return false; }
            access = true;
            return "(".equals(word);
        }

        @Override
        public DynamicAssociativityNode.IAssociativity copy() {
            return new VariableAssociativity();
        }
    }

    private static class EndTokenAssociativity implements DynamicAssociativityNode.IAssociativity {

        private final String token;

        private boolean open = true;

        public EndTokenAssociativity(String token) {
            this.token = token;
        }

        @Override
        public boolean peek(String word) {
            if(open){
                open = !token.equals(word);
                return true;
            }
            return false;
        }

        @Override
        public DynamicAssociativityNode.IAssociativity copy() {
            return new EndTokenAssociativity(this.token);
        }
    }

}