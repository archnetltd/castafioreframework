package org.castafiore.mongo;

import java.io.IOException;
import java.io.InputStream;

import javax.jcr.Binary;
import javax.jcr.RepositoryException;

import org.castafiore.mongo.io.BinaryStreamProvider;

public class MongoBinary implements Binary{
	
	
	
	private BinaryStreamProvider provider;

	
	
	public MongoBinary(BinaryStreamProvider provider) {
		super();
		this.provider = provider;
	}

	@Override
	public void dispose() {
		provider.dispose();
		
	}

	@Override
	public long getSize() throws RepositoryException {
		if(provider == null){
			throw new IllegalStateException("The size cannot be read since this Binary has already been disposed");
		}
		return provider.getSize();
	}

	@Override
	public InputStream getStream() throws RepositoryException {
		if(provider == null){
			throw new IllegalStateException("The size cannot be read since this Binary has already been disposed");
		}
		try{
		return provider.getFreshStream();
		}catch(Exception e){
			throw new RepositoryException(e);
		}
	}

	@Override
	public int read(byte[] b, long position) throws IOException,
			RepositoryException {
		if(provider == null){
			throw new IllegalStateException("The size cannot be read since this Binary has already been disposed");
		}
		
		if(b == null){
			throw new IOException("The buffer cannot be null");
		}
		
		return getStream().read(b, (int)position, b.length);
		
	}

}
