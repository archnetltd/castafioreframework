package org.castafiore.searchengine;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.wfs.types.BinaryFile;

public class EXProductImporter extends EXContainer implements Event{

	public EXProductImporter(String name) {
		super(name, "div");
		addChild(new EXInput("merchant"));
		addChild(new EXInput("category"));
		addChild(new EXInput("subcategory"));
		
		addChild(new EXUpload("proper"));
		addChild(new EXContainer("button", "button").setText("import").addEvent(this, Event.CLICK));
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			BinaryFile file = (BinaryFile)getDescendentOfType(EXUpload.class).getFile();
			String category = ((EXInput)getChild("category")).getValue().toString();
			String subcategory =((EXInput)getChild("subcategory")).getValue().toString();
			String merchant = ((EXInput)getChild("merchant")).getValue().toString();
			InputStream in = file.getInputStream();
			
			Properties prop = new Properties();
			prop.load(in);
			//MallUtil.doImport(prop,merchant, category, subcategory);
		}catch(Exception e){
			throw new UIException(e);
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	

}
