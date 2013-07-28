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
 package org.castafiore.catalogue;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.KeyValuePair;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Restrictions;

public class CatalogueServiceImpl  implements CatalogueService{
	private static final long serialVersionUID = 1L;
	
	
	private RepositoryService repositoryService;

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Override
	public List<Product> fullTextSearchProduct(String term, String organization) {
		QueryParameters params = getBasicParams(organization);
		params.addRestriction(  Restrictions.or(Restrictions.like("title", "%"+term+"%"),  Restrictions.or(Restrictions.like("summary", "%" + term+"%"), Restrictions.like("detail", "%" + term+"%"))));
		
		List l = repositoryService.executeQuery(params, Util.getRemoteUser());
		return l;
	}
	
	private QueryParameters getBasicParams(String org){
		QueryParameters params = new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.eq("status", Product.STATE_PUBLISHED)).addSearchDir("/root/users/" + org);
		return params;
	}

	@Override
	public List<Product> getFeaturedProduct(String organization) {
		QueryParameters params = getBasicParams(organization);//new QueryParameters().setEntity(Product.class).addSearchDir("/root/users/" + organization);
		params.addRestriction(Restrictions.eq("featured", true));
		List l = repositoryService.executeQuery(params, Util.getRemoteUser());
		return l;
	}

	@Override
	public List<Product> getProductsWithCategories(List<String> categories,
			String organization) {
		QueryParameters params = getBasicParams(organization);
		List<Product> result = new ArrayList<Product>();
		for(File f : repositoryService.executeQuery(params, Util.getRemoteUser())){
			Product p = (Product)f;
			List<KeyValuePair> current = p.getCategories();
			boolean brk = false;
			for(KeyValuePair c : current){
				brk = false;
				for(String i : categories){
					if(i.equals(c.getKey())){
						result.add(p);
						brk = true;
						break;
					}
				}
				if(brk){
					break;
				}
			}
		}
		return result;
		//
	}

	@Override
	public List<Product> getProductsWithCategoriesAndSubCategories(
			List<KeyValuePair> items, String organization) {
		QueryParameters params = getBasicParams(organization);
		List<Product> result = new ArrayList<Product>();
		for(File f : repositoryService.executeQuery(params, Util.getRemoteUser())){
			Product p = (Product)f;
			List<KeyValuePair> current = p.getCategories();
			boolean brk = false;
			for(KeyValuePair c : current){
				brk = false;
				for(KeyValuePair i : items){
					if(i.getKey() != null && c.getKey() != null){
						if(i.getKey().equals(c.getKey()) && i.getValue().equals(c.getValue())){
							result.add(p);
							brk = true;
							break;
						}
					}
				}
				if(brk){
					break;
				}
			}
		}
		return result;
	}

	@Override
	public List<Product> getProductsWithCategory(String category,
			String organization) {
		QueryParameters params = getBasicParams(organization);
		List<Product> result = new ArrayList<Product>();
		for(File f : repositoryService.executeQuery(params, Util.getRemoteUser())){
			Product p = (Product)f;
			if(p.hasCategory(category)){
				result.add(p);
			}
		}
		return result;
	}

	@Override
	public List<Product> getProductsWithCategoryAndSubCategory(String category,
			String subcategory, String organization) {
		QueryParameters params = getBasicParams(organization);
		List<Product> result = new ArrayList<Product>();
		for(File f : repositoryService.executeQuery(params, Util.getRemoteUser())){
			Product p = (Product)f;
			if(p.isMatching(category, subcategory)){
				result.add(p);
			}
		}
		return result;
	}



}
