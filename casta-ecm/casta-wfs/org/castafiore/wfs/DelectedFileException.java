package org.castafiore.wfs;

import org.castafiore.wfs.types.File;

public class DelectedFileException extends RepositoryException{
	public DelectedFileException(File f, String username)
	{
		super("The file " + f.getAbsolutePath() + " has been deleted");
	} 
}
