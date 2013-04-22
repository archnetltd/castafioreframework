package org.castafiore.swing.payments;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class PrintDevice {
	
	public static void main(String[] argv)throws Exception {
		String printerName = "Microsoft XPS Document Writer";
		JFrame frame = new JFrame("dfsd");
		JEditorPane a =new JEditorPane();
		a.setContentType("text/html");
		
		FileInputStream fin = new FileInputStream(new File("c://Adev//ttt.xhtml"));
		byte[] butes = new byte[fin.available()];
		fin.read(butes);
		
		a.setText(new String(butes));
		frame.add(a);
		Printable p = new PrintableDocument(a);
		
	    print(printerName, p);
	  }

	
	public static void print(String printerName, Printable p){
		PrintService[] printServices;
	    PrintService printService;
	    PageFormat pageFormat;

	    

	    PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
	    printServiceAttributeSet.add(new PrinterName(printerName, null));       
	    printServices = PrintServiceLookup.lookupPrintServices(null, printServiceAttributeSet); 

	    PrinterJob printerjob = PrinterJob.getPrinterJob();
	    
	   // pageFormat = new PageFormat();    // If you want to adjust heigh and width etc. of your paper.
	   // pageFormat = printerjob.
	    pageFormat = printerjob.defaultPage();

	    

	    
	    
	    printerjob.setPrintable(p, pageFormat);    // Server was my class's name, you use yours.
	    
	    

	    try{
	        printService = printServices[0];
	        printerjob.setPrintService(printService);   // Try setting the printer you want
	    }catch (ArrayIndexOutOfBoundsException e){
	            System.err.println("Error: No printer named '"+printerName+"', using default printer.");
	            pageFormat = printerjob.defaultPage();  // Set the default printer instead.
	    }catch (PrinterException exception) {
	        System.err.println("Printing error: " + exception);
	    }

	    try {
	        printerjob.print();   // Actual print command
	    } catch (PrinterException exception) {
	        System.err.println("Printing error: " + exception);
	    }
	}

}
