package frog.calculator.space;

import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;

public class InterleavedSpace implements ISpace {

    private static final ILiteral ZERO = new CommonLiteral("0");

    private IList<ISpace> subspaces = new ArrayList<>();

    @Override
    public ILiteral getValue(ICoordinate coordinate) {
        ICoordinateViewer viewer = coordinate.getViewer();
        IList<ISpace> subspaces = this.subspaces;
        while(viewer.hasNextAxis()){
            int axialValue = viewer.nextAxialValue();
            ISpace space = subspaces.get(axialValue);
            if(space == null){
                throw new IllegalArgumentException("the coordinate out of width. subspaces width : "
                        + subspaces.size() + ", expect pos : " + axialValue);
            }
            subspaces = space.getSubspaces();
        }
        return null;
    }

    public IList<ISpace> getSubspaces(){
        return this.subspaces;
    }

    @Override
    public int dimension() {
        return 0;
    }

    @Override
    public int width(int dimension) {
        return 0;
    }

    @Override
    public void addValue(ICoordinate coordinate, ILiteral literal) {

    }

    @Override
    public IList<ILiteral> getValues() {
        return null;
    }
}
