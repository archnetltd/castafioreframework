package org.castafiore.mongo.io;

import java.io.IOException;
import java.io.InputStream;

public interface BinaryStreamProvider {
	
	public InputStream getFreshStream()throws IOException;
	
	public void dispose();
	
	public long getSize();
	
	

}
