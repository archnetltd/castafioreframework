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

import java.io.File;
import java.util.Random;

import org.castafiore.security.api.UserProfileService;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;

public class ProductImporterImpl implements ProductImporter{
	
	private String rootDir;
	
	
	private RepositoryService repositoryService;
	
	
	
	private UserProfileService userProfile;
	
	private final static Random rand = new Random();
	
	
	
	
	public void createProducts(String username) {
//		File dir = new File(rootDir);
//		File[] files = dir.listFiles();
//		if(true){
//			Directory userDirectory = userProfile.getUserDirectory(username);
//			Directory demo = new Directory();
//			demo.setName("demo");
//			demo.makeOwner(username);
//			userDirectory.addChild(demo);
//			
//			
//			
//			Directory products = new Directory();
//			products.setName("products");
//			products.makeOwner(username);
//			
//			demo.addChild(products);
//			
//			repositoryService.update(userDirectory, username);
//			
//			for(File f : files){
//				try{
//					saveProduct(f, username, products);
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//			}
//		}
	}
	
	public void saveProduct(File file, String username, Directory products)throws Exception{
//		if(file.getName().startsWith(".")){
//			return;
//		}
//		
//		
//		
//		Product product= new Product();
//		product.setCode(file.getName());
//		product.setName(file.getName());
//		String description = IOUtil.getFileContenntAsString(new File(file.getAbsolutePath() + "/description.txt"));
//		product.setSummary(description);
//		String url = ResourceUtil.getDownloadURL("ecm", "/root/user/"+username+"/demo/products/" + file.getName() + "/" + file.getName() + ".gif");
//		
//		
//		Properties main = new Properties();
//		main.load(new FileInputStream(new File(file.getAbsolutePath() + "/main.properties")));
//		
//		product.setTitle(main.getProperty("title"));
//		product.setMade(main.getProperty("from"));
//		product.setProvidedBy(main.getProperty("soldBy"));
//		
//		//product.setPrice(new BigDecimal(main.getProperty("price", rand.nextInt(300) + "")));
//		BinaryFile img = new BinaryFile();
//		product.setOwner("system");
//		img.setOwner("system");
//		
//		byte[] bytes = IOUtil.getFileContentAsBytes(file.getAbsolutePath() + "/" + file.getName() + ".gif");
//		img.write(bytes);
//		img.setName(file.getName() + ".gif");
//		product.addChild(img);
//		products.addChild(product);
//		//repositoryService.saveIn("/root/products/demo", product, repositoryService.getSuperUser());
//		repositoryService.update(products, username);
//		Properties met = new Properties();
//		met.load(new FileInputStream(new File(file.getAbsolutePath() + "/metadata.properties")));
//		Iterator<Object> keys = met.keySet().iterator();
//		while(keys.hasNext()){
//			String key = keys.next().toString();
//			String value = met.getProperty(key);
//			Metadata m = new Metadata();
//			m.setFilePath(product.getAbsolutePath());
//			m.setName(key);
//			m.setValue(value);
//			//m.setId(0);
//			metadataService.saveOrUpdateMetadata(m);
//		}
		
	}

	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}


	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}


	

	public UserProfileService getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfileService userProfile) {
		this.userProfile = userProfile;
	}

	

	



	
	
}
