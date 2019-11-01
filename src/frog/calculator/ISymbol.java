package frog.calculator;

public interface ISymbol {
    /**
     * 表达式字面值的字符串表示<br />
     * 约定: 返回值需与传入待解析字符串中完全吻合, 内部逻辑需要使用这一约定<br />
     * 如果是动态生成表达式, 如结果表达式, 该字面值也必须保证是解析器可以识别的
     * @return 符号字面量
     */
    String symbol();
}
