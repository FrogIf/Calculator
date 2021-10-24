package sch.frog.test.impl;

import sch.frog.calculator.util.collection.ISet;
import sch.frog.calculator.util.collection.Iterator;

import java.util.HashMap;
import java.util.Random;

import sch.frog.calculator.util.collection.TreeMap;
import sch.frog.calculator.util.collection.IMap.Entry;
import sch.frog.test.AbstractTestContent;
import sch.frog.test.ICaseObject;
import sch.frog.test.util.TestUtil;

/**
 * TreeMap测试
 */
public class TreeMapTest extends AbstractTestContent<TreeMapTestObject> {
    private final Random r = new Random();

    @Override
    protected TreeMapTestObject generateCase() {
        int size = r.nextInt(100) + 1;
        TreeMapTestObject obj = new TreeMapTestObject();
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for(int i = 0; i < size; i++){
            String key = TestUtil.randomString();
            String val = TestUtil.randomString();
            val = key + "-" + val;
            obj.map.put(key, val);
            obj.standardMap.put(key, val);
            sb.append(key).append('|').append(val).append(',');
        }
        sb.append('}');
        obj.content = sb.toString();
        return obj;
    }

    @Override
    protected boolean singleStepTest(TreeMapTestObject caseObj) {
        if(caseObj.standardMap.size() != caseObj.map.size()){
            return false;
        }
        
        java.util.Iterator<String> listItr = caseObj.standardMap.keySet().iterator();
        while(listItr.hasNext()){
            String key = listItr.next();
            if(caseObj.map.containsKey(key)){
                String val = caseObj.map.get(key);
                if(!val.startsWith(key)){
                    return false;
                }
            }else{
                return false;
            }
        }
        
        ISet<Entry<String, String>> entrySet = caseObj.map.entrySet();
        Iterator<Entry<String, String>> itr = entrySet.iterator();
        while(itr.hasNext()){
            Entry<String, String> entry = itr.next();
            String key = entry.getKey();
            String val = entry.getValue();
            if(!val.startsWith(key)){
                return false;
            }
        }

        return true;
    }

    @Override
    public TreeMapTestObject parse(String content) {
        int len = content.length();
        String con = content.substring(1, len - 1);
        String[] split = con.split(",");
        TreeMapTestObject obj = new TreeMapTestObject();
        for(int i = 0; i < split.length; i++){
            String str = split[i];
            if(str != null && !str.equals("")){
                String[] kv = str.split("\\|");
                obj.map.put(kv[0], kv[1]);
                obj.standardMap.put(kv[0], kv[1]);
            }
        }
        obj.content = content;
        return obj;
    }

}

class TreeMapTestObject implements ICaseObject{

    TreeMap<String, String> map = new TreeMap<>((a, b) -> a.compareTo(b));

    HashMap<String, String> standardMap = new HashMap<>();

    String content;

    @Override
    public String content() {
        return content;
    }

}