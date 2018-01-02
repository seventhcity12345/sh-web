package com.webi.hwj.mail;

import org.apache.log4j.Logger;

import com.mingyisoft.javabase.util.MemcachedUtil;

public class MailUtil {
  private static Logger logger = Logger.getLogger(MailUtil.class);

  /**
   * 
   * Title: 发送邮件<br>
   * Description: 发送邮件<br>
   * CreateDate: 2016年9月22日 下午4:46:03<br>
   * 
   * @category 发送邮件
   * @author seven.gz
   * @param toMailAddr
   *          收件人地址
   * @param subject
   *          主题
   * @param mailbody
   *          邮件内容
   */
  public static void sendMail(String toMailAddr, String subject, String mailbody) {
    logger.info("正在发送邮件:toMailAddr:" + toMailAddr + ",subject:" + subject
        + ",mailbody" + mailbody);
    if (toMailAddr == null || "".equals(toMailAddr) || "null".equals(toMailAddr)) {

    } else {
      // 获得配置文件中的发件 信息
      Email themail = initMail(toMailAddr, subject, mailbody);
      // **改为相应邮箱密码
      themail.sendout();
    }
  }

  /**
   * Title: 发送邮件,带附件<br>
   * Description: 发送邮件,带附件<br>
   * CreateDate: 2016年9月22日 下午4:46:36<br>
   * 
   * @category 发送邮件,带附件
   * @author seven.gz
   * @param toMailAddr
   *          收件人地址
   * @param subject
   *          主题
   * @param mailbody
   *          邮件内容
   * @param filename
   *          本地文件路径
   */
  public static void sendMail(String toMailAddr, String subject, String mailbody, String filename) {
    logger.info("正在发送邮件:toMailAddr:" + toMailAddr + ",subject:" + subject
        + ",mailbody" + mailbody + ",fileName:" + filename);
    if (toMailAddr == null || "".equals(toMailAddr) || "null".equals(toMailAddr)) {

    } else {
      // 获得配置文件中的发件 信息
      Email themail = initMail(toMailAddr, subject, mailbody);
      // **改为相应邮箱密码
      themail.addFileAffix(filename);
      themail.sendout();
    }
  }

  /**
   * 
   * Title: sendMail<br>
   * Description: sendMail<br>
   * CreateDate: 2016年9月22日 下午4:49:46<br>
   * 
   * @category sendMail
   * @author seven.gz
   * @param toMailAddr
   *          收件人地址
   * @param subject
   *          主题
   * @param mailbody
   *          邮件内容
   * @param fileName
   *          附件的名称
   * @param fileContetnt
   *          附件内容
   */
  public static void sendMail(String toMailAddr, String subject, String mailbody, String fileName,
      String fileContetnt) {
    logger.info("正在发送邮件:toMailAddr:" + toMailAddr + ",subject:" + subject
        + ",mailbody" + mailbody + ",fileName:" + fileName);
    if (toMailAddr == null || "".equals(toMailAddr) || "null".equals(toMailAddr)) {

    } else {
      // 获得配置文件中的发件 信息
      Email themail = initMail(toMailAddr, subject, mailbody);
      // **改为相应邮箱密码

      themail.addFileAffixForString(fileName, fileContetnt);
      themail.sendout();
    }
  }

  /**
   * Title: 初始化邮件信息<br>
   * Description: 初始化邮件信息<br>
   * CreateDate: 2016年9月23日 上午10:04:41<br>
   * 
   * @category 初始化邮件信息
   * @author seven.gz
   * @param toMailAddr
   *          收件人地址
   * @param subject
   *          邮件主题
   * @param mailbody
   *          邮件内容
   * @return Email
   */
  private static Email initMail(String toMailAddr, String subject, String mailbody) {
    /**
     ************* 切切注意********. 注意 用此程序发邮件必须邮箱支持smtp服务 在2006年以后申请的163邮箱是不支持的 我知道sina邮箱
     * sohu邮箱 qq邮箱支持 但是sina和qq邮箱需要手工设置开启此功能 所以在测试时最好使用这三个邮箱 sina邮箱的smtp设置方法如下
     * 登录sina邮箱 依次点击 邮箱设置--->帐户--->POP/SMTP设置 将开启复选框选中 然后保存 切切注意********
     */
    Email themail = new Email(MemcachedUtil.getConfigValue("mail_smtp_host"),
        Integer.valueOf(MemcachedUtil.getConfigValue("mail_smtp_port")));// 这里以新浪邮箱为例子
    // 服务器地址
    themail.setNeedAuth(true);
    themail.setSubject(subject);// 邮件主题
    themail.setBody(mailbody);// 邮件正文
    themail.setTo(toMailAddr);// 收件人地址
    themail.setFrom(MemcachedUtil.getConfigValue("mail_from_addr"));// 发件人地址
    themail.setNamePass(MemcachedUtil.getConfigValue("mail_name"),
        MemcachedUtil.getConfigValue("mail_pwd"));// 发件人地址和密码
    // **改为相应邮箱密码
    return themail;
  }

  public static void main(String[] args) {
    // sendMail("seven.gz@webi.com.cn", "sevenTest", "sevenTest",
    // "c:\\Users\\seven.gz\\Desktop\\tem.csv");

    sendMail("seven.gz@webi.com.cn", "sevenTest", "sevenTest",
        "sevenTest.txt", "sevenTest测试");
  }
}
