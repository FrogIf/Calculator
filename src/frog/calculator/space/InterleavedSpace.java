package frog.calculator.space;

import frog.calculator.util.collection.*;

public class InterleavedSpace<T> implements ISpace<T> {

    private ISet<XSpace<T>> subspaces = new TreeSet<>();

    private ISet<IPoint<T>> points = new TreeSet<>();

    private int dimension;

    @Override
    public IPoint<T> getPoint(ICoordinate coordinate) {
        if(coordinate.dimension() > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }
        Itraveller<Integer> traveller = coordinate.traveller();
        if(traveller.hasNext()){
            int n = traveller.next();
            ISpace<T> space = this;
            if(traveller.hasNext()){
                while(traveller.hasNext() && space != null){
                    if(n == 0){
                        n = traveller.next();
                        continue;
                    }
                    space = space.getNextLevelSubspace(n);
                    n = traveller.next();
                }
                return space == null ? null : space.getPoint(new Coordinate(n));
            }else{
                XPoint<T> xPoint = new XPoint<>(coordinate);
                return points.find(xPoint);
            }
        }else{
            XPoint<T> xPoint = new XPoint<>(coordinate);
            return points.find(xPoint);
        }
    }

    @Override
    public ISpace<T> getSubspace(ICoordinate coordinate) {
        if(coordinate.dimension() > this.dimension){
            throw new IllegalArgumentException("coordinate dimension is error. current dimension : "
                    + this.dimension + ", input dimension : " + coordinate.dimension());
        }

        Itraveller<Integer> traveller = coordinate.traveller();
        ISpace<T> space = this;

        while(traveller.hasNext() && space != null){
            space = space.getNextLevelSubspace(traveller.next());
        }

        return space;
    }

    @Override
    public ISpace<T> getNextLevelSubspace(int index) {
        return this.getSubspace(index);
    }

    @Override
    public int dimension() {
        return this.dimension;
    }

    @Override
    public int width(ICoordinate coordinate) {
        Itraveller<Integer> traveller = coordinate.traveller();
        if(traveller.hasNext()){
            ISpace subspace = this.getSubspace(coordinate);
            return subspace == null ? -1 : subspace.width(Coordinate.EMPTY);
        }else{
            return this.subspaces.size() + 1;
        }
    }

    @Override
    public void addPoint(IPoint<T> point) {
        if(point == null || point.getCoordinate() == null){
            throw new IllegalArgumentException("point info is null.");
        }

        ICoordinate coordinate = point.getCoordinate();
        if(coordinate.dimension() > this.dimension){
            this.dimension = coordinate.dimension();
        }

        Itraveller<Integer> traveller = coordinate.traveller();
        if(traveller.hasNext()){
            int n = traveller.next();
            ISpace<T> space = this;
            if(traveller.hasNext()){
                while(traveller.hasNext()){
                    if(n == 0){
                        n = traveller.next();
                        continue;
                    }
                    ISpace<T> s = space.getNextLevelSubspace(n);
                    if(s == null) { break; }
                    space = s;
                    n = traveller.next();
                }
                if(!traveller.hasNext()) {
                    space.addPoint(new ReduceDimensionPoint<>(point, new Coordinate(n)));
                }else{
                    if(!(space instanceof InterleavedSpace)){
                        throw new IllegalStateException("not support space extend.");
                    }
                    while(traveller.hasNext()){
                        ISpace<T> s = new InterleavedSpace<>();
                        ((InterleavedSpace<T>)space).subspaces.add(new XSpace<>(n, s));
                        space = s;
                        n = traveller.next();
                    }
                    ((InterleavedSpace<T>)space).points.add(point);
                }
            }else{
                if(!points.add(point)){
                    throw new IllegalStateException("the point has exists.");
                }
            }
        }else{
            if(!points.add(point)){
                throw new IllegalStateException("the point has exists.");
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

    private ISpace<T> getSubspace(int index){
        Iterator<XSpace<T>> iterator = this.subspaces.iterator();
        while(iterator.hasNext()){
            XSpace<T> xspace = iterator.next();
            if(index == xspace.index){
                return xspace.space;
            }
        }
        return null;
    }

    private static class ReduceDimensionPoint<T> extends AbstractPoint<T>{

        private IPoint<T> point;

        private ICoordinate coordinate;

        private ReduceDimensionPoint(IPoint<T> point, ICoordinate coordinate) {
            this.point = point;
            this.coordinate = coordinate;
        }

        @Override
        public T intrinsic() {
            return point.intrinsic();
        }

        @Override
        public ICoordinate getCoordinate() {
            return coordinate;
        }
    }

    private static class XSpace<T> implements Comparable<XSpace<T>> {
        private int index;
        private ISpace<T> space;
        private XSpace(int index, ISpace<T> space) {
            this.index = index;
            this.space = space;
        }

        @Override
        public int compareTo(XSpace<T> o) {
            return this.index - o.index;
        }
    }

    private static class XPoint<T> extends AbstractPoint<T> {

        private ICoordinate coordinate;

        private XPoint(ICoordinate coordinate) {
            this.coordinate = coordinate;
        }

        @Override
        public T intrinsic() {
            return null;
        }

        @Override
        public ICoordinate getCoordinate() {
            return this.coordinate;
        }

    }
}
