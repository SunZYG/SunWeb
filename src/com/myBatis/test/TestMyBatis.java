package com.myBatis.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

//import com.myBatis.bean.CatalogueType;
//import com.myBatis.dao.CatalogueTypeMapper;
import com.myBatis.util.MybatisBaseGenericDAOImpl;

public class TestMyBatis {
//    static SqlSessionFactory sqlSessionFactory = null;
//    static {
//        sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
//    }
//
//    public static void main(String[] args) {
////        testAdd();
//    //    getUser();
//    	getTypes();
//    }
//
//    public static List<CatalogueType> getTypes() {
//    	  SqlSession sqlSession = sqlSessionFactory.openSession();
//          @SuppressWarnings("unused")
//		CatalogueType cat =new CatalogueType();
//          List<CatalogueType> list=new ArrayList<CatalogueType>();
//          try {
//          	CatalogueTypeMapper CatalogueTypeMapper = sqlSession.getMapper(CatalogueTypeMapper.class);
//          	list=CatalogueTypeMapper.selectByExample(null);
//          	for (int i = 0; i < list.size(); i++) {
//				System.out.println(list.get(i).getType());
//			}
//          } finally {
//              sqlSession.close();
//          }
//          return list;
//	}
//
//	public static void testAdd() {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        try {
//        	@SuppressWarnings("unused")
//			CatalogueTypeMapper CatalogueTypeMapper = sqlSession.getMapper(CatalogueTypeMapper.class);
////            User user = new User("lisi", new Integer(25));
////            userMapper.insertUser(user);
////            sqlSession.commit();// 这里一定要提交，不然数据进不去数据库中
//        } finally {
//            sqlSession.close();
//        }
//    }
//
//    public static void getUser() {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        CatalogueType cat =new CatalogueType();
//        try {
//        	CatalogueTypeMapper CatalogueTypeMapper = sqlSession.getMapper(CatalogueTypeMapper.class);
//        	cat=CatalogueTypeMapper.selectByPrimaryKey(11);
//        	System.out.println(cat.getType());
//        } finally {
//            sqlSession.close();
//        }
//    }
}