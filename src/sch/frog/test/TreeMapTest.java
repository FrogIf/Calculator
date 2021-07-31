package sch.frog.test;

import sch.frog.calculator.util.collection.ArrayList;
import sch.frog.calculator.util.collection.IList;
import sch.frog.calculator.util.collection.ISet;
import sch.frog.calculator.util.collection.Iterator;
import sch.frog.calculator.util.collection.TreeMap;
import sch.frog.calculator.util.collection.IMap.Entry;
import sch.frog.test.util.DebugUtil;

public class TreeMapTest implements ITest{

    @Override
    public void test() {
        TreeMap<String, String> map = new TreeMap<>((a, b) -> a.compareTo(b));
        int size = 100;
        IList<String> list = new ArrayList<>();
        for(int i = 0; i < size; i++){
            String key = DebugUtil.randomString();
            String val = DebugUtil.randomString();
            map.put(key, key + " - " + val);
            list.add(key);
        }

        if(size != map.size()){
            System.out.println("map size is not right. expect " + size + ", but " + map.size());
        }

        ISet<Entry<String, String>> entrySet = map.entrySet();
        Iterator<Entry<String, String>> itr = entrySet.iterator();
        while(itr.hasNext()){
            Entry<String, String> entry = itr.next();
            System.out.println(entry.getKey() + " -- " + entry.getValue());
        }

        System.out.println("-------------------");


        Iterator<String> listItr = list.iterator();
        while(listItr.hasNext()){
            String key = listItr.next();
            if(map.containsKey(key)){
                String val = map.get(key);
                if(!val.startsWith(key)){
                    System.out.println("value not right for key : " + key + ", real value is : " + val);
                }
            }else{
                System.out.println("not find for the key " + key);
            }
        }
    }
    

}
