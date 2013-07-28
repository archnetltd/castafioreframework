/*
 * Copyright (C) 2007-2010 Castafiore
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
 package org.castafiore.ecm.ui.fileexplorer;

import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.wfs.types.File;


public class DefaultFileEditorFactory implements FileEditorFactory  {
	
	public Container getFileEditor(File file, Explorer requester, int mode) {
		Map<String, String> editorConfig = SpringUtil.getBean("fileEditorConfigMap");
		String clazz = file.getClazz();
		try
		{
			if(editorConfig.containsKey(clazz))
			{
				FileEditor editor = (FileEditor)Thread.currentThread().getContextClassLoader().loadClass(editorConfig.get(clazz)).newInstance();
				editor.initialiseEditor(file,requester.getCurrentAddress(), false);
				return editor;
			}
			else{
				return null;
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException("the bean " + "openner_" + clazz + " cannot be found in the Spring container context or it does not implement the interface Openner. It is required to open a file of type " + clazz,e);
		}	
	}
}
