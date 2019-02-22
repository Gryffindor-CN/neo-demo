package com.neo.distributed.lock.zk;

import com.neo.distributed.lock.Lock;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: cp.Chen
 * @Date: 2019/2/22 16:54
 * @Description:
 */
public class SimpleZkLock extends BaseZkLock implements Lock {

    /**
     * 锁名称前缀，顺序节点名称，如：lock-0000000000,lock-0000000001,...
     */
    private static final String LOCK_NAME = "lock-";

    /**
     * zookeeper 中locker节点（基础节点）的路径，如：/locker
     */
    private final String basePath;

    /**
     * 获取锁以后自己创建的那个顺序节点的路径
     */
    private String ourLockPath;

    /**
     * 构造函数
     *
     * @param client 操作zookeeper
     * @param basePath zookeeper 的锁的根路径，即 /locker 节点
     */
    public SimpleZkLock(ZkClientExt client, String basePath) {
        super(client, basePath, LOCK_NAME);
        this.basePath = basePath;
    }

    /**
     * 获取锁资源
     *
     * @param time 超时时间
     * @param unit 时间单位
     * @return
     * @throws Exception
     */
    private boolean internalLock(long time, TimeUnit unit) throws Exception {
        ourLockPath = attemptLock(time, unit);
        return ourLockPath != null;
    }

    @Override
    public void acquire() throws Exception {
        if (!internalLock(-1, null)) {
            throw new IOException(String.format("can not find LOCKER node on path: %s", basePath));
        }
    }

    @Override
    public boolean acquire(long time, TimeUnit unit) throws Exception {
        return internalLock(time, unit);
    }

    @Override
    public void release() throws Exception {
        releaseLock(ourLockPath);
    }
}
