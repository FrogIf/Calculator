package frog.calculator.space;

import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;

public class AtomSpace<T> implements ISpace<T> {

    private final T atom;

    public AtomSpace(T atom) {
        this.atom = atom;
    }

    @Override
    public void add(T point, ICoordinate coordinate) {
        throw new IllegalStateException("atom space can't support this transform.");
    }

    @Override
    public IList<T> getElements() {
        ArrayList<T> list = new ArrayList<>(1);
        list.add(atom);
        return list;
    }

    @Override
    public T get(ICoordinate coordinate) {
        return atom;
    }

    @Override
    public ISpace<T> getSubspace(ICoordinate coordinate) {
        return null;
    }

    @Override
    public IRange getRange() {
        SpaceRange range = new SpaceRange();
        range.setMaxWidths(new int[]{1});
        return new UnmodifySpaceRange(range);
    }
}
