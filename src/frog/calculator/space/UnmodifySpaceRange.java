package frog.calculator.space;

public class UnmodifySpaceRange implements IRange {

    private IRange range;

    public UnmodifySpaceRange(IRange range) {
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
}
