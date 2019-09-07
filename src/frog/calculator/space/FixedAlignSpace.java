package frog.calculator.space;

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

        return widthInfo[coordinate.dimension()];
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

    @Override
    public ISpace<T> getSubspace(ICoordinate coordinate) {
        int dim = coordinate.dimension();
        if(dim > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }

        Itraveller<Integer> traveller = coordinate.traveller();
        ICoordinate subPos = new Coordinate();

        int offset = 0;
        int di = 0;
        int len = values.length;
        int w;
        int p;

        while(traveller.hasNext()){
            p = traveller.next();
            subPos.add(p);

            w = widthInfo[di];

            if(p >= w){
                return null;
            }

            len = len / w;
            offset += len * p;

            di++;
        }

        if(offset == -1){ return null; }

        int l = 1;
        for(int m = di, i = 0; m < this.dimension; m++, i++){
            l *= widthInfo[m];
        }


        Subspace subspace = new Subspace();
        subspace.start = offset;
        subspace.end = offset + l - 1;
        subspace.pos = subPos;

        return subspace;
    }

    private class Subspace implements ISpace<T>{

        private ICoordinate pos;

        private int start;

        private int end;

        private IList<IPoint<T>> points = null;

        @Override
        public int dimension() {
            return dimension - pos.dimension();
        }

        @Override
        public int width(ICoordinate coordinate) {
            if(coordinate.dimension() == 0){
                return widthInfo[pos.dimension()];
            }

            if(coordinate.dimension() > dimension - pos.dimension()){
                return -1;
            }

            return widthInfo[pos.dimension() + coordinate.dimension()];
        }

        @Override
        public void addPoint(IPoint<T> point, ICoordinate coordinate) {
            FixedAlignSpace.this.addPoint(point, realCoordinate(coordinate));
        }

        @Override
        public IList<IPoint<T>> getPoints() {
            if(points == null){
                IPoint<T>[] values = FixedAlignSpace.this.values;
                points = new ArrayList<>(end - start + 1);
                for(int i = start; i <= end; i++){
                    points.add(values[i]);
                }
                points = new UnmodifiableList<>(points);
            }
            return points;
        }

        @Override
        public IPoint<T> getPoint(ICoordinate coordinate) {
            return FixedAlignSpace.this.getPoint(realCoordinate(coordinate));
        }

        @Override
        public ISpace<T> getSubspace(ICoordinate coordinate) {
            return FixedAlignSpace.this.getSubspace(realCoordinate(coordinate));
        }

        private ICoordinate realCoordinate(ICoordinate coordinate){
            if(coordinate == null){ return pos; }

            ICoordinate realPos = new Coordinate();
            Itraveller<Integer> traveller = pos.traveller();
            while(traveller.hasNext()){
                realPos.add(traveller.next());
            }
            Itraveller<Integer> inputPos = coordinate.traveller();
            while(inputPos.hasNext()){
                realPos.add(inputPos.next());
            }
            return realPos;
        }
    }

}
