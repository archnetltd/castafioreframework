package org.castafiore.ecm.ui.fileexplorer.views.article;

import org.castafiore.ecm.ui.fileexplorer.FileEditor;
import org.castafiore.ecm.ui.query.EXMultiFileSelector;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXMultiInput;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class EXArticleEditor extends EXTabPanel implements TabModel, FileEditor, ComponentVisitor{

	private String[] labels = new String[]{"Basic Info", "Detail", "Attachements", "References"};
	
	private Article me ;
	
	private boolean New;
	
	private String directory;
	
	public EXArticleEditor() {
		super("EXArticleEditor");
		setModel(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getSelectedTab() {
		
		return 0;
	}

	@Override
	public Container getTabContentAt(TabPanel pane, int index) {
		if(index == 0){
			 
			EXFieldSet fs = new EXFieldSet("main", "Main Info", false);
			fs.addField("Title :", new EXInput("title"));
			fs.addField("Summary : ",new EXRichTextArea("summary"));
			
			return fs;
		}else if(index ==1){
			EXFieldSet fs = new EXFieldSet("details", "Detail", false);
			fs.addField("Detail : ", new EXRichTextArea("detail"));
			if(me != null)
			fs.getDescendentOfType(EXRichTextArea.class).setValue(me.getDetail());
			return fs;
		}else if(index == 3){
			Container c = new EXContainer("attachements", "div");
			EXReferenceEditor mu = new EXReferenceEditor("mu");
			mu.init(me, New);
			c.addChild(mu);
			c.setStyle("min-height", "400px");
			return c;
		}else {
			Container c = new EXContainer("attachements", "div");
			EXAttachementEditor mu = new EXAttachementEditor("mu");
			mu.init(me, New);
			c.addChild(mu);
			c.setStyle("min-height", "400px");
			return c;
		}
	}

	@Override
	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return labels[index];
	}

	@Override
	public int size() {
		return labels.length;
	}

	@Override
	public void initialiseEditor(File file, String directory, boolean isNew) {
		this.me = (Article)file;
		this.New = isNew;
		((StatefullComponent)getDescendentByName("title")).setValue(me!=null?me.getTitle() : "");
		((StatefullComponent)getDescendentByName("summary")).setValue(me!=null?me.getSummary() : "");
		this.directory = directory;
		
	}

	@Override
	public File save(String name) {
		if(New){
			Directory parent = SpringUtil.getRepositoryService().getDirectory(directory, Util.getRemoteUser());
			me = parent.createFile(name, Article.class);
			
		}else{
			me = (Article)SpringUtil.getRepositoryService().getFile(me.getAbsolutePath(), Util.getRemoteUser());
		}
		
		ComponentUtil.iterateOverDescendentsOfType(this, Container.class, this);
		
		New = false;
		me.save();
		return me;
	}

	@Override
	public void doVisit(Container c) {
		if(c instanceof EXFieldSet && c.getAncestorOfType(ArticleEditorTab.class) == null){
			EXFieldSet fs = (EXFieldSet)c;
			if(c.getName().equalsIgnoreCase("main")){
				me.setTitle(fs.getField("title").getValue().toString());
				me.setSummary(fs.getField("summary").getValue().toString());
			}else if(c.getName().equalsIgnoreCase("details")){
				me.setDetail(fs.getField("detail").getValue().toString());
			}
		}else if(c instanceof ArticleEditorTab){
			((ArticleEditorTab)c).save(me, New);
		}
		
	}

	

}
