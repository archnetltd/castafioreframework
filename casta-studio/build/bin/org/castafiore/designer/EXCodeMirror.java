package org.castafiore.designer;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import org.castafiore.designer.codeassist.Data;
import org.castafiore.designer.script.ScriptService;
import org.castafiore.designer.script.ScriptServiceImpl;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.ResourceUtil;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.json.JSONArray;
import org.json.JSONObject;

public class EXCodeMirror extends EXTextArea {

	public EXCodeMirror(String name, String value, String mode) {
		super(name, value);
		setAttribute("mode", mode);
	}

	@Override
	public void onReady(ClientProxy proxy) {
		super.onReady(proxy);

		ClientProxy callback = proxy.clone();
		callback.appendJSFragment("CodeMirror.modeURL = \"codemirror-2.34/mode/%N/%N.js\";");
		callback.appendJSFragment("CodeMirror.autoc = \""+ ResourceUtil.getMethodUrl(this, "getCompletion", "data",	"ploplo") + "\";");
		callback.executeFunction("var editor = CodeMirror.fromTextArea",new Var("document.getElementById(\"" + proxy.getId() + "\")"),new JMap().put("lineNumbers", true).put("mode",	getAttribute("mode")).put("extraKeys",new JMap().put("Ctrl-Space", "autocomplete")).put("onChange", new Var("function(){editor.save();}")));
		callback.appendJSFragment("CodeMirror.commands.autocomplete = function(cm) {CodeMirror.simpleHint(cm, CodeMirror.javascriptHint);}");

		callback.appendJSFragment("CodeMirror.autoLoadMode(editor, '"+ getAttribute("mode") + "');");
		
		proxy.getCSS("codemirror-2.34/lib/codemirror.css");

		proxy.getCSS("codemirror-2.34/lib/util/simple-hint.css");

		proxy.getScript("codemirror-2.34/lib/codemirror.js",proxy.clone().getScript("codemirror-2.34/lib/util/loadmode.js",proxy.clone().getScript("codemirror-2.34/lib/util/simple-hint.js",proxy.clone().getScript("codemirror-2.34/lib/util/javascript-hint.js",callback))));
 
	}
	
	
	
	
	
//	public JArray getAllGlobalVariables(BlockStatement block){
//		block.
//	}
//	
//	public JArray getInstantiableClasses(BlockStatement block){
//		//
//	}
//	
//	public JArray getMethods(Class clazz){
//		
//	}

	public JArray getCompletion(String param) {

		String[] variables = new String[]{"me", "repository", "studio", "kernel",  "root", "component"};
		JArray array = new JArray();
		try{
		
		//JSONArray obj = new JSONArray(param);
			
			JSONObject obj = new JSONObject(param);

		
		try {
			
			
			
			Data data = new Data(obj);
			
			
			
			
			
		} catch (Exception e) {
		
			
			for(String v : variables){
				array.add(v.toString());
			}
		}
		}catch(Exception e){
			
		}
		return array;
	}

	public static JArray getClasses(String pckgname)
			throws ClassNotFoundException {

		// ArrayList classes=new ArrayList();
		JArray array = new JArray();
		// Get a File object for the package
		File directory = null;
		try {
			directory = new File(Thread.currentThread().getContextClassLoader()
					.getResource('/' + pckgname.replace('.', '/')).getFile());
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(pckgname
					+ " does not  appear to be a valid package");
		}

		if (directory.exists()) {
			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				// we are only interested in .class files
				if (files[i].endsWith(".class")) {
					// removes the .class extension
					array.add(files[i]);
				}
			}
		} else {
			throw new ClassNotFoundException(pckgname
					+ " does not  appear to be a valid package");
		}

		return array;
	}

}
