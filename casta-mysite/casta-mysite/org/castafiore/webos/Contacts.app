import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.EXContainer;


EXPanel panel = new EXPanel("undercontruction", "Under Contruction");	
panel.setBody(new EXContainer("", "div").setStyle("text-align", "center"));
panel.getBody().addChild(new EXContainer("", "h2").setText("This module is still under contruction and will be available soon"));
root.addChild(panel);