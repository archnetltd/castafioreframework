package org.castafiore.shoppingmall.merchant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.EXSearchEngineApplication;
import org.castafiore.utils.IOUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.web.servlet.AbstractCastafioreServlet;

public class MerchantPageServlet extends AbstractCastafioreServlet {

	@Override
	public void doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String path = request.getContextPath().replace("/", "");
		String uri = request.getRequestURL().toString();
		String merchant = null;
		if(uri.contains("/merchant/")){
			String[] parts = StringUtil.split(uri, "/");
			merchant = parts[parts.length-1];
		}
		EXSearchEngineApplication app = (EXSearchEngineApplication)request.getSession().getAttribute("searchengine");
		if(app != null){
			app.getDescendentOfType(EXMall.class).showMerchantCard(merchant);
		}
		
		String page = "searchengine.jsp";
		if(path.equals("mall")){
			page = "index.jsp";
		}
		response.sendRedirect("/" + path + "/"+page+"?m=" + merchant);
		
	}
	
	

}
