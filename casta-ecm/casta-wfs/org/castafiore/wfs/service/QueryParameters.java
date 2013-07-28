/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.wfs.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.castafiore.wfs.types.File;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class QueryParameters implements Serializable {
	
	private Class<? extends File> entity = File.class;
	
	private List<Criterion> restrictions = new ArrayList<Criterion>();
	
	private List<String> searchDirs = new ArrayList<String>();
	
	private List<Order> orders = new ArrayList<Order>();
	
	private int firstResult= 0;
	
	private int maxResults = -1;
	
	private String fullTextSearch = null;
	
	private List<String> filesPath = new ArrayList<String>();
	
	private List<Criterion> ors = new ArrayList<Criterion>();

	public Class<? extends File> getEntity() {
		return entity;
	}

	public QueryParameters setEntity(Class<? extends File> entity) {
		this.entity = entity;
		return this;
	}
	
	public QueryParameters addRestriction(Criterion criterion){
		restrictions.add(criterion);
		return this;
	}
	
	public QueryParameters addSearchDir(String directory){
		searchDirs.add(directory);
		return this;
	}
	
	public QueryParameters addOrder(Order order){
		orders.add(order);
		return this;
	}
	
	
	
	public List<Criterion> getRestrictions() {
		return restrictions;
	}


	public List<String> getSearchDirs() {
		return searchDirs;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public Criteria getCriteria(Session session){
		Criteria crit = session.createCriteria(entity);
		
		for(Criterion cr : restrictions){
			crit.add(cr);
		}
		
		
		Criterion c = getDirectoryCrit(searchDirs, 0);
		if(c != null){
			crit.add(c);
		}
		
		if(filesPath.size() > 0){
			Criterion cc = getFilesCrit(filesPath, 0);
			if(cc != null){
				crit.add(cc);
			}
		}
		
		for(Order order : orders){
			crit.addOrder(order);
		}
		
		crit.setFirstResult(firstResult);
		if(maxResults > 0){
			crit.setMaxResults(maxResults);
		}
		if(fullTextSearch != null){
			crit.add(Restrictions.like("containsText", "%" + fullTextSearch + "%"));
		}
		
		if(ors.size() > 0){
			crit.add(getOrsCrit(ors, 0));
		}
		return crit;
	}
	
	
	
	private Criterion getDirectoryCrit(List<String> directories, int index){
		if(directories.size() > 0){
			if(index == directories.size()-1){
				return Restrictions.like("absolutePath", directories.get(index) + "%");
			}else{
				String directory = directories.get(index);
				return Restrictions.or(Restrictions.like("absolutePath", directory + "%"),getDirectoryCrit(directories, index+1) );
			}
		}
		
		return null;
	}
	
	public QueryParameters addOr(Criterion criteria){
		ors.add(criteria);
		return this;
	}
	
	private Criterion getOrsCrit(List<Criterion> ors, int index){
		if(ors.size() > 0){
			if(index == ors.size()-1){
				return ors.get(index);//Restrictions.like("absolutePath", directories.get(index) + "%");
			}else{
				//String directory = directories.get(index);
				return Restrictions.or(ors.get(index),getOrsCrit(ors, index+1) );
			}
		}
		
		return null;
	}
	
	private Criterion getFilesCrit(List<String> files, int index){
		if(files.size() > 0){
			if(index == files.size()-1){
				return Restrictions.eq("absolutePath", files.get(index));
			}else{
				String directory = files.get(index);
				return Restrictions.or(Restrictions.eq("absolutePath", directory ),getDirectoryCrit(files, index+1) );
			}
		}
		
		return null;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public QueryParameters setFirstResult(int firstResult) {
		this.firstResult = firstResult;
		return this;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public QueryParameters setMaxResults(int maxResults) {
		this.maxResults = maxResults;
		return this;
	}
	
	public QueryParameters setFullTextSearch(String value){
		this.fullTextSearch = value;
		return this;
	}
	
	public String getFullTextSearch(){
		return fullTextSearch;
	}
	
	public Criteria countCriteria(Session session){
		Criteria crit = session.createCriteria(entity);
		
		for(Criterion cr : restrictions){
			crit.add(cr);
		}
		
		
		Criterion c = getDirectoryCrit(searchDirs, 0);
		if(c != null){
			crit.add(c);
		}
		
		if(filesPath.size() > 0){
			Criterion cc = getFilesCrit(filesPath, 0);
			if(cc != null){
				crit.add(cc);
			}
		}
		
		
		
		crit.setFirstResult(firstResult);
		if(maxResults > 0){
			crit.setMaxResults(maxResults);
		}
		if(fullTextSearch != null){
			crit.add(Restrictions.like("containsText", "%" + fullTextSearch + "%"));
		}
		
		crit.setProjection(Projections.rowCount());
		return crit;
	}

}
