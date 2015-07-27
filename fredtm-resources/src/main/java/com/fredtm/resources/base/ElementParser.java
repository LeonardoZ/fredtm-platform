package com.fredtm.resources.base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class ElementParser<T, R extends FredResourceSupport> {
	
	public abstract R toResource(T entity);

	public abstract T toEntity(R r);

	public List<R> toResources(List<T> entities) {
		List<R> resources = new LinkedList<R>();
		for (T t : entities) {
			resources.add(toResource(t));
		}
		return resources;
	}

	public List<R> toResources(Set<T> entities) {
		return toResources(new ArrayList<T>(entities));
	}
	
	public List<T> toEntities(List<R> resources) {
		List<T> entities = new LinkedList<T>();
		for (R d : resources) {
			entities.add(toEntity(d));
		}
		return entities;
	}
	
	public List<T> toEntities(Set<R> resources) {
		return toEntities(new LinkedList<R>(resources));
	}

	
	 public boolean hasValidUuid(R r){
		 return (r != null &&r.getUuid() != null && !r.getUuid().isEmpty());
	 }
	 
	 public List<R> toList(Set<R> rs){
		 return new LinkedList<R>(rs);
	 }
}
