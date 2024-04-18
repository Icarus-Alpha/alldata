package com.platform.admin.util;

/**
 * @CLassName YarnUtils
 * @Description TODO
 * 1. 获取 Yarn 某任务的状态信息
 * 2. 监控 Yarn Job 运行状态
 * 3. 根据 Yarn 任务运行日志获取 Flink_Job id
 * @Date 2024/4/15 下午6:11
 * @Created by hc
 */
import com.platform.admin.core.conf.ExcecutorConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YarnUtils {

    private String yarnAddress;

    // 构造函数，接受一个 ExecutorConfig 实例
    public YarnUtils(ExcecutorConfig executorConfig) {
        this.yarnAddress = executorConfig.getYarnAddress();  // 获取并存储 Yarn 地址
    }

    public String getJobStatus(String appId) throws Exception {
        String urlStr = this.yarnAddress + "/ws/v1/cluster/apps/" + appId;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            output.append(line);
        }
        conn.disconnect();
        return output.toString();
    }

    // 其他方法可以以类似方式修改，这里省略以简化示例
    public static void main(String[] args) {
        ExcecutorConfig config = new ExcecutorConfig();
        // 假设这些设置已经通过其他方式被设置
        config.setYarnAddress("http://hadoop102:8088");

        YarnUtils yarnUtils = new YarnUtils(config);
        try {
            String appId = "application_1713146274075_0009";  // 实际的 Yarn 应用 ID
            String jobStatus = yarnUtils.getJobStatus(appId);
            System.out.println("Job Status: " + jobStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

