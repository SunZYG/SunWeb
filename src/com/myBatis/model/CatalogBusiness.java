package com.myBatis.model;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.myBatis.bean.Catalogue;
import com.myBatis.bean.CatalogueType;
/**
 * 小说 业务层
 * @author 85366
 *
 */
public interface CatalogBusiness {
	public CatalogueType  selectCatalogueType(String name);
	/**
	 * 模糊查询 名称接近的 小说
	 * @param name 小说名
	 * @return 小说类型列表
	 */
	public List<CatalogueType>  selectCatalogueTypeList(String name);
	/**
	 * 查询对应小说的目录（不包含小说内容）
	 * @param id
	 * @return 小说目录
	 */
	public List<Catalogue> selectCatalogueIndex(String id);
	
	/**
	 * 查询对应小说的内容
	 * @param id
	 * @return 内容
	 */
	public Catalogue selectCatalogueContext(String id);
	/**
	 * 多线程 处理 爬取小说及 插入目录
	 */
	public void enteringCatalog();
	/**
	 *  爬取章节开始
	 */
	public void stars();
	
	/**
	 * 爬取内容开始
	 */
	public void starsContent();
	

	
}
