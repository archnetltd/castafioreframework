package org.castafiore.mongo.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;



public class ByteArrayBinaryStreamProvider implements BinaryStreamProvider{

	private byte[] data;
	
	
	
	public ByteArrayBinaryStreamProvider(byte[] data) {
		super();
		this.data = data;
	}

	@Override
	public void dispose() {
		data = null;
		
	}

	@Override
	public InputStream getFreshStream() throws IOException {
		if(data != null){
			return new ByteArrayInputStream(data);
		}else{
			throw new IllegalStateException("Thos BinaryStreamProvider has already been disposed");
		}
	}

	@Override
	public long getSize() {
		if(data != null){
			return data.length;
		}else{
			throw new IllegalStateException("Thos BinaryStreamProvider has already been disposed");
		}
	}

}
