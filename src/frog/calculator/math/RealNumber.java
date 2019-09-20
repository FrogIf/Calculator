package frog.calculator.math;

public class RealNumber implements INumber {

    /*
     * 一个实数 = 加数 + 因数 * 函数
     */

    private INumber addend;

    private INumber coefficient;

    private INumber functionNumber;

    @Override
    public INumber add(INumber number) {
        return null;
    }

    @Override
    public INumber sub(INumber number) {
        return null;
    }

    @Override
    public INumber mult(INumber number) {
        return null;
    }

    @Override
    public INumber div(INumber number) {
        return null;
    }

    @Override
    public int compareTo(INumber o) {
        return 0;
    }
}
