package org.jfaster.badger.sql.delete;

import java.util.Collection;

import org.jfaster.badger.Badger;

public interface DeleteStatement {
	
	/*
	 * 添加参数
	 */
	DeleteStatement addParam(Object obj);
	
	/*
	 * 添加参数
	 */
	DeleteStatement addParam(Object... objs);
	
	/*
	 * 添加参数
	 */
	DeleteStatement addParam(Collection<Object> objs);


	/**
	 * 执行删除
	 * @return
	 * @throws Exception
	 */
	int execute();

}
