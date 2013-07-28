package org.castafiore.wfs.types;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.beanlib.hibernate.HibernateBeanReplicator;
import net.sf.beanlib.hibernate3.Hibernate3BeanReplicator;
import net.sf.beanlib.provider.replicator.BeanReplicator;
import net.sf.beanlib.spi.PropertyFilter;

import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.FileAlreadyExistException;
import org.castafiore.wfs.FileCopyException;
import org.castafiore.wfs.FileMoveException;
import org.castafiore.wfs.FileNotFoundException;
import org.castafiore.wfs.InsufficientPriviledgeException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.session.Session;
import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

@Entity()
@Table(name="WFS_FILE")
public class FileImpl implements File{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @Column(updatable=true) @AccessType(value="field")
	private String absolutePath;
	
	@ManyToOne( cascade = {CascadeType.PERSIST,CascadeType.MERGE}) @JoinColumn(name="parent_id")
	private Directory parent;
	
	@Column(updatable=true) @AccessType(value="field")
	private String name;
	
	@Column(updatable=true) @AccessType(value="field")
	private Calendar lastModified = Calendar.getInstance();
		
	@Column(updatable=false) @AccessType(value="field")
	private Calendar dateCreated = Calendar.getInstance();

	private long size;
	
	@Column(updatable=true) @AccessType(value="field") @Type(type="text")
	private String clazz = getClass().getName();
	
	@Type(type="text")
	private String editPermissions = null;
	
	@Type(type="text")
	private String readPermissions = null;
	
	@Type(type="text")
	private String owner = null;
	
	@AccessType(value="field")
	private Boolean locked = false;
	
	@Type(type="text")
	private String containsText;
	
	private int status = STATE_NEW;
	
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL, targetEntity=Metadata.class)
	@AccessType("field")
	private Set<Metadata> metadatasssss = new HashSet<Metadata>();
	
	public <T extends File> T getAncestorOfType(Class<T> classType) 
	{
		Assert.notNull(classType, "cannot find ancestor of type null");
		if(classType.isAssignableFrom(getClass()))
			return (T) this;
		
		if (parent == null) 
		{
			return null;
		}
		if (classType.isAssignableFrom(parent.getClass())) 
		{
			return (T)parent;
		} 
		else 
		{
			return parent.getAncestorOfType(classType);
		}
	} 
	

	public FileImpl() {
		
	}
	
	
	private Boolean getLocked() {
		return locked;
	}


	private void setLocked(Boolean locked) {
		this.locked = locked;
	}


	private Set<Metadata> getMetadatasssss() {
		return metadatasssss;
	}


	private void setMetadatasssss(Set<Metadata> metadatasssss) {
		this.metadatasssss = metadatasssss;
	}


	private void setLastModified(Calendar lastModified) {
		this.lastModified = lastModified;
	}


	private void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}


	private void setClazz(String clazz) {
		this.clazz = clazz;
	}


	public <T extends File> T createFile(String name, Class<T> type){
		if(isDirectory()){
			try{
				if(((Directory)this).getFile(name) == null){
				//if(!getSession().itemExists(getAbsolutePath() + "/" + name) ){
					File t = type.newInstance();
					((FileImpl)t).setName(name);
					((Directory)this).addChild(t);
					return (T)t;
				}else{
					throw new FileAlreadyExistException("The file " + name + " already exists in directory " + getAbsolutePath());
				}
			}
			catch(Exception e){
				if(e instanceof RuntimeException){
					throw (RuntimeException)e;
				}else{
					throw new RuntimeException(e);
				}
			}
		}else{
			throw new RuntimeException("not a directory. Cannot create file");
			
		}
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public File makeOwner(String owner) {
		this.owner = owner;
		return this;
	}
	public Calendar getLastModified() {
		return lastModified;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Directory getParent() {
		return parent;
	}

	public void setParent(Directory parent) {
		this.parent = parent;
	}

	public String getClazz() {
		return clazz;
	}

	public Calendar getDateCreated() {
		return dateCreated;
	}
	
	public String getPath()
	{
		return getAbsolutePath();
	}
	
	public String getAbsolutePath()
	{
		return absolutePath;
	}
	
	protected void setAbsolutePath(String absolutePath){
		this.absolutePath = absolutePath;
	}
	
	@Override
	public String toString()
	{
		return getAbsolutePath();
	}
	
	public boolean isDirectory()
	{
		try
		{
			Directory d = (Directory)this;
			return true;
			
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public long length()
	{
		return size;
	}
	
	public long lastModified()
	{
		return this.lastModified.getTime().getTime();
	}
	
	public File[] listFiles()
	{
		if(isDirectory())
		{
			FileIterator iterator  = ((Directory)this).getFiles();
			
			
			File[] result = new File[iterator.count()];
			
			int counter = 0;
			while(iterator.hasNext())
			{
				result[counter] = iterator.next();
			}
			
			return result;
		}
		return null;
	}
	
	
	public String[] listFileNames(){
		if(isDirectory())
		{
			FileIterator iterator  = ((Directory)this).getFiles();
			
			
			String[] result = new String[iterator.count()];
			
			int counter = 0;
			while(iterator.hasNext())
			{
				result[counter] = iterator.next().getName();
			}
			
			return result;
		}
		return null;
	}
	
	
	public void save()
	{
		getSession().save(this);
	}
	
	public void evict(){
		refresh();
		parent.children.remove(this);
		setParent(null);
		
	}
	
	public void remove()
	{
		
		parent.children.remove(this);
		//SpringUtil.getBeanOfType(Dao.class).getSession().delete(this);
		
		getSession().delete(this);
	}
	
	public void refresh()
	{
		getSession().refresh(this);
	}
	
	public Session getSession()
	{
		return SpringUtil.getRepositoryService().getCastafioreSession();
	}
	
	
	public int getDepth()
	{
		int result = 1;
		File tmp = this;
		while(true)
		{
			if(tmp.getParent() != null)
			{
				result++;
				tmp = tmp.getParent();
			}
			else
			{
				break;
			}
		}
		return result;
	}
	
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null)
		{
			return false;
		}
		
		
		if(!(o instanceof File)){
			return false;
		}
		try
		{
			File f = (File)o;
			if(f.getAbsolutePath() == null && getAbsolutePath() == null){
				return f.getName().equals(getName());
			}else if(f.getAbsolutePath() == null && getAbsolutePath() != null){
				return f.getName().equals(getName());
			}else if(f.getAbsolutePath() != null && getAbsolutePath() != null){
				return f.getAbsolutePath().equals(getAbsolutePath());
			}else if(f.getAbsolutePath() != null && getAbsolutePath() == null){
				return false;
			}else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	
	
	protected void addChild(File file){
		
		Assert.notNull(file, "Cannot add a null file");
		addChild(file.getName(), file);
	}
	
	private void addChild(String name,File file)
	{
		Assert.notNull(name, "name cannot be null");
		Assert.notNull(file, "Cannot add a null file");
		//set permissions
		if(file.getEditPermissions() == null){
			file.setEditPermissions(getEditPermissions());
		}
		
		if(file.getReadPermissions() == null){
			file.setReadPermissions(getReadPermissions());
		}
		
		//set same session as parent
		//file.setSession(getSession());
		
		//set same owner as sessions owner
		if(file.getOwner() == null && getSession() != null)
			file.setOwner(getSession().getRemoteUser());
		
		if(file.getOwner() == null){
			try{
				file.setOwner(Util.getRemoteUser());
			}catch(Exception e){
				
			}
		}
		
	
		((FileImpl)file).absolutePath = getAbsolutePath() + "/" + name;
		((Directory)this).children.add(file);
		((FileImpl)file).setParent((Directory)this);
		
	}
	
	public boolean canRead(){
		//return true;
		return canRead(getParent().getSession().getRemoteUser());
	}
	
	public boolean canWrite(String username) {
		//if(true)return true;
		try
		{
			SpringUtil.getSecurityManager().checkWrite(this, username);
			return true;
		}
		catch(InsufficientPriviledgeException ade)
		{
			return false;
		}
		
		
	}
	
	public boolean canRead(String username)	{
		try
		{
			//SpringUtil.getSecurityManager().checkRead(this, username);
		}
		catch(InsufficientPriviledgeException ade)
		{
			return false;
		}
		return true;
	}
	
	public boolean canWrite(){
		return canWrite(getParent().getSession().getRemoteUser());
	}
	
	public boolean isFile(){
		return !this.isDirectory();
	}
	
	

	public String getEditPermissions() {
		return editPermissions;
	}

	public void setEditPermissions(String editPermissions) {
		this.editPermissions = editPermissions;
	}

	public String getReadPermissions() {
		return readPermissions;
	}

	public void setReadPermissions(String readPermissions) {
		this.readPermissions = readPermissions;
	}	
	
	
	
	public boolean isLocked(){
		if(locked == null){
			locked = false;
		}
		return locked;
	}
	
	public void lock(){
		locked = true;	
	}
	
	public void releaseLock(){
		locked = false;
	}

	public String getContainsText() {
		return containsText;
	}

	public void setContainsText(String containsText) {
		this.containsText = containsText;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@Transient
	public void setMetadata(String name, String value){
		
		for(Metadata m : metadatasssss){
			if(m.getName().equals(name)){
				m.setName(name);
				m.setValue(value);
				return;
			}
		}
		Metadata n =  new Metadata();
		metadatasssss.add(n);
	}
	
	@Transient
	public boolean hasMetadata(String name){
		for(Metadata m : metadatasssss){
			if(m.getName().equals(name)){
				return true;
			}
		}
		return false;
		
	}
	
	@Transient
	public String getMetadata(String category){
		for(Metadata m : metadatasssss){
			if(m.getName().equals(name)){
				return m.getValue();
			}
		}return null;
	}
	

	public Set<Metadata> getMetadata() {
		return metadatasssss;
	}


	public void copyTo(String destination){
		if(destination == null){
			throw new FileCopyException("Cannot copy file to a null destination");
		}
		
		if(destination.equals(getParent().getAbsolutePath())){
			throw new FileCopyException("Cannot copy to same directory");
		}
		try{
			Directory dest = getSession().getDirectory(destination);
			if(dest == null){
				throw new FileCopyException("destination folder does not exist");
			}
			
		copyTo(dest);
		dest.save();
		}catch(FileNotFoundException fnf){
			throw new FileCopyException("destination folder does not exist");
		}catch(Exception e){
			throw new FileCopyException("unknow error occured while copying file",e);
		}
		
	}
	
	public void copyTo(Directory dest)throws Exception{
		//String clazz = getClazz();
		//Directory dest = getSession().getDirectory(destination);
		//Class cls = Thread.currentThread().getContextClassLoader().loadClass(clazz);
		//File clone = dest.createFile(getName(), cls);
		BeanReplicator rep = new BeanReplicator();
	
		HibernateBeanReplicator r = new Hibernate3BeanReplicator().initPropertyFilter(new PropertyFilter() {
			
			@Override
			public boolean propagate(String property, Method readerMethod) {
				if(!property.equals("children") && ! property.equals("absolutePath") && !property.equals("parent") && !property.equals("dateCreated") && !property.equals("lastModified")){
					return true;
				}return false;
			}
		});
		File f = r.copy(this);
		dest.addChild(f);
		

		
		
//		ClassMetadata me = SpringUtil.getBeanOfType(Dao.class).getHibernateTemplate().getSessionFactory().getClassMetadata(clazz);
//		String[] properties = me.getPropertyNames();
//		for(String property : properties){
//			if(!property.equals("children") && ! property.equals("absolutePath") && !property.equals("parent") && !property.equals("dateCreated") && !property.equals("lastModified")){
//				try{
//					if(me.getPropertyType(property).isCollectionType()){
//						Collection collection = (Collection)me.getPropertyValue(this, property, EntityMode.POJO);
//						if(collection != null){
//							Collection nc = collection.getClass().newInstance();
//							me.get
//							Iterator iter = collection.iterator();
//							while(iter.hasNext()){
//								//copy entity
//							}
//						}
//					}else{
//						me.setPropertyValue(clone, property, me.getPropertyValue(this, property, EntityMode.POJO), EntityMode.POJO);
//						if(isDirectory()){
//							FileIterator<FileImpl> children = ((Directory)this).getChildren(FileImpl.class);
//							for(FileImpl child : children.toList()){
//								child.copyTo((Directory)clone);
//							}
//						}
//					}
//				}catch(Exception e){
//					e.printStackTrace();
//				}
//			}
//		}
		
		
	}
	
	public void moveTo(String destination){
		try{
			copyTo(destination);
			Directory parent = getParent();
			remove();
			parent.save();
		}catch(Exception e){
			throw new FileMoveException(e);
		}
		
		
	}

}
