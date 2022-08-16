package sch.frog.calculator.compile.lexical;

public interface IScannerOperator extends IScanner {

    void moveToMark();

    void markTo(int offset);
    
}
