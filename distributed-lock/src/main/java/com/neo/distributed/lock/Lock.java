package com.neo.distributed.lock;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: cp.Chen
 * @Date: 2019/2/22 15:16
 * @Description:
 */
public interface Lock {

    /**
     * 获取锁，如果没有获得就一直等待
     *
     * @throws Exception
     */
    void acquire() throws Exception;

    /**
     * 获取锁，直到超时
     *
     * @param time 超时时间
     * @param unit 时间单位
     * @return
     * @throws Exception
     */
    boolean acquire(long time, TimeUnit unit) throws Exception;

    /**
     * 释放锁
     *
     * @return
     * @throws Exception
     */
    void release() throws Exception;
}
