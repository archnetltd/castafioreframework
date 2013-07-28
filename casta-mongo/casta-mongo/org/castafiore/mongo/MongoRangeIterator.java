package org.castafiore.mongo;

import javax.jcr.RangeIterator;

import com.mongodb.DBCursor;

public class MongoRangeIterator implements RangeIterator{
	
	private DBCursor c;
	private int position = 0;

	@Override
	public long getPosition() {
		return position;
	}

	@Override
	public long getSize() {
		return c.size();
	}

	@Override
	public void skip(long skipNum) {
		c.skip((int)skipNum);
		
	}

	@Override
	public boolean hasNext() {
		return  c.hasNext();
	}

	@Override
	public Object next() {
		Object o = c.next();
		position++;
		return o;
	}

	@Override
	public void remove() {
		while(c.hasNext()){
			MongoNode o = (MongoNode)c.next();
			o.remove();
			
		}
		
	}

}
