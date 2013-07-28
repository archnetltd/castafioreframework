package org.castafiore.spring;

import java.io.Serializable;

import org.castafiore.security.configs.CreatorHelper;
import org.castafiore.wfs.config.creator.FileCreator;
import org.castafiore.wfs.service.RepositoryService;
import org.springframework.context.ApplicationContextAware;


/**
 * Interface to be implemented by classes.
 * This interface executes that start method at a correct moment to guarantee stability of execution<br>
 * By correct moment we mean after {@link FileCreator} has been executed, {@link CreatorHelper} has been executed, {@link RepositoryService} has been loaded
 * 
 * 
 * @author Kureem Rossaye
 */
public interface Startable extends Serializable {
	
	
	/**
	 * executed at correct moment.
	 */
	public void start();
	
	/**
	 * The priority in which it is executed
	 * @return
	 */
	public Integer getPriority();

}
