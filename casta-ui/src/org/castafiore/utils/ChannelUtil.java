/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelUtil {

	public static void fastChannelCopy(final ReadableByteChannel src,final WritableByteChannel dest) throws IOException 
	{
		final ByteBuffer buffer = ByteBuffer.allocateDirect( 1024);
		while (src.read(buffer) != -1) 
		{
			buffer.flip();
			dest.write(buffer);
			buffer.compact();
		}
		buffer.flip();
		while (buffer.hasRemaining()) 
		{
			dest.write(buffer);
		}
	}
	
	
	public static void TransferData(final InputStream input, final OutputStream output)throws IOException
	{
		final ReadableByteChannel inputChannel = Channels.newChannel(input);  
		final WritableByteChannel outputChannel = Channels.newChannel(output);    
		ChannelUtil.fastChannelCopy(inputChannel, outputChannel);  
		inputChannel.close();  
		outputChannel.close()  ;
	}
	
	
	public static void CopyFiles(String inputFile, String outputFile)throws IOException, FileNotFoundException
	{
		final InputStream input = new FileInputStream(inputFile);  
		final OutputStream output = new FileOutputStream(outputFile);  
		final ReadableByteChannel inputChannel = Channels.newChannel(input);  
		final WritableByteChannel outputChannel = Channels.newChannel(output);  
		ChannelUtil.fastChannelCopy(inputChannel, outputChannel);    
		inputChannel.close();  
		outputChannel.close()  ;
		
	}
	
	static final int BUFF_SIZE = 100000;
	static final byte[] buffer = new byte[BUFF_SIZE];

	public static void copy(String from, String to) throws IOException{
	   InputStream in = null;
	   OutputStream out = null; 
	   try {
	      in = new FileInputStream(from);
	      out = new FileOutputStream(to);
	      while (true) {
	         synchronized (buffer) {
	            int amountRead = in.read(buffer);
	            if (amountRead == -1) {
	               break;
	            }
	            out.write(buffer, 0, amountRead); 
	         }
	      } 
	   } finally {
	      if (in != null) {
	         in.close();
	      }
	      if (out != null) {
	         out.close();
	      }
	   }
	}


}
