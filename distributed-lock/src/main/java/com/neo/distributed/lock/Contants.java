package com.neo.distributed.lock;

/**
 * @Auther: cp.Chen
 * @Date: 2019/2/22 17:16
 * @Description:
 */
public class Contants {

    public static int ZK_SESSION_TIMEOUT = 5000;

    public static int ZK_CONNECTION_TIMEOUT = 1000;

    public static String ZK_ROOT_PATH = "/neo";

    public static String REGISTRY_TYPE_PROVIDERS = "providers";

    public static String REGISTRY_TYPE_CONSUMERS = "consumers";

    /**
     * 注册中心失败事件重试周期
     */
    public static final int DEFAULT_REGISTRY_RETRY_PERIOD = 5 * 1000;
}
