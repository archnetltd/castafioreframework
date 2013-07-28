package org.castafiore.designer;

import java.util.HashMap;
import java.util.Map;

import org.castafiore.designer.config.ConfigForm;
import org.castafiore.ecm.ui.finder.EXFinder;
import org.castafiore.ecm.ui.finder.EXFinder.SelectFileHandler;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.springframework.expression.spel.ast.Selection;

public class EXTextFileEditor extends EXPanel implements Event, ConfigForm, SelectFileHandler{

	
	private Container selectedElem ;
	
	private String datasource = null;
	public EXTextFileEditor(String name) {
		super(name);
		setTitle("Text editor -");
		
		setBody(new EXContainer("bb", "div"));
		setShowFooter(true);
		addToolbar();
		setStyle("-moz-transition", "all 1000ms ease-in-out 0s");
		//this.selectedElem = selectedElem;
	}
	
	public void addToolbar(){
		EXToolBar tb = new EXToolBar("tb");
		
		EXIconButton simple = new EXIconButton("simpl", Icons.ICON_NOTE);
		simple.setAttribute("title", "Edit with simple text editor");
		simple.setAttribute("method", "switchToSimple").setAttribute("ancestor", getClass().getName()).addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		
		EXIconButton complex = new EXIconButton("complex", Icons.ICON_DOCUMENT_B);
		complex.setAttribute("title", "Edit with rich text area");
		complex.setAttribute("method", "switchToComplex").setAttribute("ancestor", getClass().getName()).addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		
		EXIconButton preview = new EXIconButton("saveAs", Icons.ICON_DISK);
		preview.setAttribute("title", "Save As").addEvent(this, CLICK);
		
		EXButtonSet set = new EXButtonSet("set1");
		set.addItem(simple);
		set.addItem(complex);
		set.addItem(preview);
		set.setTouching(true);
		tb.addItem(set);
		getBody().addChild(tb);
		tb.removeClass("ui-corner-all");
		setWidth(Dimension.parse("500px"));
		
		EXButton save = new EXButton("save", "Apply");
		save.addEvent(this, Event.CLICK);
		

		
		EXButton cancel = new EXButton("cancel", "Cancel");
		cancel.addEvent(CLOSE_EVENT, Event.CLICK);
		getFooterContainer().addChild(save);
		getFooterContainer().addChild(cancel);
		
		
		
		EXTextArea area = new EXTextArea("textarea");
		area.setWidth(Dimension.parse("462px")).setStyle("margin", "0");
		Container editArea = new EXContainer("editArea", "div").addChild(area);
		getBody().addChild(editArea);
		
		
		
		
	}
	
	
	public void switchToComplex(Container caller){
		if(getDescendentOfType(EXRichTextArea.class) != null){
			return;
		}
		String text = ((EXTextArea)getDescendentByName("textarea")).getValue().toString();
		getDescendentOfType(EXTextArea.class).remove();
		EXRichTextArea rta = new EXRichTextArea("rta");
		rta.setWidth(Dimension.parse("700px")).setStyle("margin", "0");
		rta.setValue(text);
		getDescendentByName("editArea").addChild(rta);
		setStyle("width", "750px");
	}
	
	public void switchToSimple(Container caller){
		if(getDescendentByName("textarea") != null){
			return;
		}
		String text = getDescendentOfType(EXRichTextArea.class).getValue().toString();
		getDescendentOfType(EXRichTextArea.class).remove();
		EXTextArea rta = new EXTextArea("textarea");
		rta.setWidth(Dimension.parse("466px")).setStyle("margin", "0");
		rta.setValue(text);
		getDescendentByName("editArea").addChild(rta);
		setStyle("width", "504px");
	}
	
	public void saveAs(Container caller){
		
		String text = getDescendentOfType(EXTextArea.class).getValue().toString();
		
		String datasource = selectedElem.getAttribute("datasource");
		
		if(StringUtil.isNotEmpty(datasource) && datasource.startsWith("ecm:")){
			String file = datasource.replace("ecm:", "");
			BinaryFile bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(file, Util.getRemoteUser());
			try{
				bf.write(text.getBytes());
			}catch(Exception e){
				throw new UIException(e);
			}
			
			applyConfigs();
			
		}else{
			EXFinder finder = new EXFinder("finder", new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					if(pathname.getClazz().equalsIgnoreCase(Directory.class.getName())){
						return true;
					}else{
						return false;
					}
				}
			}, this, "/root/users/" + Util.getLoggedOrganization());
			
			
			finder.addInput("Save as :");
			
			getAncestorOfType(PopupContainer.class).addPopup(finder);
		}
	}

	@Override
	public void onSelectFile(File file, EXFinder finder) {
		String name = finder.getInputValue();
		String text = getDescendentOfType(EXTextArea.class).getValue().toString();
		Directory dir = (Directory)file;
		BinaryFile bf = dir.createFile(name, BinaryFile.class);
		//selectedElem.setAttribute("datasource", "ecm:" + bf.getAbsolutePath());
		datasource = "ecm:" + bf.getAbsolutePath();
		
		try{
			bf.write(text.getBytes());
			dir.save();
		}catch(Exception e){
			throw new UIException(e);
		}
		
		//ComponentUtil.applyAttributes(selectedElem, m);
		
		applyConfigs();
		
		
		finder.remove();
		
	}
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("saveAs")){
			saveAs(container);
			return true;
		}
		this.applyConfigs();
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void applyConfigs() {
		Map<String, String> map = new HashMap<String, String>(1);
		String text = getDescendentOfType(EXTextArea.class).getValue().toString();
		if(StringUtil.isNotEmpty(datasource)){
			map.put("datasource", datasource);
			//String datasource = selectedElem.getAttribute("datasource");
			if(datasource.startsWith("ecm:")){
				String file = datasource.replace("ecm:", "");
				BinaryFile bf = (BinaryFile)SpringUtil.getRepositoryService().getFile(file, Util.getRemoteUser());
				try{
					bf.write(text.getBytes());
				}catch(Exception e){
					throw new UIException(e);
				}
			}
		}else{
			map.put("text", text);
		}
		Studio.applyAttributes(selectedElem, map);
		
	}

	@Override
	public void setContainer(Container container) {
		this.selectedElem = container;
		getDescendentOfType(StatefullComponent.class).setValue(container.getText(false));
		
		if(StringUtil.isNotEmpty(container.getAttribute("datasource"))){
			datasource = container.getAttribute("datasource"); 
		}
		
	}
	
	

}
