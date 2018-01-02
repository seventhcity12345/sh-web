package com.webi.hwj.index.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.CommonJsonObject;
import com.webi.hwj.bean.SessionUser;
import com.webi.hwj.constant.ErrorCodeEnum;
import com.webi.hwj.course.service.CourseCommentService;
import com.webi.hwj.course.service.CourseOne2OneService;
import com.webi.hwj.index.param.FindLearningProgressParam;
import com.webi.hwj.index.service.OrderCourseProgressService;
import com.webi.hwj.ordercourse.entity.OrderCourse;
import com.webi.hwj.ordercourse.param.FindUserEffectiveContractReturnParam;
import com.webi.hwj.ordercourse.param.OrderCourseOptionParam;
import com.webi.hwj.ordercourse.service.OrderCourseOptionService;
import com.webi.hwj.ordercourse.service.OrderCourseService;
import com.webi.hwj.subscribecourse.param.SubscribeCourseParam;
import com.webi.hwj.subscribecourse.service.SubscribeCourseService;
import com.webi.hwj.tellmemore.service.TellmemoreService;
import com.webi.hwj.user.service.SutdentLearningProgressService;
import com.webi.hwj.user.service.UserService;
import com.webi.hwj.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "会员首页相关接口信息")
@Controller
public class UserCenterController {
  private static Logger logger = Logger.getLogger(UserCenterController.class);
  @Resource
  private UserService userService;

  @Resource
  private CourseOne2OneService courseOne2OneService;

  @Resource
  private OrderCourseService orderCourseService;

  @Resource
  private OrderCourseOptionService orderCourseOptionService;

  @Resource
  private OrderCourseProgressService orderCourseProgressService;

  @Resource
  private SubscribeCourseService subscribeCourseService;

  @Resource
  private CourseCommentService courseCommentService;

  @Resource
  private TellmemoreService tellmemoreService;

  @Resource
  SutdentLearningProgressService sutdentLearningProgressService;

  /**
   * Title: 会员中心课程表<br>
   * Description: 会员中心课程表<br>
   * CreateDate: 2016年9月20日 下午3:48:56<br>
   * 
   * @category 会员中心课程表
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/ucenter/classTable")

  public CommonJsonObject classTable(HttpServletRequest request) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    try {
      // 课程表
      /**
       * 1.通过session中的当前user_id以及当前时间作为条件查询t_subscribe_course,
       * 并按start_time排倒序（只显示下课前，已上完的课不显示，is_used = 1）
       * 2.注意：后台的查询出来的列表不需要加分页，直接显示到前台，前台使用障眼法。 3.用户在预约的时候将新加入的若干字段。
       */
      List<SubscribeCourseParam> subscribeCourseList = subscribeCourseService
          .findClassTablesByUserId(sessionUser.getKeyId());
      json.setData(subscribeCourseList);

    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }

    return json;
  }

  /**
   * Title: 课程回顾<br>
   * Description: 课程回顾<br>
   * CreateDate: 2016年9月21日 上午10:01:40<br>
   * 
   * @category 课程回顾
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/ucenter/reviewCourses")
  public CommonJsonObject reviewCourses(HttpServletRequest request) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    try {
      /**
       * 查询课程预约表里的上过课的数据（如果noshow的数据不查出来,老师已删除的课程也查出来）
       * 现在默认查前三条数据，以后有需要可以从前台传页数和行数
       */
      List<SubscribeCourseParam> subscribeCourseList = subscribeCourseService
          .findCompleteSubscribeCourseByUserId(sessionUser.getKeyId(), 1, 3);
      json.setData(subscribeCourseList);

    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }

    return json;
  }

  /**
   * Title: 获取合同学习进度<br>
   * Description: 会员中心首页学员学习进度<br>
   * CreateDate: 2016年9月21日 下午2:37:03<br>
   * 
   * @category 获取合同学习进度
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/ucenter/findContractLearningProgress")
  public CommonJsonObject findContractLearningProgress(HttpServletRequest request)
      throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    try {
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("isStudent", sessionUser.getIsStudent());

      if (sessionUser.getIsStudent()) {
        // 学员： 合同进度计算
        paramMap.put("contractProgressList",
            orderCourseProgressService.findStartingContractListByUserId(sessionUser.getKeyId()));
      } else {
        // 非学员：找是否有未付款的合同
        /**
         * 在非学员状态的用户中心允许快速找到未付款的订单入口 在非学员状态的用户中心允许快速找到CC已发送状态的合同入口。
         * 查看order表中的status状态，确定查询到是否有未签的合同或者
         */
        List<OrderCourse> orderCourseList = orderCourseService
            .findOrdersByUserId(sessionUser.getKeyId());
        if (orderCourseList != null && orderCourseList.size() > 0) {
          // 如果有多个合同，则取最新的那一个
          paramMap.put("contract", orderCourseList.get(0));
        } else {
          paramMap.put("contract", null);
        }
      }

      json.setData(paramMap);

    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
      json.setCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
    }

    return json;
  }

  /**
   * Title: 个人资料学习进度模块<br>
   * Description: 合同剩余课时详情<br>
   * CreateDate: 2016年9月20日 下午3:49:18<br>
   * 
   * @category 个人资料学习进度模块
   * @author komi.zsy
   * @param request
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/ucenter/remainCourseCount")
  public CommonJsonObject remainCourseCount(HttpServletRequest request) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    try {
      // 查询合同课时信息
      List<OrderCourseOptionParam> orderCourseOptionList = orderCourseOptionService
          .findOrderCourseInfoList(sessionUser.getKeyId());

      if (orderCourseOptionList == null) {
        json.setCode(ErrorCodeEnum.ORDER_NOT_EXIST.getCode());
      } else if (orderCourseOptionList.size() == 0) {
        json.setCode(ErrorCodeEnum.ORDER_NOT_EXIST.getCode());
      } else {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("endOrderTime", orderCourseOptionList.get(0).getEndOrderTime());
        paramMap.put("remainCourseCountList", orderCourseOptionList);
        json.setData(paramMap);
      }

    } catch (Exception e) {
      logger.error(e.toString());
      e.printStackTrace();
      json.setCode(ErrorCodeEnum.ORDER_NOT_EXIST.getCode());
    }

    return json;
  }

  @ResponseBody
  @RequestMapping("/api/speakhi/v1/ucenter/finishedCourses")
  public CommonJsonObject findFinishedCourses(HttpServletRequest request, Integer page,
      Integer rows) throws Exception {
    CommonJsonObject json = new CommonJsonObject();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    json.setData(courseCommentService.findCoursesAndCommentsByUserId(sessionUser.getKeyId(), page,
        rows));
    return json;
  }

  /**
   * 
   * Title: 查询学习进度<br>
   * Description: findLearningProgress<br>
   * CreateDate: 2017年7月20日 下午4:42:02<br>
   * 
   * @category 查询学习进度
   * @author seven.gz
   */
  @ApiOperation(value = "查询学员学习进度", notes = "查询学员学习进度，新版学员中心使用")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/learningProgress", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<FindLearningProgressParam> findLearningProgress(
      HttpServletRequest request) throws Exception {
    CommonJsonObject<FindLearningProgressParam> json =
        new CommonJsonObject<FindLearningProgressParam>();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);
    json.setData(sutdentLearningProgressService.findLearningProgress(
        sessionUser.getKeyId(),
        new Date()));
    return json;
  }

  /**
   * 
   * Title: 查询学员合同信息正在执行的一个合同<br>
   * Description: findUserEffectiveContractInfo<br>
   * CreateDate: 2017年7月21日 下午4:47:38<br>
   * 
   * @category 查询学员合同信息正在执行的一个合同
   * @author seven.gz
   */
  @ApiOperation(value = "查询学员合同信息正在执行的一个合同", notes = "成人不支持多合同所以不做考虑")
  @ResponseBody
  @RequestMapping(value = "/api/speakhi/v1/ucenter/effectiveContractInfo",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public CommonJsonObject<FindUserEffectiveContractReturnParam> findUserEffectiveContractInfo(
      HttpServletRequest request) throws Exception {

    CommonJsonObject<FindUserEffectiveContractReturnParam> json =
        new CommonJsonObject<FindUserEffectiveContractReturnParam>();
    SessionUser sessionUser = SessionUtil.getSessionUser(request);

    FindUserEffectiveContractReturnParam findUserEffectiveContractReturnParam =
        new FindUserEffectiveContractReturnParam();
    findUserEffectiveContractReturnParam.setCorseCountList(orderCourseOptionService
        .findOrderCourseInfoListMergeCourseType(sessionUser.getKeyId()));
    json.setData(findUserEffectiveContractReturnParam);
    return json;
  }

}
