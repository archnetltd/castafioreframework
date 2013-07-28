/*
\ * Copyright (C) 2007-2010 Castafiore
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;

import org.castafiore.KeyValuePair;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.ui.Container;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.iterator.SimpleFileIterator;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Link;
import org.castafiore.wfs.types.Shortcut;
import org.castafiore.wfs.types.Value;
import org.hibernate.annotations.Type;

@Entity
public class Product extends Article {
	
	public Product(){
		super();
	}
	
	private String code;
	
	
	private BigDecimal costPrice;
	
	/**
	 * The price including tax
	 *///prix
	private BigDecimal totalPrice;
	
	/**
	 * username
	 */
	@Type(type="text")
	private String providedBy;
	
	//marque
	@Type(type="text")
	private String made;
	
	//garantie
	private int warranty;
	
	//famille
	@Type(type="text")
	private String category;
	
	//decomposition famille
	@Type(type="text")
	private String category_1;
	
	
	//produit composant majoritaire
	@Type(type="text")
	private String category_2;
	
	//occasion
	@Type(type="text")
	private String category_3;
	
	//cible
	@Type(type="text")
	private String category_4;

	@Type(type="text")
	private String subCategory;
	
	@Type(type="text")
	private String subCategory_1;
	
	@Type(type="text")
	private String subCategory_2;
	
	@Type(type="text")
	private String subCategory_3;
	
	@Type(type="text")
	private String subCategory_4;
	
	//poid
	
	private BigDecimal weight;
	
	private BigDecimal length;
	
	private BigDecimal width;
	
	private BigDecimal height;
	
	private Boolean fragile;
	
	private String currency = "MUR";
	
	@Type(type="text")
	private String priceScript;
	
	
	
	
	
	
	/**
	 * tax rate in percentage
	 */
	private BigDecimal taxRate = new BigDecimal(15);
	
	
	private boolean featured = false;
	
	
	public String getPriceScript() {
		return priceScript;
	}

	public void setPriceScript(String priceScript) {
		this.priceScript = priceScript;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getLength() {
		return length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
		
	}
	
	public KeyValuePair getCategory(int index){
		if(index < 5){
			if(index == 0){
				return new SimpleKeyValuePair(category,subCategory);
			}else if(index == 1){
				return new SimpleKeyValuePair(category_1,subCategory_1);
			}else if(index == 2){
				return new SimpleKeyValuePair(category_2,subCategory_2);
			}else if(index == 3){
				return new SimpleKeyValuePair(category_3,subCategory_3);
			}else {
				return new SimpleKeyValuePair(category_4,subCategory_4);
			}
		}else{
			return new SimpleKeyValuePair(getFile("category_" + index, Value.class).getString(),getFile("subcategory_" + index, Value.class).getString() );
		}
	}
	
	
	
	
	public List<KeyValuePair> getCategories(){
		List<KeyValuePair> kv = new ArrayList<KeyValuePair>();
		
		if(StringUtil.isNotEmpty(category))
			kv.add(new SimpleKeyValuePair(category, subCategory));
		
		if(StringUtil.isNotEmpty(category_1))
			kv.add(new SimpleKeyValuePair(category_1, subCategory_1));
		
		if(StringUtil.isNotEmpty(category_2))
			kv.add(new SimpleKeyValuePair(category_2, subCategory_2));
		
		if(StringUtil.isNotEmpty(category_3))
			kv.add(new SimpleKeyValuePair(category_3, subCategory_3));
		
		if(StringUtil.isNotEmpty(category_4))
			kv.add(new SimpleKeyValuePair(category_4, subCategory_4));
		
		List<Value> values = getFiles(Value.class).toList();
		for(Value val : values){
			if(val.getName().startsWith("category_")){
				int index = Integer.parseInt(val.getName().replace("category_", ""));
				String category = val.getString();
				String sCategory = getFile("subcategory_" + index, Value.class).getString();
				kv.add(new SimpleKeyValuePair(category,sCategory));
				
			}
		}
		return kv;
	}
	
	public void addCategory(String name, String value){
		
		if(!StringUtil.isNotEmpty(category)){
			setCategory(name);
			setSubCategory(value);
			return;
		}
		
		if(!StringUtil.isNotEmpty(category_1)){
			setCategory_1(name);
			setSubCategory_1(value);
			return;
		}
		
		if(!StringUtil.isNotEmpty(category_2)){
			setCategory_2(name);
			setSubCategory_2(value);
			return;
		}
		
		
		if(!StringUtil.isNotEmpty(category_3)){
			setCategory_3(name);
			setSubCategory_3(value);
			return;
		}
		
		if(!StringUtil.isNotEmpty(category_4)){
			setCategory_4(name);
			setSubCategory_4(value);
			return;
		}
		
		String category = "category_5";
		int index = 5;
		while(true){
			if(getFile(category) != null){
				index ++;
				category = "category_" + index;
			}else{
				break;
			}
		}
		
		Value cat = createFile(category, Value.class);
		cat.setString(name);
		
		Value scat = createFile("sub" + category, Value.class);
		scat.setString(value);
	}
	
	
	public void setCategory(String key, String value){
		
		
		if(category != null && category.equals(key)){
			setSubCategory(value);
			return;
		}
		
		
		if(category_1 != null && category_1.equals(key)){
			setSubCategory_1(value);
			return;
		}
		
		if(category_2 != null && category_2.equals(key)){
			setSubCategory_2(value);
			return;
		}
		
		
		if(category_3 != null && category_3.equals(key)){
			setSubCategory_3(value);
			return;
		}
		
		
		if(category_4 != null && category_4.equals(key)){
			setSubCategory_4(value);
			return;
		}
		

		
		List<Value> values = getFiles(Value.class).toList();
		for(Value val : values){
			if(val.getName().startsWith("category_")){
				int index = Integer.parseInt(val.getName().replace("category_", ""));
				String category = val.getString();
				String sCategory = getFile("subcategory_" + index, Value.class).getString();
				
				if(category != null && category.equals(key)){
					if(sCategory == null || !sCategory.equals(value)){
						val.setString(value);
						val.save();
						return;
					}
				}
			}
		}
		
	}
	
	
	public boolean hasCategory(String category){
		for(KeyValuePair kv : getCategories()){
			if(kv.getKey() != null && kv.getKey().equals(category)){
				return true;
				
			}
		}
		return false;
	}
	
	public boolean isMatching(String category, String subCategory){
		KeyValuePair kv = getCategory(category);
		if(kv != null){
			if(kv.getValue() != null)
				return kv.getValue().equals(subCategory);
		}
		return false;
	}
	
	
	public KeyValuePair getCategory(String category){
		for(KeyValuePair kv : getCategories()){
			if(kv.getKey() != null && kv.getKey().equals(category)){
				return kv;
				
			}
		}
		return null;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public Boolean getFragile() {
		return fragile;
	}

	public void setFragile(Boolean fragile) {
		this.fragile = fragile;
	}

	private BigDecimal reorderLevel;
	
	
	private BigDecimal currentQty;
	
	public BigDecimal getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(BigDecimal reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public String getCategory_1() {
		return category_1;
	}

	public void setCategory_1(String category_1) {
		this.category_1 = category_1;
	}

	public String getCategory_2() {
		return category_2;
	}

	public void setCategory_2(String category_2) {
		this.category_2 = category_2;
	}

	public String getCategory_3() {
		return category_3;
	}

	public void setCategory_3(String category_3) {
		this.category_3 = category_3;
	}

	public String getCategory_4() {
		return category_4;
	}

	public void setCategory_4(String category_4) {
		this.category_4 = category_4;
	}

	public String getSubCategory_1() {
		return subCategory_1;
	}

	public void setSubCategory_1(String subCategory_1) {
		this.subCategory_1 = subCategory_1;
	}

	public String getSubCategory_2() {
		return subCategory_2;
	}

	public void setSubCategory_2(String subCategory_2) {
		this.subCategory_2 = subCategory_2;
	}

	public String getSubCategory_3() {
		return subCategory_3;
	}

	public void setSubCategory_3(String subCategory_3) {
		this.subCategory_3 = subCategory_3;
	}

	public String getSubCategory_4() {
		return subCategory_4;
	}

	public void setSubCategory_4(String subCategory_4) {
		this.subCategory_4 = subCategory_4;
	}

	
	
	
	public BigDecimal getTotalPrice() {
		
		if(totalPrice == null){
			totalPrice = BigDecimal.ZERO;
		}
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getTaxRate() {
		if(taxRate == null){
			taxRate = new BigDecimal(0.15);
		}
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	
	public Value createCharacteristics(String name, String value, BigDecimal overhead){
		ProductOption val = (ProductOption)getFile(name);
		if(val == null)
			val = createFile(name, ProductOption.class);
			
		val.setString(value);
		val.setOverhead(overhead);
		
		return val;
		
		
	}
	
	public Container getOptionTemplate(){
		try{
		Shortcut template = (Shortcut)getFile("optiontemplate");
		BinaryFile btemplate = (BinaryFile)template.getReferencedFile();
		return DesignableUtil.buildContainer(btemplate.getInputStream(), true);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public BinaryFile getOption(){
		try{
			BinaryFile bf = (BinaryFile)getFile("productOption");
			if(bf == null){
				bf = createFile("productOption", BinaryFile.class);
				bf.write(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/shoppingmall/product/ui/tab/productoption.xml"));
				save();
			}
			return bf;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public List<ProductOption> getCharacteristics(){
		return getFiles(ProductOption.class).toList();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getProvidedBy() {
		return providedBy;
	}

	public void setProvidedBy(String providedBy) {
		this.providedBy = providedBy;
	}

	public String getMade() {
		return made;
	}

	public void setMade(String made) {
		this.made = made;
	}

	public int getWarranty() {
		return warranty;
	}

	public void setWarranty(int warranty) {
		this.warranty = warranty;
	}
	
	
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	
	public String getCurrency() {
		if(currency == null){
			currency = "MUR";
		}
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public FileIterator<Link> getImages(){
		FileIterator<Link> images = getFiles(Link.class);
		if(images != null && images.count() > 0){
List<Link> imgs = images.toList();
			
			Collections.sort(imgs, new Comparator<Link>() {

				@Override
				public int compare(Link o1, Link o2) {
					return o1.getName().compareTo(o2.getName());
				}
				
			});
			
			List l = imgs;
			
			FileIterator iter = new SimpleFileIterator(l);
			return iter;
		}
		Directory dImages = (Directory)getFile("images");
		if(dImages != null){
			List<Link> imgs = dImages.getFiles(Link.class).toList();
			
			Collections.sort(imgs, new Comparator<Link>() {

				@Override
				public int compare(Link o1, Link o2) {
					return o1.getName().compareTo(o2.getName());
				}
				
			});
			
			List l = imgs;
			
			FileIterator iter = new SimpleFileIterator(l);
			return iter;
		}
		
		FileIterator iter = new SimpleFileIterator(new ArrayList());
		return iter;
	}
	
	public String getImageUrl(String defaultUrl){
		FileIterator<Link> images =getImages();
		if(images != null && images.hasNext()){
			return images.next().getUrl();
		}
		return defaultUrl;
	}
	

	
	public Link createImage(String name, String url){
		Link link = getFile(name, Link.class);
		if(link == null)
			link = createFile(name, Link.class);
		link.setUrl(url);
		
		
		
		//SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(Product.class).addSearchDir("/root/users/profocus").setMaxResults(10), Util.getRemoteUser());
		
		return link;
	}
	
	

	
	public BigDecimal getPriceExcludingTax(){
		
		BigDecimal tr = taxRate;
		if(taxRate == null){
			tr = new BigDecimal("15");
		}
		
		BigDecimal tp =new BigDecimal(0);
		if(totalPrice != null){
			tp = totalPrice;
		}
		return new BigDecimal("100").subtract(tr).multiply(tp).divide(new BigDecimal(100));
	}

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	public BigDecimal getCurrentQty() {
		return currentQty;
	}

	public void setCurrentQty(BigDecimal currentQty) {
		this.currentQty = currentQty;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
		
	
}
