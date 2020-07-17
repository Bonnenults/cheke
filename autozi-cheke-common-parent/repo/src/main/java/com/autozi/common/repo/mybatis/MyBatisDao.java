package com.autozi.common.repo.mybatis;

import com.autozi.common.core.page.Page;
import com.autozi.common.repo.entity.IdEntity;
import com.autozi.common.repo.mapper.BaseMapper;
import com.autozi.common.utils.util1.ReflectionUtils;
import com.autozi.common.utils.util1.UID;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mybatis分页查询工具类,为分页查询增加传递:
 * 
 */
public abstract class MyBatisDao<T extends IdEntity> extends MybatisSessionDaoSupport implements BaseMapper<T> {
	private static Logger logger = LoggerFactory.getLogger(MyBatisDao.class);
	
	protected Class<T> entityClass=null;

	//private static boolean allStatementsBuilt = false;
	
	private Class<?> mapperClass = null;
	
	/**
	 * 用于Dao层子类使用的构造函数.
	 * 通过子类的泛型定义取得对象类型Class.
	 * eg.
	 * public class UserDao extends SimpleHibernateDao<User>
	 */
	public MyBatisDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 用于用于省略Dao层, 在Service层直接使用通用MyBatisDao的构造函数.
	 * 在构造函数中定义对象类型Class.
	 * eg.
	 * SimpleHibernateDao<User> userDao = new MyBatisDao<User>(sessionFactory, User.class);
	 */
	public MyBatisDao(final Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	public T get(final Long id) {
		Assert.notNull(id, "id不能为空");
		if(logger.isDebugEnabled()){
			logger.debug("MyBatisDao.get()");
		}
		
		BaseMapper<T> mapper = getBaseMapper();		
		T obj = (T)mapper.get(id);
		return obj;
	}
	
	public T get(final Long id, boolean isCached){
		Assert.notNull(id, "id不能为空");
		BaseMapper<T> mapper = getBaseMapper();		
		
		return (T)mapper.get(id);
	}
	
	public int delete(final Long id) {
		Assert.notNull(id, "id不能为空");
		BaseMapper<T> mapper = getBaseMapper();	
		return mapper.delete(id);
	}
	
	public int delete(final T entity) {
		Assert.notNull(entity, "对象不能为空");
		if(logger.isDebugEnabled()){
			logger.debug("删除实体 "+entity.getClass().getName()+"id="+entity.getId().toString());
		}
		BaseMapper<T> mapper = getBaseMapper();		
		int deleted= mapper.delete(entity.getId());
		return deleted;
	}

	public int batchDelete(final List<Long> ids) {
		if(ids==null|| ids.size()<=0){
			return 0;
		}
		BaseMapper<T> mapper = getBaseMapper();		
		int deleted= mapper.batchDelete(ids);
		return deleted;
	}
	
	/**
	 * 保存新增的对象.
	 */
	public int insert(final T entity) {
		Assert.notNull(entity, "entity不能为空");
		
		if(entity.getId()==null){
			entity.setId(UID.next());
		}
		BaseMapper<T> mapper = getBaseMapper();		
		int inserted= mapper.insert(entity);
		if(inserted==0){
			logger.info("Dao insert is error.This entity message is :"+entity.toString());
		}
		return inserted;
	}
	
	@SuppressWarnings("unchecked")
	private BaseMapper<T> getBaseMapper(){
		return (BaseMapper<T>)getMapper(getMapperClass());	
	}

	/**
	 * 保存修改的对象. 返回0表示更新失败。如果这个值已经被人修改过，会抛出ConcurrentModificationException
	 */
	public int update(final T entity) throws ConcurrentModificationException, ConcurrentModificationException {
		//TODO 这里的事务处理有问题，请大家注意
		
		BaseMapper<T> mapper = getBaseMapper();		
		int count = mapper.update(entity);
		if(logger.isDebugEnabled()){
			logger.debug("count:"+count);
		}
		if (count == 0) {
			IdEntity o = mapper.get(entity.getId());
			logger.info("Dao update is error.This entity message is :"+o.toString());
			int version = o == null ? -1 : o.getVersion();
			if (version != entity.getVersion()) {
				getSqlSession().clearCache();
				throw new ConcurrentModificationException("Stale data: version="+entity.getVersion()+", version in db="+version);
			}
			throw new RuntimeException("mapper.update return 0 for:"+ mapper.getClass().getName());
		}
		return count;
	}
	// TODO need a better method
	public void batchInsert(List<T> entities) {
		Assert.notNull(entities);
		
		for (T entity: entities) {
			insert(entity);
		}
	}
	public int singleBatchInsert(List<T> entities) {
		if(entities.size()<=0){
			return 0;
		}
		for (T item : entities) {
			if(item.getId()==null){
				item.setId(UID.next());
			}
			if (item.getVersion()==null) {
				item.setVersion(10);
			}
		}
		BaseMapper<T> mapper = getBaseMapper();		
		int inserted= mapper.singleBatchInsert(entities);
		if(inserted==0){
			logger.info("Dao insert is error.This entity message is :"+entities.toString());
		}
		return inserted;
	}
	
	// TODO need a better method
	public void batchUpdate(List<T> entities) {
		Assert.notNull(entities);
		
		for (T entity: entities) {
			update(entity);
		}
	}
	/**
	 * 待完善@ TODO
	 */
	public int  batchSaveOrUpdate(List<T> entities) {
		if(entities.size()<=0){
			return 0;
		}
		for (T t : entities) {
			if(t.getId()==null){
				return 0;
			}
			t.setVersion(t.getVersion()+1);
		}
		BaseMapper<T> mapper = getBaseMapper();		
		int updated= mapper.batchSaveOrUpdate(entities);
		if(updated==0){
			logger.info("Dao updated is error.This entity message is :"+entities.toString());
		}
		return updated;
		
	}
	protected Class<?> getMapperClass() {
		if(mapperClass!=null){
			return mapperClass;
		}
			
		try {
			String mapperCls=this.getClass().getName().replace("dao", "mapper").replace("Dao", "").trim()+"Mapper";
			mapperClass = Class.forName(mapperCls);
			return mapperClass;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e.toString());
		}
	}
	protected <X>List<X> queryData(final Page<X> page, String statementName, final Map<String, ?> values){
		return this.findPage(page, statementName, values).getResult();
	}
	
	public <X> Page<X> findPage(final Page<X> page, String statementName, final Object values) {
		Assert.notNull(page, "page不能为空");
		
		List<X> result = getSqlSession().selectList(statementName, toParameterMap(values, page), page.getRowBounds());

		postProcess(page, result);

		return page;
	}
	public <X> Page<X> findPage(final Page<X> page, String statementName, final Object values,String dataSource) {
		Assert.notNull(page, "page不能为空");
		
		List<X> result = getSqlSession().selectList(statementName, toParameterMap(values, page), page.getRowBounds());

		postProcess(page, result);

		return page;
	}
	
	public <X> Page<X> findPage(final Page<X> page, String statementName, final Map<String, ?> values) {
		Assert.notNull(page, "page不能为空");

		statementName = getMapperClass().getName()+"." + statementName;
		
		List<X> result = getSqlSession().selectList(statementName, toParameterMap(values, page), page.getRowBounds());
		postProcess(page, result);

		return page;
	}

	private <X> void postProcess(final Page<X> page, List<X> result) {
		int pageStart = (page.getPageNo() - 1) * page.getPageSize();
		
		if (result == null) {
			result = new ArrayList<X>();
		}
		
		if (result.size() > page.getPageSize()) {
			page.setCacheResult(result);
			page.setCachePageNo(page.getPageNo());
			page.setMaybeHasMore(page.getPageSize() <= result.size());
			page.setTotalCount(pageStart + result.size());
			page.setTotalCount(pageStart + Math.min(result.size(), page.getPageFetchumber()));
			page.setResult(new ArrayList<X>(result.subList(0, page.getPageSize())));
		} else {
			// only 1 page, need not cache it
			page.setResult(result);
			page.setTotalCount(pageStart + result.size());
		}		
	}
	
	/**
	 * 
	 * <PRE>
	 * 
	 * 中文描述：列表查询-没有分页
	 * 
	 * </PRE>
	 * @作者 lihf
	 * @日期 2016-3-21
	 * @param statementName
	 * @param values
	 * @return
	 */
	public <X>List<X> findList(String statementName, Map<String, Object> values){
		statementName = getMapperClass().getName()+"." + statementName;
		List<X> result = getSqlSession().selectList(statementName, values);
		return result;
	}
	
	protected Map<String,Object> toParameterMap(Object parameter, Page<?> p) {
		Map<String,Object> map = toParameterMap(parameter);

		map.put("endRow", p.getFirst() + p.getPageFetchumber());
		map.put("offset", p.getFirst() - 1);
		map.put("limit", p.getPageSize());
		return map;
	}
	
	
	
	@SuppressWarnings("unchecked")
	protected Map<String,Object> toParameterMap(Object parameter) {
		if (parameter == null) {
			return new HashMap<String,Object>();
		}
		
		if (parameter instanceof Map) {
			return (Map<String,Object>) parameter;
		} else {
			try {
				return PropertyUtils.describe(parameter);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 获取Mapper。参数一表示mapper,参数二表示数据源。不传入默认连接oracle
	 * @param type
	 * @return
	 */
	protected <X> X getMapper(Class<X> type) {
		return getSqlSession().getMapper(type);
	}

	public List<T> findListForExample(T entity) {	
		return getBaseMapper().findListForExample(entity);
	}
	
	public List<T> findListForMap(Map<String, Object> map) {
		return getBaseMapper().findListForMap(map);
	}
	
	public List<T> findListForMapWithoutCache(Map<String, Object> map) {
		return getBaseMapper().findListForMapWithoutCache(map);
	}


	public Page<T> findPageForExample(Page<T> page, T entity) {
		return findPage(page,"findListForExample",entity);
	}

	public Page<T> findPageForMap(Page<T> page, Map<String, Object> map) {
		return findPage(page,"findListForMap",map);
	}
	
	public Page<T> findPageForMapWithCache(Page<T> page, Map<String, Object> map) {
		return findPage(page,"findPageForMapWithCache",map);
	}
	
	public Page<T> findPageForMapWithoutCache(Page<T> page, Map<String, Object> map) {
		return findPage(page,"findListForMapWithoutCache",map);
	}

}
