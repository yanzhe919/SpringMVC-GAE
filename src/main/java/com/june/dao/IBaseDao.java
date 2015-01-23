package com.june.dao;

import java.io.Serializable;
import java.util.Collection;

public interface IBaseDao<T> {

	public T get(Serializable id) throws Exception;
	
	public T get(T t,Serializable key) throws Exception;

	public Serializable add(T t) throws Exception;

	public Serializable update(T t) throws Exception;
	
	public Serializable update(Serializable key,T t) throws Exception;

	public Serializable delete(T t) throws Exception;
	
	public Serializable delete(Serializable key,T t) throws Exception;

	public Serializable deleteByID(Serializable id) throws Exception;

	public Serializable deleteByIDs(Serializable... id) throws Exception;

	public Serializable getTotalCount() throws Exception;

	public Collection<T> getPage(int startIndex, int count) throws Exception;

	public Collection<T> getPage(T t, int startIndex, int count)
			throws Exception;

	public Collection<T> getAll(T t) throws Exception;

	public Collection<T> getAll() throws Exception;
}
