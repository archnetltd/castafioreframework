/*
 * Copyright (C) 2007-2008 Castafiore
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

package org.castafiore.groovy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTMLDocument.BlockElement;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.builder.AstBuilder;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Reduction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	
	private ApplicationContext context;

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
//		Main m = new Main();
//		 m.startContainer();
//		 TutorialService tService = (TutorialService)m.getObject("tutorial");
//		 tService.testMethod();
//		 List<ASTNode> nodes = new AstBuilder().buildFromString("def w; def v; System.out.println(\"fsdfsdfs\");");
//		 
//		 
//		 for(ASTNode n : nodes){
//			 if(n instanceof BlockStatement){
//				 BlockStatement block = (BlockStatement)n;
//				
//				 List<Statement> stms = block.getStatements();
//				 for(Statement s : stms){
//					 if(s instanceof ExpressionStatement){
//						 ExpressionStatement expression = (ExpressionStatement)s;
//						 Expression exp = expression.getExpression();
//						 if(exp instanceof DeclarationExpression){
//							 DeclarationExpression declaration = (DeclarationExpression)exp;
//							 System.out.println( declaration.getLeftExpression().getText());
//						 }
//						 
//						//System.out.println(expression.getExpression().getClass().getName()); 
//						 
//					 }
//					 //System.out.println(s.getClass().getName());
//				 }
//			 }
//			// System.out.println(n.getText());
//		 }
		
	///root/users/erevolution/Applications/e-Shop/erevolution/DefaultCashBook/entries/2012/
		//INSERT INTO WFS_FILE VALUES ('Account', '/root/users/erevolution/Applications/e-Shop/erevolution/DefaultCashBook/accounts/1351867186927', 'org.castafiore.accounting.Account', NULL, '2012-11-02 23:23:20', NULL, '2012-11-02 23:23:20', '\0', '1351867186927', 'erevolution', '*:users', 0, 0, '', NULL, 1, 1, '', 'Whenever there is a sales', NULL, 'Sales Account', NULL, NULL, NULL, NULL, NULL, 'SALES', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Income', 0.00, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/root/users/erevolution/Applications/e-Shop/erevolution/DefaultCashBook/accounts', NULL);

//		List<String> tier1 = new ArrayList<String>();
//		List<String> tier2 = new ArrayList<String>();
//		Sheet s = new HSSFWorkbook(new FileInputStream(new File("c:\\java\\erevolution\\tiers.xls"))).getSheetAt(0);
//		for(int i =1; i <= s.getLastRowNum();i++){
//			
//			Row r = s.getRow(i);
//			if(r != null && r.getCell(1) != null && r.getCell(1).getNumericCellValue() >0){
//				//tier 1 agent for erevolution
//				//System.out.println("insert into SECU_RELATIONSHIP values ("+(i+11000)+",'erevolution', 'Tier 2 Agent', '"+new Double(r.getCell(1).getNumericCellValue()).intValue()+"');");
//				tier2.add(new Double(r.getCell(1).getNumericCellValue()).intValue() + "");
//				
//			}
//			
//			if(r != null && r.getCell(0) != null && r.getCell(0).getNumericCellValue() >0){
//				//tier 1 agent for erevolution
//				//System.out.println("insert into SECU_RELATIONSHIP values ("+(i+11000)+",'erevolution', 'Tier 2 Agent', '"+new Double(r.getCell(1).getNumericCellValue()).intValue()+"');");
//				tier1.add(new Double(r.getCell(0).getNumericCellValue()).intValue() + "");
//				
//			}
////			if(s.getRow(i).getCell(0).getStringCellValue().equalsIgnoreCase("merchant"))
////				System.out.println("insert into SECU_RELATIONSHIP values ("+(i+10000)+",'erevolution', '"+s.getRow(i).getCell(3).getStringCellValue()+"', '"+s.getRow(i).getCell(1).getStringCellValue()+"');");
//		}
		//FileOutputStream fout = new FileOutputStream(new File("c:\\java\\erevolution\\tiers.sql"));
		//FileWriter writer = new FileWriter(new File("c:\\java\\erevolution\\tiers.sql"));
		//int i = 12000;
//		for(String t1 : tier1){
//			for(String t2 : tier2){
//				//writer.write(str)
//				writer.write("insert into SECU_RELATIONSHIP values ("+(i++)+",'"+t1+"', 'Customer', '"+t2+"');\n");
//				writer.write("insert into SECU_RELATIONSHIP values ("+(i++)+",'"+t2+"', 'Supplier', '"+t1+"');\n");
//			}
//		}
		
		//writer.flush();
		//writer.close();
		
//		FileWriter writer = new FileWriter(new File("c:\\java\\erevolution\\tiers.sql"));
//		tier2.add("erevolution");
//		String[] accs = new String[]{"Sales:SALES","Purchases:PURCHASES", 
//				"Travelling Expenses:TRAVELLING", "Salary:SALARY", "Tax:TAX", "Misc:MISC", 
//				"Electricity:ELEC,Water,Telecom","NPS,NPF:NPS", "Bank charges and Interest:BNKCHGS","Loan:LOAN","Petty cash:PETTY", "Vehicle expenses:VEHICLE"};
//		
//		
//		
//		for(String t1 : tier1){
//			
//			for(String ss : accs){
//				String[] parts = StringUtil.split(ss, ":");
//				String code = parts[1];
//				String acco = parts[0];
//				String name = System.currentTimeMillis()+StringUtil.nextString(10);
//				writer.write("INSERT INTO WFS_FILE VALUES ('Account', '/root/users/"+t1+"/Applications/e-Shop/"+t1+"/DefaultCashBook/accounts/"+name+ "', 'org.castafiore.accounting.Account', NULL, '2012-11-02 23:23:20', NULL, '2012-11-02 23:23:20', '\0', '1351867186927', '"+t1+"', '*:users', 0, 0, ' ', NULL, 1, 1, ' ', '"+acco+"', NULL, '"+acco+"', NULL, NULL, NULL, NULL, NULL, '"+code+"', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Income', 0.00, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/root/users/"+t1+"/Applications/e-Shop/"+t1+"/DefaultCashBook/accounts', NULL);\n");
//			}
//			
//			
//			
//		}
//		
//		for(String t1 : tier2){
//			for(String ss : accs){
//				String[] parts = StringUtil.split(ss, ":");
//				String code = parts[1];
//				String acco = parts[0];
//				String name = System.currentTimeMillis()+StringUtil.nextString(10);
//				writer.write("INSERT INTO WFS_FILE VALUES ('Account', '/root/users/"+t1+"/Applications/e-Shop/"+t1+"/DefaultCashBook/accounts/"+name+ "', 'org.castafiore.accounting.Account', NULL, '2012-11-02 23:23:20', NULL, '2012-11-02 23:23:20', '\0', '1351867186927', '"+t1+"', '*:users', 0, 0, ' ', NULL, 1, 1, ' ', '"+acco+"', NULL, '"+acco+"', NULL, NULL, NULL, NULL, NULL, '"+code+"', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Income', 0.00, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/root/users/"+t1+"/Applications/e-Shop/"+t1+"/DefaultCashBook/accounts', NULL);\n");
//			}
//		}
//		
//		writer.flush();
//		writer.close();
		Workbook hs = new HSSFWorkbook();
		Cell c = hs.createSheet().createRow(0).createCell(0);
		c.setCellValue("45");
		
		c.getCellStyle().setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0_);($#,##0)"));
		DataFormatter d = new HSSFDataFormatter();
		
		
		
		System.out.println(d.formatCellValue(c));
		//MessageFormat.format(pattern, arguments)
		
	}
	
	//each tier 1 agent has customer all tier 2 agent
	
	/**
	 * Piece of code to start a spring container
	 */
	public void startContainer(){
		context = new ClassPathXmlApplicationContext("org/castafiore/groovy/tutorial-context.xml");
		
	}
	
	
	/**
	 * piece of code to load an object from container
	 * @param objectId
	 */
	public Object getObject(String objectId){
		return context.getBean(objectId);
	}
	
	
	
	
	
	
	

}
