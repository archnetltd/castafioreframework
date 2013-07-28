package org.castafiore.designer.projects;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.designable.portal.PageContainer;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.EXConfigVerticalBar;
import org.castafiore.designer.marshalling.DesignableDTO;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Restrictions;

public class MyProjectsModel implements TableModel, CellRenderer, Event{
	private String[] labels = new String[]{"Name", "Status", "Preview Link", "Actions"};
	
	private List<BinaryFile> projects = new ArrayList<BinaryFile>();

	
	
	
	public MyProjectsModel() {
		super();
		QueryParameters params = new QueryParameters().setEntity(BinaryFile.class).addSearchDir("/root/users/" + Util.getLoggedOrganization());
		params.addRestriction(Restrictions.like("name", "%.ptl"));
		
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		projects = result;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return labels.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return labels[index];
	}

	@Override
	public int getRowCount() {
		return projects.size();
	}

	@Override
	public int getRowsPerPage() {
		return 20;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int rrow = row + (getRowsPerPage()*page);
		return projects.get(rrow);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		BinaryFile bf = (BinaryFile)model.getValueAt(column, row, page); 
		if(column == 0){
			return new EXLabel("name", bf.getName());
		}else if(column == 2){
			Application app = table.getRoot();
			String contextPath = app.getContextPath();
			String serverPort = app.getServerPort();
			String servaerName = app.getServerName();
			
			String path =bf.getAbsolutePath();
			String url ="http://" + servaerName + ":" + serverPort  + contextPath + "/portal.jsp?portal=" + path;
			return new EXContainer("link", "a").setAttribute("href", url).setAttribute("target", "_blank").setText("Preview");
		}else if(column == 1){
			if(bf.getStatus() == File.STATE_PUBLISHED){
				return new EXContainer("status", "span").setText("Published");
			}else
				return new EXContainer("status", "span").setText("Draft");
		}else{
			Container acts = new EXContainer("", "div");
			if(!(bf.getStatus() == File.STATE_PUBLISHED)){
				acts.addChild(new EXContainer("publish", "a").setStyle("margin", "6px").setAttribute("href", "#u").addEvent(this, CLICK).setAttribute("path", bf.getAbsolutePath()).setText("<img src=icons-2/fugue/icons/globe.png></img>").setAttribute("title", "Publish this project"));
			}else
				acts.addChild( new EXContainer("unpublish", "a").setStyle("margin", "6px").setAttribute("href", "#u").addEvent(this, CLICK).setAttribute("path", bf.getAbsolutePath()).setText("<img src=icons-2/fugue/icons/exclamation-red.png></img>").setAttribute("title", "Un Publish this project"));
			
			acts.addChild( new EXContainer("open", "a").setStyle("margin", "6px").setAttribute("href", "#u").addEvent(this, CLICK).setAttribute("path", bf.getAbsolutePath()).setText("<img src=icons/pencil.png></img>").setAttribute("title", "Edit this project"));
			return acts;
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("publish")){
			String path = container.getAttribute("path");
			BinaryFile bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
			bf.setStatus(File.STATE_PUBLISHED);
			container.getAncestorOfType(EXTable.class).setModel(new MyProjectsModel());
			return true;
		}else if(container.getName().equalsIgnoreCase("unpublish")){
			String path = container.getAttribute("path");
			BinaryFile bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
			bf.setStatus(File.STATE_DRAFT);
			container.getAncestorOfType(EXTable.class).setModel(new MyProjectsModel());
			return true;
		}else if(container.getName().equalsIgnoreCase("delete")){
			String path = container.getAttribute("path");
			BinaryFile bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
			bf.setStatus(File.STATE_DELETED);
			container.getAncestorOfType(EXTable.class).setModel(new MyProjectsModel());
			return true;
		}else if(container.getName().equalsIgnoreCase("open")){
			EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
			
			try{
				String path = container.getAttribute("path");
				BinaryFile bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
				
				String name = bf.getName();
				String dir = bf.getParent().getAbsolutePath();
				designer.open(dir, name);
				
				
				
				
				PageContainer pc = designer.getRootLayout().getDescendentOfType(PageContainer.class);
				
				if(pc != null){
					BinaryFile home = (BinaryFile)((Directory)bf.getFile("pages")).getFile("home");
					
					InputStream in = home.getInputStream();
					
					Container page = DesignableDTO.buildContainer(in, true);
					page.setAttribute("pagepath", home.getAbsolutePath());
					pc.setPage(page);
					designer.getDescendentOfType(EXConfigVerticalBar.class).getNode(pc).refresh();
					try{
						designer.getDescendentByName(page.getAttribute("__oid") + "_c-item").getParent().setStyle("border", "double steelBlue");
					}catch(Exception e){
						
					}
				}
				container.getAncestorOfType(EXPanel.class).remove();
				
				
				
			}catch( ClassCastException ce){
				throw new UIException("Unknown format. Cannot open this file");
			}
			catch(Exception e){
				throw new UIException("Unknown format. Cannot open this file",e);
			}
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
