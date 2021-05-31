package com.github.lmm1990.blackhode.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.lmm1990.blackhode.mapper.TableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TableService {

    @Autowired
    private TableMapper tableMapper;

    /**
     * 查询已经存在的数据表列表
     *
     * @param tableNameList 表名列表
     */
    public List<String> getExistsTableList(Collection<String> tableNameList) {
        if (tableNameList.isEmpty()) {
            return new ArrayList<>();
        }
        return tableMapper.getExistsTableList(String.join("','", tableNameList));
    }

    /**
     * 插入操作
     *
     * @param sql sql
     */
    public void insert(String sql) {
        tableMapper.insert(new UpdateWrapper() {{
            setSql(sql);
        }});
    }
}
