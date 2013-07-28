package org.castafiore.designer.marshalling;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.scripting.TemplateComponent;

public class ExpressionEvaluatorUtil {
	
	
	
	/**
	 * replaces all formula by evaluated value
	 * @param c
	 * @throws EvaluationException
	 */
	public static void evaluate(Container c)throws EvaluationException{
		Evaluator eval = new Evaluator();
		eval.setVariableResolver(new CastaStudioVariableResolver(c));
		if(!(c instanceof TemplateComponent) && c.getText() != null && c.getText().startsWith("=")){
			String s = eval.evaluate(c.getText(false).substring(1));
			s = s.substring(1,s.length()-1);
			c.setText(s);
		}
		
		for(String s : c.getAttributeNames()){
			String attr = c.getAttribute(s);
			if(attr != null && attr.startsWith("=")){
				String ss = eval.evaluate(attr.substring(1));
				ss = ss.substring(1,ss.length()-1);
				c.setAttribute(s, ss);
			}
		}
		
		for(String s : c.getStyleNames()){
			String attr = c.getStyle(s);
			if(attr != null && attr.startsWith("=")){
				String ss = eval.evaluate(attr.substring(1));
				ss = ss.substring(1,ss.length()-1);
				c.setStyle(s, ss);
			}
		}
		
		if(c instanceof StatefullComponent){
			String val = ((StatefullComponent)c).getRawValue();
			if(val != null && val.startsWith("=")){
				String s =eval.evaluate(val.substring(1));
				s = s.substring(1,s.length()-1);
				((StatefullComponent)c).setRawValue(s);
			}
		}
	}

}
