package com.lylbp.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 多线程工具类
 *
 * @Author weiwenbin
 * @Date: 2020/10/20 下午3:29
 */
public class ThreadUtil {
    /**
     * 多线程处理list
     * 这里我改造成了静态方法
     *
     * @param data           数据list
     * @param countDownLatch 协调多个线程之间的同步
     * @param threadNum      开启的线程数:也可以使用countDownLatch.getCount();//来获取开启的线程数但是要注意这个会是一个风险。因为这是一个可变的数。而控制他改变的罪魁祸首就是countDownLatch.countDown();
     */
    public static synchronized void handleList(List<String> data, CountDownLatch countDownLatch, int threadNum) {
        //获取数据的总数
        int length = data.size();
        //计算每个线程平均处理的数据
        int tl = length % threadNum == 0 ? length / threadNum : (length / threadNum + 1);
        for (int i = 0; i < threadNum; i++) {
            int start = i * tl;
            //获得每个线程的最后下标
            int end = (i + 1) * tl;
            //最后一个线程拿到的是剩余的数据
            HandleThread thread = new HandleThread("线程[" + (i + 1) + "] ", data, start,
                    Math.min(end, length), countDownLatch);
            //开启线程运行run方法进行数据处理
            thread.start();
        }
    }

    /**
     * 内置类继承线程类
     * 这里为了方便大家学习就直接这样写了.
     */
    static class HandleThread extends Thread {
        //线程名称
        private String threadName;
        //该线程负责的数据
        private List<String> data;
        //开始集合的下标
        private int start;
        //结束集合的下标
        private int end;
        //协调多个线程之间的同步
        private CountDownLatch countDownLatch;

        /**
         * 无参构造函数
         */
        public HandleThread() {
            super();
        }

        /**
         * 带参构造方法
         *
         * @param threadName     当前线程名称
         * @param data           数据
         * @param start          开始的下标
         * @param end            结束的下标
         * @param countDownLatch 协调多个线程之间的同步
         */
        public HandleThread(String threadName, List<String> data, int start, int end, CountDownLatch countDownLatch) {
            this.threadName = threadName;
            this.data = data;
            this.start = start;
            this.end = end;
            this.countDownLatch = countDownLatch;
        }

        /**
         * 重写Thread的run方法  调用start方法之后自动调用run方法
         */
        @Override
        public void run() {
            // 这里处理数据
            List<String> subList = data.subList(start, end);//获取当前线程需要处理的数据
            for (int a = 0; a < subList.size(); a++) {
                System.out.println(threadName + "处理了   " + subList.get(a) + "  ！");
            }
            System.out.println(threadName + "处理了 " + subList.size() + "条数据！");
            // 执行子任务完毕之后，countDown减少一个点
            countDownLatch.countDown();
        }

    }

    /**
     * 使用main方法进行测试
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 准备测试数据
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            data.add("item" + i);
        }

        //设置需要开启的线程数
        int threadNum = 3;
        //CountDownLatch实现使用一个计数器，而参数cout就是初始化计数器的值，该值一经初始化就不能再被修改。
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        ThreadUtil.handleList(data, countDownLatch, threadNum);
        //调用await方法阻塞当前线程，等待子线程完成后在继续执行
        countDownLatch.await();
        System.out.println("=============主线程执行完毕!================");
    }
}
