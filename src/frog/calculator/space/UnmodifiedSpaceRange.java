package frog.calculator.space;

public class UnmodifiedSpaceRange implements IRange {

    private IRange range;

    public UnmodifiedSpaceRange(IRange range) {
        this.range = range;
    }

    @Override
    public int dimension() {
        return range.dimension();
    }

    @Override
    public int[] maxWidths() {
        return range.maxWidths().clone();
    }

    @Override
    public boolean equals(Object obj) {
        return range.equals(obj);
    }

    @Override
    public String toString() {
        return range.toString();
    }
}
