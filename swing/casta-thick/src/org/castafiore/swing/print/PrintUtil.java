package org.castafiore.swing.print;

import java.awt.print.Printable;
import java.io.InputStream;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

import org.castafiore.swing.options.PropertiesUtil;

public class PrintUtil {

	public static void printPaymentReceipt(String id, String fsCode, String date, String amount, String description,String name){
		try{
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/swing/payments/receipt.tpl");
			byte buff[] = new byte[in.available()];
			in.read(buff);
			String tpl = new String(buff);
			tpl = tpl.replace("$name", name).replace("id", id).replace("fsCode", fsCode).replace("$date", date).replace("$amount", amount).replace("$description", description);
			
			String printerName = PropertiesUtil.properties.getProperty("thermalPrinterName");
			JFrame frame = new JFrame("dfsd");
			JEditorPane a =new JEditorPane();
			a.setContentType("text/html");
			
			
			
			a.setText(tpl);
			frame.add(a);
			Printable p = new PrintableDocument(a);
			
		    PrintDevice.print(printerName, p);
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
