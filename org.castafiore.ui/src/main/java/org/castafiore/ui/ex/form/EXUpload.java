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

package org.castafiore.ui.ex.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.resource.FileData;
import org.castafiore.ui.FormComponent;
import org.castafiore.ui.dynaform.InputVerifier;
import org.castafiore.ui.template.Compiler;
import org.castafiore.ui.template.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class EXUpload extends EXXHTMLFragment implements
		FormComponent<List<FileData>>, Compiler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<FileData> items = new ArrayList<FileData>();

	public EXUpload(String name) {
		super(name, ResourceUtil.getDownloadURL("classpath",
				"org/castafiore/resources/upload/EXUpload.groovy"));

	}

	public String compile(String template, Map<String, Object> context)
			throws Exception {
		return template.trim()
				.replace("${component.applicationId}", getRoot().getId())
				.replace("${component.id}", getId());
	}

	public Compiler getCompiler() {
		return this;
	}

	public String getApplicationId() {
		return getRoot().getId();
	}

	public FileData getFile() {
		if (items.size() == 0)
			return null;
		return items.get(0);
	}

	// value is set in castafioreServlet
	public void setFile(FileData item) {
		items.clear();
		this.items.add(item);
	}

	public void addFile(FileData item) {
		this.items.add(item);
	}

	public List<FileData> getValue() {
		return items;
	}

	public void setValue(List<FileData> value) {
		items = value;
	}

	@Override
	public FormComponent<List<FileData>> setInputVerifier(InputVerifier verifier) {
		return this;
	}

	@Override
	public InputVerifier getInputVerifier() {
		return null;
	}

}
