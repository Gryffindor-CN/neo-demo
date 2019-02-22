package com.neo.distributed.lock.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: cp.Chen
 * @Date: 2019/2/22 15:25
 * @Description:
 */
public class BaseZkLock {
    private static final Logger logger = LoggerFactory.getLogger(BaseZkLock.class);

    private final ZkClientExt client;

    private final String path;

    private String basePath;

    private String lockName;

    private static final Integer MAX_RETRY_COUNT = 10;

    public BaseZkLock(ZkClientExt client, String path, String lockName) {
        this.client = client;
        this.basePath = path;
        this.path = path.concat("/");
        this.lockName = lockName;
    }

    /**
     * 删除指定路径
     *
     * @param ourPath zookeeper路径
     * @throws Exception
     */
    public void deleteOurPath(String ourPath) throws Exception {
        client.delete(ourPath);
    }

    /**
     * 创建锁节点
     *
     * @param client
     * @param path
     * @return
     * @throws Exception
     */
    private String createLockNode(ZkClient client, String path) throws Exception {
        return client.createEphemeralSequential(path, null);
    }

    /**
     * 释放锁资源
     *
     * @param lockPath zookeeper路径
     * @throws Exception
     */
    protected void releaseLock(String lockPath) throws Exception {
        deleteOurPath(lockPath);
    }

    /**
     * 尝试获取锁
     * @param time
     * @param unit
     * @return
     * @throws Exception
     */
    protected String attemptLock(long time, TimeUnit unit) throws Exception {
        final long startMillis = System.currentTimeMillis();
        final long millisToWait = (unit != null) ? unit.toMillis(time) : -1;

        String ourPath = null;
        boolean hasTheLock = false;
        boolean isDone = false;
        int retryCount = 0;

        // 网络闪断重试
        while (!isDone) {
            isDone = true;

            try {
                ourPath = createLockNode(client, path);
                hasTheLock = waitToLock(startMillis, millisToWait, ourPath);
            } catch (ZkNoNodeException e) {
                if (retryCount++ < MAX_RETRY_COUNT) {
                    isDone = false;
                } else {
                    throw e;
                }
            }
        }

        if (hasTheLock) {
            return ourPath;
        }

        return null;
    }

    private boolean waitToLock(long startMillis, Long millisToWait, String ourPath) throws Exception {
        boolean haveTheLock = false;
        boolean doDelete = false;

        try {
            while (!haveTheLock) {
                List<String> children = getSortedChildren();
                String sequenceNodeName = ourPath.substring(basePath.length() + 1);

                int ourIndex = children.indexOf(sequenceNodeName);
                if (ourIndex < 0) {
                    throw new ZkNoNodeException(String.format("can not find sequence node on path: %s has bean deleted",
                            sequenceNodeName));
                }

                boolean isGetTheLock = ourIndex == 0;
                String pathToWatch = isGetTheLock ? null : children.get(ourIndex - 1);

                if (isGetTheLock) {
                    haveTheLock = true;
                } else {
                    String previousSequencePath = basePath.concat("/").concat(pathToWatch);
                    final CountDownLatch latch = new CountDownLatch(1);
                    final IZkDataListener previousListener = new IZkDataListener() {
                        @Override
                        public void handleDataChange(String dataPath, Object data) throws Exception {
                        }

                        @Override
                        public void handleDataDeleted(String dataPath) throws Exception {
                            logger.info("EphemeralSequential node: %s has bean deleted.", dataPath);
                            latch.countDown();
                        }
                    };

                    try {
                        // 如果节点不存在则会出现异常
                        client.subscribeDataChanges(previousSequencePath, previousListener);
                        if (millisToWait != -1) {
                            millisToWait -= (System.currentTimeMillis() - startMillis);
                            startMillis = System.currentTimeMillis();
                            if (millisToWait <= 0) {
                                doDelete = true; // timed out - delete our node
                                break;
                            }

                            latch.await(millisToWait, TimeUnit.MICROSECONDS);
                        } else {
                            latch.wait();
                        }
                    } catch (ZkNoNodeException e) {
                        client.unsubscribeDataChanges(previousSequencePath, previousListener);
                    }
                }
            }
        } catch (Exception e) {
            // 发生异常需要删除节点
            doDelete = true;
            throw e;
        } finally {
            // 如果需要删除节点
            if (doDelete) deleteOurPath(ourPath);
        }

        return haveTheLock;
    }

    List<String> getSortedChildren() throws Exception {
        try {
            List<String> children = client.getChildren(basePath);
            Collections.sort(
                    children,
                    Comparator.comparing(o -> getLockNodeNumber(o, lockName))
            );
            return children;
        } catch (ZkNoNodeException e) {
            client.createPersistent(basePath, true);
            return getSortedChildren();
        }
    }

    private String getLockNodeNumber(String str, String lockName) {
        int index = str.lastIndexOf(lockName);
        if (index >= 0) {
            index += lockName.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;
    }

}
