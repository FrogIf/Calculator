package frog.calculator.space;

public class SpaceRange implements IRange {

    private int[] maxWidths;

    public void setMaxWidths(int[] maxWidths) {
        this.maxWidths = maxWidths;
    }

    @Override
    public int dimension() {
        return this.maxWidths.length;
    }

    @Override
    public int[] maxWidths() {
        return maxWidths;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof IRange)){
            return false;
        }else{
            IRange range = (IRange) obj;
            if(this.maxWidths.length != range.dimension()){
                return false;
            }
            int[] aimWidths = range.maxWidths();
            for(int i = 0; i < this.maxWidths.length; i++){
                if(aimWidths[i] != this.maxWidths[i]){
                    return false;
                }
            }
            return true;
        }
    }
}
