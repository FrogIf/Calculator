package io.github.frogif.calculator.platform;

import io.github.frogif.calculator.compile.lexical.GeneralLexer;
import io.github.frogif.calculator.compile.lexical.matcher.IMatcher;
import io.github.frogif.calculator.compile.lexical.matcher.NumberMatcher;
import io.github.frogif.calculator.compile.lexical.matcher.PlusMinusMatcher;
import io.github.frogif.calculator.compile.lexical.matcher.WordMatcher;
import io.github.frogif.calculator.compile.semantic.exec.DoNothingExecutor;
import io.github.frogif.calculator.compile.semantic.exec.IExecutor;
import io.github.frogif.calculator.compile.syntax.CommonSyntaxTreeBuilder;
import io.github.frogif.calculator.compile.syntax.DynamicAssociativityNode;
import io.github.frogif.calculator.compile.syntax.ISyntaxTreeBuilder;
import io.github.frogif.calculator.cell.exec.impl.base.AssignExecutor;
import io.github.frogif.calculator.cell.exec.impl.base.BracketExecutor;
import io.github.frogif.calculator.cell.exec.impl.base.CommaExecutor;
import io.github.frogif.calculator.cell.exec.impl.base.ComplexMarkExecutor;
import io.github.frogif.calculator.cell.exec.impl.base.DeclareExecutor;
import io.github.frogif.calculator.cell.exec.impl.base.DivExecutor;
import io.github.frogif.calculator.cell.exec.impl.base.MultExecutor;
import io.github.frogif.calculator.cell.exec.impl.base.NumberExecutor;
import io.github.frogif.calculator.cell.exec.impl.base.PlusMinusExecutor;
import io.github.frogif.calculator.cell.exec.impl.base.PowerExecutor;
import io.github.frogif.calculator.cell.exec.impl.base.VariableExecutor;
import io.github.frogif.calculator.cell.exec.impl.ext.FactorialExecutor;
import io.github.frogif.calculator.cell.exec.impl.ext.PercentExecutor;
import io.github.frogif.calculator.cell.exec.impl.fun.AverageExecutor;
import io.github.frogif.calculator.cell.exec.impl.fun.SumExecutor;

public class GeneralCompileManager {

    private final GeneralLexer lexer = new GeneralLexer();

    private final ISyntaxTreeBuilder astTreeBuilder = new CommonSyntaxTreeBuilder(lexer);

    private static final LanguageRule[] rules = new LanguageRule[]{
            rule(new NumberMatcher(), Integer.MAX_VALUE, LanguageRule.Type.TERMINAL, new NumberExecutor()),
            rule(new PlusMinusMatcher(), 10, LanguageRule.Type.BOTH_ASSOCIATE, new PlusMinusExecutor()),
            rule("=", 5, LanguageRule.Type.BOTH_ASSOCIATE, new AssignExecutor()),
            rule("*", 20, LanguageRule.Type.BOTH_ASSOCIATE, new MultExecutor()),
            rule("/", 20, LanguageRule.Type.BOTH_ASSOCIATE, new DivExecutor()),
            rule("^", 30, LanguageRule.Type.BOTH_ASSOCIATE, new PowerExecutor()),
            rule("!", 40, LanguageRule.Type.LEFT_ASSOCIATE, new FactorialExecutor()),
            rule("%", 40, LanguageRule.Type.LEFT_ASSOCIATE, new PercentExecutor()),
            rule("i", 40, LanguageRule.Type.LEFT_ASSOCIATE, new ComplexMarkExecutor()),
            rule("sum", 50, LanguageRule.Type.RIGHT_ASSOCIATE, new SumExecutor()),
            rule("avg", 50, LanguageRule.Type.RIGHT_ASSOCIATE, new AverageExecutor()),
            rule("@", 60, LanguageRule.Type.RIGHT_ASSOCIATE, new DeclareExecutor()),
            rule(",", 0, LanguageRule.Type.BOTH_ASSOCIATE, new CommaExecutor()),
            rule("(", -1, LanguageRule.Type.DYNAMIC_ASSOCIATE_AND_NO_LEFT_ASSOCIATE, new BracketExecutor(), new EndTokenAssociativity(")")),
            rule(")", 0, LanguageRule.Type.LEFT_ASSOCIATE, new BracketExecutor()),
            rule("{", -1, LanguageRule.Type.DYNAMIC_ASSOCIATE_AND_NO_LEFT_ASSOCIATE, new BracketExecutor(), new EndTokenAssociativity("}")),
            rule("}", 0, LanguageRule.Type.LEFT_ASSOCIATE, new BracketExecutor()),
            rule(new WordMatcher(), 0, LanguageRule.Type.DYNAMIC_ASSOCIATE_AND_NO_LEFT_ASSOCIATE, new VariableExecutor(), new VariableAssociativity()),
            rule("fun", 50, LanguageRule.Type.RIGHT_ASSOCIATE, new DoNothingExecutor()),
            rule("funabc", 50, LanguageRule.Type.RIGHT_ASSOCIATE, new DoNothingExecutor())
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

    public ISyntaxTreeBuilder getAstTreeBuilder() {
        return astTreeBuilder;
    }
}