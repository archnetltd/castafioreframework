package org.castafiore.wfs.cloud;

import java.io.InputStream;

public interface ReadableCloudResource extends CloudResource{
	
	public InputStream getInputStream()throws Exception;

}
