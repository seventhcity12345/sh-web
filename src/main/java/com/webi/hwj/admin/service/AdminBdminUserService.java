package com.webi.hwj.admin.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mingyisoft.javabase.aliyun.ocs.OSSClientUtil;
import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.mingyisoft.javabase.util.HttpClientUtil;
import com.mingyisoft.javabase.util.MemcachedUtil;
import com.mingyisoft.javabase.util.SqlUtil;
import com.webi.hwj.admin.dao.AdminBdminUserDao;
import com.webi.hwj.admin.dao.AdminBdminUserEntityDao;
import com.webi.hwj.admin.dao.BadminUserDao;
import com.webi.hwj.admin.entity.BadminUser;
import com.webi.hwj.constant.ConfigConstant;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class AdminBdminUserService {
  private static Logger logger = Logger.getLogger(AdminBdminUserService.class);
  @Resource
  AdminBdminUserDao adminBdminUserDao;
  @Resource
  BadminUserDao badminUserDao;

  @Resource
  AdminBdminUserEntityDao adminBdminUserEntityDao;

  /**
   * Title: 保存管理员信息<br>
   * Description: 保存管理员信息<br>
   * CreateDate: 2017年8月9日 下午5:51:27<br>
   * 
   * @category 保存管理员信息
   * @author komi.zsy
   * @param paramMap
   * @param bAdminPic
   *          头像文件流
   * @return
   * @throws Exception
   */
  public JsonMessage saveAdmin(Map<String, Object> paramMap, MultipartFile bAdminPic)
      throws Exception {
    JsonMessage json = new JsonMessage();
    // aliyun上保存图片的路径 images/user/phoneNumber
    String aliyunPath = "images/admin/";
    // String imagePaths = OSSClientUtil.uploadPhoto(request, fieldName,
    // aliyunPath);
    String imagePaths = OSSClientUtil.uploadFile(bAdminPic, SqlUtil.createUUID(), aliyunPath);

    // 原来照片路径
    String currentPhoto = (String) paramMap.get("admin_user_photo");

    if (StringUtils.isEmpty(imagePaths)) {
      json.setMsg("操作成功！(无新图片上传)");
    } else {
      paramMap.put("admin_user_photo", imagePaths);

      logger.debug("修改图片成功---开始删除原来图片---->" + currentPhoto);
      try {
        String finalOssPath = currentPhoto
            .replace(MemcachedUtil.getConfigValue("aliyun_oss_returnurl"), "");
        OSSClientUtil.deleteFile(finalOssPath);
      } catch (Exception e) {
        logger.error("删除原来图片失败------>" + e.toString());
      }
    }

    if (paramMap.get("key_id") != null && !"".equals(paramMap.get("key_id"))) {// 修改
      if (paramMap.get("pwd") != null && !"".equals(paramMap.get("pwd"))) {
        this.updateAdminInfoByKeyId(paramMap, true);
      } else {
        this.updateAdminInfoByKeyId(paramMap, false);
      }
    } else {// 新增
      // modify by seven 2017年5月23日17:43:55 检验账号是否存在
      if (this.checkAccountExist((String) paramMap.get("account"))) {
        json.setSuccess(false);
        json.setMsg("账号已存在");
        return json;
      }
      paramMap.put("admin_user_type", "1");
      this.insert(paramMap);
    }
    return json;
  }

  /**
   * @category tUser 插入
   * @param fields
   * @return
   * @throws Exception
   */
  public Map<String, Object> insert(Map<String, Object> fields) throws Exception {
    return adminBdminUserDao.insert(fields);
  }

  /**
   * @category 通用查询数据方法(不带分页+dao自动生成sql)
   * @param paramMap
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> findSQLCommon(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return adminBdminUserDao.findList(paramMap, columnName);
  }

  /**
   * @category 修改数据
   * @return
   */
  public int update(Map<String, Object> fields) throws Exception {
    return adminBdminUserDao.update(fields);
  }

  /**
   * Title: 根据keyid更新管理员信息<br>
   * Description: 因为pwd要求为空时不更新，如果改成bean又有奇怪的地方出问题，优先修改为bug<br>
   * CreateDate: 2017年7月11日 下午1:57:29<br>
   * 
   * @category 根据keyid更新管理员信息
   * @author komi.zsy
   * @param badminUser
   *          管理员信息
   * @param isUpdatePwd
   *          是否更新密码，true更新
   * @return
   * @throws Exception
   */
  public int updateAdminInfoByKeyId(Map<String, Object> fields, boolean isUpdatePwd)
      throws Exception {
    return adminBdminUserDao.updateAdminInfoByKeyId(fields, isUpdatePwd);
  }

  /**
   * @category 查询单个数据
   * @param id
   * @return
   * @throws Exception
   */

  public Map<String, Object> findOne(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return adminBdminUserDao.findOne(paramMap, columnName);
  }

  /**
   * @category 查询单条数据(通过key,value)
   * @author mingyisoft代码生成工具
   * @param paramMap
   * @return
   * @throws Exception
   */
  public Map<String, Object> findOne(String key, String value, String columnName) throws Exception {
    return adminBdminUserDao.findOne(key, value, columnName);
  }

  public List<Map<String, Object>> findList(Map<String, Object> paramMap, String columnName)
      throws Exception {
    return adminBdminUserDao.findList(paramMap, columnName);
  }

  public Page findPage(Map<String, Object> paramMap) throws Exception {
    return adminBdminUserDao.findPage(paramMap);
  }

  /**
   * @category 按照ID数组批量删除数据
   * @param id
   * @return
   */
  public int delete(String ids) throws Exception {
    return adminBdminUserDao.delete(ids);
  }

  /**
   * @category 通过userId查询对应的role的keyid 用逗号分割。
   * @param roleId
   * @return
   * @throws Exception
   */
  public String findUserRoles(String userId) throws Exception {
    StringBuffer returnStr = new StringBuffer(1024);
    List<Map<String, Object>> resultList = adminBdminUserDao.findUserRoles(userId);

    for (Map<String, Object> tempMap : resultList) {
      if (returnStr.length() != 0)
        returnStr.append(",");
      returnStr.append(tempMap.get("role_id"));
    }
    return returnStr.toString();
  }

  /**
   * @category 保存中间表数据
   * @param paramMap
   * @throws Exception
   */
  public void submitUserRoles(Map paramMap) throws Exception {
    adminBdminUserDao.submitUserRoles(paramMap);
  }

  /**
   * @category 通用查询数据方法(分页+dao自动生成sql)
   * @param paramMap
   *          参数map
   * @param pageNumber
   *          当前页数
   * @param pageSize
   *          每页显示多少条数据
   * @return
   * @throws Exception
   */
  public Page findPageEasyui(Map<String, Object> paramMap) throws Exception {
    return adminBdminUserDao.findPageEasyui("*", paramMap);
  }

  /**
   * 
   * Title: 查询值班中的lc<br>
   * Description: FindLcRota<br>
   * CreateDate: 2017年1月17日 下午8:55:29<br>
   * 
   * @category 查询值班中的lc
   * @author seven.gz
   * @return
   * @throws Exception
   */
  public List<BadminUser> findLcRota() throws Exception {
    // 获取oss上lc值班表
    byte[] lcRotaStrByte = HttpClientUtil
        .doGetReturnByte((String) MemcachedUtil.getValue(ConfigConstant.LC_ROTA_FILE));

    // 处理吐出来的csv文件内容
    String lcRotaStr = new String(lcRotaStrByte, "utf-8");
    // 获得在值班的人的员工号
    List<String> employeeNoList = parseEmployeeNoList(lcRotaStr);

    // 根据员工id查询管理员对象
    if (employeeNoList != null && employeeNoList.size() > 0) {
      return badminUserDao.findListByEmployeeNumber(employeeNoList);
    }
    return null;
  }

  /**
   * 
   * Title: 用于格式化lc工作表中的时间<br>
   * Description: strToDateForRota<br>
   * CreateDate: 2017年1月17日 下午8:24:51<br>
   * 
   * @category 用于格式化lc工作表中的时间
   * @author seven.gz
   * @param strd
   * @return
   * @throws ParseException
   */
  public Date strToDateForRota(String strd) throws ParseException {
    SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    return dateSdf.parse(strd);
  }

  /**
   * 
   * Title: 通过字符出得到在值班的员工号<br>
   * Description: 通过字符出得到在值班的员工号<br>
   * CreateDate: 2017年1月17日 下午8:42:44<br>
   * 
   * @category 通过字符出得到在值班的员工号
   * @author seven.gz
   * @param lcRotaStr
   * @return
   * @throws ParseException
   */
  public List<String> parseEmployeeNoList(String lcRotaStr) throws ParseException {
    List<String> employeeNoList = new ArrayList<String>();
    if (!StringUtils.isEmpty(lcRotaStr)) {
      // 获取每行的数据
      // 避免回车是\r\n的情况
      lcRotaStr = lcRotaStr.replaceAll("\r", "");
      String[] lcRotaArray = lcRotaStr.split("\n");
      if (lcRotaArray != null && lcRotaArray.length > 0) {
        String lcRota = null;
        String[] contentTemp = null;
        String[] staffTemp = null;
        Date currentTime = new Date();
        // 获取每行的数据
        for (int i = 0; i < lcRotaArray.length; i++) {
          lcRota = lcRotaArray[i];
          if (!StringUtils.isEmpty(lcRota)) {
            contentTemp = lcRota.split(",");
            if (contentTemp.length == 4) {
              // 判断是否在值班
              if (strToDateForRota(contentTemp[0] + " " + contentTemp[1]).getTime() < currentTime
                  .getTime()
                  && strToDateForRota(contentTemp[0] + " " + contentTemp[2]).getTime() > currentTime
                      .getTime()) {
                staffTemp = contentTemp[3].split("/");
                if (staffTemp != null && staffTemp.length > 0) {
                  for (int j = 0; j < staffTemp.length; j++) {
                    employeeNoList.add(staffTemp[j]);
                  }
                }
              }
            }
          }
        }
      }
    }
    return employeeNoList;
  }

  /**
   * Title: 根据权限ids查询管理员 <br>
   * Description: findAdminUserListByRoleIds<br>
   * CreateDate: 2017年3月28日 下午4:46:28<br>
   * 
   * @category 根据权限ids查询管理员
   * @author seven.gz
   * @param roleIds
   *          权限ids
   */
  public List<BadminUser> findAdminUserListByRoleIds(String roleIds) throws Exception {
    // 从码表中获取哪些角色id算lc
    List<String> roleIdList = new ArrayList<String>();
    if (!StringUtils.isEmpty(roleIds)) {
      String[] roleIdArray = roleIds.split(",");
      roleIdList.addAll(Arrays.asList(roleIdArray));
    }
    // 根据角色id查找管理员
    List<BadminUser> adminUserList = adminBdminUserEntityDao.findAdminUserListByRoleId(roleIdList);
    return adminUserList;
  }

  /**
   * Title: 获取所有有效的lc<br>
   * Description: 获取所有有效的lc<br>
   * CreateDate: 2017年3月2日 上午11:51:55<br>
   * 
   * @category 获取所有有效的lc
   * @author komi.zsy
   * @return
   * @throws Exception
   */
  public List<BadminUser> findListCc() throws Exception {
    // 从码表中获取哪些角色id算lc
    String roleIdstr = MemcachedUtil.getConfigValue(ConfigConstant.CC_ROLE_IDS);
    List<BadminUser> adminUserList = findAdminUserListByRoleIds(roleIdstr);
    return adminUserList;
  }

  /**
   * Title: 判断账号是否存在<br>
   * Description: 判断账号是否存在，存在返回ture 否则 返回false<br>
   * CreateDate: 2017年5月23日 下午5:38:30<br>
   * 
   * @category 判断账号是否存在
   * @author seven.gz
   * @param account
   *          账号
   */
  public boolean checkAccountExist(String account) throws Exception {
    Map<String, Object> adminUserMap = adminBdminUserDao.findOneByAccount(account);
    return adminUserMap != null;
  }
}