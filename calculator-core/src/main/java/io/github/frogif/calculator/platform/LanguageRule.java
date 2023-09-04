package io.github.frogif.calculator.platform;

import io.github.frogif.calculator.compile.lexical.matcher.IMatcher;
import io.github.frogif.calculator.compile.semantic.exec.IExecutor;
import io.github.frogif.calculator.compile.syntax.DeducibleNode;
import io.github.frogif.calculator.compile.syntax.DynamicAssociativityNode;
import io.github.frogif.calculator.compile.syntax.ISyntaxNodeGenerator;
import io.github.frogif.calculator.compile.syntax.UndeducibleNode;

public class LanguageRule {

    private final String word;

    private final IMatcher matcher;

    private final int priority;

    private final IExecutor executor;

    private final Type type;

    private final DynamicAssociativityNode.IAssociativity associativity;

    public LanguageRule(String word, IMatcher matcher, int priority, Type type, IExecutor executor, DynamicAssociativityNode.IAssociativity associativity) {
        if(word == null && matcher == null){
            throw new IllegalLanguageRuleError("word or matcher must be assign");
        }
        if(word != null && matcher != null){
            throw new IllegalArgumentException("word and matcher must assign only one");
        }
        if(type == null){
            throw new IllegalArgumentException("type must be assign");
        }
        if((type == Type.DYNAMIC_ASSOCIATE_AND_LEFT_ASSOCIATE || type == Type.DYNAMIC_ASSOCIATE_AND_NO_LEFT_ASSOCIATE) && associativity == null){
            throw new IllegalArgumentException("associativity must be assign for dynamic associate");
        }
        if(executor == null){
            throw new IllegalArgumentException("executor must be assign");
        }
        this.word = word;
        this.matcher = matcher;
        this.priority = priority;
        this.executor = executor;
        this.type = type;
        this.associativity = associativity;
    }

    public ISyntaxNodeGenerator generator(){
        if(this.type == Type.TERMINAL){
            return (word, position) -> new UndeducibleNode(word, executor, position);
        }else if(this.type == Type.LEFT_ASSOCIATE){
            return (word, position) -> new DeducibleNode(word, priority, DeducibleNode.AssociateType.LEFT, executor, position);
        }else if(this.type == Type.BOTH_ASSOCIATE){
            return (word, position) -> new DeducibleNode(word, priority, executor, position);
        }else if(this.type == Type.RIGHT_ASSOCIATE){
            return (word, position) -> new DeducibleNode(word, priority, DeducibleNode.AssociateType.RIGHT, executor, position);
        }else if(this.type == Type.DYNAMIC_ASSOCIATE_AND_LEFT_ASSOCIATE){
            return (word, position) -> new DynamicAssociativityNode(word, priority, true, associativity.copy(), executor, position);
        }else if(this.type == Type.DYNAMIC_ASSOCIATE_AND_NO_LEFT_ASSOCIATE){
            return (word, position) -> new DynamicAssociativityNode(word, priority, false, associativity.copy(), executor, position);
        }else{
            throw new IllegalLanguageRuleError("unknown rule type : " + type);
        }
    }

    public String getWord() {
        return word;
    }

    public IMatcher getMatcher() {
        return matcher;
    }

    public enum Type{
        /**
         * 终结节点
         */
        TERMINAL,
        /**
         * 左结合节点
         */
        LEFT_ASSOCIATE,
        /**
         * 右结合节点
         */
        RIGHT_ASSOCIATE,
        /**
         * 左右都可结合
         */
        BOTH_ASSOCIATE,
        /**
         * 动态结合节点, 并且是左结合的
         */
        DYNAMIC_ASSOCIATE_AND_LEFT_ASSOCIATE,

        /**
         * 动态结合节点, 左不结合
         */
        DYNAMIC_ASSOCIATE_AND_NO_LEFT_ASSOCIATE;
    }



}
