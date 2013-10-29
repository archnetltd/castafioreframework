package org.castafiore.sample.integrations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.KeyValuePair;
import org.castafiore.ui.CastafioreController;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.ListOrderedMap;
import org.castafiore.utils.ResourceUtil;
import org.springframework.web.servlet.ModelAndView;

/**
 * This sample shows how easily the js component flexigrid could be used integrated in castafiore<br>
 * 
 * @author arossaye
 *
 */
public class EXFlexGrid extends EXContainer implements CastafioreController, Event{
	
	private JMap options = new JMap();
	
	private JArray columns = new JArray();
	
	private JArray buttons = new JArray();
	
	private FlexiGridListener listener;
	
	private FlexiGridDataSource dataSource;

	public EXFlexGrid(String name) {
		super(name, "div");
		options.put("dataType", "xml");
		String url = ResourceUtil.getMethodUrl(this);
		options.put("url", url);
		options.put("singleSelect", true);
		addEvent(this, MISC);
	}
	
	
	public FlexiGridDataSource getDataSource() {
		return dataSource;
	}


	public void setDataSource(FlexiGridDataSource dataSource) {
		setRendered(false);
		this.dataSource = dataSource;
	}


	public FlexiGridListener getListener() {
		return listener;
	}


	public void setListener(FlexiGridListener listener) {
		this.listener = listener;
	}


	public EXFlexGrid addColumn(String name, String display, int width, boolean sortable, String align){
		JMap col = new JMap();
		col.put("name", name).put("display", display).put("width", width).put("sortable", sortable).put("align", align);
		columns.add(col);
		return this;
	}
	
	public EXFlexGrid addButton(String name, String cssClass){
		JMap btn = new JMap();
		btn.put("name", name).put("bclass", cssClass).put("onpress", createEvent(name), "name", "grid");
		buttons.add(btn);
		return this;
	}
	
	
	private ClientProxy createEvent(String name){
		ClientProxy proxy = new ClientProxy(this,new ListOrderedMap<String, List<KeyValuePair>>());
		JMap params = new JMap();
		params.put("selected", new Var("$('.trSelected', grid).attr('id')"));
		params.put("name", new Var("name"));
		proxy.makeServerRequest(params,this);
		return proxy;
		
	}
	
	public EXFlexGrid setTitle(String title){
		options.put("title", title);
		return this;
	}
	
	public EXFlexGrid setDefaultSort(String name, String dir){
		options.put("sortname", name).put("sortorder", dir);
		return this;
	}
	
	public EXFlexGrid setUsePager(boolean b){
		options.put("usepager", b);
		return this;
	}
	
	public EXFlexGrid setUseRp(int rp){
		options.put("useRp", true);
		options.put("rp", rp);
		return this;
	}
	public EXFlexGrid setShowTableToggleBtn(boolean b){
		options.put("showTableToggleBtn", b);
		return this;
	}
	
	public EXFlexGrid setWidth(int w){
		options.put("width", w);
		return this;
	}
	
	public EXFlexGrid setHeight(int h){
		options.put("height", h);
		return this;
	}

	

	@Override
	public void onReady(ClientProxy proxy) {
		super.onReady(proxy);
		options.put("colModel", columns);
		options.put("buttons", buttons);
		
		proxy.getCSS("http://flexigrid.info/css/flexigrid.pack.css");
		proxy.getScript(ResourceUtil.getDownloadURL("classpath", "org/castafiore/sample/integrations/flexigrid.pack.js"), proxy.clone().addMethod("flexigrid", options));
		
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest r,
			HttpServletResponse response) throws Exception {
		if(dataSource != null){
			String page = r.getParameter("page");
			String rp = r.getParameter("rp");
			String sortname = r.getParameter("sortname");
			String sortorder = r.getParameter("sortorder");
			String query = r.getParameter("qtype");
			List<FlexiGridDataRow> rows = dataSource.getPage(Integer.parseInt(page), Integer.parseInt(rp), sortname, sortorder, query);
			StringBuilder b = new StringBuilder();
			b.append("<rows>").append("<page>").append(page).append("</page>").append("<total>").append(dataSource.getSize()).append("</total>");
			for(FlexiGridDataRow row : rows){
				b.append("<row ").append("id=\"").append(row.getId()).append("\">");
				for(String key : row.keySet()){
					String value = row.get(key);
					b.append("<cell>").append(value).append("</cell>");
				}
				b.append("</row>");
			}
			b.append("</rows>");
			response.getWriter().write(b.toString());
		}
		
		return null;
	}


	
	public ModelAndView handleRequest_(HttpServletRequest r,
			HttpServletResponse response) throws Exception {
		String page = r.getParameter("page");
		String request = "http://flexigrid.info/post-xml.php";
		String rp = r.getParameter("rp");
		String sortname = r.getParameter("sortname");
		String sortorder = r.getParameter("sortorder");
		
		String urlParameters ="page=" + page + "&rp=" + rp + "&sortname=" + sortname + "&sortorder=" + sortorder  + "&qtype=name&query=";
		URL url = new URL(request); 
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setInstanceFollowRedirects(false); 
		connection.setRequestMethod("POST"); 
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
		connection.setUseCaches (false);

		
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

		writer.write(urlParameters);
		writer.flush();

		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		Writer w = response.getWriter();
		while ((line = reader.readLine()) != null) {
		    w.append(line);
		}
		writer.close();
		reader.close();         
		return null;
	}


	@Override
	public void ClientAction(ClientProxy container) {
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(listener != null){
			String name = request.get("name");
			String ids = request.get("selected");
			listener.execute(name, ids, this);
		}
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
	}
	
	

}
