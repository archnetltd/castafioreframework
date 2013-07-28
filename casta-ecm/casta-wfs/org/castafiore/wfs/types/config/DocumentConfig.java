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
 package org.castafiore.wfs.types.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.File;

public class DocumentConfig extends DirectoryConfig {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private String title;
	
	private String summaryFilePath;
	
	private String detaileFilePath;
	
	

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummaryFilePath() {
		return summaryFilePath;
	}

	public void setSummaryFilePath(String summaryFilePath) {
		this.summaryFilePath = summaryFilePath;
	}

	public String getDetaileFilePath() {
		return detaileFilePath;
	}

	public void setDetaileFilePath(String detaileFilePath) {
		this.detaileFilePath = detaileFilePath;
	}

	@Override
	public void fillInstance(File empty) {
		super.fillInstance(empty);
		//empty.setName(getName());
		try{
			String summary = IOUtil.getFileContenntAsString(new java.io.File(this.summaryFilePath));
			((Article)empty).setSummary(summary);
		}catch(Exception e){
			logger.error(e);
		}
		try{
			String detail = IOUtil.getFileContenntAsString(new java.io.File(this.detaileFilePath));
			((Article)empty).setDetail(detail);
		}catch(Exception e){
			logger.error(e);
		}
		((Article)empty).setTitle(this.title);
		
		
		
		
	}

	@Override
	public File getNewInstance() {
		return SpringUtil.getRepositoryService().getDirectory(getParentDir(), SpringUtil.getRepositoryService().getSuperUser()).createFile(getName(), Article.class);
	}
}
