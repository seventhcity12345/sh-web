package com.webi.hwj.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * Title: 文本文件相关工具类<br>
 * Description: TxtUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2016年3月28日 上午10:48:42
 * 
 * @author komi.zsy
 */
public class TxtUtil {
  private static Logger logger = Logger.getLogger(TxtUtil.class);

  /**
   * Title: 读取txt文件<br>
   * Description: loadTxtFile<br>
   * CreateDate: 2016年3月28日 上午11:01:41<br>
   * 
   * @category 读取txt文件
   * @author alex.yangmh
   * @param path
   *          文件所在路径,注意区分win系统和linux系统
   * @return txt中每行的list集合,注意使用.trim()来屏蔽前后空格!
   * @throws Exception
   */
  public static List<List<String>> loadTxtFile(String path) throws Exception {
    List<List<String>> returnList = new ArrayList<List<String>>();

    InputStreamReader read = null;
    BufferedReader bufferedReader = null;
    try {
      String encoding = "GBK";
      File file = new File(path);
      if (file.isFile() && file.exists()) { // 判断文件是否存在
        read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
        bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        // 遍历txt文件

        while ((lineTxt = bufferedReader.readLine()) != null) {
          logger.debug("lineTxt------>" + lineTxt);
          if (!"".equals(lineTxt.trim())) {
            String time = lineTxt.trim();
            String[] timeArray = time.split(",");
           
            returnList.add(Arrays.asList(timeArray));
            if(returnList!=null && returnList.size()!=0){
              for(List<String> timeList : returnList){
                if(timeList!=null && timeList.size()!=0){
                  for(int i = 0 ;i<timeList.size();i++){
                    timeList.set(i, timeList.get(i).trim());
                  }
                }
              }
            }
          }
        }

        return returnList;
      } else {
        throw new RuntimeException("找不到指定的文件");
      }
    } catch (Exception e) {
      throw new RuntimeException("读取文件内容出错");
    } finally {
      if (read != null) {
        read.close();
      }
      if (bufferedReader != null) {
        bufferedReader.close();
      }

    }
  }
  
  /**
   * Title: 导出Txt、csv文件<br>
   * Description: 导出Txt、csv文件<br>
   * CreateDate: 2017年1月16日 下午5:07:35<br>
   * @category 导出Txt、csv文件 
   * @author seven.gz
   * @param fileName 文件名
   * @param csvFileString 文件内容
   * @return
   * @throws Exception
   */
  public static ResponseEntity<byte[]> exportTxtFile(String fileName,String csvFileString) throws Exception {
    String dfileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDispositionFormData("attachment", dfileName);
    return new ResponseEntity<byte[]>(csvFileString.getBytes("GBK"), headers,
        HttpStatus.CREATED);
  }
}
