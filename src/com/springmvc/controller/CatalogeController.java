package com.springmvc.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myBatis.bean.Catalogue;
import com.myBatis.bean.CatalogueType;
import com.myBatis.dao.ICatalogueDao;
import com.myBatis.dao.ICatalogueTypeDao;
import com.myBatis.model.CatalogBusiness;
import com.myBatis.model.impl.CatalogBusinessImpl;
import com.util.HintMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 *  小说
 * @author zyg
 */
@Controller
//@Scope("prototype")
@RequestMapping("/catalog")
public class CatalogeController {
	@Autowired
	private ICatalogueTypeDao catalogueTypeDao;
	
	@Autowired
	private ICatalogueDao catalogueDao;
	
	@Resource(name="CatalogService")
	private CatalogBusiness catalogBusiness;
	
	@Resource(name = "catalogueType")
	private CatalogueType catalogueType;
	


	@RequestMapping("/gettype")
	@ResponseBody
	public List<CatalogueType> selectAllType() {
		List<CatalogueType>  list =catalogueTypeDao.selectByExample(null);
		return list;
	}
	
	/**
	 * 根据name 查询类似的结果集，
	 * list 
	 * size>1 时 返回多个结果 让用户判断
	 * size=1 时 返回小说和目录结构 
	 * size=0 时 提示用户 无数据
	 */
	@RequestMapping(value="/getDataByName")
	@ResponseBody
	public Object getDataByName(String name) {
		JSONObject jsb =new JSONObject();
		List<CatalogueType> list =catalogBusiness.selectCatalogueTypeList(name);
		if(list!=null){
			Integer size=list.size();
			if(size==0){
				jsb.put(HintMessage.CODE, HintMessage.NOT_FOUND_CODE);
				jsb.put(HintMessage.MESSAGE, HintMessage.NULL_MESSAGE);
			}else if(size==1){
				jsb.put(HintMessage.DATA, catalogBusiness.selectCatalogueIndex(list.get(0).getUuid()));
			}else{
				jsb.put(HintMessage.DATA, list);
			}
			jsb.put(HintMessage.CODE, HintMessage.SUCCESS_CODE);
		}
		return jsb;
	}
	
	@RequestMapping("/getindex")
	@ResponseBody
	public List<Catalogue> selectindexById(String id) {
		List<Catalogue>  list =catalogBusiness.selectCatalogueIndex(id);
		return list;
	}
	
	@RequestMapping("/getcontext")
	@ResponseBody
	public Object selectContextById(String id) {
		JSONObject jsb =new JSONObject();
		Catalogue  list =catalogBusiness.selectCatalogueContext(id);
		if(list!=null){
			jsb.put(HintMessage.DATA, list);
			jsb.put(HintMessage.CODE, HintMessage.SUCCESS_CODE);
		}else{
			jsb.put(HintMessage.CODE, HintMessage.NOT_FOUND_CODE);
			jsb.put(HintMessage.MESSAGE, HintMessage.NULL_MESSAGE);
		}
		return jsb;
	}
	
	@RequestMapping("/start")
	@ResponseBody
	public void start() {
		try {
		catalogBusiness.stars();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/startcontext")
	@ResponseBody
	public void starsContent() {
		try {
		catalogBusiness.starsContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
}
