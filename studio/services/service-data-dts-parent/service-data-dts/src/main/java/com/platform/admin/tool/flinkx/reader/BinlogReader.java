package com.platform.admin.tool.flinkx.reader;

import com.platform.admin.tool.pojo.FlinkxBinlogPojo;
import java.util.Collections;
import java.util.Map;

/**
 * @Classname BinlogReader
 * @Description TODO
 * @Date 2024/4/26 下午5:39
 * @Created by Icarus
 */
public class BinlogReader extends BaseReaderPlugin implements FlinkxReaderInterface {

    public Map<String, Object> buildBinlog(FlinkxBinlogPojo plugin) {
        Map<String, Object> readerObj = Collections.singletonMap("name", getName());
        return readerObj;
    }

    @Override
    public String getName() {
        return "binlogreader";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }
}
