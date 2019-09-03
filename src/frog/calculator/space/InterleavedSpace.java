package frog.calculator.space;

import frog.calculator.util.ComparableComparator;
import frog.calculator.util.collection.*;

public class InterleavedSpace<T> implements ISpace<T> {

    private ISet<XSpace<T>> subspaces = new TreeSet<>(ComparableComparator.<XSpace<T>>getInstance());

    private ISet<IPoint<T>> points = new TreeSet<>(PointComparator.getInstance());

    private int dimension;

    @Override
    public void addPoint(IPoint<T> point, ICoordinate coordinate) {
        if(point == null || coordinate == null){
            throw new IllegalArgumentException("point info is null.");
        }

        if(coordinate.dimension() > this.dimension){
            this.dimension = coordinate.dimension();
        }

        Itraveller<Integer> traveller = coordinate.traveller();
        int pos = traveller.hasNext() ? traveller.next() : 0;
        int di = 0;

        XSpace<T> finder = new XSpace<>(pos);
        InterleavedSpace<T> pointSpace = this;
        ISet<IPoint<T>> axialPoints = pointSpace.points;

        ISet<IPoint<T>> movePoints = null;


        // 确定点所在子空间
        while(traveller.hasNext()){
            if(!pointSpace.points.isEmpty()){   // 将坐标寻址时, 沿途所有将低维度的点向高维度移动(不足维度的位置是使用0坐标)
                if(movePoints == null){
                    movePoints = pointSpace.points;
                }else{
                    Iterator<IPoint<T>> iterator = pointSpace.points.iterator();
                    while (iterator.hasNext()){
                        IPoint<T> upDimensionPoint = iterator.next();
                        upDimensionPoint.setAxialValue(0);
                        movePoints.add(upDimensionPoint);
                    }
                    pointSpace.points.clear();
                }
            }

            finder.index = pos;
            XSpace<T> xSpace = pointSpace.subspaces.find(finder);
            if(xSpace == null){
                InterleavedSpace<T> subSpace = new InterleavedSpace<>();
                subSpace.dimension = coordinate.dimension() - di;
                xSpace = new XSpace<>(pos, subSpace);
                pointSpace.subspaces.add(xSpace);
            }
            ISpace<T> subspace = xSpace.space;
            if(subspace instanceof InterleavedSpace) {
                pointSpace = ((InterleavedSpace<T>)subspace);
                if(pos != 0){
                    axialPoints = pointSpace.points;
                }
            }else{
                pointSpace.addPoint(point, new ContinueCoordinate(traveller, coordinate.dimension() - di));
                return;
            }

            pos = traveller.next();
            di++;
        }

        // 向子空间中添加点
        point.setAxialValue(pos);
        if(!axialPoints.add(point)){
            throw new IllegalStateException("this coordinate's point has exists. point : " + point.intrinsic());
        }
    }

    @Override
    public IPoint<T> getPoint(ICoordinate coordinate) {
        if(coordinate.dimension() > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }

        Itraveller<Integer> traveller = coordinate.traveller();

        int pos = 0;
        int prepos = 0;
        int di = 0;
        XSpace<T> finder = new XSpace<>(pos);

        ISet<IPoint<T>> axialPoints = this.points;
        InterleavedSpace<T> pointSpace = this;

        while(traveller.hasNext()){
            pos = traveller.next();
            di++;

            finder.index = pos;
            XSpace<T> xSpace = pointSpace.subspaces.find(finder);

            if(xSpace == null){
                if(traveller.hasNext()){
                    return null;
                }else{
                    break;
                }
            }else{
                ISpace<T> subSpace = xSpace.space;
                if(subSpace instanceof InterleavedSpace){
                    pointSpace = (InterleavedSpace<T>) subSpace;
                    if(pos != 0){
                        prepos = pos;
                        axialPoints = pointSpace.points;
                    }
                }else{
                    return subSpace.getPoint(new ContinueCoordinate(traveller, this.dimension - di));
                }
            }

        }

        pos = pos == 0 ? prepos : pos;
        return axialPoints.find(new XPoint<>(pos));
    }

    @Override
    public ISpace<T> getSubspace(ICoordinate coordinate) {
        if(coordinate.dimension() > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }

        Itraveller<Integer> traveller = coordinate.traveller();
        ISpace<T> space = this;
        ISpace<T> result = space;

        int pos = 0;
        int di = 0;

        XSpace<T> finder = new XSpace<>(pos);
        XSpace<T> xSpace;

        while(traveller.hasNext()) {
            di++;
            pos = traveller.next();
            if(space instanceof InterleavedSpace){
                finder.index = pos;
                xSpace = ((InterleavedSpace<T>) space).subspaces.find(finder);
                if(xSpace == null){ break; }
                else{ space = xSpace.space; }
            }else{
                return space.getSubspace(new ContinueCoordinate(traveller, this.dimension - di));
            }

            result = space;
        }

        if(traveller.hasNext()){
            while(traveller.hasNext()){
                if(traveller.next() != 0){
                    return null;
                }
            }
            result = space;
        }

        return result;
    }

    @Override
    public int dimension() {
        return this.dimension;
    }

    @Override
    public int width(ICoordinate coordinate) {
        ISpace<T> space = this.getSubspace(coordinate);
        if(space == null){
            return 0;
        }else{
            if(space instanceof InterleavedSpace){
                if(coordinate.dimension() == this.dimension){
                    return ((InterleavedSpace)space).points.size();
                }else{
                    return ((InterleavedSpace)space).subspaces.size();
                }
            }else{
                return space.width(Coordinate.ORIGIN);
            }
        }
    }

    @Override
    public IList<IPoint<T>> getPoints() {
        IList<IPoint<T>> result = new ArrayList<>();
        Iterator<IPoint<T>> iterator = this.points.iterator();
        while(iterator.hasNext()){
            result.add(iterator.next());
        }

        Iterator<XSpace<T>> spaceIterator = this.subspaces.iterator();
        while(spaceIterator.hasNext()){
            XSpace<T> space = spaceIterator.next();
            ISpace<T> s = space.space;
            IList<IPoint<T>> points = s.getPoints();
            Iterator<IPoint<T>> pi = points.iterator();
            while(pi.hasNext()){
                result.add(pi.next());
            }
        }

        return new UnmodifiableList<>(result);
    }

    private static class ContinueCoordinate extends AbstractCoordinate{

        private Itraveller<Integer> traveller;

        private int dimension;

        private ContinueCoordinate(Itraveller<Integer> traveller, int dimension) {
            this.traveller = traveller;
            this.dimension = dimension;
        }

        @Override
        public void add(int axialValue) {
            throw new IllegalStateException("this object can't support this method.");
        }

        @Override
        public int dimension() {
            return this.dimension;
        }

        @Override
        public Itraveller<Integer> traveller() {
            return traveller;
        }
    }

    private static class XSpace<T> implements Comparable<XSpace<T>> {
        private int index;
        private ISpace<T> space;
        private XSpace(int index, ISpace<T> space) {
            this.index = index;
            this.space = space;
        }

        private XSpace(int index){
            this.index = index;
        }

        @Override
        public int compareTo(XSpace<T> o) {
            return this.index - o.index;
        }
    }

    private static class XPoint<T> extends AbstractPoint<T> {

        private final int axialValue;

        private XPoint(int axialValue) {
            this.axialValue = axialValue;
        }

        @Override
        public T intrinsic() {
            return null;
        }

        @Override
        public int getAxialValue() {
            return this.axialValue;
        }

        @Override
        public void setAxialValue(int val) {
            // do nothing.
        }

    }
}
