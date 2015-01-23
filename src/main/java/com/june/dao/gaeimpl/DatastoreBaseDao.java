package com.june.dao.gaeimpl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.util.StringUtils;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.june.dao.IBaseDao;
import com.june.utils.YzUtils;

public class DatastoreBaseDao<T> implements IBaseDao<T> {
	DatastoreService datastore;

	public DatastoreBaseDao() {
		if (datastore == null) {
			datastore = DatastoreServiceFactory.getDatastoreService();
		}
	}

	@Override
	public Serializable add(T t) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Key key = null;
		if (null != t) {
			Entity entity = putEntityPropertyData(t, null);
			key = datastore.put(entity);
		}

		return key;
	}

	public Entity putEntityPropertyData(T t, Entity entity)
			throws IllegalAccessException, InvocationTargetException {
		if (null == entity) {
			entity = new Entity(t.getClass().getSimpleName());
		}
		Method[] methods = t.getClass().getMethods();
		String fieldName = "";
		String fieldValue = "";
		for (Method method : methods) {
			if (null != method && !StringUtils.isEmpty(method.getName())
					&& 0 == method.getName().indexOf("get")
					&& !"getClass".equals(method.getName())) {
				fieldName = method.getName().substring(3);
				fieldName = YzUtils.toLowerCaseFirstOne(fieldName);
				fieldValue = String.valueOf(method.invoke(t));
				entity.setProperty(fieldName, fieldValue);
			}
		}

		return entity;
	}

	public T getEntityPropertyData(T t, Entity entity)
			throws IllegalAccessException, InvocationTargetException,
			InstantiationException {
		if (null != entity.getKind() && null != t
				&& entity.getKind().equals(t.getClass().getSimpleName())) {
			Method[] methods = t.getClass().getMethods();
			String fieldName = "";
			Object fieldValue = "";
			for (Method method : methods) {
				if (null != method && !StringUtils.isEmpty(method.getName())
						&& 0 == method.getName().indexOf("set")) {
					fieldName = method.getName().substring(3);
					fieldName = YzUtils.toLowerCaseFirstOne(fieldName);
					fieldValue = entity.getProperty(fieldName);
					Type[] clazz = method.getParameterTypes();
					if (null != fieldValue && null != clazz[0]) {
						if ("class java.lang.String"
								.equals(clazz[0].toString())) {
							method.invoke(t, String.valueOf(fieldValue));
						} else if ("long".equals(clazz[0].toString())) {
							method.invoke(t,
									Long.valueOf(String.valueOf(fieldValue))
											.longValue());
						} else if ("class com.google.appengine.api.datastore.Key"
								.equals(clazz[0].toString())) {
							method.invoke(t, entity.getKey());
						} else if ("int".equals(clazz[0].toString())) {
							method.invoke(t,
									Integer.valueOf(String.valueOf(fieldValue))
											.intValue());
						}

					}
				}
			}
		}

		return t;
	}

	@Override
	public Serializable delete(T t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable delete(Serializable key, T t) {
		Filter propertyFilter = new FilterPredicate("key",
				FilterOperator.EQUAL, key);
		Query query = new Query(t.getClass().getSimpleName())
				.setFilter(propertyFilter);
		PreparedQuery pq = datastore.prepare(query);
		Entity entity = pq.asSingleEntity();
		datastore.delete(entity.getKey());
		return null;
	}

	@Override
	public Serializable deleteByID(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable deleteByIDs(Serializable... id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get(Serializable id) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(T t, Serializable id) throws EntityNotFoundException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		t = (T) t.getClass().newInstance();
		// Entity entity =
		// datastore.get(KeyFactory.stringToKey(String.valueOf(id)));
		// System.out.println(entity.getProperty("key")+"===========");

		Query query = new Query(t.getClass().getSimpleName());
		query.setFilter(new FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
				FilterOperator.EQUAL,
				KeyFactory.stringToKey(String.valueOf(id))));
		PreparedQuery pq = datastore.prepare(query);
		Entity entity = pq.asSingleEntity();
		getEntityPropertyData(t, entity);
		return t;
	}

	@Override
	public Collection<T> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> getAll(T t) throws IllegalAccessException,
			InvocationTargetException, InstantiationException {
		Query query = new Query(t.getClass().getSimpleName());
		PreparedQuery pq = datastore.prepare(query);
		List<Entity> listEntity = pq.asList(FetchOptions.Builder.withLimit(10));
		T temp = (T) t.getClass().newInstance();
		List<T> list = new ArrayList<T>();
		for (Entity entity : listEntity) {
			temp = (T) t.getClass().newInstance();
			temp = getEntityPropertyData(temp, entity);
			list.add(temp);
		}
		return list;
	}

	public DatastoreService getDatastoreService() {
		return datastore;
	}

	@Override
	public Collection<T> getPage(int startIndex, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> getPage(T t, int startIndex, int count)
			throws InstantiationException, IllegalAccessException,
			InvocationTargetException {
		Query query = new Query(t.getClass().getSimpleName());
		PreparedQuery pq = datastore.prepare(query);
		List<Entity> listEntity = pq.asList(FetchOptions.Builder.withOffset(
				startIndex).limit(count));
		T temp = (T) t.getClass().newInstance();
		List<T> list = new ArrayList<T>();
		for (Entity entity : listEntity) {
			temp = (T) t.getClass().newInstance();
			temp = getEntityPropertyData(temp, entity);
			list.add(temp);
		}
		return list;
	}

	@Override
	public Serializable getTotalCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serializable update(Serializable key, T t)
			throws IllegalAccessException, InvocationTargetException {
		Filter propertyFilter = new FilterPredicate("key",
				FilterOperator.EQUAL, key);
		Query query = new Query(t.getClass().getSimpleName())
				.setFilter(propertyFilter);
		PreparedQuery pq = datastore.prepare(query);
		Entity entity = pq.asSingleEntity();
		entity = putEntityPropertyData(t, entity);
		return datastore.put(entity);
	}

	@Override
	public Serializable update(T t) {
		return null;
	}

}
