/**
 * 
 */
package com.autozi.common.repo.mybatis.read;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

/**
 * @author haocheng
 *
 */
public abstract class MybatisReadSessionDaoSupport extends DaoSupport {

	protected SqlSession sqlSession;

	@Resource(name="readSqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
	}

	/**
	 * Users should use this method to get a SqlSession to call its statement
	 * methods This is SqlSession is managed by spring. Users should not
	 * commit/rollback/close it because it will be automatically done.
	 * 
	 * @return Spring managed thread safe SqlSession
	 */
	public final SqlSession getSqlSession() {
		return this.sqlSession;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void checkDaoConfig() {
		Assert.notNull(this.sqlSession,
				"Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
	}

}
