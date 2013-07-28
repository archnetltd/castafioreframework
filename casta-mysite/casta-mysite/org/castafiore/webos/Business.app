
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.sms.ui.EXSMSModule;
EXPanel panel = new EXPanel("", "Configure SMS Module");
panel.setWidth(Dimension.parse("960px"));
EXSMSModule module = new EXSMSModule();
panel.setBody(module);
root.addChild(panel);