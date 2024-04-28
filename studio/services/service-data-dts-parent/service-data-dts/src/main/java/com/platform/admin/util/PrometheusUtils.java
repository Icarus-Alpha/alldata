package com.platform.admin.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.platform.admin.core.conf.ExcecutorConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @CLassName PromethusUtils
 * @Description
 * @Date 2024/4/15 下午6:22
 * @Created by hc
 */
public class PrometheusUtils {

    private final String prometheusBaseURL;

    // 构造器，接受 ExecutorConfig 实例来获取 Prometheus 地址
    public PrometheusUtils(ExcecutorConfig config) {
        this.prometheusBaseURL = config.getPrometheusAddress();
    }
    public PrometheusUtils() {
        this.prometheusBaseURL = "http://hadoop101:9090";
    }

    /**
     * 构造 Prometheus 查询的完整 URL
     *
     * @param jobId Flink Job ID
     * @return 完整的查询 URL
     */
    private  String constructQueryUrl(String jobId) {
        String metricQuery = getMetricName(jobId);
        try {
            return this.prometheusBaseURL + "/api/v1/query?query=" + java.net.URLEncoder.encode(metricQuery, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指标名称和格式化查询
     *
     * @param jobId Flink Job ID
     * @return 格式化后的 Prometheus 查询字符串
     */
    private String getMetricName(String jobId) {
        return "flink_taskmanager_job_task_operator_chunjun_endLocation{job_id=\"" + jobId + "\"}";
    }

    /**
     * 查询 Prometheus 以获取特定 Flink Job 的 endLocation
     *
     * @param jobId Flink Job ID
     * @return endLocation 字符串或者错误信息
     */
    public String getFlinkJobEndLocation(String jobId) {
        String urlStr = constructQueryUrl(jobId);
        if (urlStr == null) {
            return "Error: Unable to encode query URL.";
        }

        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                return "Failed : HTTP error code : " + conn.getResponseCode();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            conn.disconnect();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    /**
     * 解析 Prometheus 的响应并提取 endLocation 值
     * @param jsonResponse JSON 格式的响应字符串
     * @return endLocation 值或者错误信息
     */
    public static String extractEndLocation(String jsonResponse) {
        try {
            JSONObject jsonObject = JSON.parseObject(jsonResponse);
            JSONArray results = jsonObject.getJSONObject("data").getJSONArray("result");
            if (results != null && !results.isEmpty()) {
                JSONObject firstResult = results.getJSONObject(0);
                JSONArray valueArray = firstResult.getJSONArray("value");
                if (valueArray != null && valueArray.size() > 1) {
                    return valueArray.getString(1);  // 提取 endLocation
                }
            }
            return "No results found";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing JSON: " + e.getMessage();
        }
    }

    // 示例主方法
    public static void main(String[] args) {
        String jobId = "b946d74845c3b012ad92469e78853858";
        String result = new PrometheusUtils().getFlinkJobEndLocation(jobId);
        String endLocation = extractEndLocation(result);
        System.out.println("endLocation: " + endLocation);
    }
}