package frog.calculator.analyzer;

public class DefaultAnalyzer implements IAnalyzer {

    @Override
    public AnalyzeResult analyze(String expression) {
        AnalyzeResult result = new AnalyzeResult();
        int pos = expression.indexOf("=");
        return null;
    }

}
