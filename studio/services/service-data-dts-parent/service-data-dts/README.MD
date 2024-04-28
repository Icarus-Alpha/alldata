数据同步任务分为两种

- 实时任务 
  - Flink 任务(集成dolphin scheduler)
    - [ ] FlinkSQL
    - [ ] FlinkCDC
    - [ ] Flink任务
  - [ ] 实时采集(CHUNJUN插件)
    - binlog source -> kafka sink
    ![17141209127021714120912619.png](https://cdn.jsdelivr.net/gh/Icarus-Alpha/Imgs@main/typora/17141209127021714120912619.png)
    ![17141210025281714121002394.png](https://cdn.jsdelivr.net/gh/Icarus-Alpha/Imgs@main/typora/17141210025281714121002394.png)
    ```json
    {
        "job": {
            "content": [
                {
                    "reader": {
                        "parameter": {
                            "password": "readonly_123456",
                            "port": 3306,
                            "cat": "insert,update,delete",
                            "host": "10.66.221.13",
                            "jdbcUrl": "jdbc:mysql://10.66.221.13:3306/infinova-cloud-overall?serverTimezone=Hongkong&useSSL=false&tinyInt1isBit=false",
                            "start": {},
                            "pavingData": true,
                            "table": [
                                "branch_table"
                            ],
                            "username": "readonly"
                        },
                        "name": "binlogreader"
                    },
                    "writer": {
                        "parameter": {
                            "producerSettings": {
                                "zookeeper.connect": "",
                                "bootstrap.servers": "10.66.221.137:9092"
                            },
                            "dataCompelOrder": true,
                            "topic": "topic-test"
                        },
                        "name": "kafkawriter"
                    }
                }
            ],
            "setting": {
                "speed": {
                    "channel": 1,
                    "readerChannel": 1,
                    "writerChannel": 1,
                    "bytes": -1048576
                },
                "restore": {
                    "isRestore": false,
                    "isStream": false
                }
            }
        }
    }
    ```
    - binlog source -> hive sink 
- 离线任务
  - 数据同步 (离线数据同步，周期性任务)
    - [x] 增量主键标识
      - 使用配置表记录增量主键同步进度，每次同步任务会根据配置表的增量主键同步进度进行增量同步
    - [x] 非增量主键标识
      - 使用chunjun插件进行全量同步