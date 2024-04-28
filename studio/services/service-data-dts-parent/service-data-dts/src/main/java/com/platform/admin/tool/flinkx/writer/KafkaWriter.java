package com.platform.admin.tool.flinkx.writer;

import java.util.Collections;
import java.util.Map;

/**
 * @Classname KafkaWriter
 * @Description TODO
 * @Date 2024/4/26 下午5:40
 * @Created by Icarus
 */
public class KafkaWriter extends BaseWriterPlugin implements FlinkxWriterInterface {

    @Override
    public String getName() {
        return "kafkawriter";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }
}
