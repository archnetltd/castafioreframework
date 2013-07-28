package org.castafiore.mongo.query;

import java.util.Iterator;

import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;

public class MongoRowIterator implements RowIterator{
	
	private Iterator<Row> iter;
	
	private long position=0;
	
	private long size;
	


	@Override
	public Row nextRow() {
		position++;
		
		return iter.next();
	}

	@Override
	public long getPosition() {
		return position;
	}

	@Override
	public long getSize() {
		return size;
	}

	@Override
	public void skip(long skipNum) {
		long s = 0;
		while(s < skipNum)
		{
			next();
			s++;
		}
		
	}

	@Override
	public boolean hasNext() {
		return iter.hasNext();
	}

	@Override
	public Object next() {
		return nextRow();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(new UnsupportedRepositoryOperationException("cannot remove an item from a row iterator"));
		 
		
	}

}
