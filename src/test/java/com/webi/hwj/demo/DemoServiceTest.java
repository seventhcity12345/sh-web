package com.webi.hwj.demo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.demo.entity.Demo;
import com.webi.hwj.demo.service.DemoService;

/**
 * Title: DemoServiceTest<br>
 * Description: DemoServiceTest<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年4月4日 下午7:04:26
 * 
 * @author yangmh
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class DemoServiceTest {
  @Resource
  DemoService demoService;

  @Test
  public void demo() {

  }

  // @Test
  public void testInsert() throws Exception {
    Demo demo = new Demo();
    demo.setUserName("alex_yang");
    demo.setPwd("yang213");
    demo.setPhone("111");
    int result = demoService.insert(demo);
    System.out.println(result);
    System.out.println(demo);
  }

  // @Test
  public void testInsertBatch() throws Exception {
    List demoList = new ArrayList();
    Demo demo = new Demo();
    demo.setUserName("alex_yang");
    demo.setPwd("yang213");
    demo.setPhone("111");

    Demo demo1 = new Demo();
    demo1.setUserName("alex_yang");
    demo1.setPwd("yang213");
    demo1.setPhone("111");

    Demo demo2 = new Demo();
    demo2.setUserName("alex_yang");
    demo2.setPwd("yang213");
    demo2.setPhone("111");

    demoList.add(demo);
    demoList.add(demo1);
    demoList.add(demo2);

    int result = demoService.batchInsert(demoList);
    System.out.println(result);
    System.out.println(demo);
  }

  // @Test
  public void testUpdateBatch() throws Exception {
    List demoList = new ArrayList();
    Demo demo = new Demo();
    demo.setKeyId("00449e0368cb43a1acd377cd6d72a320");
    demo.setUserName("ggggggg1");
    demo.setPwd("zzzzzz");
    demo.setPhone("xxxx");

    Demo demo1 = new Demo();
    demo1.setKeyId("0294ddfa3a344b02aa82f86ffeebf552");
    demo1.setUserName("ggggggg2");
    demo1.setPwd("zzzzzz");
    demo1.setPhone("xxxx");

    Demo demo2 = new Demo();
    demo2.setKeyId("04ca5a3056144546bf5427793a91df90");
    demo2.setUserName("ggggggg3");
    demo2.setPwd("zzzzzz");
    demo2.setPhone("xxxx");

    demoList.add(demo);
    demoList.add(demo1);
    demoList.add(demo2);

    int result = demoService.batchUpdate(demoList);
    System.out.println(result);
  }

  // @Test
  public void testDelete() throws Exception {
    int result = demoService.delete("f930da4cb6e44cdd946f7bc2463a01ab");
    System.out.println(result);
  }

  // @Test
  public void testUpdate() throws Exception {
    Demo demo = new Demo();
    demo.setKeyId("f930da4cb6e44cdd946f7bc2463a01ab");
    demo.setUserName("zzzz");

    int result = demoService.update(demo);
    System.out.println(result);
  }

  // @Test
  public void testFindList() throws Exception {
    Demo paramDemo = new Demo();
    paramDemo.setIsStudent(true);

    List<Demo> demoList = demoService.findList(paramDemo);
    System.out.println(demoList.size());
    for (Demo demo : demoList) {
      System.out.println(demo);
    }
  }

  // @Test
  public void testFindListWithSql() throws Exception {
    Demo paramDemo = new Demo();
    paramDemo.setIsStudent(true);

    List<Demo> demoList = demoService.findListWithSql(paramDemo);
    System.out.println(demoList.size());
    for (Demo demo : demoList) {
      System.out.println(demo);
    }
  }

  // @Test
  public void testFindPage() throws Exception {
    Demo paramDemo = new Demo();
    paramDemo.setIsStudent(true);

    Page p = demoService.findPage(paramDemo, 1, 2);
    System.out.println(p);

    List<Demo> demoList = p.getDatas();
    System.out.println(demoList.size());
    for (Demo demo : demoList) {
      System.out.println(demo);
    }
  }

  // @Test
  public void testFindPageWithSql() throws Exception {
    Demo paramDemo = new Demo();
    paramDemo.setIsStudent(true);

    Page p = demoService.findPageWithSql(paramDemo, 1, 2);
    System.out.println(p);

    List<Demo> demoList = p.getDatas();
    System.out.println(demoList.size());
    for (Demo demo : demoList) {
      System.out.println(demo);
    }
  }

  // @Test
  public void testFindOne() throws Exception {
    Demo paramDemo = new Demo();
    paramDemo.setIsStudent(false);

    Demo demo = demoService.findOne(paramDemo);
    System.out.println("---------------------------------------------");
    System.out.println(demo);

    Demo paramDemo2 = new Demo();
    paramDemo2.setPhone("11100000006");

    Demo demo2 = demoService.findOne(paramDemo2);
    System.out.println("---------------------------------------------");
    System.out.println(demo2);

    Demo paramDemo3 = new Demo();
    paramDemo3.setPhone("asdasdadsasd");
    Demo demo3 = demoService.findOne(paramDemo3);
    System.out.println("---------------------------------------------");
    System.out.println(demo3);
  }

  // @Test
  public void testFindOneSql() throws Exception {
    Demo paramDemo = new Demo();
    paramDemo.setIsStudent(false);

    Demo demo = demoService.findOneSql(paramDemo);
    System.out.println("---------------------------------------------");
    System.out.println(demo);

    Demo paramDemo2 = new Demo();
    paramDemo2.setIsStudent(true);

    Demo demo2 = demoService.findOneSql(paramDemo2);
    System.out.println("---------------------------------------------");
    System.out.println(demo2);

  }

  // @Test
  public void testFindOneByKeyId() throws Exception {
    Demo demo = demoService.findOneByKeyId("22c1613ef3ff40479e0a9ee3a566ea79");
    System.out.println("---------------------------------------------");
    System.out.println(demo);
  }

  // @Test
  public void testFindOneStringByKeyId() throws Exception {
    String s = demoService.findOneStringByKeyId("22c1613ef3ff40479e0a9ee3a566ea79");
    System.out.println("---------------------------------------------");
    System.out.println(s);
  }

  // @Test
  public void testFindOneString() throws Exception {
    Demo demo = new Demo();
    demo.setPhone("17091958842");
    String s = demoService.findOneString(demo);
    System.out.println("---------------------------------------------");
    System.out.println(s);
  }

  // @Test
  public void testFindOneString2() throws Exception {
    Demo paramDemo = new Demo();
    paramDemo.setPhone("17091958842");
    String s = demoService.findOneString2(paramDemo);
    System.out.println("---------------------------------------------");
    System.out.println(s);
  }

  // @Test
  public void testFindCount() throws Exception {
    Demo demo = new Demo();
    demo.setIsStudent(false);
    int i = demoService.findCount(demo);
    System.out.println("---------------------------------------------");
    System.out.println(i);
  }
}
