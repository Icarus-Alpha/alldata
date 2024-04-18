package com.platform.admin.tool.pojo;

import com.platform.admin.entity.JobDatasource;
import lombok.Data;

import java.util.List;

/**
 * 用于传参，构建json
 *
 * @author AllDataDC
 * @ClassName FlinkxRdbmsPojo
 * @date 2022/01/11 15:19
 */
@Data
public class FlinkxRdbmsPojo {

    /**
     * 表名
     */
    private List<String> tables;

    /**
     * 列名
     */
    private Object rdbmsColumns;

    /**
     * 数据源信息
     */
    private JobDatasource jobDatasource;

    /**
     * querySql 属性，如果指定了，则优先于columns参数
     */
    private String querySql;

    /**
     * increColumn
     *     描述：增量字段，可以是对应的增量字段名，也可以是纯数字，表示增量字段在 column 中的顺序位置（从 0 开始）
     *     必选：否
     *     参数类型：String 或 int
     *     默认值：无
     */
    private String increColumn;

    /**
     * startLocation
     *     描述：增量查询起始位置
     *     必选：否
     *     参数类型：String
     *     默认值：无
     */
    private String startLocation;

    /**
     * useMaxFunc
     *      描述：用于标记是否保存 endLocation 位置的一条或多条数据，true：不保存，false(默认)：保存， 某些情况下可能出现最后几条数据被重复记录的情况，可以将此参数配置为 true
     *      必选：否
     *      参数类型：Boolean
     *      默认值：false
     */
    private Boolean useMaxFunc;

    /**
     * preSql 属性
     */
    private String preSql;

    /**
     * postSql 属性
     */
    private String postSql;

    /**
     * 切分主键
     */
    private String splitPk;

    /**
     * where
     */
    private String whereParam;
}
