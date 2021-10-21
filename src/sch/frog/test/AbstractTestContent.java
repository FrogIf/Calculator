package sch.frog.test;

public abstract class AbstractTestContent<C extends ICaseObject> {

    /**
     * 生成测试数据
     * @return 测试数据
     */
    protected abstract C generateCase();

    /**
     * 单步测试
     * @return 测试结果, true - 成功; false - 失败
     */
    protected abstract boolean singleStepTest(C caseObj);

    /**
     * 解析content字符串
     * @param content
     * @return
     */
    protected abstract C parse(String content);

    /**
     * 重复测试
     * @param count 测试次数
     */
    public boolean repeatTest(int count){
        while(count-- > 0){
            C caseObj = generateCase();
            if(!singleStepTest(caseObj)){
                System.out.println(String.format("test failed, case object : %s, test class : %s", 
                    caseObj.content(), this.getClass().getSimpleName()));
                return false;
            }
        }
        System.out.println(this.getClass().getSimpleName() + " -- success!");
        return true;
    }

    public void singleTest(String caseObjContent){
        C caseObj = this.parse(caseObjContent);
        this.singleTest(caseObj);
    }

    /**
     * 单数据测试
     * @param caseObj 测试数据
     */
    public void singleTest(C caseObj){
        if(singleStepTest(caseObj)){
            System.out.println("success!");
        }else{
            System.out.println(String.format("test failed, case object : %s", caseObj.content()));
        }
    }

}
