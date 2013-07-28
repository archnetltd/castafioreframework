package org.castafiore.swing.sales.options;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.AttributedCharacterIterator;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import com.lowagie.text.pdf.codec.Base64;

public class CanvaGraphic extends Graphics{
	
	private Color color = Color.BLACK;
	
	private Point origin = new Point(0,0);
	
	private Font font = Font.decode("Times New Roman");
	
	private Area clip = null;
	
	private Color xOrMode;
	
	private boolean paintMode = true;
	
	
	Writer writer; //= new FileWriter(new File("c:\\java\\canva.js"));

	private CanvaGraphic (Writer w){
		this.writer = w;
	}
	
	public CanvaGraphic(){
		
	}
	
	@Override
	public Graphics create() {
		try {
			return new CanvaGraphic(new FileWriter(new File("c:\\java\\canva.js")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void translate(int x, int y) {
		origin = new Point(x,y);
		append("ctx.translate("+x+","+y+");");
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color c) {
		this.color = c;
		append("ctx.strokeStyle=\"#"+Integer.toHexString(c.getRGB())+"\";");
		append("ctx.fillStyle=\"#"+Integer.toHexString(c.getRGB())+"\";");
		
	}

	@Override
	public void setPaintMode() {
		this.paintMode = true;
		
	}

	@Override
	public void setXORMode(Color c1) {
		
		this.xOrMode = c1;
		paintMode = false;
	}

	@Override
	public Font getFont() {
		return font;
	}

	@Override
	public void setFont(Font font) {
		this.font = font;
		String txt = font.getSize() + " ";
		if(font.isBold()){
			txt = txt + " bold ";
		}
		if(font.isItalic()){
			txt = txt + " italic ";
		}
		txt = txt + font.getFamily() + " ";
		append("ctx.font=\""+txt+"\";");
		
	}
	
	protected CanvaGraphic append(String s){
		try{
			writer.write(s);
			return this;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public FontMetrics getFontMetrics(Font f) {
		return new FontMetrics(f) {
		};
	}

	@Override
	public Rectangle getClipBounds() {
		Shape clip = getClip();
		if(clip != null){
			return clip.getBounds();
		}
		return null;
	}

	@Override
	public void clipRect(int x, int y, int width, int height) {
		clip.intersects(x, y, width, height);
		setClip(clip);
		
	}

	@Override
	public void setClip(int x, int y, int width, int height) {
		
		 setClip(new Area(new Rectangle(x, y, width, height)));
		 
		 //ctx.rect(50,20,200,120);ctx.clip();
		
	}

	@Override
	public Shape getClip() {
		return clip;
	}

	@Override
	public void setClip(Shape clip) {
		if(clip == null){
			this.clip = null;
			return;
		}
		if(clip instanceof Area){
			this.clip = (Area)clip;
		}else{
			this.clip = new Area(clip);
		}
		
		Rectangle r = clip.getBounds();
		append("ctx.rect("+r.x+","+r.y+","+r.width+","+r.height+");ctx.clip();");
		
	}

	@Override
	public void copyArea(int x, int y, int width, int height, int dx, int dy) {
		// TODO Auto-generated method stub
		
	}
	
	
	

	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		
		append("ctx.beginPath();");
		append("ctx.moveTo(").append(x1+ ",").append(y1 + ");").append("ctx.lineTo("+x2+","+y2+");ctx.stroke();");
		
		
		
	
		
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		
		append("ctx.fillRect("+x+","+y+","+width+","+height+");");
		
		
	}

	@Override
	public void clearRect(int x, int y, int width, int height) {
		append("ctx.clearRect("+x+","+y+","+width+","+height+");");
			
		
	}

	@Override
	public void drawRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight) {
		append("roundRect(ctx, "+x+", "+y+", "+width+", "+height+", "+arcWidth+", "+arcHeight+");");
		append("ctx.stroke();");
		
	}

	@Override
	public void fillRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight) {
		append("roundRect(ctx, "+x+", "+y+", "+width+", "+height+", "+arcWidth+", "+arcHeight+");");
		append("ctx.fill();");
		
	}

	@Override
	public void drawOval(int x, int y, int width, int height) {
		append("drawEllipse(ctx, "+x+", "+y+", "+width+", "+height+");");
		append("ctx.stroke();");
		
	}

	@Override
	public void fillOval(int x, int y, int width, int height) {
		append("drawEllipse(ctx, "+x+", "+y+", "+width+", "+height+");");
		append("ctx.fill();");
		
	}

	@Override
	public void drawArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {
		append("ctx.arc("+x+","+y+","+width+","+startAngle+","+arcAngle+");");
		append("ctx.stroke();");
		
	}

	@Override
	public void fillArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {
		append("ctx.arc("+x+","+y+","+width+","+startAngle+","+arcAngle+");");
		append("ctx.fill();");
		
	}

	@Override
	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
		append("ctx.beginPath();");
		for(int i = 0; i < nPoints;i++){
			int x1 = xPoints[i];
			int y1 = yPoints[i];
			if(i >0){
				append("cxt.lineTo("+x1+", "+y1+");");
			}
			if(i < nPoints-1)
				append("cxt.moveTo("+x1+", "+y1+");");
		}
		
		append("ctx.stroke();");
		
	}

	@Override
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		append("ctx.beginPath();");
		for(int i = 0; i < nPoints;i++){
			int x1 = xPoints[i];
			int y1 = yPoints[i];
			if(i >0){
				append("cxt.lineTo("+x1+", "+y1+");");
			}
			if(i < nPoints-1)
				append("cxt.moveTo("+x1+", "+y1+");");
		}
		append("ctx.closePath();");
		append("ctx.stroke();");
		
	}

	@Override
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		append("ctx.beginPath();");
		for(int i = 0; i < nPoints;i++){
			int x1 = xPoints[i];
			int y1 = yPoints[i];
			if(i >0){
				append("cxt.lineTo("+x1+", "+y1+");");
			}
			if(i < nPoints-1)
				append("cxt.moveTo("+x1+", "+y1+");");
		}
		append("ctx.closePath();");
		append("ctx.fill();");
		
	}

	@Override
	public void drawString(String str, int x, int y) {
		append("ctx.strokeText(\""+str+"\","+x+","+y+"); ");
		
	}

	@Override
	public void drawString(AttributedCharacterIterator iterator, int x, int y) {
		StringBuilder b = new StringBuilder();
		for(int i = iterator.getBeginIndex(); i <= iterator.getEndIndex(); i++){
			b.append(iterator.current());
			iterator.next();
		}
		drawString(b.toString(), x, y);
		
	}

	@Override
	public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
		if(img instanceof RenderedImage){

			try{
				int width = img.getWidth(observer);
				int height = img.getHeight(observer);
				BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	            Graphics2D g3 = scaled.createGraphics();
	            g3.drawImage(img, x, y, width, height, observer);
	            g3.dispose();
	            
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
	            iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	           
	            ImageWriter iw = (ImageWriter)ImageIO.getImageWritersByFormatName("jpg").next();
	            ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
	            iw.setOutput(ios);
	            iw.write(null, new IIOImage(scaled, null, null), iwparam);
	            iw.dispose();
	            ios.close();
	
	            scaled.flush();
	            scaled = null;
				byte[] data = baos.toByteArray();
				String attrName = "img_" + System.currentTimeMillis();
				append("var " + attrName + " = new Image();");
				
				
				String enc = Base64.encodeBytes(data);
				enc = "'data:image/jpg;base64," + enc + "';";
				
				append(attrName + ".src="+ enc);
			}catch(Exception e){
				e.printStackTrace();
			}
			return true;
		}
		
		
		
		
		return false;
	}

	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height,
			ImageObserver observer) {
		
		if(img instanceof RenderedImage){

			try{
				BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	            Graphics2D g3 = scaled.createGraphics();
	            g3.drawImage(img, x, y, width, height, observer);
	            g3.dispose();
	            
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
	            iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	           
	            ImageWriter iw = (ImageWriter)ImageIO.getImageWritersByFormatName("jpg").next();
	            ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
	            iw.setOutput(ios);
	            iw.write(null, new IIOImage(scaled, null, null), iwparam);
	            iw.dispose();
	            ios.close();
	
	            scaled.flush();
	            scaled = null;
				byte[] data = baos.toByteArray();
				String attrName = "img_" + System.currentTimeMillis();
				append("var " + attrName + " = new Image();");
				
				
				String enc = Base64.encodeBytes(data);
				enc = "'data:image/jpg;base64," + enc + "';";
				
				append(attrName + ".src="+ enc);
			}catch(Exception e){
				e.printStackTrace();
			}
			return true;
		}
		
		
		
		
		return false;
	}

	@Override
	public boolean drawImage(Image img, int x, int y, Color bgcolor,
			ImageObserver observer) {
		if(img instanceof RenderedImage){

			try{
				BufferedImage scaled = new BufferedImage(img.getWidth(observer), img.getHeight(observer), BufferedImage.TYPE_INT_RGB);
	            Graphics2D g3 = scaled.createGraphics();
	            g3.drawImage(img, x, y, img.getWidth(observer), img.getHeight(observer), observer);
	            g3.dispose();
	            
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
	            iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	           
	            ImageWriter iw = (ImageWriter)ImageIO.getImageWritersByFormatName("jpg").next();
	            ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
	            iw.setOutput(ios);
	            iw.write(null, new IIOImage(scaled, null, null), iwparam);
	            iw.dispose();
	            ios.close();
	
	            scaled.flush();
	            scaled = null;
				byte[] data = baos.toByteArray();
				String attrName = "img_" + System.currentTimeMillis();
				append("var " + attrName + " = new Image();");
				
				
				String enc = Base64.encodeBytes(data);
				enc = "'data:image/jpg;base64," + enc + "';";
				
				append(attrName + ".src="+ enc);
			}catch(Exception e){
				e.printStackTrace();
			}
			return true;
		}
		
		
		
		
		return false;
	}
	
	

	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height,
			Color bgcolor, ImageObserver observer) {
		
		if(img instanceof RenderedImage){

			try{
				BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	            Graphics2D g3 = scaled.createGraphics();
	            g3.drawImage(img, x, y, width, height,bgcolor, observer);
	            g3.dispose();
	            
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
	            iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	           
	            ImageWriter iw = (ImageWriter)ImageIO.getImageWritersByFormatName("jpg").next();
	            ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
	            iw.setOutput(ios);
	            iw.write(null, new IIOImage(scaled, null, null), iwparam);
	            iw.dispose();
	            ios.close();
	
	            scaled.flush();
	            scaled = null;
				byte[] data = baos.toByteArray();
				String attrName = "img_" + System.currentTimeMillis();
				append("var " + attrName + " = new Image();");
				
				
				String enc = Base64.encodeBytes(data);
				enc = "'data:image/jpg;base64," + enc + "';";
				
				append(attrName + ".src="+ enc);
			}catch(Exception e){
				e.printStackTrace();
			}
			return true;
		}
		
		
		
		
		return false;
	}

	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
		if(img instanceof RenderedImage){

			try{
				BufferedImage scaled = new BufferedImage(dx2-dx1, dy2-dy1, BufferedImage.TYPE_INT_RGB);
	            Graphics2D g3 = scaled.createGraphics();
	            g3.drawImage( img,  dx1,  dy1,  dx2,  dy2, sx1,  sy1,  sx2,  sy2, observer);
	            g3.dispose();
	            
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
	            iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	           
	            ImageWriter iw = (ImageWriter)ImageIO.getImageWritersByFormatName("jpg").next();
	            ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
	            iw.setOutput(ios);
	            iw.write(null, new IIOImage(scaled, null, null), iwparam);
	            iw.dispose();
	            ios.close();
	
	            scaled.flush();
	            scaled = null;
				byte[] data = baos.toByteArray();
				String attrName = "img_" + System.currentTimeMillis();
				append("var " + attrName + " = new Image();");
				
				
				String enc = Base64.encodeBytes(data);
				enc = "'data:image/jpg;base64," + enc + "';";
				
				append(attrName + ".src="+ enc);
			}catch(Exception e){
				e.printStackTrace();
			}
			return true;
		}
		
		
		
		
		return false;
	}

	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2,int sx1, int sy1, int sx2, int sy2, Color bgcolor,ImageObserver observer) {
		if(img instanceof RenderedImage){

			try{
				BufferedImage scaled = new BufferedImage(dx2-dx1, dy2-dy1, BufferedImage.TYPE_INT_RGB);
	            Graphics2D g3 = scaled.createGraphics();
	            g3.drawImage( img,  dx1,  dy1,  dx2,  dy2, sx1,  sy1,  sx2,  sy2,  bgcolor, observer);
	            g3.dispose();
	            
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
	            iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	           
	            ImageWriter iw = (ImageWriter)ImageIO.getImageWritersByFormatName("jpg").next();
	            ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
	            iw.setOutput(ios);
	            iw.write(null, new IIOImage(scaled, null, null), iwparam);
	            iw.dispose();
	            ios.close();
	
	            scaled.flush();
	            scaled = null;
				byte[] data = baos.toByteArray();
				String attrName = "img_" + System.currentTimeMillis();
				append("var " + attrName + " = new Image();");
				
				
				String enc = Base64.encodeBytes(data);
				enc = "'data:image/jpg;base64," + enc + "';";
				
				append(attrName + ".src="+ enc);
			}catch(Exception e){
				e.printStackTrace();
			}
			return true;
		}
		
		
		
		
		return false;
	}

	@Override
	public void dispose() {
		
		try {
			writer.flush();
			writer.close();
			writer=null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
