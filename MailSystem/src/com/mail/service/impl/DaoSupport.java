package com.mail.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mail.service.Dao;

@Repository("daoSupport")
public class DaoSupport implements Dao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 保存对象
	 * @param str
	 * @param obj
	 * @return
	 * @
	 */
	public Object save(String str, Object obj)  {
		return sqlSessionTemplate.insert(str, obj);
	}
	
	/**
	 * 批量更新
	 * @param str
	 * @param obj
	 * @return
	 * @
	 */
	public Object batchSave(String str, List objs ){
		return sqlSessionTemplate.insert(str, objs);
	}
	
	/**
	 * 修改对象
	 * @param str
	 * @param obj
	 * @return
	 * @
	 */
	public Object update(String str, Object obj)  {
		return sqlSessionTemplate.update(str, obj);
	}

	/**
	 * 批量更新
	 * @param str
	 * @param obj
	 * @return
	 * @
	 */
	public void batchUpdate(String str, List objs ){
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		//批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		try{
			if(objs!=null){
				for(int i=0,size=objs.size();i<size;i++){
					sqlSession.update(str, objs.get(i));
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		}finally{
			sqlSession.close();
		}
	}
	
	/**
	 * 批量更新
	 * @param str
	 * @param obj
	 * @return
	 * @
	 */
	public Object batchDelete(String str, List objs ){
		return sqlSessionTemplate.delete(str, objs);
	}
	
	/**
	 * 删除对象 
	 * @param str
	 * @param obj
	 * @return
	 * @
	 */
	public Object delete(String str, Object obj)  {
		return sqlSessionTemplate.delete(str, obj);
	}
	 
	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @
	 */
	public Object findForObject(String str, Object obj)  {
		return sqlSessionTemplate.selectOne(str, obj);
	}

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @
	 */
	public Object findForList(String str, Object obj)  {
		return sqlSessionTemplate.selectList(str, obj);
	}
	
	public Object findForMap(String str, Object obj, String key, String value)  {
		return sqlSessionTemplate.selectMap(str, obj, key);
	}
	
}



