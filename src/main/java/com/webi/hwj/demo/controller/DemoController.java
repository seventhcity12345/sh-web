package com.webi.hwj.demo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingyisoft.javabase.bean.JsonMessage;
import com.mingyisoft.javabase.bean.Page;
import com.webi.hwj.demo.entity.Demo;
import com.webi.hwj.demo.service.DemoService;

/**
 * @category demo控制类
 * @author mingyisoft代码生成工具
 *
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
  private static Logger logger = Logger.getLogger(DemoController.class);
  @Resource
  private DemoService demoService;

  /**
   * 列表页面 http://localhost:8899/demo/list
   * 
   * @category 列表页面-条件查询(分页)
   * @author alex.ymh
   * @param model
   * @return
   * @throws Exception
   */
  @RequestMapping("/list")
  public String list(HttpServletRequest request, Model model, Demo demo, Integer page, Integer rows)
      throws Exception {
    model.addAttribute("page", demoService.findPage(demo, page, rows));
    // 访问地址为 localhost:8080/hwj/demo/detail/主键
    // jsp路径为 hwj\src\main\webapp\WEB-INF\jsp\demo\demo_list.jsp
    return "demo/demo_list";
  }

  /**
   * 返回json http://localhost:8899/demo/jsonList
   * 
   * @category 列表页面-条件查询(分页)
   * @author alex.ymh
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/jsonList")
  public Page jsonList(HttpServletRequest request, Model model, Demo demo, Integer page,
      Integer rows) throws Exception {
    return demoService.findPage(demo, page, rows);
  }

  /**
   * 查看详情页面
   * 
   * @category 查看详情页面
   * @author alex.ymh
   * @param model
   * @param keyId
   *          主键
   * @return
   * @throws Exception
   */
  @RequestMapping("/detail/{keyId}")
  public String detail(Model model, @PathVariable(value = "keyId") String keyId) throws Exception {
    model.addAttribute("obj", demoService.findOneByKeyId(keyId));
    return "demo/demo_detail";
  }

  @RequestMapping("/delete/{keyId}")
  public String delete(Model model, @PathVariable(value = "keyId") String keyId) throws Exception {
    model.addAttribute("obj", demoService.delete(keyId));
    // 直接跳转回某个controller里的方法
    return "redirect:/demo/list";
  }

  /**
   * demo保存
   * 
   * @category sign保存
   * @param request
   * @param model
   * @return
   * @throws Exception
   */
  @ResponseBody
  @SuppressWarnings("rawtypes")
  @RequestMapping("/save")
  public JsonMessage save(HttpServletRequest request, Demo demo) throws Exception {
    if (!StringUtils.isEmpty(demo.getKeyId())) {// 修改
      demoService.update(demo);
    } else {// 新增
      demoService.insert(demo);
    }

    return new JsonMessage();
  }
}