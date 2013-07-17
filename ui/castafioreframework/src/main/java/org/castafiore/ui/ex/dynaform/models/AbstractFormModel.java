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

package org.castafiore.ui.ex.dynaform.models;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.button.EXButton;
import org.castafiore.ui.ex.dynaform.DynaForm;

import org.castafiore.ui.ex.dynaform.FormModel;
import org.castafiore.ui.ex.dynaform.annotations.Field;
import org.castafiore.ui.ex.dynaform.annotations.ValidatorConfig;
import org.castafiore.ui.ex.dynaform.validator.Validator;
import org.castafiore.ui.ex.list.DataModel;
import org.castafiore.utils.ExceptionUtil;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public abstract class AbstractFormModel<T> implements FormModel {
	
	private T bean_;
	
	private Class<T> classType_;
	
	
	
	public List<Field> fields = new ArrayList<Field>();
	
	public List<Method> methods = new ArrayList<Method>();
	
	
	public AbstractFormModel(T bean, Class<T> classType)throws Exception
	{
		this.bean_ = bean;
		this.classType_ = classType;
		loadFields();
		
		if(bean_ == null)
		{
			bean_ = classType.newInstance();
		}
	}
	
	public String validate(StatefullComponent component, Field field)throws Exception
	{
		ValidatorConfig[] valConfigs = field.validator();
		if(valConfigs.length > 0)
		{
			ValidatorConfig config = valConfigs[0];
			Validator validator  = (Validator)config.validator().newInstance();
			boolean isValid = validator.validate(component);
			
			if(isValid)
			{
				return null;
			}
			else
			{
				return config.message();
			}
		}
		
		return null;
	}
	 
	private void loadFields()
	{
		Method[] methods = classType_.getMethods();
		List<Field> tmp = new ArrayList<Field>();
		List<Method> tmpMethods = new ArrayList<Method>();
		for(Method m : methods)
		{
			Field f = m.getAnnotation(Field.class);
			if(f != null)
			{
				int position = f.position();
				
				while(position >= tmp.size())
				{
					tmp.add(null);
					tmpMethods.add(null);
				}
				tmp.add(position,f);
				tmpMethods.add(position,m);	
			} 
		}
		
		for(Field f : tmp)
		{
			if(f != null)
			{
				fields.add(f);
			}
		
		}
		for(Method m : tmpMethods)
		{
			if(m != null)
			{
				this.methods.add(m);
			}
		}
	} 

	public T getBean() {
		return bean_;
	}

	

	public Class<T> getClassType() {
		return classType_;
	}

	public void setClassType(Class<T> classType_) {
		this.classType_ = classType_;
	}

	public abstract int actionSize() ;

	public abstract EXButton getActionAt(int index, DynaForm form);
		

	private StatefullComponent buildComponent(Field fieldAnnotation)throws Exception
	{
		
		Class inputType = fieldAnnotation.fieldType();
		
		String name = fieldAnnotation.name();
		
		Class listproviderClass = fieldAnnotation.listProvider();
		
		boolean readonly = fieldAnnotation.readonly();
		
		Class[] argumentsTypes = null;
		Object[] params = null;
		
		//no list provider, so, constructor with name only
		if(listproviderClass.equals(void.class))
		{
			argumentsTypes = new Class[]{String.class};
			
			params = new Object[]{name};
		}
		else
		{
			ListProvider lProvider = (ListProvider)listproviderClass.newInstance();
			final List list = lProvider.getList(this.classType_);
			
			DataModel model = new DataModel(){

				public int getSize() {
					return list.size();
				}

				public Object getValue(int index) {
					return list.get(index);
				}	
			};
			
			argumentsTypes = new Class[]{String.class, DataModel.class};
			
			params = new Object[]{name, model};
		}
		
		Constructor c = inputType.getConstructor(argumentsTypes);
		Object o = c.newInstance(params);
		StatefullComponent component = (StatefullComponent)o;
		
		if(readonly)
		{
			component.setAttribute("readonly", "readonly");
		}
		
		return component;
		
	}
	
	public StatefullComponent getFieldAt(int index, DynaForm form) {
		Class<T> inputType = null;
		try 
		{
			Field fieldAnnotation = fields.get(index);
			
			
			
 
			StatefullComponent component = buildComponent(fieldAnnotation);
			
			Method m = this.methods.get(index);
			
			Object value = null;
			
			if(bean_ != null)
			{
				if(m.getName().startsWith("get"))
				{
					value = m.invoke(bean_,null);
				}
				else if(m.getName().startsWith("is"))
				{
					value = m.invoke(bean_, null);
				}
				else if(m.getName().startsWith("set"))
				{
					if(m.getReturnType().equals(Boolean.class ))
					{
						 value = classType_.getMethod("is" + m.getName().substring(3), null).invoke(bean_, null);
					}
					else if(m.getReturnType().equals(boolean.class))
					{
						value = classType_.getMethod("is" + m.getName().substring(3), null).invoke(bean_, null);
					}
					else
					{
						value = classType_.getMethod("get" + m.getName().substring(3), null).invoke(bean_, null);
					}
					//setter
				}
			}
			
			if(value != null)
				component.setValue(value);

			return component;
		}
		catch(Exception e)
		{
			throw ExceptionUtil.getRuntimeException("cannot instantiate the fieldType of type:" + inputType , e);
		}
	}

	public String getLabelAt(int index, DynaForm form) {
		return fields.get(index).label();
	}

	public int size() {
		return fields.size();
	}

}
