package com.neo.distributed.lock;

import com.neo.distributed.lock.zk.SimpleZkLock;
import com.neo.distributed.lock.zk.ZkClientExt;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: cp.Chen
 * @Date: 2019/2/22 17:27
 * @Description:
 */
public class ZookeeperRetiryTest {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperRetiryTest.class);

    private String zkServers = "134.175.215.187:2181,134.175.215.187:2182,134.175.215.187:2183";

    private int sessionTimeout = 5000;

    private int connectionTimeout = 5000;

    private String basePath = "/Mutex";

    @Test
    public void test() {

        final ZkClientExt zkClientExt1 = new ZkClientExt(zkServers, sessionTimeout, connectionTimeout, new BytesPushThroughSerializer());
        final SimpleZkLock mutex1 = new SimpleZkLock(zkClientExt1, basePath);

        final ZkClientExt zkClientExt2 = new ZkClientExt(zkServers, sessionTimeout, connectionTimeout, new BytesPushThroughSerializer());
        final SimpleZkLock mutex2 = new SimpleZkLock(zkClientExt2, basePath);

        try {
            mutex1.acquire();
            System.out.println("Client1 locked");
            Thread client2Thd = new Thread(() -> {
                try {
                    mutex2.acquire();
                    System.out.println("Client2 locked");
                    mutex2.release();
                    System.out.println("Client2 released lock");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            client2Thd.start();
            Thread.sleep(5000);
            mutex1.release();
            System.out.println("Client1 released lock");

            client2Thd.join();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Test
    public void test1() {
        final ZkClientExt client = new ZkClientExt(zkServers, sessionTimeout, connectionTimeout, new BytesPushThroughSerializer());
        final SimpleZkLock mutex = new SimpleZkLock(client, basePath);
        try {
            mutex.acquire();
            System.out.println("Client1 locked");
            Thread.sleep(10000);
            mutex.release();
            System.out.println("Client released lock");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        final ZkClientExt client = new ZkClientExt(zkServers, sessionTimeout, connectionTimeout, new
                BytesPushThroughSerializer());
        final SimpleZkLock mutex = new SimpleZkLock(client, basePath);
        try {
            mutex.acquire();
            System.out.println("Client2 locked");
            Thread.sleep(5000);
            mutex.release();
            System.out.println("Client2 released lock");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        final ZkClientExt client = new ZkClientExt(zkServers, sessionTimeout, connectionTimeout, new
                BytesPushThroughSerializer());
        final SimpleZkLock mutex = new SimpleZkLock(client, basePath);
        try {
            mutex.acquire();
            System.out.println("Client3 locked");
            mutex.release();
            System.out.println("Client3 release lock");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
