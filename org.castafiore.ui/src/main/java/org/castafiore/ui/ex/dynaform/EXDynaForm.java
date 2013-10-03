/*
 * 
 */
package org.castafiore.ui.ex.dynaform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.FormComponent;
import org.castafiore.ui.button.Button;
import org.castafiore.ui.dynaform.DynaForm;
import org.castafiore.ui.dynaform.FormModel;
import org.castafiore.ui.dynaform.SubmitFormHandler;
import org.castafiore.ui.dynaform.validator.RequiredValidator;
import org.castafiore.ui.dynaform.validator.Validator;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.layout.EXVLayout;
import org.castafiore.utils.ComponentUtil;

public class EXDynaForm extends EXPanel implements DynaForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, List<Validator>> validators = new LinkedHashMap<String, List<Validator>>();
	
	private List<SubmitFormHandler> handlers = new LinkedList<SubmitFormHandler>();

	/**
	 * Creates a DynaForm base on an jquery ui EXPanel
	 * 
	 * @param name
	 *            The name of the form
	 * @param title
	 *            The title to display on header of form
	 */
	public EXDynaForm(String name, String title) {
		super(name, title);
		EXVLayout vlayout = new EXVLayout("errors");
		addChild(vlayout.setDisplay(false).addClass("ui-state-error"));
		Container fieldSet = ComponentUtil.getContainer("fieldSet", "table",null, "dynaform");
		fieldSet.setDisplay(false);
		addChildAt(vlayout,1);
		setBody(fieldSet);
	}

	/**
	 * Creates a DynaForm base on a jquery ui EXPanel. The content of the form
	 * is delegated to a formModel
	 * 
	 * @param name
	 *            The name of the form
	 * @param title
	 *            The title to display on the header of the form
	 * @param model
	 *            The model managing the content of the form
	 */
	public EXDynaForm(String name, String title, FormModel model) {
		this(name, title);

		int actSize = model.actionSize();

		for (int i = 0; i < actSize; i++) {
			addButton(model.getActionAt(i, this));
		}

		int fieldSize = model.size();
		for (int i = 0; i < fieldSize; i++) {
			addField(model.getLabelAt(i, this), model.getFieldAt(i, this));
		}
	}

	/**
	 * @see DynaForm#addButton
	 */
	public DynaForm addButton(Button button) {
		setShowFooter(true);
		getFooterContainer().addChild(button);
		return this;
	}

	/**
	 * @see DynaForm#addField(String, FormComponent)
	 */
	public DynaForm addField(String label, FormComponent<?> input) {

		if (input instanceof EXFieldSet) {

			Container tr = ComponentUtil.getContainer("tr", "tr", null,"dynaformRow");

			getBody().addChild(tr);

			Container td = ComponentUtil.getContainer("td", "td", null,"dynaformInput");
			td.setAttribute("colspan", "2");
			td.addChild(input);
			tr.addChild(td);
		} else {
			Container uiLabel = ComponentUtil.getContainer(	"label_" + input.getId(), "td", label, "dynaformLabel");
			Container tr = ComponentUtil.getContainer("tr", "tr", null,	"dynaformRow");
			tr.addChild(uiLabel);
			getBody().addChild(tr);

			Container td = ComponentUtil.getContainer("td", "td", null,	"dynaformInput");
			td.addChild(input);
			tr.addChild(td);
		}
		return this;
	}

	/**
	 * allow to add an arbitrary component to this form
	 * @param input The component to add
	 * @return this form
	 */
	public DynaForm addOtherItem(Container input) {

		Container tr = ComponentUtil.getContainer("tr", "tr", null,	"dynaformRow");

		getBody().addChild(tr);

		Container td = ComponentUtil.getContainer("td", "td", null,	"dynaformInput");
		td.setAttribute("colspan", "2");
		td.addChild(input);
		tr.addChild(td);

		return this;
	}

	/**
	 * hides a specified field
	 * @param name The name of the field to hide
	 * @return This form
	 */
	public DynaForm hideField(String name) {
		getField(name).getParent().getParent().setDisplay(false);
		return this;
	}

	/**
	 * Show a specified field
	 * @param name The name of the field to show
	 * @return This form
	 */
	public DynaForm showField(String name) {
		getField(name).getParent().getParent().setDisplay(true);
		return this;
	}

	
	/**
	 * @see DynaForm#setLabelFor(String, FormComponent)
	 */
	public void setLabelFor(String label, FormComponent<?> input) {
		Container uiLabel = getDescendentByName("label_" + input.getId());
		if (uiLabel != null) {
			uiLabel.setText(label);
		}
	}
	
	public String getLabel(String fieldName){
		String fieldId =getField(fieldName).getId();
		
		Container uiLabel = getDescendentByName("label_" + fieldId);
		if (uiLabel != null) {
			return uiLabel.getText(false);
		}
		return "";
	}

	/**
	 * @see DynaForm#getFieldsMap()
	 */
	public Map<String, FormComponent<?>> getFieldsMap() {
		Map<String, FormComponent<?>> result = new HashMap<String, FormComponent<?>>();
		List<Container> children = getBody().getChildren();
		for (Container c : children) {
			FormComponent<?> stf = c.getDescendentOfType(FormComponent.class);
			if (stf != null) {
				result.put(stf.getName(), stf);
			}
		}
		return result;
	}

	/**
	 * @see DynaForm#getField(String)
	 */
	public FormComponent<?> getField(String name) {
		return getFieldsMap().get(name);
	}

	/**
	 * @see DynaForm#getFields()
	 */
	public List<FormComponent<?>> getFields() {
		List<FormComponent<?>> result = new ArrayList<FormComponent<?>>();
		List<Container> children = getBody().getChildren();

		for (Container c : children) {
			FormComponent<?> stf = c.getDescendentOfType(FormComponent.class);
			if (stf != null) {
				result.add(stf);
			}
		}

		return result;
	}

	/**
	 * 
	 * @return A map of field names and values
	 */
	public Map<String, Object> getFieldValues() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Container> children = getBody().getChildren();
		for (Container c : children) {
			FormComponent<?> stf = c.getDescendentOfType(FormComponent.class);
			if (stf != null) {
				result.put(stf.getName(), stf.getValue());
			}
		}
		return result;
	}

	/**
	 * 
	 * @return a list of buttons in this form
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Button> getButtons() {
		Container footer = getFooterContainer();
		List result = new ArrayList();
		ComponentUtil.getDescendentsOfType(footer, result, Button.class);

		return result;
	}

	@Override
	public void onReady(ClientProxy proxy) {
		super.onReady(proxy);
		if (proxy.getDescendentByName("fieldSet") != null)
			proxy.getDescendentByName("fieldSet").fadeIn(100);
	}

	/**
	 * @see DynaForm#clearErrors()
	 */
	@Override
	public DynaForm clearErrors() {
		getChild("errors").setDisplay(false).getChildren().clear();
		getChild("errors").setRendered(false);
		return this;
	}

	/**
	 * @see DynaForm#reset()
	 */
	@Override
	public DynaForm reset() {
		for(FormComponent<?> f : getFields()){
			f.setValue(null);
			f.removeClass("ui-state-error");
		}
		clearErrors();
		return this;
	}

	/**
	 * @see DynaForm#addErrorMsg(String)
	 */
	@Override
	public DynaForm addErrorMsg(String msg) {
		Container label = new EXContainer("", "label").setStyle("margin", "4px 15px").setStyle("display", "block").setText(msg);
		getChild("errors").setDisplay(true).addChild(label);
		return this;
		
	}

	/**
	 * @see DynaForm#validate()
	 */
	@Override
	public boolean validate() {
		clearErrors();
		boolean result = true;
		for(FormComponent<?> field : getFields()){
			String name = field.getName();
			if(validators.containsKey(name)){
				for(Validator v : validators.get(name)){
					boolean valid =v.validate(field, this);
					if(!valid)
						result = false;
				}
			}
		}
		
		return result;
	}

	/**
	 * @see DynaForm#addValidator(String, Validator)
	 */
	@Override
	public DynaForm addValidator(String fieldName, Validator validator) {
		if(validators.containsKey(fieldName)){
			validators.get(fieldName).add(validator);
		}else{
			List<Validator> lValidators = new LinkedList<Validator>();
			lValidators.add(validator);
			validators.put(fieldName, lValidators);
		}
		
		if(validator instanceof RequiredValidator){
			FormComponent<?> field = getField(fieldName);
			String label = getLabel(fieldName);
			if(!label.contains("<b style='color:red'>&nbsp;*&nbsp;</span>")){
				label = label + "<b style='color:red'>&nbsp;*&nbsp;</span>";
			}
			setLabelFor(label, field);
			
		}
		
		return this;
	}

	/**
	 * @see DynaForm#addSubmitFormHandler(SubmitFormHandler)
	 */
	@Override
	public DynaForm addSubmitFormHandler(SubmitFormHandler handler) {
		handlers.add(handler);
		return this;
	}

	/**
	 * @see DynaForm#triggerOnSubmitFormHandlers(Map)
	 */
	@Override
	public void triggerOnSubmitFormHandlers(Map<String, String> request) {
		for(SubmitFormHandler h : handlers){
			h.onSubmitForm(this, request);
		}
	}

	/**
	 * @see DynaForm#triggerOnSubmitFormHandlersSuccess(ClientProxy, Map)
	 */
	@Override
	public void triggerOnSubmitFormHandlersSuccess(ClientProxy form,
			Map<String, String> request) {
		for(SubmitFormHandler h : handlers){
			h.onSubmitFormSuccess(form, request);
		}		
	}

}
