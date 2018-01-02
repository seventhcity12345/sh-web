package com.webi.hwj.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.SHAUtil;
import com.webi.hwj.index.param.UserRegisterParam;
import com.webi.hwj.index.service.IndexService;
import com.webi.hwj.user.service.AdminUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/app-config.xml")
@Transactional // 事务自动回滚
public class AdminUserServiceTest {
  @Resource
  AdminUserService adminUserService;
  @Resource
  IndexService indexService;

  @Test
  public void demo() {

  }

  /**
   * Title: 修改用户级别<br>
   * Description: 修改用户级别<br>
   * CreateDate: 2016年7月21日 下午3:40:58<br>
   * 
   * @category 修改用户级别
   * @author komi.zsy
   */
  @Test
  public void saveCurrentLevel() throws Exception {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("key_id", "04ca5a3056144546bf5427793a91df90");
    paramMap.put("phone", "12345678911");
    paramMap.put("current_level", "General Level 5");

    String updateUserId = "f49232f951e5495492fa753ff812d362";

    JsonMessage json = adminUserService.saveCurrentLevel(paramMap, updateUserId);

    System.out.println(json);

    Assert.assertTrue(json.isSuccess());
  }

  /**
   * Title: 根据用户手机号将密码重置为<br>
   * Description: 根据用户手机号将密码重置为<br>
   * CreateDate: 2016年7月27日 下午5:31:16<br>
   * 
   * @category 根据用户手机号将密码重置为
   * @author komi.zsy
   * @throws Exception
   */
  @Test
  public void resetUserPasswordByAdmin() throws Exception {
    Map<String, Object> updateMap = new HashMap<String, Object>();
    updateMap.put("phone", "12345678913");
    updateMap.put("pwd", SHAUtil.encode("123456"));

    int num = adminUserService.resetUserPasswordByAdmin(updateMap);

    System.out.println(num);

    Assert.assertEquals(1, num);
  }

  /**
   * 
   * Title: 新增潜客测试<br>
   * Description: 新增潜客测试<br>
   * CreateDate: 2016年10月21日 上午11:11:39<br>
   * 
   * @category 新增潜客测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void addNewUserTest() throws Exception {
    String random6BitNumber = "111111";

    UserRegisterParam userRegisterParam = new UserRegisterParam();
    userRegisterParam.setPhone("9999999991");
    userRegisterParam.setPwd(SHAUtil.encode(random6BitNumber));
    userRegisterParam.setCreateUserId("JunitTest");
    Map<String, Object> userObj = indexService.saveUser(userRegisterParam);
    Assert.assertNotNull(userObj.get("key_id"));

  }

  /**
   * 
   * Title: 潜客页面测试<br>
   * Description: 潜客页面测试<br>
   * CreateDate: 2016年12月26日 下午3:10:06<br>
   * 
   * @category 潜客页面测试
   * @author seven.gz
   * @throws Exception
   */
  @Test
  public void findPageEasyuiUserTest() throws Exception {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("sort", null);
    param.put("order", null);
    param.put("page", "1");
    param.put("rows", "20");
    Page p = adminUserService.findPageEasyuiUser(param);
    System.out.println(p);
  }

}
