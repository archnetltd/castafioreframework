package org.castafiore.shoppingmall.reports;

import java.io.BufferedOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.mvc.CastafioreController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class EXJasperReports extends EXContainer implements CastafioreController{
	
	private JasperSource source;
	
	public EXJasperReports(String name, String tagName, JasperSource source) {
		super(name, tagName);
		this.source = source;
		
	}
	
	public void setJasperSource(JasperSource source){
		this.source = source;
	}
	
	private void render(HttpServletRequest request, HttpServletResponse response)throws JRException, IOException{
		JRDataSource data = source.getDataSource();
		Map<String,Object> params = source.getParameters();
		String fileName = source.getFileName();
		String format = source.getFormat();
		JasperReport report = source.getReport();
		JRExporter exp = getExporter(format);
		
         JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, data);
         ServletOutputStream out = response.getOutputStream();
         BufferedOutputStream bout = new BufferedOutputStream(out);
         exp.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
         exp.setParameter(JRExporterParameter.OUTPUT_STREAM,new BufferedOutputStream(bout) );
        exp.exportReport();
         String attachement = "attachment";
         if(format.equalsIgnoreCase("html") || format.equalsIgnoreCase("xhtml") || format.equalsIgnoreCase("xml")){
        	 attachement = "inline";
         }
         response.setHeader("Content-Disposition",""+attachement+"; filename=\"" + ""+fileName+"\"");
		bout.flush();
		bout.close();
	}
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		render(request, response);
		return null;
	}
	
	
	public JRExporter getExporter(String format){
		
		
		/*JRGraphics2DExporter
		JRHtmlExporter
		JRPdfExporter
		JRPrintServiceExporter
		JRRtfExporter
		JRTextExporter
		JRXhtmlExporter
		JROdtExporter
		JRXmlExporter
		JRDocxExporter
		JRPptxExporter*/
		if(format.equalsIgnoreCase("html")){
			return new JRHtmlExporter();
		}else if (format.equalsIgnoreCase("pdf")){
			return new JRPdfExporter();
		}else if(format.equalsIgnoreCase("rtf")){
			return new JRRtfExporter();
		}else if(format.equalsIgnoreCase("xhtml")){
			return new JRXhtmlExporter();
		}else if(format.equalsIgnoreCase("odt")){
			return new JROdsExporter();
		}else if(format.startsWith("doc")){
			return new JRDocxExporter();
		}else if(format.startsWith("ppt")){
			return new JRPptxExporter();
		}else if(format.equalsIgnoreCase("xml")){
			return new JRXmlExporter();
		}else if(format.equalsIgnoreCase("print")){
			return new JRPrintServiceExporter();
		}else{
			return null;
		}
	}

	
	
	
	

	
	
	

}
