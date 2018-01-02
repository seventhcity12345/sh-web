package com.webi.hwj.trainingcamp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.trainingcamp.dao.TrainingCampEntityDao;
import com.webi.hwj.trainingcamp.dao.TrainingMemberEntityDao;
import com.webi.hwj.trainingcamp.dao.TrainingMemberOptionEntityDao;
import com.webi.hwj.trainingcamp.entity.TrainingCamp;
import com.webi.hwj.trainingcamp.entity.TrainingMember;
import com.webi.hwj.trainingcamp.param.TrainingMemberParam;
import com.webi.hwj.user.constant.UserConstant;
import com.webi.hwj.user.dao.UserEntityDao;
import com.webi.hwj.user.param.UserInfoByTrainingMemberParam;
import com.webi.hwj.user.param.UserInfoOrderCourseParam;

/**
 * @category trainingMember控制类
 * @author mingyisoft代码生成工具
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
// @Transactional(propagation = Propagation.REQUIRED, isolation =
// Isolation.SERIALIZABLE)
public class TrainingMemberService {
  private static Logger logger = Logger.getLogger(TrainingMemberService.class);
  @Resource
  TrainingMemberEntityDao trainingMemberEntityDao;
  @Resource
  UserEntityDao userEntityDao;
  @Resource
  TrainingMemberOptionEntityDao trainingMemberOptionEntityDao;
  @Resource
  TrainingCampEntityDao trainingCampEntityDao;

  /**
   * Title: 查询所有不在有效训练营中的学员<br>
   * Description: 查询所有不在有效训练营中的学员<br>
   * CreateDate: 2017年9月12日 下午7:46:17<br>
   * 
   * @category 查询所有不在有效训练营中的学员
   * @author komi.zsy
   * @param cons
   * @param sort
   * @param order
   * @param page
   * @param rows
   * @return
   * @throws Exception
   */
  public Page pagelistByNotInTrainingCamp(String cons, String sort, String order, Integer page,
      Integer rows) throws Exception {
    // 查找在有效训练营中的学员id
    List<TrainingMember> trainingMemberList = trainingMemberEntityDao
        .findTrainingMemberUnTrainingCamp();
    List<String> keyIds = new ArrayList<String>();
    if (trainingMemberList != null && trainingMemberList.size() != 0) {
      for (TrainingMember trainingMember : trainingMemberList) {
        keyIds.add(trainingMember.getTrainingMemberUserId());
      }
    }
    else{
      //因为list里没值的话sql里in()会报错
      keyIds.add("");
    }

    // 查询不在正在执行中的训练营中的学员
    Page p = trainingMemberEntityDao.findPageByUserNotInTrainingCamp(cons, sort, order, page, rows,
        keyIds);

    return p;
  }

  /**
   * Title: 新增训练营成员<br>
   * Description: 新增训练营成员<br>
   * CreateDate: 2017年9月12日 下午8:08:00<br>
   * 
   * @category 新增训练营成员
   * @author komi.zsy
   * @param keys
   *          新增的学员id列表，逗号分隔
   * @param trainingCampId
   *          训练营id
   * @param updateUserId
   *          更新人id
   * @return
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public void addTrainingMember(String keys, String trainingCampId, String updateUserId)
      throws Exception {

    if (!StringUtils.isEmpty(keys)) {
      String[] keyIds = keys.split(",");
      // 根据用户id列表查找用户信息
      List<UserInfoByTrainingMemberParam> userInfoByTrainingMemberParamList = userEntityDao
          .findUserInfoByUserIds(Arrays.asList(keyIds));
      if (userInfoByTrainingMemberParamList != null && userInfoByTrainingMemberParamList
          .size() != 0) {
        List<TrainingMember> trainingMemberList = new ArrayList<TrainingMember>();
        for (UserInfoByTrainingMemberParam userInfoByTrainingMemberParam : userInfoByTrainingMemberParamList) {
          TrainingMember trainingMember = new TrainingMember();
          trainingMember.setCreateUserId(updateUserId);
          trainingMember.setUpdateUserId(updateUserId);
          trainingMember.setTrainingCampId(trainingCampId);
          trainingMember.setTrainingMemberUserId(userInfoByTrainingMemberParam.getKeyId());
          trainingMember.setTrainingMemberUserCode(userInfoByTrainingMemberParam.getUserCode());
          trainingMember.setTrainingMemberEnglishName(userInfoByTrainingMemberParam
              .getEnglishName());
          trainingMember.setTrainingMemberRealName(userInfoByTrainingMemberParam.getRealName());
          trainingMember.setTrainingMemberGender(userInfoByTrainingMemberParam.getGender());
          trainingMember.setTrainingMemberPic(userInfoByTrainingMemberParam.getUserPhoto());
          trainingMember.setTrainingMemberPhone(userInfoByTrainingMemberParam.getPhone());
          trainingMember.setTrainingMemberCurrentLevel(userInfoByTrainingMemberParam
              .getCurrentLevel());
          // 计算年龄
          if (!StringUtils.isEmpty(userInfoByTrainingMemberParam.getIdcard())) {
            try {
              // 有时候身份证会有莫名其妙的错误，不管
              int idCardYear = Integer.parseInt(userInfoByTrainingMemberParam.getIdcard().substring(
                  6,
                  10));
              int nowYear = Calendar.getInstance().get(Calendar.YEAR);
              if (idCardYear >= 1900 && idCardYear <= nowYear) {
                // 根据tom要求，从身份证算年龄
                int age = nowYear - idCardYear;
                trainingMember.setTrainingMemberAge(age);
              }
            } catch (Exception e) {
              // 有时候身份证会有莫名其妙的错误，不管.这里如果出错，这个学员就不算年龄
            }
          }
          trainingMemberList.add(trainingMember);
        }
        // 训练营成员批量插入
        trainingMemberEntityDao.batchInsert(trainingMemberList);
        // 更新训练营平均值数据
        this.updateTrainingCampAverage(trainingCampId);
      }
    }
  }

  /**
   * Title: 删除训练营成员<br>
   * Description: 删除训练营成员<br>
   * CreateDate: 2017年9月13日 上午11:28:16<br>
   * 
   * @category 删除训练营成员
   * @author komi.zsy
   * @param keys
   *          用户ids，逗号分隔
   * @param trainingCampId
   *          训练营id
   * @param updateUserId
   *          更新人id
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public void deleteTrainingMember(String keys, String trainingCampId, String updateUserId)
      throws Exception {

    if (!StringUtils.isEmpty(keys)) {
      String[] keyIds = keys.split(",");
      List<String> userIds = Arrays.asList(keyIds);
      //删除训练营成员
      trainingMemberEntityDao.deleteMemberByCampIsAndUserIds(trainingCampId,userIds ,
          updateUserId);
      //删除训练营成员加分减分项
      trainingMemberOptionEntityDao.deleteMemberOptionByCampIsAndUserIds(trainingCampId, userIds, updateUserId);
      // 更新训练营平均值数据
      this.updateTrainingCampAverage(trainingCampId);
    }
  }

  /**
   * Title: 更新训练营平均值数据<br>
   * Description: 更新训练营平均值数据<br>
   * CreateDate: 2017年9月13日 上午11:10:48<br>
   * 
   * @category 更新训练营平均值数据
   * @author komi.zsy
   * @param trainingCampId
   *          训练营id
   * @throws Exception
   */
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
  public void updateTrainingCampAverage(String trainingCampId) throws Exception {
    // 求出的所有平均分返回，供训练营表使用，下标0为平均年龄，1为平均级别，2为女性占百分比,3为当前训练营人数
    List<Integer> avg = new ArrayList<Integer>();
    avg.add(0);
    avg.add(0);
    avg.add(0);
    avg.add(0);
    List<TrainingMember> trainingMemberInfoList = trainingMemberEntityDao
        .findMemberInfoByCampId(trainingCampId);
    if (trainingMemberInfoList != null && trainingMemberInfoList.size() != 0) {

      // 所有有年龄的人年龄相加
      int totalAge = 0;
      // 所有有年龄的人的人数
      int ageNum = 0;
      // 所有有级别的人级别相加
      int totalLevel = 0;
      // 所有有级别的人的人数
      int levelNum = 0;
      // 所有女性人数
      int totalFemal = 0;
      // 所有有性别的人的人数
      int hasGenderNum = 0;

      for (TrainingMember trainingMember : trainingMemberInfoList) {
        // 计算平均级别
        if (!StringUtils.isEmpty(trainingMember.getTrainingMemberCurrentLevel())) {
          levelNum++;
          totalLevel += Integer.parseInt(trainingMember.getTrainingMemberCurrentLevel()
              .substring(14));
        }

        // 计算平均年龄
        if (!StringUtils.isEmpty(trainingMember.getTrainingMemberAge())) {
          ageNum++;
          totalAge += trainingMember.getTrainingMemberAge();
        }

        // 计算女性百分比
        // 0:女;1:男;2:还没选
        if (trainingMember.getTrainingMemberGender() != UserConstant.USER_GENDER_NOT) {
          hasGenderNum++;
          if (trainingMember.getTrainingMemberGender() == UserConstant.USER_GENDER_FEMALE) {
            totalFemal++;
          }
        }
      }

      // 求出的所有平均分返回，供训练营表使用，下标0为平均年龄，1为平均级别，2为女性占百分比
      if (ageNum != 0) {
        avg.set(0, totalAge / ageNum);
      }
      if (levelNum != 0) {
        avg.set(1, totalLevel / levelNum);
      }
      if (hasGenderNum != 0) {
        avg.set(2, 100 * totalFemal / hasGenderNum);
      }
      avg.set(3, trainingMemberInfoList.size());
    }

    // 更新训练营平均数等数据
    TrainingCamp paramObj = new TrainingCamp();
    paramObj.setKeyId(trainingCampId);
    paramObj.setTrainingCampAverageAge(avg.get(0));
    paramObj.setTrainingCampAverageLevel(avg.get(1));
    paramObj.setTrainingCampFemalePresent(avg.get(2));
    paramObj.setTrainingCampNum(avg.get(3));
    trainingCampEntityDao.update(paramObj);
  }

  /**
   * Title: 根据训练营查询成员列表<br>
   * Description: 根据训练营查询成员列表<br>
   * CreateDate: 2017年8月8日 下午4:47:24<br>
   * 
   * @category 根据训练营查询成员列表
   * @author komi.zsy
   * @param cons
   *          组合查询
   * @param sort
   * @param order
   * @param page
   * @param rows
   * @param trainingCampId
   *          训练营id
   * @return
   * @throws Exception
   */
  public Page findPageEasyui(String cons, String sort, String order, Integer page,
      Integer rows, String trainingCampId) throws Exception {
    Page p = trainingMemberEntityDao.findPageEasyui(cons, sort, order, page, rows, trainingCampId);
    List<TrainingMemberParam> trainingMemberParamList = (List<TrainingMemberParam>) p.getDatas();

    // 将trainingMemberParamList里的加分扣分项合并成一个字符串
    if (trainingMemberParamList != null && trainingMemberParamList.size() != 0) {
      Map<String, TrainingMemberParam> paramMap = new HashMap<String, TrainingMemberParam>();
      for (TrainingMemberParam trainingMemberParam : trainingMemberParamList) {
        TrainingMemberParam trainingMemberParamMapObj = paramMap.get(trainingMemberParam
            .getTrainingMemberUserId());
        // map里已有，则继续添加加分扣分字符串，没有则插入一个新的
        if (trainingMemberParamMapObj != null) {
          if (trainingMemberParam.getTrainingMemberOptionType() != null) {
            // true是加分，false是扣分
            if (trainingMemberParam.getTrainingMemberOptionType()) {
              // 如果是第一项，就不需要回车，否则需要回车
              if (!StringUtils.isEmpty(trainingMemberParamMapObj.getAddScore())) {
                trainingMemberParamMapObj.setAddScore(trainingMemberParamMapObj.getAddScore()
                    + "<br/>");
              }
              trainingMemberParamMapObj.setAddScore(trainingMemberParamMapObj.getAddScore()
                  + trainingMemberParam.getTrainingMemberOptionReason() + "+" + trainingMemberParam
                      .getTrainingMemberOptionScore());
            } else {
              // 如果是第一项，就不需要回车，否则需要回车
              if (!StringUtils.isEmpty(trainingMemberParamMapObj.getReductionScore())) {
                trainingMemberParamMapObj.setReductionScore(trainingMemberParamMapObj
                    .getReductionScore()
                    + "<br/>");
              }
              trainingMemberParamMapObj.setReductionScore(trainingMemberParamMapObj
                  .getReductionScore()
                  + trainingMemberParam.getTrainingMemberOptionReason() + trainingMemberParam
                      .getTrainingMemberOptionScore());
            }
          }
        } else {
          if (trainingMemberParam.getTrainingMemberOptionType() != null) {
            // true是加分，false是扣分
            if (trainingMemberParam.getTrainingMemberOptionType()) {
              trainingMemberParam.setAddScore(trainingMemberParam.getTrainingMemberOptionReason()
                  + "+" + trainingMemberParam.getTrainingMemberOptionScore());
              trainingMemberParam.setReductionScore("");
            } else {
              trainingMemberParam.setAddScore("");
              trainingMemberParam.setReductionScore(trainingMemberParam
                  .getTrainingMemberOptionReason() + trainingMemberParam
                      .getTrainingMemberOptionScore());
            }
          }

          paramMap.put(trainingMemberParam.getTrainingMemberUserId(), trainingMemberParam);
        }
      }

      trainingMemberParamList = new ArrayList<>(paramMap.values());
      p.setDatas(trainingMemberParamList);
    }

    return p;
  }

  /**
   * Title: 新增数据<br>
   * Description: insert<br>
   * CreateDate: 2016年4月11日 上午8:57:05<br>
   * 
   * @category 新增数据
   * @author yangmh
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int insert(TrainingMember paramObj) throws Exception {
    return trainingMemberEntityDao.insert(paramObj);
  }

  /**
   * Title: 批量新增数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:04:36<br>
   * 
   * @category 批量新增数据
   * @author yangmh
   * @param paramObj
   *          参数对象List
   * @return 执行成功数
   * @throws Exception
   */
  public int batchInsert(List<TrainingMember> paramObjList) throws Exception {
    return trainingMemberEntityDao.batchInsert(paramObjList);
  }

  /**
   * Title: 逻辑删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:00<br>
   * 
   * @category 逻辑删除数据
   * @author yangmh
   * @param keyId
   *          如果是多个删除就使用逗号分隔,否则就是一个keyId
   * @return 执行成功数
   * @throws Exception
   */
  public int delete(String keyIds) throws Exception {
    return trainingMemberEntityDao.delete(keyIds);
  }

  /**
   * Title: 逻辑删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:23<br>
   * 
   * @category 逻辑删除数据
   * @author yangmh
   * @param keyIds
   * @return 执行成功数
   * @throws Exception
   */
  public int delete(List<String> keyIds) throws Exception {
    return trainingMemberEntityDao.delete(keyIds);
  }

  /**
   * Title: 物理删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:50<br>
   * 
   * @category 物理删除数据
   * @author yangmh
   * @param keyIds
   * @return 执行成功数
   * @throws Exception
   */
  public int deleteForReal(List<String> keyIds) throws Exception {
    return trainingMemberEntityDao.deleteForReal(keyIds);
  }

  /**
   * Title: 物理删除数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:12:50<br>
   * 
   * @category 物理删除数据
   * @author yangmh
   * @param keyId
   *          如果是多个删除就使用逗号分隔,否则就是一个keyId
   * @return 执行成功数
   * @throws Exception
   */
  public int deleteForReal(String keyIds) throws Exception {
    return trainingMemberEntityDao.deleteForReal(keyIds);
  }

  /**
   * Title: 修改数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:40:18<br>
   * 
   * @category 修改数据
   * @author yangmh
   * @param paramObj
   *          参数对象
   * @return 执行成功数
   * @throws Exception
   */
  public int update(TrainingMember paramObj) throws Exception {
    return trainingMemberEntityDao.update(paramObj);
  }

  /**
   * Title: 更新用户信息<br>
   * Description: 更新用户信息<br>
   * CreateDate: 2017年8月10日 下午6:48:32<br>
   * 
   * @category 更新用户信息
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int updateUserInfo(TrainingMember paramObj) throws Exception {
    return trainingMemberEntityDao.updateMemberUserInfo(paramObj);
  }

  /**
   * Title: 更新手机号<br>
   * Description: 更新手机号<br>
   * CreateDate: 2017年8月10日 下午6:48:35<br>
   * 
   * @category 更新手机号
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int updateUserPhone(TrainingMember paramObj) throws Exception {
    return trainingMemberEntityDao.updateMemberUserPhone(paramObj);
  }

  /**
   * Title: 更新当前等级<br>
   * Description: 更新当前等级<br>
   * CreateDate: 2017年8月10日 下午6:48:39<br>
   * 
   * @category 更新当前等级
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int updateMemberUserCurrentLevel(TrainingMember paramObj) throws Exception {
    return trainingMemberEntityDao.updateMemberUserCurrentLevel(paramObj);
  }

  /**
   * Title: 更新用户真实姓名<br>
   * Description: 更新用户真实姓名<br>
   * CreateDate: 2017年8月10日 下午6:48:32<br>
   * 
   * @category 更新用户真实姓名
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int updateMemberUserRealName(TrainingMember paramObj) throws Exception {
    return trainingMemberEntityDao.updateMemberUserRealName(paramObj);
  }

  /**
   * Title: 更新用户头像<br>
   * Description: 更新用户头像<br>
   * CreateDate: 2017年8月10日 下午6:48:32<br>
   * 
   * @category 更新用户头像
   * @author komi.zsy
   * @param paramObj
   * @return
   * @throws Exception
   */
  public int updateMemberUserPic(TrainingMember paramObj) throws Exception {
    return trainingMemberEntityDao.updateMemberUserPic(paramObj);
  }

  /**
   * Title: 批量修改数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午12:46:26<br>
   * 
   * @category 批量修改数据
   * @author yangmh
   * @param paramObjList
   *          参数对象List
   * @return 执行成功数
   * @throws Exception
   */
  public int batchUpdate(List<TrainingMember> paramObjList) throws Exception {
    return trainingMemberEntityDao.batchUpdate(paramObjList);
  }

  /**
   * Title: 查询数量数据<br>
   * Description: 仅用于当前Dao所属表的数据操作<br>
   * CreateDate: 2016年4月4日 上午1:09:45<br>
   * 
   * @category 查询数量数据
   * @author yangmh
   * @param paramObj
   *          参数对象
   * @return
   * @throws Exception
   */
  public int findCount(TrainingMember paramObj) throws Exception {
    return trainingMemberEntityDao.findCount(paramObj);
  }

  /**
   * Title: 批量插入训练营成员<br>
   * Description: 插入创建lc名下所有训练营时间在口语训练营有效期内的学员，并计算各项平均值返回<br>
   * CreateDate: 2017年8月11日 下午6:18:28<br>
   * 
   * @category insertTrainingMember
   * @author komi.zsy
   * @param trainingCampId
   *          训练营id
   * @param learningCoachId
   *          lc id
   * @param trainingCampStartTime
   *          训练营开始时间
   * @param trainingCampEndTime
   *          训练营结束时间
   * @param updateUserId
   *          更新人id
   * @return List 下标0为平均年龄，1为平均级别，2为女性占百分比,3为当前训练营人数
   * @throws Exception
   */
  @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
  public List<Integer> insertTrainingMember(String trainingCampId, String learningCoachId,
      Date trainingCampStartTime, Date trainingCampEndTime, String updateUserId)
      throws Exception {
    // 根据learning_coach_id 查出其名下的所有开始时间和结束时间在合同中课程有效期期间内的学员
    List<UserInfoOrderCourseParam> userInfoOrderCourseParamList = userEntityDao
        .findUserByOrderCourseIsUsedAndLearningCoachId(learningCoachId, trainingCampStartTime,
            trainingCampEndTime);
    // 求出的所有平均分返回，供训练营表使用，下标0为平均年龄，1为平均级别，2为女性占百分比,3为当前训练营人数
    List<Integer> avg = new ArrayList<Integer>();
    avg.add(0);
    avg.add(0);
    avg.add(0);
    avg.add(0);
    if (userInfoOrderCourseParamList != null && userInfoOrderCourseParamList.size() != 0) {
      List<TrainingMember> insertTrainingMemberList = new ArrayList<TrainingMember>();
      // 所有有年龄的人年龄相加
      int totalAge = 0;
      // 所有有年龄的人的人数
      int ageNum = 0;
      // 所有有级别的人级别相加
      int totalLevel = 0;
      // 所有有级别的人的人数
      int levelNum = 0;
      // 所有女性人数
      int totalFemal = 0;
      // 所有有性别的人的人数
      int hasGenderNum = 0;
      for (UserInfoOrderCourseParam userInfoOrderCourseParam : userInfoOrderCourseParamList) {
        // 处理一下课程的有效期，看是否在训练营时间内
        Calendar cal = Calendar.getInstance();
        cal.setTime(userInfoOrderCourseParam.getStartOrderTime());
        // 课程单位类型（0:节，1:月，2：天）
        switch (userInfoOrderCourseParam.getCourseUnitType()) {
          case 0:
            // 口语训练营不能按节卖
            break;
          case 1:
            cal.add(Calendar.MONTH, userInfoOrderCourseParam.getCourseCount());
            break;
          case 2:
            cal.add(Calendar.DATE, userInfoOrderCourseParam.getCourseCount());
            break;
          default:
            break;
        }
        if (cal.getTimeInMillis() >= trainingCampEndTime.getTime()) {
          TrainingMember trainingMember = new TrainingMember();
          trainingMember.setCreateUserId(updateUserId);
          trainingMember.setUpdateUserId(updateUserId);
          trainingMember.setTrainingCampId(trainingCampId);
          trainingMember.setTrainingMemberUserId(userInfoOrderCourseParam.getKeyId());
          trainingMember.setTrainingMemberUserCode(userInfoOrderCourseParam.getUserCode());
          trainingMember.setTrainingMemberEnglishName(userInfoOrderCourseParam.getEnglishName());
          trainingMember.setTrainingMemberRealName(userInfoOrderCourseParam.getRealName());
          trainingMember.setTrainingMemberGender(userInfoOrderCourseParam.getGender());
          trainingMember.setTrainingMemberCurrentLevel(userInfoOrderCourseParam.getCurrentLevel());
          trainingMember.setTrainingMemberPic(userInfoOrderCourseParam.getUserPhoto());
          trainingMember.setTrainingMemberPhone(userInfoOrderCourseParam.getPhone());
          // 需要插入的训练营成员数据
          insertTrainingMemberList.add(trainingMember);
          // 计算平均级别
          if (!StringUtils.isEmpty(userInfoOrderCourseParam.getCurrentLevel())) {
            levelNum++;
            totalLevel += Integer.parseInt(userInfoOrderCourseParam.getCurrentLevel().substring(
                14));
          }

          // 计算平均年龄
          if (!StringUtils.isEmpty(userInfoOrderCourseParam.getIdcard())) {
            try {
              // 有时候身份证会有莫名其妙的错误，不管
              int idCardYear = Integer.parseInt(userInfoOrderCourseParam.getIdcard().substring(6,
                  10));
              int nowYear = Calendar.getInstance().get(Calendar.YEAR);
              if (idCardYear >= 1900 && idCardYear <= nowYear) {
                // 根据tom要求，从身份证算年龄，10xx年到20xx年都为合法年龄
                int age = nowYear - idCardYear;
                ageNum++;
                totalAge += age;
                trainingMember.setTrainingMemberAge(age);
              }
            } catch (Exception e) {
              // 有时候身份证会有莫名其妙的错误，不管.这里如果出错，这个学员就不算年龄
            }
          }

          // 计算女性百分比
          // 0:女;1:男;2:还没选
          if (userInfoOrderCourseParam.getGender() != UserConstant.USER_GENDER_NOT) {
            hasGenderNum++;
            if (userInfoOrderCourseParam.getGender() == UserConstant.USER_GENDER_FEMALE) {
              totalFemal++;
            }
          }
        }
      }
      // 批量插入训练营成员信息
      trainingMemberEntityDao.batchInsert(insertTrainingMemberList);

      // 求出的所有平均分返回，供训练营表使用，下标0为平均年龄，1为平均级别，2为女性占百分比
      if (ageNum != 0) {
        avg.set(0, totalAge / ageNum);
      }
      if (levelNum != 0) {
        avg.set(1, totalLevel / levelNum);
      }
      if (hasGenderNum != 0) {
        avg.set(2, 100 * totalFemal / hasGenderNum);
      }
      avg.set(3, insertTrainingMemberList.size());
    }

    return avg;
  }
}