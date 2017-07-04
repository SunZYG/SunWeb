package com.myBatis.dao;

import com.myBatis.bean.Catalogue;
import com.myBatis.bean.CatalogueExample;
import com.myBatis.bean.Test;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository(value="iCatalogueDao")
public interface ICatalogueDao {
    long countByExample(CatalogueExample example);

    int deleteByExample(CatalogueExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Catalogue record);

    int insertSelective(Catalogue record);

    List<Catalogue> selectByExampleWithBLOBs(CatalogueExample example);

    List<Catalogue> selectByExample(CatalogueExample example);

    Catalogue selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Catalogue record, @Param("example") CatalogueExample example);

    int updateByExampleWithBLOBs(@Param("record") Catalogue record, @Param("example") CatalogueExample example);

    int updateByExample(@Param("record") Catalogue record, @Param("example") CatalogueExample example);

    int updateByPrimaryKeySelective(Catalogue record);

    int updateByPrimaryKeyWithBLOBs(Catalogue record);

    int updateByPrimaryKey(Catalogue record);
    /**
     * 根据uuid查询 对应目录结构
     * @param id
     * @return 目录
     */
    List<Catalogue> selectByUUID(String id);
    /**
     * 根据id查询 对应章节内容
     * @param id
     * @return 内容
     */
    Catalogue selectByID(String id);
    
    void insertBatch(List<Catalogue> list);
    void updateContext(Catalogue record);
    void updateContextList(List<Catalogue> record);
    
    /**
     *  查询所有 context为null的 数据
     */
    List<Catalogue> selectContextIsNULL();
    
}