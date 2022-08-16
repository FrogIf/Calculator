package sch.frog.calculator.doc;

import sch.frog.calculator.util.collection.ArrayList;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.Iterator;
import sch.frog.calculator.util.collection.UnmodifiableList;

public class Section {

    private String title;

    private IList<String> lines;

    public String getTitle() {
        return title;
    }

    public IList<String> getLines() {
        return lines;
    }

    private String text = null;

    @Override
    public String toString(){
        if(text == null){
            StringBuilder sb = new StringBuilder();
            sb.append("---").append(title).append("---").append('\n');
            Iterator<String> iterator = lines.iterator();
            while(iterator.hasNext()){
                String line = iterator.next();
                sb.append(line).append('\n');
            }
            text = sb.toString();
        }
        return text;
    }

    public static class Builder{
        private String title;
        private final ArrayList<String> lines = new ArrayList<>();
        public static Builder newBuilder(){
            return new Builder();
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public Builder addLine(String line){
            this.lines.add(line);
            return this;
        }

        public String getTitle() {
            return title;
        }

        public ArrayList<String> getLines() {
            return lines;
        }

        public Section build(){
            Section section = new Section();
            section.lines = new UnmodifiableList<>(lines);
            section.title = this.title;
            return section;
        }
    }
}
