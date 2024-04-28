package com.platform.admin.tool.pojo;

import com.platform.admin.entity.JobDatasource;
import java.util.List;
import java.util.Map;

/**
 * @Classname FlinkxBinlogPojo
 * @Description TODO
 * @Date 2024/4/26 下午5:53
 * @Created by Icarus
 */
public class FlinkxBinlogPojo {

    private List<String> tables;

    /**
     * 数据源信息
     */
    private JobDatasource jobDatasource;

    private String cat;

    /**
     * 要读取的binlog文件的开始位置
     */
    private Map<String, Object> start;

    /**
     * 过滤表名的Perl正则表达式
     */
    private String filter;

    private Boolean pavingData;
}
