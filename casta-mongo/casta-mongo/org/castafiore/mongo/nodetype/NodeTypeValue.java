package org.castafiore.mongo.nodetype;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.castafiore.mongo.MongoBinary;
import org.castafiore.mongo.io.BinaryStreamProvider;
import org.castafiore.mongo.io.ByteArrayBinaryStreamProvider;

import com.mongodb.BasicDBObject;

public class NodeTypeValue implements Value{
	
	private int type;
	
	private Object content;
	
	public NodeTypeValue(BasicDBObject value){
		this.type = value.getInt("type");
		this.content = value.get("content");
	}
	
	public NodeTypeValue(int value){
		this.type = PropertyType.LONG;
		this.content = value;
	}
	
	public NodeTypeValue(long value){
		this.type = PropertyType.LONG;
		this.content = value;
	}
	
	public NodeTypeValue(double value){
		this.type = PropertyType.DOUBLE;
		this.content = value;
	}
	
	public NodeTypeValue(String value){
		this.type = PropertyType.STRING;
		this.content = value;
	}
	
	public NodeTypeValue(String value, int type){
		this.type = type;
		this.content = value;
	}
	
	public NodeTypeValue(Date value){
		this.type = PropertyType.DATE;
		this.content = value;
	}
	
	public NodeTypeValue(Binary value){
		this.type = PropertyType.BINARY;
		this.content = value;
	}
	
	public NodeTypeValue(boolean value){
		this.type = PropertyType.BOOLEAN;
		this.content = value;
	}
	
	public NodeTypeValue(BigDecimal value){
		this.type = PropertyType.DECIMAL;
		this.content = value;
	}
	
	public NodeTypeValue(Node node)throws RepositoryException{
		this.type = PropertyType.REFERENCE;
		this.content = node.getPath();
	}
	
	public NodeTypeValue(Object o)throws RepositoryException{
		this.content = o;
		if(o instanceof Integer || o instanceof Long){
			type = PropertyType.LONG;
		}else if( o instanceof Double || o instanceof Float){
			type = PropertyType.DOUBLE;
		}else if(o instanceof Date){
			type = PropertyType.DATE;
		}else if(o instanceof Binary){
			type = PropertyType.BINARY;
		}else if(o instanceof Boolean){
			type = PropertyType.BOOLEAN;
		}else if(o instanceof Node){
			content = ((Node)o).getPath();
			type = PropertyType.REFERENCE;
		}else if(o instanceof BigDecimal){
			type = PropertyType.DECIMAL;
		}else if(o instanceof String){
			type = PropertyType.STRING;
		}
		
	}

	@Override
	public Binary getBinary() throws RepositoryException {
		if(content == null){
			return null;
		}
		if(content instanceof Binary){
			return (Binary)content;
		}else if(content instanceof byte[]){
			final byte[] data = (byte[])content;
			BinaryStreamProvider provider = new ByteArrayBinaryStreamProvider(data);
			return new MongoBinary(provider);
		}else if(content instanceof String){
			final byte[] data = content.toString().getBytes();
			BinaryStreamProvider provider = new ByteArrayBinaryStreamProvider(data);
			return new MongoBinary(provider);
		}else if(type == PropertyType.URI){
			//@TODO read data from url
		}
		
		throw new RepositoryException("Unable to get binary data from content:" + content.toString());
	}

	@Override
	public boolean getBoolean() throws ValueFormatException,
			RepositoryException {
		if(content == null){
			return false;
		}
		if(content instanceof Boolean){
			return (Boolean)content;
		}else{
			try{
			return Boolean.parseBoolean(content.toString());
			}catch(Exception e){
				throw new ValueFormatException(content.toString() + " cannot be converted to boolean");
			}
		}
	}

	@Override
	public Calendar getDate() throws ValueFormatException, RepositoryException {
		if(content == null){
			return null;
		}
		
		if(content instanceof Date){
			Calendar cal = Calendar.getInstance();
			cal.setTime((Date)content);
			return cal;
		}else if(content instanceof Calendar){
			return (Calendar)content;
		}else{
			try{
			Date d = new SimpleDateFormat("sYYYY-MM-DDThh:mm:ss.sssTZD").parse(content.toString());
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			return cal;
			}catch(Exception e){
				throw new ValueFormatException("cannot convert " + content + "  to date");
			}
		}
	}

	@Override
	public BigDecimal getDecimal() throws ValueFormatException,
			RepositoryException {
		if(content == null){
			return null;
		}else if(content instanceof BigDecimal){
			return (BigDecimal)content;
		}else {
			try{
				
			return new BigDecimal(content.toString());
			}catch(Exception e){
				throw new ValueFormatException(content.toString() + " cannot be converted to bigdecimal");
			}
		}
	}

	@Override
	public double getDouble() throws ValueFormatException, RepositoryException {
		if(content == null){
			return 0;
		}else if(content instanceof Double){
			return (Double)content;
		}else {
			try{
				
			return  Double.valueOf(content.toString());
			}catch(Exception e){
				throw new ValueFormatException(content.toString() + " cannot be converted to double");
			}
		}
	}

	@Override
	public long getLong() throws ValueFormatException, RepositoryException {
		if(content == null){
			return 0;
		}else if(content instanceof Long){
			return (Long)content;
		}else if(content instanceof Date){
			return ((Date)content).getTime();
		}else if(content instanceof Calendar){
			return ((Calendar)content).getTimeInMillis();
		}else {
			try{
				
			return  Long.valueOf(content.toString());
			}catch(Exception e){
				throw new ValueFormatException(content.toString() + " cannot be converted to Long");
			}
		}
	}

	@Override
	public InputStream getStream() throws RepositoryException {
		if(content == null){
			return null;
		}else if(content instanceof InputStream){
			return (InputStream)content;
		}else if(content instanceof Binary){
			content = ((Binary)content).getStream();
			return getStream();
		}else{
			try{
			return new ByteArrayInputStream(content.toString().getBytes());
			}catch(Exception e){
				throw new ValueFormatException(e);
			}
		}
	}

	@Override
	public String getString() throws ValueFormatException,
			IllegalStateException, RepositoryException {
		if(content == null){
			return null;
		}else if(content instanceof String){
			return (String)content;
		}else if(content instanceof Date){
			return new SimpleDateFormat("sYYYY-MM-DDThh:mm:ss.sssTZD").format((Date)content);
		}else if(content instanceof Calendar){
			return new SimpleDateFormat("sYYYY-MM-DDThh:mm:ss.sssTZD").format(((Calendar)content).getTime());
		}else{
			return content.toString();
		}
	}

	@Override
	public int getType() {
		return type;
	}

}
