package com.webi.hwj.tellmemore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.webi.hwj.tellmemore.util.TellmemoreUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class TellmemoreUtilTest {
  @Test
  public void demo() {

  }

  /**
   * Title: 更新tmm用户<br>
   * Description: 更新tmm用户<br>
   * CreateDate: 2016年8月10日 下午3:13:02<br>
   * 
   * @category 更新tmm用户
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void updateTmmUserAccountTest() throws Exception {
    TellmemoreUtil.updateTmmUserAccount("1", "13588888802aaa");
  }

  /**
   * Title: 创建用户<br>
   * Description: 创建用户<br>
   * CreateDate: 2016年8月23日 下午2:43:42<br>
   * 
   * @category 创建用户
   * @author komi.zsy
   * @throws Exception
   */
  // @Test
  public void createTmmUserAccountTest() throws Exception {
    TellmemoreUtil.createTmmUser("12345678912", "f0558acc3cf84400a9232af20488894a");
  }

  /**
   * Title: 删除用户<br>
   * Description: 删除用户<br>
   * CreateDate: 2016年8月16日 上午10:47:45<br>
   * 
   * @category 删除用户
   * @author komi.zsy
   * @throws Exception
   */
//   @Test
  public void userRemovalTest() throws Exception {
    TellmemoreUtil.userRemoval("18603412349");
  }

}
