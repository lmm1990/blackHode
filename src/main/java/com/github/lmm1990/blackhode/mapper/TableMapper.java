package com.github.lmm1990.blackhode.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TableMapper {

    /**
     * 查询已经存在的数据表列表
     *
     * @param tableNameListStr 表名列表
     * */
    @Select("select TABLE_NAME from information_schema.TABLES where TABLE_NAME in('${tableNameListStr}')")
    List<String> getExistsTableList(@Param("tableNameListStr") String tableNameListStr);

    /**
     * 插入操作
     *
     * @param ew sql
     */
    @Insert("${ew.sqlSet}")
    void insert(@Param(Constants.WRAPPER) Wrapper ew);
}
