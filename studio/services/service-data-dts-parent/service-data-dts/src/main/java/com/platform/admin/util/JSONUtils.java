package com.platform.admin.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * FlinkX JSON 用户名密码解密
 *
 * @author AllDataDC
 * @ClassName JSONUtils * @date 2022/7/31 14:54
 */
public class JSONUtils {

    /**
     * decrypt 解密
     */
    public static Integer decrypt = 0;
    /**
     * decrypt 加密
     */
    public static Integer encrypt = 1;

    /**
     * @param content
     * @param key
     * @param changeType 0加密 or 1解密
     * @return
     */
    public static JSONObject change(String content, String key, Integer changeType) {
        JSONObject keyObj = JSONObject.parseObject(JSONObject.parseObject(content).getString(key));
        JSONObject params = JSONObject.parseObject(keyObj.getString("parameter"));
        String dUsername = null, dPassword = null;
        if (decrypt.equals(changeType)) { //解密
            dUsername = AESUtil.decrypt(params.getString("username"));
            dPassword = AESUtil.decrypt(params.getString("password"));

        } else if (encrypt.equals(changeType)) {//加密

            dUsername = AESUtil.encrypt(params.getString("username"));
            dPassword = AESUtil.encrypt(params.getString("password"));
        }
        String username = dUsername == null ? params.getString("username") : dUsername;
        String password = dPassword == null ? params.getString("password") : dPassword;
        params.put("username", username);
        params.put("password", password);
        keyObj.put("parameter", params);
        return keyObj;
    }

    /**
     * @param jsonStr
     * @param changeType 0加密 or 1解密
     * @return jsonStr
     */
    public static String changeJson(String jsonStr, Integer changeType) {
        JSONObject json = JSONObject.parseObject(jsonStr);
        JSONObject job = json.getJSONObject("job");
        JSONArray contents = job.getJSONArray("content");
        for (int i = 0; i < contents.size(); i++) {
            String contentStr = contents.getString(i);
            Object obj = contents.get(i);
            if (decrypt.equals(changeType)) { //解密
                ((JSONObject) obj).put("reader", change(contentStr, "reader", decrypt));
                ((JSONObject) obj).put("writer", change(contentStr, "writer", decrypt));
            } else if (encrypt.equals(changeType)) {//加密
                ((JSONObject) obj).put("reader", change(contentStr, "reader", encrypt));
                ((JSONObject) obj).put("writer", change(contentStr, "writer", encrypt));
            }
        }
        job.put("content", contents);
        json.put("job", job);
        return json.toJSONString();
    }

    /**
     * 更新 Job JSON 的 startLocation 字段。
     * @param jobJson 原始的 jobJson 字符串。
     * @param lastIncEndId 要设置的新 startLocation 值。
     * @return 更新后的 jobJson 字符串。
     */
    public static String updateStartLocation(String jobJson, Long lastIncEndId) {
        JSONObject jsonObject = JSONObject.parseObject(jobJson);
        // 检查 lastIncEndId 是否为 null，如果是则用"0"代替
        String newStartLocation = (lastIncEndId != null) ? String.valueOf(lastIncEndId) : "0";
        jsonObject.getJSONObject("job")
                .getJSONArray("content")
                .getJSONObject(0)
                .getJSONObject("reader")
                .getJSONObject("parameter")
                .put("startLocation", newStartLocation);

        return jsonObject.toJSONString();
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"job\": {\n" +
                "        \"setting\": {\n" +
                "            \"speed\": {\n" +
                "                \"channel\": 1,\n" +
                "                \"bytes\": 0\n" +
                "            },\n" +
                "            \"errorLimit\": {\n" +
                "                \"record\": 100\n" +
                "            },\n" +
                "            \"restore\": {\n" +
                "                \"maxRowNumForCheckpoint\": 0,\n" +
                "                \"isRestore\": false,\n" +
                "                \"restoreColumnName\": \"\",\n" +
                "                \"restoreColumnIndex\": 0\n" +
                "            },\n" +
                "            \"log\": {\n" +
                "                \"isLogger\": false,\n" +
                "                \"level\": \"debug\",\n" +
                "                \"path\": \"\",\n" +
                "                \"pattern\": \"\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"content\": [\n" +
                "            {\n" +
                "                \"reader\": {\n" +
                "                    \"name\": \"mysqlreader\",\n" +
                "                    \"parameter\": {\n" +
                "                        \"username\": \"root\",\n" +
                "                        \"password\": \"root\",\n" +
                "                        \"column\": [\n" +
                "                            \"id\",\n" +
                "                            \"student\",\n" +
                "                            \"age\"\n" +
                "                        ],\n" +
                "                        \"increColumn\": \"id\",\n" +
                "                        \"startLocation\": \"1\",\n" +
                "                        \"splitPk\": \"\",\n" +
                "                        \"connection\": [\n" +
                "                            {\n" +
                "                                \"table\": [\n" +
                "                                    \"table1\"\n" +
                "                                ],\n" +
                "                                \"jdbcUrl\": [\n" +
                "                                    \"jdbc:mysql://hadoop101:3306/test_import\"\n" +
                "                                ]\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    }\n" +
                "                },\n" +
                "                \"writer\": {\n" +
                "                    \"name\": \"mysqlwriter\",\n" +
                "                    \"parameter\": {\n" +
                "                        \"username\": \"root\",\n" +
                "                        \"password\": \"root\",\n" +
                "                        \"writeMode\": \"insert\",\n" +
                "                        \"column\": [\n" +
                "                            \"id\",\n" +
                "                            \"student\",\n" +
                "                            \"age\"\n" +
                "                        ],\n" +
                "                        \"connection\": [\n" +
                "                            {\n" +
                "                                \"table\": [\n" +
                "                                    \"table1\"\n" +
                "                                ],\n" +
                "                                \"jdbcUrl\": \"jdbc:mysql://hadoop101:3306/test_export\"\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}\n";
        System.out.println(updateStartLocation(json, 1l));
    }
}
