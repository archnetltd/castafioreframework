package org.castafiore.wfs;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Value;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.type.Type;

public class WFSEventsInterceptor extends EmptyInterceptor {
	Session session;
	private Set<File> inserts = new LinkedHashSet<File>();
	private Set<File> updates = new LinkedHashSet<File>();
	private Set<File> deletes = new LinkedHashSet<File>();

	public void setSession(Session session) {
		this.session = session;
	}

	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) throws CallbackException {

		//System.out.println("onSave");

		if (entity instanceof File) {
			inserts.add((File)entity);
		}
		return false;

	}

	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) throws CallbackException {

		//System.out.println("onFlushDirty");

		if (entity instanceof File) {
			updates.add((File)entity);
		}
		return false;

	}

	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {

		//System.out.println("onDelete");

		if (entity instanceof File) {
			deletes.add((File)entity);
		}
	}

	
	protected void evaluate(File entity, String prop){
		
		if(!(entity instanceof Value) && !entity.getName().startsWith("evt:")){
			Directory parent = entity.getParent();
			if(parent != null){
				String property = parent.getProperty(prop);
				if(property != null){
					Binding b = new Binding();
					b.setVariable("file", entity);
					GroovyShell shell = new GroovyShell(b);
					shell.evaluate(property);
				}
			}
		}
	}
	// called before commit into database
	public void preFlush(Iterator iterator) {
		//System.out.println("preFlush");
	}

	// called after committed into database
	public void postFlush(Iterator iterator) {
		//System.out.println("postFlush");

		try {

			if(inserts.size() > 0){
				evaluate(inserts.iterator().next(), "evt:batchinsert");
			}
			for (Iterator it = inserts.iterator(); it.hasNext();) {
				File entity = (File) it.next();
				//System.out.println("postFlush - insert");
				LogIt("Saved", entity);
				
				evaluate(entity, "evt:insert");
				//if(entity.getParent().getProperty("cst:event"))
			}

			if(updates.size() > 0){
				evaluate(updates.iterator().next(), "evt:batchupdate");
			}
			for (Iterator it = updates.iterator(); it.hasNext();) {
				File entity = (File) it.next();
				//System.out.println("postFlush - update");
				LogIt("Updated", entity);
				evaluate(entity, "evt:update");
			}

			if(deletes.size() > 0){
				evaluate(deletes.iterator().next(), "evt:batchdelete");
			}
			for (Iterator it = deletes.iterator(); it.hasNext();) {
				File entity = (File) it.next();
				//System.out.println("postFlush - delete");
				LogIt("Deleted", entity);
				evaluate(entity, "evt:delete");
			}

		} finally {
			inserts.clear();
			updates.clear();
			deletes.clear();
		}
	}

	public static void LogIt(String action, File entity) {

		//	System.out.println(action + ":" + entity.getAbsolutePath());
		
	}
}