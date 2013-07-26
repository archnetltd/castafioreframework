package org.castafiore.workflow;

import java.io.InputStream;
import java.util.Properties;

import org.castafiore.shoppingmall.orders.DefaultGUIReactor;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.BinaryFile;


/**
 * a flexible workflow where we specify a base dire from which the workflow is configured<br>
 * This gives the possibility to create as many workflow as necessary organization wide.
 * @author acer
 *
 */
public class FlexibleWorkflow extends MerchantWorkflow implements OrdersWorkflow{

	//private String pathDir ;//= "workflow";
	
	
	
	public FlexibleWorkflow(String baseDir) {
		super(new DefaultGUIReactor(), baseDir);
		//this.pathDir = baseDir;
	}



//	@Override
//	protected Properties getWorkflow(String m) throws Exception {
//		String path = pathDir + "/workflow.properties";
//		
//		if(workflows.containsKey(m)){
//			return workflows.get(m);
//		}
//		if(SpringUtil.getRepositoryService().itemExists(path)){
//			
//			InputStream in = ((BinaryFile)SpringUtil.getRepositoryService().getDirectory(path, Util.getRemoteUser())).getInputStream();
//			Properties p = new Properties();
//			p.load(in);
//			workflows.put(m, p);
//			return p;
//			
//		}else{
//			Properties prop = new Properties();
//			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("org/castafiore/shoppingmall/orders/workflow.properties"));
//			workflows.put(m,prop);
//			return prop;
//		}
//	}

	

}
