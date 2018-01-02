package com.webi.hwj.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Title: 线程池工具类<br>
 * Description: ThreadPoolUtil<br>
 * Company: 韦博英语在线教育部<br>
 * CreateDate:2017年7月4日 下午9:28:15
 * 
 * @author komi.zsy
 */
public class ThreadPoolUtil {

  /**
   * 构造一个线程池 创建线程池，最小线程数为2，最大线程数为20，线程池维护线程的空闲时间为30秒，
   * 使用队列深度为4的有界队列，如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，
   * 然后重试执行程序（如果再次失败，则重复此过程），里面已经根据队列深度对任务加载进行了控制。
   */
  private static volatile ThreadPoolExecutor threadPool;

  public static ThreadPoolExecutor getThreadPool() {
    if (threadPool == null) {
      synchronized (ThreadPoolUtil.class) {
        if (threadPool == null) {
          threadPool = new ThreadPoolExecutor(2, 20, 30, TimeUnit.SECONDS,
              new ArrayBlockingQueue<Runnable>(4),
              new ThreadPoolExecutor.DiscardOldestPolicy());
        }
      }
    }
    return threadPool;
  }

}