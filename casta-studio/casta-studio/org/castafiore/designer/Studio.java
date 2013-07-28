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
 package org.castafiore.designer;
import groovy.lang.Writable;
import groovy.text.Template;

import java.io.CharArrayWriter;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.castafiore.designable.portal.PageContainer;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.dataenvironment.DatasourceFactoryService;
import org.castafiore.designer.marshalling.DesignableDTO;
import org.castafiore.groovy.GroovyUtil;
import org.castafiore.persistence.Dao;
import org.castafiore.security.User;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.ui.events.Event;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.FileNotFoundException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.File;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


public class Studio {
	
public static void applyAttributes(Container c, Map<String,String> attrs){
		
		
		
		Iterator<String> keys = attrs.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			
			if(key.equalsIgnoreCase("text") || key.equalsIgnoreCase("datasource")){
				if(!attrs.containsKey("datasource")){
					String text = attrs.get(key);
					if(text == null){
						text = "";
					}
					c.setText(text);
				}
				
				else {
					String datasource = attrs.get("datasource");
					String[] parts = StringUtil.split(datasource, ":");
					c.setAttribute("datasource", datasource);
					
					if(parts != null && parts.length == 2){
						
						String spec = parts[0];
						
						
						String path = parts[1];
						String url = ResourceUtil.getDownloadURL(spec, path);
						String text = "";
						try{
							text = ResourceUtil.getTemplate(url, CastafioreApplicationContextHolder.getCurrentApplication());
						}catch(Exception e){
							
							e.printStackTrace();
							text = "unable to load " + text;
						}
						if(c instanceof StatefullComponent){
							((StatefullComponent)c).setValue(text);
						}else{
							c.setText(text);
						}
					}
				}
			}else if(key.equalsIgnoreCase("stylesheet")){
				c.addStyleSheet(attrs.get(key));
			}else if(key.equalsIgnoreCase("script")){
				c.addScript(attrs.get(key));
			}else
				c.setAttribute(key, attrs.get(key));
			
		}
	}
	
	public final static Event CHANGE_PAGE_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			try{
				String pageName = container.getAttribute("pagename");
				request.put("pageId", goToPage(pageName, container));
				
				return true;
			}catch(Exception e){
				throw new UIException(e);
			}
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			container.appendJSFragment("$.url.update( 'pageId', '"+request.get("pageId")+"', false);");
		}
		
	};
	
	public final static Event GO_TO_ARTICLE = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			try{
				String art = container.getAttribute("art");
				request.put("pageId", goToArticle(art, container));
				
				return true;
			}catch(Exception e){
				throw new UIException(e);
			}
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			container.appendJSFragment("$.url.update( 'pageId', '"+request.get("pageId")+"', false);");
		}
		
	};
	
	
	public static void addEventToArticle(String article, Container container){
		container.setAttribute("art", article);
		container.addEvent(GO_TO_ARTICLE, Event.CLICK);
		
		
	}
	public static File addCashBookEntry(String accountCode, String title, BigDecimal amount){
		return addCashBookEntry(accountCode, title, "Other", amount);
	}
	
	public static File addCashBookEntry(String accountCode, String title,String pos, BigDecimal amount){
	 	String query = "insert into WFS_FILE (clazz, dateCreated, lastModified, name, parent_id, summary, title, code, paymentMethod,total, accountCode, dateOfTransaction, DTYPE, absolutePath, size, status, commentable, dislikeit, likeit,ratable, pointOfSale) values " +
		"" +
		"('org.castafiore.accounting.CashBookEntry',now(),now(),?, '/root/users/elieandsons/Applications/e-Shop/elieandsons/DefaultCashBook', ?, ?, ?, 'Cash', ?, ?,? ,'CashBookEntry',?,1,1,true,1,1, true,?);";
	 	
	 	String name = System.currentTimeMillis() + "";
	 	SpringUtil.getBeanOfType(Dao.class).getSession().createSQLQuery(query).setParameter(0, name)
		.setParameter(1, title).setParameter(2, title).setParameter(3, SpringUtil.getRepositoryService().getNextSequence("CashBook", Util.getRemoteUser()))
		.setParameter(4, amount).setParameter(5, accountCode).setParameter(6, new Timestamp(System.currentTimeMillis()))
		.setParameter(7, "/root/users/"+Util.getLoggedOrganization()+"/Applications/e-Shop/elieandsons/DefaultCashBook/" + name).setParameter(8, pos).executeUpdate();
	 	
	 	return SpringUtil.getRepositoryService().getFile("/root/users/"+Util.getLoggedOrganization()+"/Applications/e-Shop/elieandsons/DefaultCashBook/" + name, Util.getRemoteUser());
		
		
	}
	
	public static String goToPage(String pageName, Container container){
		if(container.getAncestorOfType(EXDesigner.class) != null){
			throw new UIException("Well, the change page event won't work in the designer. Please execute your portal and try again in runtime mode");
		}
		
		try{
			PortalContainer portalContainer = container.getAncestorOfType(PortalContainer.class);
			String defnPath = portalContainer.getDefinitionPath();
			PageContainer pageContainer = portalContainer.getDescendentOfType(PageContainer.class);
			Container page = null;
			if(!pageContainer.isCached(pageName)){
				BinaryFile bf = (BinaryFile)BaseSpringUtil.getBeanOfType(RepositoryService.class).getFile(defnPath + "/pages/" + pageName, Util.getRemoteUser());
				InputStream in = bf.getInputStream();
				page = DesignableDTO.buildContainer(in, container.getAncestorOfType(EXDesigner.class) !=null);
				page.setAttribute("pagepath", defnPath + "/pages/" + pageName);
				pageContainer.setPage(page);
			}else{
				page = pageContainer.getCachedPageIfPresent(pageName);
				pageContainer.setPage(page);
			}
			return page.getId();
		}catch(FileNotFoundException nfe){
			throw new UIException("It seems that you cannot access the page " + pageName + " or the page might not exist",nfe);
		}
		catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	public static String goToArticle(String article, Container container){
		if(container.getAncestorOfType(EXDesigner.class) != null){
			throw new UIException("Well, the change page event won't work in the designer. Please execute your portal and try again in runtime mode");
		}
		
		try{
			Article art = (Article)SpringUtil.getRepositoryService().getFile(article, Util.getRemoteUser());
			String pageName = "article";
			PortalContainer portalContainer = container.getAncestorOfType(PortalContainer.class);
			String defnPath = portalContainer.getDefinitionPath();
			PageContainer pageContainer = portalContainer.getDescendentOfType(PageContainer.class);
			Container page = null;
			if(!pageContainer.isCached(pageName)){
				BinaryFile bf = (BinaryFile)BaseSpringUtil.getBeanOfType(RepositoryService.class).getFile(defnPath + "/pages/" + pageName, Util.getRemoteUser());
				InputStream in = bf.getInputStream();
				page = DesignableDTO.buildContainer(in, container.getAncestorOfType(EXDesigner.class) !=null);
				page.setAttribute("pagepath", defnPath + "/pages/" + pageName);
				pageContainer.setPage(page);
				
				
			}else{
				page = pageContainer.getCachedPageIfPresent(pageName);
				pageContainer.setPage(page);
			}
			page.getDescendentByName("title").setText(art.getTitle());
			page.getDescendentByName("summary").setText(art.getSummary());
			return page.getId();
		}catch(FileNotFoundException nfe){
			throw new UIException("It seems that you cannot access the page " + "article" + " or the page might not exist");
		}
		catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	public static User registerUser(String username, String password,String firstName,String lastName, String email, String phone, String mobile){
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setEmail(email);
		u.setPhone(phone);
		u.setMobile(mobile);
		try{
			SpringUtil.getSecurityService().registerUser(u);
			return u;
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	
	
	public static void addPopup(String pageName, Container container){
		
		if(container.getAncestorOfType(EXDesigner.class) != null){
			throw new UIException("Well, the change page event won't work in the designer. Please execute your portal and try again in runtime mode");
		}
		
		try{
			PortalContainer portalContainer = container.getAncestorOfType(PortalContainer.class);
			String defnPath = portalContainer.getDefinitionPath();
			
			BinaryFile bf = (BinaryFile)BaseSpringUtil.getBeanOfType(RepositoryService.class).getFile(defnPath + "/pages/" + pageName, Util.getRemoteUser());
			InputStream in = bf.getInputStream();
			Container page = DesignableDTO.buildContainer(in, container.getAncestorOfType(EXDesigner.class) !=null);
			container.getAncestorOfType(PopupContainer.class).addPopup(page);
			
		}catch(FileNotFoundException nfe){
			throw new UIException("It seems that you cannot access the page " + pageName + " or the page might not exist");
		}
		catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	
	
	
	
	public static void sendMail(String templatePath,String subject,String from, String to,Map context)throws Exception{
		BinaryFile bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(templatePath, Util.getRemoteUser());
		String template = IOUtil.getStreamContentAsString(bf.getInputStream());
		Template gTemplate = GroovyUtil.getGroovyTemplate(template);
		
		Writable writable = gTemplate.make( context);
		
		 CharArrayWriter writer =  new CharArrayWriter(); 
		 writable.writeTo(writer);
		 String msg = writer.toString();
		 
		 try{
				JavaMailSender sender = SpringUtil.getBeanOfType(JavaMailSender.class);
				MimeMessage message = sender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setSubject(subject);
				helper.setFrom(from);
				helper.setTo(to);
				helper.setText(msg, true);
				sender.send(message);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	
	
}
