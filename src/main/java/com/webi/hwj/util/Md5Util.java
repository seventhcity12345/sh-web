package com.webi.hwj.util;

import java.security.MessageDigest;

public class Md5Util {

  /**
   * 
   * Title: 生成md5编码<br>
   * Description: getMD5<br>
   * CreateDate: 2017年8月24日 下午6:11:25<br>
   * 
   * @category 生成md5编码
   * @author seven.gz
   * @param str
   * @return
   */
  public static String getMd5Str(String str) {
    try {
      // 生成一个MD5加密计算摘要
      MessageDigest md = MessageDigest.getInstance("MD5");
      // 计算md5函数
      md.update(str.getBytes());
      return byteArrayToHex(md.digest());
    } catch (Exception e) {
      e.printStackTrace();
      return str;
    }
  }

  /**
   * 
   * Title: byte转16位字符串<br>
   * Description: byteArrayToHex<br>
   * CreateDate: 2017年8月28日 下午4:01:12<br>
   * 
   * @category byte转16位字符串
   * @author seven.gz
   * @param byteArray
   * @return
   */
  public static String byteArrayToHex(byte[] byteArray) {

    // 首先初始化一个字符数组，用来存放每个16进制字符

    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
        'f' };

    // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））

    char[] resultCharArray = new char[byteArray.length * 2];

    // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

    int index = 0;

    for (byte b : byteArray) {

      resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];

      resultCharArray[index++] = hexDigits[b & 0xf];

    }
    // 字符数组组合成字符串返回

    return new String(resultCharArray);
  }
}
