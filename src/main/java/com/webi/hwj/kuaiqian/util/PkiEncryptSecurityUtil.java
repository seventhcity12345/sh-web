/** 
 * File: PkiEncryptSecurityUtil.java<br> 
 * Project: hwj-svn<br> 
 * Package: com.webi.hwj.kuaiqian.util<br> 
 * Company:韦博英语在线教育部<br> 
 * CreateDate:2015年8月15日 上午10:50:08
 * @author athrun.cw
 */
package com.webi.hwj.kuaiqian.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.apache.log4j.Logger;

import com.webi.hwj.kuaiqian.bean.WebiHwjParameterConstant;

/**
 * Title: 加密签证，获得加密后的 签证密文 Description: PkiEncryptSecurityUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2015年8月15日 上午10:50:08
 * 
 * @author athrun.cw
 */
public class PkiEncryptSecurityUtil {
  private static Logger logger = Logger.getLogger(PkiEncryptSecurityUtil.class);

  /**
   * 
   * Title: 对明文加密，获得经过证书加密的密文 Description: signSecurityMessage<br>
   * CreateDate: 2015年8月15日 下午12:08:37<br>
   * 
   * @category signSecurityMessage
   * @author athrun.cw
   * @param signMsgVal
   * @return
   */
  public static String signSecurityMessage(String signMsg) {
    String base64 = "";
    try {
      logger.info("开始证书加密...");
      // 密钥仓库
      KeyStore ks = KeyStore.getInstance("PKCS12");
      // 读取 私钥
      // String file =
      // PkiEncryptSecurityUtil.class.getResource("tester-rsa.pfx").getPath().replaceAll("%20",
      // " ");
      String file = PkiEncryptSecurityUtil.class.getClassLoader()
          .getResource(WebiHwjParameterConstant.PRIVATE_WEBI_RSA_KEY).getPath()
          .replaceAll("%20", " ");
      logger.debug("读取商户私钥仓库（相对路径）-------> " + file);

      FileInputStream ksfis = new FileInputStream(file);
      BufferedInputStream ksbufin = new BufferedInputStream(ksfis);
      // char[] keyPwd = WebiHwjParameterUtil.MERCHANT_PASSWORD.toCharArray();
      char[] keyPwd = WebiHwjParameterConstant.MERCHANT_WEBI_HWJ_PASSWORD.toCharArray();
      // logger.debug("证书密码字符集为：" + keyPwd.toString());
      // char[] keyPwd = "YaoJiaNiLOVE999Year".toCharArray();
      ks.load(ksbufin, keyPwd);
      // 从密钥仓库得到私钥
      PrivateKey priK = (PrivateKey) ks.getKey("test-alias", keyPwd);
      Signature signature = Signature.getInstance("SHA1withRSA");
      signature.initSign(priK);
      signature.update(signMsg.getBytes("utf-8"));
      sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
      base64 = encoder.encode(signature.sign());
    } catch (FileNotFoundException e) {
      logger.error("文件找不到");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    logger.debug("证书加密完成，密文为： " + base64);
    return base64;
  }

  /**
   * 
   * Title: 判断明文和密文的一致性 Description: enCodeByCer<br>
   * CreateDate: 2015年8月15日 下午12:09:07<br>
   * 
   * @category enCodeByCer
   * @author athrun.cw
   * @param signMsgVal
   * @param signSecurityMsgVal
   * @return
   */
  public static boolean enCodeByCer(String val, String msg) {
    boolean flag = false;
    try {
      logger.info("证书解密验证开始...");
      // 获得快钱公钥
      // String file =
      // PkiEncryptSecurityUtil.class.getResource("99bill[1].cert.rsa.20140803.cer").toURI().getPath();
      // String file =
      // PkiEncryptSecurityUtil.class.getResource(WebiHwjParameterUtil.PUBLIC_KUAIQIAN_RSA_KEY).toURI().getPath();
      String file = PkiEncryptSecurityUtil.class.getClassLoader()
          .getResource(WebiHwjParameterConstant.PUBLIC_KUAIQIAN_RSA_KEY).toURI().getPath();

      logger.debug("读取快钱公钥仓库（相对路径）-------> " + file);
      FileInputStream inStream = new FileInputStream(file);
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
      // 获得公钥
      PublicKey pk = cert.getPublicKey();
      // 签名
      Signature signature = Signature.getInstance("SHA1withRSA");
      signature.initVerify(pk);
      signature.update(val.getBytes());
      // 解码
      sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
      System.out.println(new String(decoder.decodeBuffer(msg)));
      flag = signature.verify(decoder.decodeBuffer(msg));
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("no");
    }
    logger.info("证书解密验证完成 ---> " + flag);
    return flag;
  }
}
