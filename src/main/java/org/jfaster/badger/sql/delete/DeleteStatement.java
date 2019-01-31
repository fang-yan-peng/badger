package org.jfaster.badger.sql.delete;

import java.util.Collection;

import org.jfaster.badger.Badger;

public interface DeleteStatement {
	
	/*
	 * 添加参数
	 */
	DeleteStatement addParam(Object obj) throws Exception;
	
	/*
	 * 添加参数
	 */
	DeleteStatement addParam(Object... objs) throws Exception;
	
	/*
	 * 添加参数
	 */
	DeleteStatement addParam(Collection<Object> objs) throws Exception;


	/**
	 * 执行删除
	 * @return
	 * @throws Exception
	 */
	int executeDelete() throws Exception;

}
