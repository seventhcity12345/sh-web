package com.webi.hwj.util;

import java.security.MessageDigest;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class SHATool {
  public static final String KEY = "xzN-5up-FM9-qxX";

  /**
   * SHA-512消息摘要算法
   */
  public static String encodeSHA512(String data) throws Exception {
    MessageDigest md = MessageDigest.getInstance("SHA-512");
    byte[] digest = md.digest(data.getBytes());
    return new HexBinaryAdapter().marshal(digest);
  }

  /**
   * 验签加密
   * 
   * @throws Exception
   */
  public static String encryption(String rcode, String mobile) throws Exception {
    return SHATool.encodeSHA512(rcode + "~!@!~" + mobile + "~!@!~" + SHATool.encodeSHA512(KEY));
  }

  public static void main(String[] args) {
    try {
      System.out.println(SHATool.encryption("1", "18600609747"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
