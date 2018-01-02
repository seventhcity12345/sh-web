package com.mingyisoft.javabase.util;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.generation.GenerationUtil;
import com.webi.hwj.demo.dao.DemoDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional
public class GenerationTest {
  @Resource
  private DemoDao demoDao;

  @Test
  public void demo() {

  }

  @Test
  public void begin() throws Exception {
    System.out.println("开始生产");
    // 如果是mysql的话，需要将jdbc.properties文件中的库改成information_schema，
    // 因为需要查他的系统库(不用改用户名密码)
    // 生成完毕后，访问格式例如：http://localhost:8888/chexiaoniu/admin/tYangTest/index
    // 0:生成entity,
    // 1:生成entity+controller+service+dao(没有admin),
    // 2:生成entity+controller+service+dao(有admin),
    // 3:生成entity+controller+service+dao(有admin)+jsp+js

    // 需要谨慎操作，如果有老文件则会被覆盖掉!!!
    GenerationUtil.ownerName = "hwj";// 数据库名
    GenerationUtil.projectName = "hwj";// 项目名(包名里的com.webi.hwj,这个hwj) speakhi
    GenerationUtil.companyName = "webi";// 公司名

//    GenerationUtil.genCode("t_course_comment_demo", demoDao, 1);

    System.out.println("生产结束");
  }
}