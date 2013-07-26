package org.castafiore.shoppingmall.reports;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.BinaryFile;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

public class ReportDownloadServlet extends HttpServlet {

	
	

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
		
		
		String type = ((HttpServletRequest)request).getParameter("type");
		OutputStream out = response.getOutputStream();
		
		
		if(type.equalsIgnoreCase("excel")){
			TableModel model = getModel((HttpServletRequest)request);
			response.setContentType("application/excel");
			response.setHeader("Content-Disposition", "filename=excelexport.xls" ); 
			
			generateEXCel(model, out);
			
		}else if(type.equalsIgnoreCase("pdf")){
			TableModel model = getModel((HttpServletRequest)request);
			response.setContentType("application/pdf");
			((HttpServletResponse)response).setHeader("Content-Disposition", "filename=pdfexport.xls" ); 
			generatePDF(model, out);
		}else if(type.equalsIgnoreCase("chart")){
			TableModel model = getModel((HttpServletRequest)request);
			response.setContentType("image/png");
			((HttpServletResponse)response).setHeader("Content-Disposition", "filename=chart.png" ); 
			generateBarChart(model, out);
		}else if(type.equalsIgnoreCase("excelreceipt")){
			try{
				response.setContentType("application/excel");
				response.setHeader("Content-Disposition", "filename=excelexport.xls" ); 
				Workbook wb = getWorkBook(request);
				Sheet sheet = getSheet(request, wb);
				printReceipt(sheet, request.getParameterMap());
				wb.write(out);
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}else if(type.equalsIgnoreCase("zipdownload")){
			String dir = request.getParameter("dir");
			String[] names = StringUtil.split(request.getParameter("names"), ";");
			ZipOutputStream zout = new ZipOutputStream(out);
			
			String contextPath = request.getContextPath();
			String serverPort = request.getServerPort() + "";
			String servaerName = request.getServerName();
			
			
			if(!contextPath.startsWith("/")){
				contextPath = "/" + contextPath;
			}
			
			for(String s : names){
				try{
					String path = dir + "/" +  URLEncoder.encode( s, "UTF-8");
					//BinaryFile bf = (BinaryFile)repo.getFile(path, Util.getRemoteUser());
					
					
					String url =  "http://" + servaerName + ":" + serverPort  + contextPath + "/" + ResourceUtil.getDownloadURL("ecm", path);
					byte[] bytes = ResourceUtil.read(url);
					
					zout.putNextEntry(new ZipEntry(s));
					zout.write(bytes);
					zout.closeEntry();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			
			response.setContentType("application/zip");
			response.setHeader("Content-Disposition", "filename=export.zip" ); 
			zout.flush();
			zout.close();
		}
		if(!type.equalsIgnoreCase("zipdownload"))
		out.flush();
	}
	
	
	private Sheet getSheet(HttpServletRequest request, Workbook wb)throws Exception{
		Map params =request.getParameterMap();
		Sheet sheet = null;
		
		if(params.containsKey("sheet")){
			sheet = wb.getSheet(params.get("sheet").toString());
		}else{
			sheet = wb.getSheetAt(0);
		}
		return sheet;
	}
	
	private Workbook getWorkBook(HttpServletRequest request)throws Exception{
		Map params =request.getParameterMap();
		String template = ((String[])params.get("template"))[0];
		Workbook wb = new HSSFWorkbook(new ByteArrayInputStream(ResourceUtil.readUrlBinary(template)));
		return wb;
	}
	

	private  void printReceipt(Sheet sheet, Map params){
		for(int i =0; i< sheet.getLastRowNum(); i++){
			try{
				Row row = sheet.getRow(i);
				for(int j = 0; j < row.getLastCellNum(); j++){
					try{
					org.apache.poi.ss.usermodel.Cell cell = row.getCell(j);
					String formula = cell.getStringCellValue();
					System.out.println(formula);
					if(formula != null){
						if(formula.startsWith("$rep(")){
							formula = formula.replace("$rep(", "").replace(")", "").trim();
							if(params.containsKey(formula)){
								String[] val = (String[])params.get(formula);
								cell.setCellValue(val[0]);
							}
						}
					}
					}catch(Exception e){
						
					}
				}
			}catch(Exception e){
				
			}
		}
	}
	



	private TableModel getModel(HttpServletRequest request){
		String app = request.getParameter("app");
		String compId = request.getParameter("compid");
		EXApplication uiApp = (EXApplication)request.getSession().getAttribute(app);
		
		Container c = uiApp.getDescendentById(compId);
		return c.getDescendentOfType(EXTable.class).getModel();
	}
	
	private void generateBarChart(TableModel model,OutputStream out){
		try{
			
			DefaultCategoryDataset data = new DefaultCategoryDataset();
			
			int columns = model.getColumnCount();
			int rows = model.getRowCount();
			for(int col =0; col < columns; col++){				
				for(int row =1; row <= rows; row++){
					Object o = model.getValueAt(col, row-1, 0);
					
					if(o instanceof Number){
						data.addValue((Number)o, model.getValueAt(col-1, row-1, 0).toString(), model.getValueAt(col-1, row-1, 0).toString());
					}
		
						
				}
			}
			
			JFreeChart chart = ChartFactory.createBarChart("", "", "", data, PlotOrientation.VERTICAL, true, true, false);
			BufferedImage img = chart.createBufferedImage(800, 600);
			ImageIO.write(img, "png", out);
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		
		
		
	}
	
	private void generatePDF(TableModel model,OutputStream out){
		try{
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document,out);
			document.open();
			
			int columns = model.getColumnCount();
			int rows = model.getRowCount();
			Table t = new Table(columns, rows +1);
			for(int col =0; col < columns; col++){
				t.addCell(model.getColumnNameAt(col), new Point(0, col));
				for(int row =1; row <= rows; row++){
					Object o = model.getValueAt(col, row-1, 0);
					if(o != null)
						t.addCell(o.toString(),new Point(row,col));
					else
						t.addCell("",new Point(row,col));
						
				}
			}
			
			t.setPadding(2);
			
			document.add(t);
			document.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		
		
		
	}
	
	
	private void generateEXCel(TableModel model,OutputStream out) throws IOException{
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		
		int columns =model.getColumnCount();
		int rows = model.getRowCount();
		for(int col =0; col < columns; col++){
			Row r = sheet.getRow(0);
			if( r== null){
				r = sheet.createRow(0);
			}
			
			Cell cell = r.getCell(col);
			if(cell == null){
				cell = r.createCell(col);
			}
			cell.setCellValue(model.getColumnNameAt(col));
			for(int row =1; row <= rows; row++){
				Row rr = sheet.getRow(row);
				if(rr == null){
					rr = sheet.createRow(row);
				}
				
				Cell cc = rr.getCell(col);
				if(cc == null){
					cc = rr.createCell(col);
				}
				
				Object o = model.getValueAt(col, row-1, 0);
				if(o != null){
				if(o instanceof Date )
					cc.setCellValue((Date)o);
				else if(o instanceof Calendar)
					cc.setCellValue((Calendar)o);
				else if(o instanceof Number)
					cc.setCellValue(Double.parseDouble(o.toString()));
				else
					cc.setCellValue(o.toString());
				
				
				}
			}
			
		}
		
		
		
		wb.write(out);
		
		
		
	}
	
	
	
	

}
