package org.castafiore.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListOrderedMap implements Map{

	private Set keySet = new HashSet();
	
	private Map entrySet = new HashMap();
	
	@Override
	public int size() {
		return keySet.size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return keySet.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return entrySet.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return entrySet.get(key);
	}

	@Override
	public Object put(Object key, Object value) {
		Object o = entrySet.put(key, value);
		keySet.add(key);
		return o;
		
	}

	@Override
	public Object remove(Object key) {
		keySet.remove(key);
		return entrySet.remove(key);
	}

	@Override
	public void putAll(Map m) {
		Iterator i = m.keySet().iterator();
		while(i.hasNext()){
			Object key = i.next();
			Object value = m.get(key);
			put(key, value);
		}
		
	}

	@Override
	public void clear() {
		keySet.clear();
		entrySet.clear();
		
	}

	@Override
	public Set keySet() {
		return keySet;
	}

	@Override
	public Collection values() {
		return entrySet.values();
	}

	@Override
	public Set entrySet() {
		return entrySet.entrySet();
	}

	
	
	

}
