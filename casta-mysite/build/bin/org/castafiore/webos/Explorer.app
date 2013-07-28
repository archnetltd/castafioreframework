import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.Container;
try{
			EXPanel panel = new EXPanel("myExplorer", "File explorer");
			panel.setWidth(Dimension.parse("960px"));
			def container = SpringUtil.getBean("fileExplorer");
			panel.setBody(container);
			root.addChild(panel);
			ComponentUtil.metamorphoseExplorer(container);
			
		}catch(Exception e){
			e.printStackTrace();
		}