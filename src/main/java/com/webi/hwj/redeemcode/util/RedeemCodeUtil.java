package com.webi.hwj.redeemcode.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;

import com.webi.hwj.redeemcode.constant.RedeemCodeConstant;
import com.webi.hwj.redeemcode.entity.RedeemCode;
import com.webi.hwj.util.TxtUtil;
import com.webi.hwj.util.UUIDUtil;

public class RedeemCodeUtil {
  private static Logger logger = Logger.getLogger(RedeemCodeUtil.class);

  public static void main(String[] args) {

  }

  /**
   * Title: 生成兑换码<br>
   * Description: 生成兑换码<br>
   * CreateDate: 2017年1月18日 上午10:52:04<br>
   * @category 生成兑换码 
   * @author komi.zsy
   * @param paramObj 兑换码相关信息
   * @return
   * @throws Exception
   */
  public static RedeemCode createRedeemcodeObj(RedeemCode paramObj) throws Exception {
    // 32为key_id
    String keyId = UUIDUtil.uuid(32);
    RedeemCode redeemCode = new RedeemCode();
    BeanUtils.copyProperties(paramObj, redeemCode);
    // key_id
    redeemCode.setKeyId(keyId);
    // redeem_code 12位兑换码
    redeemCode.setRedeemCode(keyId.substring(keyId.length() - RedeemCodeConstant.REDEEMCODE_LENGTH, keyId.length()));

    logger.debug("生成的redeemcode信息：" + redeemCode);
    return redeemCode;
  }
  
  /**
   * Title: 生成兑换码文件<br>
   * Description: 生成兑换码文件<br>
   * CreateDate: 2017年1月16日 下午5:38:35<br>
   * @category 生成兑换码文件 
   * @author komi.zsy
   * @param redeemCodeList
   * @return
   * @throws Exception
   */
  public static String createRedeemcodeFile(List<RedeemCode> redeemCodeList) throws Exception {
    //生成兑换码csv文件，以供下载
    if(redeemCodeList!=null && redeemCodeList.size()!=0){
      //获取系统回车符号
      String enterString = "\t"+System.getProperty("line.separator");
      //文件内容
      StringBuffer mailBufferString = new StringBuffer();
      //列的标题
//      mailBufferString.append("兑换码" + enterString);
      for(RedeemCode redeemCode : redeemCodeList){
        //兑换码
        mailBufferString.append(redeemCode.getRedeemCode() + enterString);
      }
      //生成文件
//      return TxtUtil.exportTxtFile("兑换码.csv", mailBufferString.toString());
      return mailBufferString.toString();
    }
    return null;
  }
 
}
