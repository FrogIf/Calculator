package sch.frog.calculator.doc;

import sch.frog.calculator.util.collection.ArrayList;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.Iterator;
import sch.frog.calculator.util.collection.UnmodifiableList;

public class Document {

    private String title;

    private IList<Section> sections;

    public String getTitle() {
        return title;
    }

    public IList<Section> getSections() {
        return sections;
    }

    private String text = null;

    @Override
    public String toString(){
        if(text == null){
            StringBuilder sb = new StringBuilder();
            sb.append("=========").append(title).append("=========").append('\n');
            Iterator<Section> iterator = sections.iterator();
            while(iterator.hasNext()){
                Section section = iterator.next();
                sb.append(section.toString()).append('\n');
            }
            text = sb.toString();
        }
        return text;
    }

    public static class Builder{
        private String title;
        private final ArrayList<Section> sections = new ArrayList<>();
        public static Builder newBuilder(){
            return new Builder();
        }

        public Builder addSection(Section section){
            sections.add(section);
            return this;
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public ArrayList<Section> getSections() {
            return sections;
        }

        public Document build(){
            Document document = new Document();
            document.sections = new UnmodifiableList<>(this.sections);
            document.title = title;
            return document;
        }
    }
}
