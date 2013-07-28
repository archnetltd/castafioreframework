package org.castafiore.site.wizard;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import jodd.bean.BeanUtil;

import org.castafiore.site.CollectedUserDate;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.springframework.beans.BeanUtils;

public class UserInfo extends EXXHTMLFragment{

	public UserInfo() {
		super("UserInfo", "youdo-templates/pages/UserInfo.xhtml");
		addClass("UserInfo");
		initForm();
	}
	
	
	
	public void initForm(){
		Class<?> beanType = getBeanType();
		
		PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(beanType);
		
		List<String> ex = getExcludes();
		
		for(PropertyDescriptor descri : descriptors){
			System.out.println(descri.getName());
			if(!ex.contains(descri.getName()) ){
				String name = descri.getName();
				if(descri.getName().equalsIgnoreCase("password")){
					addChild(new EXPassword("password"));
				}
				else if(name.equalsIgnoreCase("description")){
					addChild(new EXTextArea("description"));
				}else{
					addChild(new EXInput(descri.getName()));
				}
			}
			//descri.getName()
		}
	}
	
	public Class<?> getBeanType(){
		return CollectedUserDate.class;
	}
	
	
	
	public List<String> getExcludes(){
		List<String> excludes = new ArrayList<String>();
		excludes.add("layout");
		excludes.add("menu");
		excludes.add("banner");
		excludes.add("class");
		excludes.add("description");
		excludes.add("businessAddressLine1");
		excludes.add("businessAddressLine2");
		excludes.add("description");
		excludes.add("businessRegistrationNumber");
		
		return excludes;
	}

	
	
	
	public void fillUserData(CollectedUserDate data){
		for(Container c : getChildren()){
			
			if(c instanceof StatefullComponent){
				StatefullComponent input = (StatefullComponent)c;
				
				Object value = input.getValue();
				
				BeanUtil.setProperty(data, input.getName(), value);
			}
		}
		System.out.println("sdsdssdsds");
	}

}
