package org.castafiore.wfs.cloud;

import java.io.InputStream;

public interface WritableCloudResource extends CloudResource{
	
	public void write(InputStream in, String mimeType)throws Exception;

}
