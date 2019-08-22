package frog.calculator.space;

import frog.calculator.util.collection.ArrayList;
import frog.calculator.util.collection.IList;
import frog.calculator.util.collection.Iterator;
import frog.calculator.util.collection.UnmodifiableList;

public class CommonSpace implements ISpace {

    private int dimension;

    private final int[] widthInfo;

    private final ILiteral[] values;

    CommonSpace(int[] widths) {
        this.dimension = widths.length;
        this.widthInfo = widths;

        int totalWidth = 1;
        for (int width : widths) {
            totalWidth *= width;
        }

        values = new ILiteral[totalWidth];
    }

    CommonSpace(int[] widths, IList<ILiteral> values){
        this.dimension = widths.length;
        this.widthInfo = widths;
        int totalWidth = 1;
        for (int width : widths) {
            totalWidth *= width;
        }
        if(values.size() <= totalWidth){
            this.values = new ILiteral[totalWidth];
            Iterator<ILiteral> iterator = values.iterator();
            int i = 0;
            while(iterator.hasNext()){
                this.values[i] = iterator.next();
                i++;
            }
        }else{
            throw new IllegalArgumentException("input values is too large.");
        }
    }

    @Override
    public ILiteral getValue(ICoordinate coordinate) {
        return values[locate(coordinate)];
    }

    @Override
    public int dimension() {
        return this.dimension;
    }

    @Override
    public int width(int dimension) {
        return widthInfo[dimension];
    }

    @Override
    public void addValue(ICoordinate coordinate, ILiteral literal) {
        int pos = locate(coordinate);
        values[pos] = literal;
    }

    @Override
    public IList<ILiteral> getValues() {
        return new UnmodifiableList<>(new ArrayList<>(this.values));
    }

    @Override
    public IList<ISpace> getSubspaces() {
        return null;
    }

    private int locate(ICoordinate coordinate){
        ICoordinateViewer viewer = coordinate.getViewer();

        int di = 0;
        int len = values.length;
        int w;
        int p;
        int offset = 0;

        while(viewer.hasNextAxis()){
            if(di >= widthInfo.length){
                throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                        + this.dimension + ", input dimension : " + di);
            }

            w = widthInfo[di];
            p = viewer.nextAxialValue();

            if(p >= w){
                throw new IllegalArgumentException("coordinate out of the range. dimension : "
                        + di + ", current width : " + w + ", assign position : " + p);
            }

            len = len / w;
            offset += len * p;

            di++;
        }

        if(di != this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + di);
        }

        return offset;
    }

}
