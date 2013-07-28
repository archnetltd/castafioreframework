package org.castafiore.appstore;

import java.math.BigDecimal;

import org.castafiore.catalogue.Product;

@javax.persistence.Entity
public class AppPackage extends Product{
	
	private String version;
	
	private String packageType;
	
	private BigDecimal downloaded;
	
	private String rootDir;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public BigDecimal getDownloaded() {
		return downloaded;
	}

	public void setDownloaded(BigDecimal downloaded) {
		this.downloaded = downloaded;
	}

	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}
	
	

}
