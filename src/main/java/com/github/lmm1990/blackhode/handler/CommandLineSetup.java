package com.github.lmm1990.blackhode.handler;

import com.alibaba.fastjson.JSONArray;
import com.github.lmm1990.blackhode.handler.job.AtuoCreateTableJob;
import com.github.lmm1990.blackhode.handler.task.ITask;
import com.github.lmm1990.blackhode.model.source.SourceData;
import com.github.lmm1990.blackhode.model.table.TableConfig;
import com.github.lmm1990.blackhode.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandLineSetup implements CommandLineRunner {

    @Autowired
    Map<String, ITask> taskMap = new HashMap<>();

    @Autowired
    private AtuoCreateTableJob atuoCreateTableJob;

    @Override
    public void run(String... args) {
        loadTableList();
        loadSourceDataMapper();
        atuoCreateTableJob.run();
        taskMap.forEach((k, v) -> {
            v.execute();
        });
    }


    /**
     * 加载数据表配置
     */
    private void loadTableList() {
        final String tableConfigPath = String.format("%s/tableConfig.json", AppConfig.configPath);
        JSONArray.parseArray(FileUtil.readAllText(tableConfigPath), TableConfig.class).forEach((item) -> {
            AppConfig.tableConfigMap.put(item.getTableName(), item);
        });
    }

    /**
     * 加载数据源配置信息
     */
    private void loadSourceDataMapper() {
        final String sourceDataMapperPath = String.format("%s/sourceDataMapper.json", AppConfig.configPath);
        AppConfig.sourceDataMapperList = JSONArray.parseArray(FileUtil.readAllText(sourceDataMapperPath), SourceData.class);
    }
}
