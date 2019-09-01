package frog.calculator.space;

import frog.calculator.util.Arrays;
import frog.calculator.util.collection.*;

public class FixedAlignSpace<T> implements ISpace<T> {

    private final int dimension;

    private final int[] widthInfo;

    private final IPoint<T>[] values;

    @SuppressWarnings("unchecked")
    FixedAlignSpace(int[] widths) {
        this.dimension = widths.length;
        this.widthInfo = widths;

        int totalWidth = 1;
        for (int width : widths) {
            totalWidth *= width;
        }

        values = new IPoint[totalWidth];
    }

    @SuppressWarnings("unchecked")
    FixedAlignSpace(int[] widths, IList<IPoint<T>> values){
        this.dimension = widths.length;
        this.widthInfo = widths;
        int totalWidth = 1;
        for (int width : widths) {
            totalWidth *= width;
        }
        if(values.size() <= totalWidth){
            this.values = new IPoint[totalWidth];
            Iterator<IPoint<T>> iterator = values.iterator();
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
    public IPoint<T> getPoint(ICoordinate coordinate) {
        if(coordinate.dimension() > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }
        int offset = locate(coordinate.traveller());
        return offset >= 0 ? values[offset] : null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ISpace<T> getSubspace(ICoordinate coordinate) {
        int dim = coordinate.dimension();
        if(dim > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }

        Itraveller<Integer> traveller = coordinate.traveller();


        int offset = 0;
        int di = 0;
        int len = values.length;
        int w;
        int p = traveller.hasNext() ? traveller.next() : 0;

        while(traveller.hasNext()){
            w = widthInfo[di];

            if(p >= w){
                return null;
            }

            len = len / w;
            offset += len * p;

            di++;

            p = traveller.next();
        }

        if(offset == -1){ return null; }

        int l = 1;
        int[] widths = new int[this.dimension - di];
        for(int m = di, i = 0; m < this.dimension; m++, i++){
            l *= widths[i] = widthInfo[m];
        }

        // TODO 使得对子空间的修改可以反映到父空间
        return new FixedAlignSpace<T>(widths, new ArrayList<>(Arrays.copy(this.values, new IPoint[l], offset, offset + l - 1)));
    }

    @Override
    public int dimension() {
        return this.dimension;
    }

    /*
     * 就是下级子空间数量
     */
    @Override
    public int width(ICoordinate coordinate) {
        if(coordinate.dimension() == 0){
            return widthInfo[0];
        }

        if(coordinate.dimension() > this.dimension){
            return -1;
        }

        return widthInfo[coordinate.dimension() - 1];
    }

    @Override
    public void addPoint(IPoint<T> point, ICoordinate coordinate) {
        if(point == null || coordinate == null){
            throw new IllegalArgumentException("point info is null.");
        }

        if(coordinate.dimension() > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }

        int pos = locate(coordinate.traveller());
        if(pos == -1){
            throw new IllegalArgumentException("can't locate the coordinate.");
        }

        if(values[pos] == null) { values[pos] = point; }
        else{
            throw new IllegalStateException("the point has exists.");
        }
    }

    @Override
    public IList<IPoint<T>> getPoints() {
        return new UnmodifiableList<>(new ArrayList<>(this.values));
    }

    private int locate(Itraveller<Integer> traveller){
        int di = 0;
        int len = values.length;
        int w;
        int p;
        int offset = 0;

        while(traveller.hasNext()){
            w = widthInfo[di];
            p = traveller.next();

            if(p >= w){
                return -1;
            }

            len = len / w;
            offset += len * p;

            di++;
        }

        return offset;
    }

}
