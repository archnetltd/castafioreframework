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

import java.io.Serializable;
import java.util.List;

import org.castafiore.KeyValuePair;

public interface CatalogueService extends Serializable {
	
	public List<Product> getFeaturedProduct(String organization);
	
	
	public List<Product> getProductsWithCategory(String category, String organization);
	
	public List<Product> getProductsWithCategoryAndSubCategory(String category, String subcategory, String organization);
	
	
	public List<Product> getProductsWithCategories(List<String> categories, String organization);
	
	public List<Product> getProductsWithCategoriesAndSubCategories(List<KeyValuePair> items, String organization);
	
	
	public List<Product> fullTextSearchProduct(String term, String organization);

}
