package org.castafiore.designer.portalmenu;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.ui.ex.form.list.DataModel;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class EXPagesDataModel  implements DataModel{
	
	private List<SimpleKeyValuePair> pagesIndented = new ArrayList<SimpleKeyValuePair>();
	
	private String portalAbsPath ;
	
	private String sdefault = null;

	public EXPagesDataModel(BinaryFile portal) {
		this.portalAbsPath = portal.getAbsolutePath();
		Directory pages = (Directory)portal.getFile("pages");
		doInit(pages, 0);
	}
	
	public EXPagesDataModel(BinaryFile portal, String sdefault) {
		this(portal);
		pagesIndented.add(0, new SimpleKeyValuePair(sdefault, sdefault));
		this.sdefault = sdefault;
	}
	
	public void setDefault(String sdefault){
		if(this.sdefault != null){
			pagesIndented.remove(0);
		}
		if(sdefault != null){
			pagesIndented.add(0, new SimpleKeyValuePair(sdefault, sdefault));
		}
	}
	
	protected void doInit(Directory page, int indentation){
		
		FileIterator fPages = page.getFiles(BinaryFile.class);
		while(fPages.hasNext()){
			BinaryFile bf = (BinaryFile)fPages.next();
			StringBuilder b = new StringBuilder();
			for(int i = 0; i < indentation; i++){
				b.append("&nbsp;");
			}
			SimpleKeyValuePair kv = new SimpleKeyValuePair(bf.getAbsolutePath(), b.append(bf.getName()).toString());
			pagesIndented.add(kv);
			doInit(bf,indentation + 3);
		}
	}

	@Override
	public int getSize() {
		return pagesIndented.size();
	}

	@Override
	public Object getValue(int index) {
		return pagesIndented.get(index);
	}

}
