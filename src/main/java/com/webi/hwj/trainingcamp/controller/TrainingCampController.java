package com.webi.hwj.trainingcamp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.statistics.param.CoursewareLearningTimeApiParam;
import com.webi.hwj.statistics.service.StatisticsTellmemoreDayService;
import com.webi.hwj.trainingcamp.param.TrainingCampClanIntegralRankingApiParam;
import com.webi.hwj.trainingcamp.param.TrainingCampClanMemberApiParam;
import com.webi.hwj.trainingcamp.param.TrainingCampIntegralProfileParam;
import com.webi.hwj.trainingcamp.param.TrainingCampParam;
import com.webi.hwj.trainingcamp.service.TrainingCampService;
import com.webi.hwj.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * Title: 口语训练营相关<br>
 * Description: 口语训练营相关<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年8月8日 下午3:17:36
 * 
 * @author felix.yl
 */
@Api(description = "口语训练营相关")
@Controller
public class TrainingCampController {

  private static Logger logger = Logger.getLogger(TrainingCampController.class);

  @Resource
  private TrainingCampService trainingCampService;

  @Resource
  StatisticsTellmemoreDayService statisticsTellmemoreDayService;

  /**
   * 
   * Title: 查询训练营基本信息、LC信息、成员平均级别、昨日活跃人数、平均年龄、女性占比<br>
   * Description: 查询训练营基本信息、LC信息、成员平均级别、昨日活跃人数、平均年龄、女性占比<br>
   * CreateDate: 2017年8月9日 下午3:39:32<br>
   * 
   * @category 查询训练营基本信息、LC信息、成员平均级别、昨日活跃人数、平均年龄、女性占比
   * @author felix.yl
   * @param request
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "成人-会员首页-口语训练营-训练营基本信息【接口】",
      notes = "训练营基本信息、LC信息、成员平均级别、昨日活跃人数、平均年龄、女性占比;<br>"
          + "返回为null,说明该学员没有有效的口语训练营;")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/trainingCampInfo",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<TrainingCampParam> findTrainingCampInfo(HttpServletRequest request)
      throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<TrainingCampParam> json = new CommonJsonObject<TrainingCampParam>();

    // 获取当前登录用户session
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    // 调用Service层
    TrainingCampParam trainingCampInfo = trainingCampService.findTrainingCampInfo(sessionUser);

    // 传给前端
    json.setData(trainingCampInfo);

    return json;
  }

  /**
   * 
   * Title: 查询积分概况接口信息<br>
   * Description: 查询积分概况接口信息<br>
   * CreateDate: 2017年8月9日 下午3:40:23<br>
   * 
   * @category 查询积分概况接口信息
   * @author felix.yl
   * @param request
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "成人-会员首页-口语训练营-积分概况基本信息【接口】",
      notes = "今日学习时间【今日学习时间算法=1v1节数*30+1vn节数*60+rsa时长】、个人总积分、课件学习分、预约上课分、战队活动分(即该学员的加分总分)、<br>"
          + "团队奖励分(训练营平均分第一5分，其余0分)")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/trainingCampIntegralProfileParmInfo",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<TrainingCampIntegralProfileParam> findTrainingCampIntegralProfileParmInfo(
      HttpServletRequest request)
          throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<TrainingCampIntegralProfileParam> json =
        new CommonJsonObject<TrainingCampIntegralProfileParam>();

    // 获取当前登录用户session
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    // 调用Service层
    TrainingCampIntegralProfileParam trainingCampIntegralProfileParmInfo = trainingCampService
        .findTrainingCampIntegralProfileParmInfo(sessionUser);

    // 传给前端
    json.setData(trainingCampIntegralProfileParmInfo);

    return json;
  }

  /**
   * 
   * Title: 战队积分排名接口<br>
   * Description: 战队积分排名接口<br>
   * CreateDate: 2017年8月9日 下午3:40:39<br>
   * 
   * @category 战队积分排名接口
   * @author felix.yl
   * @param request
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "成人-会员首页-口语训练营-战队积分排名【接口】",
      notes = "战队积分排名的前15名,以及当前登录学员的名次、头像、英文名、个人总积分;<br>"
          + "当前学员总积分高于平台的百分比")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/trainingCampClanIntegralRankingInfo",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<TrainingCampClanIntegralRankingApiParam>
      findTrainingCampClanIntegralRankingInfo(HttpServletRequest request) throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<TrainingCampClanIntegralRankingApiParam> json =
        new CommonJsonObject<TrainingCampClanIntegralRankingApiParam>();

    // 获取当前登录用户session
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    // 调用Service层
    TrainingCampClanIntegralRankingApiParam trainingCampClanIntegralRankingInfo =
        trainingCampService.findTrainingCampClanIntegralRankingInfo(sessionUser);

    // 传给前端
    json.setData(trainingCampClanIntegralRankingInfo);

    return json;
  }

  /**
   * 
   * Title: 战队成员接口<br>
   * Description: 战队成员接口<br>
   * CreateDate: 2017年8月9日 下午3:40:39<br>
   * 
   * @category 战队成员接口
   * @author felix.yl
   * @param request
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "成人-会员首页-口语训练营-战队成员【接口】",
      notes = "返回战队成员的头像、英文名称,以及该战队的所有成员人数;<br>"
          + "需要前端传递两个参数：查询战队成员的开始下标(从0开始)、展示条数")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/trainingCampClanMembersInfo",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)

  public CommonJsonObject<TrainingCampClanMemberApiParam>
      findTrainingCampClanMembersInfo(HttpServletRequest request, @ApiParam(
          name = "start", value = "start", required = true,
          example = "0") @RequestParam("start") String start,
          @ApiParam(name = "end", value = "end", required = false,
              example = "15") @RequestParam("end") String end) throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<TrainingCampClanMemberApiParam> json =
        new CommonJsonObject<TrainingCampClanMemberApiParam>();

    // 获取当前登录用户session
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    // 调用Service层
    TrainingCampClanMemberApiParam trainingCampClanMemberApiParam = trainingCampService
        .findTrainingCampClanMembersInfo(sessionUser, start, end);

    // 传给前端
    json.setData(trainingCampClanMemberApiParam);

    return json;
  }

  /**
   * 
   * Title: 课件学习时长对比接口<br>
   * Description: 课件学习时长对比接口<br>
   * CreateDate: 2017年8月9日 下午3:40:39<br>
   * 
   * @category 课件学习时长对比接口
   * @author felix.yl
   * @param request
   * @return
   * @throws Exception
   */
  @ApiOperation(value = "成人-会员首页-口语训练营-课件学习时间对比【接口】",
      notes = "查询最近7天学员平均课件学习时长和当前学员课件学习时长对比;<br>")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/coursewareLearningTimeInfo",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<CoursewareLearningTimeApiParam>
      findCoursewareLearningTimeInfo(HttpServletRequest request) throws Exception {

    // 构建CommonJsonObject
    CommonJsonObject<CoursewareLearningTimeApiParam> json =
        new CommonJsonObject<CoursewareLearningTimeApiParam>();

    // 获取当前登录用户session
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    // 调用service
    CoursewareLearningTimeApiParam coursewareLearningTimeApiParam = statisticsTellmemoreDayService
        .findCoursewareLearningTimeInfo(sessionUser);

    // 传给前端
    json.setData(coursewareLearningTimeApiParam);

    return json;
  }

}