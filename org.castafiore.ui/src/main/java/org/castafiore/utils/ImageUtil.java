package org.castafiore.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;

import javax.imageio.ImageIO;

public class ImageUtil {

	public static BufferedImage loadImage(String ref) {
		BufferedImage bimg = null;
		try {

			bimg = ImageIO.read(new File(ref));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bimg;
	}

	public static BufferedImage loadTranslucentImage(String url,
			float transperancy) {
		// Load the image
		BufferedImage loaded = loadImage(url);
		// Create the image using the
		BufferedImage aimg = new BufferedImage(loaded.getWidth(),
				loaded.getHeight(), BufferedImage.TRANSLUCENT);
		// Get the images graphics
		Graphics2D g = aimg.createGraphics();
		// Set the Graphics composite to Alpha
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				transperancy));
		// Draw the LOADED img into the prepared reciver image
		g.drawImage(loaded, null, 0, 0);
		// let go of all system resources in this Graphics
		g.dispose();
		// Return the image
		return aimg;

	}

	public static BufferedImage applyFilter(String url, BufferedImageOp filter) {
		BufferedImage img = loadImage(url);

		return applyFilter(img, filter);

	}

	public static BufferedImage applyFilter(BufferedImage img,
			BufferedImageOp filter) {

		BufferedImage re = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		filter.filter(img, re);
		return re;

	}

	public static void saveImage(BufferedImage img, String ref) {
		try {

			String format = "png";
			ImageIO.write(img, format, new File(ref));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void testLayout() throws Exception {

		File dir = new File("C:\\java\\html\\html\\tpl");

		File[] imgs = dir.listFiles();

		// Random rand = new Random();

		for (File f : imgs) {
			if (f.isDirectory()) {
				continue;
			}

			System.out.println(f.getName());
			BufferedImage input = loadImage(f.getAbsolutePath());
			int width = input.getWidth();
			int height = input.getHeight();

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					int rgb = input.getRGB(i, j);
					Color c = new Color(rgb);
					int red = c.getRed();
					int green = c.getGreen();
					int blue = c.getBlue();
					// int alpha = c.getAlpha();

					if (red > 200 || green > 200 || blue > 200 || red < 25
							|| green < 25 || blue < 25) {

					} else {
						Color c1 = new Color((red * 60) % 255,
								(red * 60) % 255, (blue * 45) % 255);
						// Color c1 =c.brighter();
						input.setRGB(i, j, c1.getRGB());
					}

					System.out.println(red + "," + green + "," + blue);

				}
			}

			// GradientFilter filter = new GradientFilter(new Point(0, 0),new
			// Point(0,height),new Color(r1, g1, b1).getRGB(),new Color(r2,g2,
			// b2).getRGB(),false,GradientFilter.LINEAR,GradientFilter.LINEAR);
			// BufferedImage res = applyFilter(f.getAbsolutePath(), filter);
			saveImage(input, dir.getParent() + "\\images\\" + f.getName());
		}

	}

	public static void resizeImage(InputStream image, int width,
			String extension, OutputStream out) throws IOException {

		BufferedImage originalImage = ImageIO.read(image);

		BufferedImage res = resizeImage(originalImage, width);
		// ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(res, extension, out);

		// return out.toByteArray();

	}

	private static BufferedImage resizeImage(BufferedImage originalImage,
			int width) {
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB
				: originalImage.getType();
		BigDecimal originalw = new BigDecimal(originalImage.getWidth());
		BigDecimal originalh = new BigDecimal(originalImage.getHeight());
		BigDecimal ratio = originalh.divide(originalw, MathContext.DECIMAL32);
		BigDecimal nh = ratio.multiply(new BigDecimal(width));
		BufferedImage resizedImage = new BufferedImage(width, nh.intValue(),
				type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, nh.intValue(), null);
		g.dispose();
		return resizedImage;
	}

	public static void main(String[] args) throws Exception {

		testLayout();

		// StringBuilder complete = new StringBuilder();
		// String templateDir =
		// "C:\\Users\\kureem\\castafiorea\\casta-mysite\\web\\youdo\\menu\\template";
		// Random rand = new Random();
		// String template = IOUtil.getFileContenntAsString(templateDir +
		// "\\menu7.html");
		// for(int i = 0; i < 100; i ++){
		// int r1 = rand.nextInt(255);
		// int r2 = rand.nextInt(255);
		//
		// int g1 = rand.nextInt(255);
		// int g2 = rand.nextInt(255);
		//
		// int b1 = rand.nextInt(255);
		// int b2 = rand.nextInt(255);
		// String folder = "" + r1 + g1 + b1;
		// String s = "";
		// s = template.replace("${folder}",folder);
		// s = s.replace("tabs10", "cls_" +folder);
		// complete.append(s).append("\n");
		// String resultName =
		// "C:\\Users\\kureem\\castafiorea\\casta-mysite\\web\\youdo\\menu\\" +
		// r1 + g1 + b1;
		// String[] imgs = new String[]{ "right.png", "right-selected.png"};
		//
		// //String dir = "" +r1 + g1 + b1;
		// //make directory (
		// File dir = new File(resultName);
		// if(!dir.exists()){
		// dir.mkdir();
		// }
		// //create 2 images
		// for(int j = 0; j < imgs.length; j ++){
		// if(j == 0)
		// saveImage(applyFilter(templateDir + "\\" + imgs[j], new
		// GradientFilter(new Point(0, 0),new Point(174,42),new Color(r1, g1,
		// b1).getRGB(),new Color(r2,g2,
		// b2).getRGB(),false,GradientFilter.BILINEAR,GradientFilter.INT_LINEAR)),
		// resultName + "\\"+ imgs[j]);
		// else{
		// BufferedImage img = loadImage(templateDir + "\\" + imgs[0]);
		// BufferedImage re = new BufferedImage(img.getWidth(), img.getHeight(),
		// BufferedImage.TYPE_INT_RGB);
		//
		// BufferedImageOp op =new GradientFilter(new Point(0, 0),new
		// Point(174,42),new Color(r1, g1, b1).getRGB(),new Color(r2,g2,
		// b2).getRGB(),false,GradientFilter.BILINEAR,GradientFilter.INT_LINEAR);
		// op.filter(img, re);
		// //BufferedImage img = applyFilter(templateDir + "\\" + imgs[0], op);
		//
		// op = new MedianFilter();
		// op.filter(re, re);
		//
		// //img = applyFilter(url, filter)
		// saveImage(re, resultName + "\\"+ imgs[j]);
		// }
		// }
		//
		//
		//
		//
		//
		//
		//
		//
		//
		//
		//
		// }
		//
		// FileOutputStream out = new
		// FileOutputStream("C:\\Users\\kureem\\castafiorea\\casta-mysite\\web\\youdo\\menu"
		// + "\\menu.html");
		// template = "<html><head></head><body><div>" + complete.toString() +
		// "</div></body></html>";
		// out.write(template.getBytes());
		// out.flush();
		// out.close();

	}
}
