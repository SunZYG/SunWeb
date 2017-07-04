package com.myBatis.dao;

import com.myBatis.bean.CatalogueType;
import com.myBatis.bean.CatalogueTypeExample;
import com.myBatis.bean.Test;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository(value="iCatalogueTypeDao")
public interface ICatalogueTypeDao {
    long countByExample(CatalogueTypeExample example);

    int deleteByExample(CatalogueTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CatalogueType record);

    int insertSelective(CatalogueType record);

    List<CatalogueType> selectByExample(CatalogueTypeExample example);

    CatalogueType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CatalogueType record, @Param("example") CatalogueTypeExample example);

    int updateByExample(@Param("record") CatalogueType record, @Param("example") CatalogueTypeExample example);

    int updateByPrimaryKeySelective(CatalogueType record);

    int updateByPrimaryKey(CatalogueType record);
    
    List<CatalogueType> selectByName(String name);
    int insertBatch(List<CatalogueType> List);
    /**
     * 查询最新 位置
     */
    int selectNewsSum();
    
}