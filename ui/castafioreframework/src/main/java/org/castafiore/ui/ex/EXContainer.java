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
package org.castafiore.ui.ex;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.Draggable;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.events.SimpleDraggableEvent;
import org.castafiore.ui.events.SimpleResizableEvent;
import org.castafiore.ui.ex.layout.Layout;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.StringUtil;
import org.springframework.util.Assert;

/**
 * <p>
 * Most basic visible object in the framework.This class represents a tag. It is
 * constructed by passing the name and the tag name. In the castafiore web
 * framework, the id of the component is managed internally. API user cannot set
 * the id of the component. This is a design decision to achieve high
 * performance at the price of a small limitation.
 * </p>
 * 
 * The code below will create a new H1 tag with<br>
 * <hr>
 * <code>
 * Container h1 = new Container("anyname", "h1");<br>
 * parent.addChild(h1);
 * </code>
 * <hr>
 * It is possible to set the style and attributes of the tag using the methods
 * <code>setStyle(String name, String value)</code> and
 * <code>setAttribute(String name, String value)</code>
 * <hr>
 * <code>
 * h1.setStyle("border", "solid 1px blue");<br>
 * h1.setAttribute("title", "For rendering a Header !");<br>
 * h1.setText("Hello World");
 * </code>
 * <hr>
 * 
 * The result should be as follows
 * 
 * <h1 style="border:solid 1px blue", title="For rendering a header">Hello World
 * </h1>
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com June 27 2008
 */
@SuppressWarnings("deprecation")
public class EXContainer extends EXDynamicHTMLTag implements Container {

	private static final long serialVersionUID = 8510467682895944117L;

	private Map<String, String> readonlyAttributes_ = new LinkedHashMap<String, String>();

	private Layout layout_;

	private Set<String> resources = null;

	private List<Container> children_ = new LinkedList<Container>();

	/**
	 * Creates a Container with a name and the specified tag
	 * 
	 * @param name
	 *            The name of the container
	 * @param tagName
	 *            The tag to use
	 */
	public EXContainer(String name, String tagName) {
		super(name, tagName);
	}

	/**
	 * adds a javascript library to this container<br>
	 * This library will be loaded on the browser only when this container is
	 * being rendered<br>
	 * It is safe to add many times the same library, the library will be
	 * delivered to the browser only once<br>
	 * Please not that, the library will NOT be removed from the browser when
	 * this container is removed.
	 * 
	 * @return This container itself
	 */
	public Container addScript(String script) {
		Assert.notNull(script, "cannot add a null script");
		if (resources == null) {
			resources = new LinkedHashSet<String>();
		}
		resources.add(script);
		return this;
	}

	/**
	 * Returns all javascript and CSS added to this container.<br>
	 * This is used internally by the engine to manage the delivery of the
	 * resource<br>
	 * It is unlikely that API user will use this method.<br>
	 * However it is perfectly safe to call this method for whatever need, e.g
	 * to test if a particular library is being used, then do something else or
	 * whatever
	 * 
	 * @return A set of libraries
	 */
	public Set<String> getResources() {
		return resources;
	}

	/**
	 * Returns the {@link Application} being used. Note that, this method will
	 * still return the {@link Application} in which this container is intended
	 * to be used even if it has not been added to any parent container yet. The
	 * {@link Application} is being stored in a {@link ThreadLocal} and it
	 * attached to the thread of a particular web request
	 * 
	 * @return The {@link Application} in which this container is intended to be
	 *         used
	 * 
	 */
	public Application getRoot() {
		return CastafioreApplicationContextHolder.getCurrentApplication();
	}

	/**
	 * Adds a style class to the container. It is safe to add the same style
	 * class several times, only once will be rendered. The style class is
	 * concatenated to the current style class
	 * 
	 * @return This {@link Container} itself
	 */
	public Container addClass(String styleClass) {
		Assert.notNull(styleClass, "cannot add a not style class");
		String styles = getAttribute("class");
		String[] aStyles = StringUtil.split(styles, " ");

		boolean add = true;
		for (String style : aStyles) {
			if (style.trim().equals(styleClass)) {
				add = false;
			}
		}
		if (add)
			setAttribute("class", styles.trim() + " " + styleClass);

		return this;
	}

	/**
	 * @return The direct child by index
	 * 
	 */
	public Container getChildByIndex(int index) {
		return this.children_.get(index);
	}

	/**
	 * Refreshes the layout of this container if ever a layout is applied
	 */
	public void refresh() {
		if (layout_ != null) {
			for (int i = 0; i < children_.size(); i++) {
				layout_.doStyling(children_.get(i), this);
			}
		}

	}

	/**
	 * @return The HTML to be used to render on the browser
	 */
	@Override
	public String getHTML() {

		String readonlyAttributes = StringUtil
				.buildattributesFromMap(this.readonlyAttributes_);

		/* id="sdfsd" */
		return "<" + getTag() + " " + readonlyAttributes + ">" + getText()
				+ "</" + getTag() + ">";
	}

	/**
	 * Returns the direct child with the specified name. name cannot be null.
	 * Returns null if no child found
	 * 
	 * @return The direct child with the specified name
	 */
	public Container getChild(String name) {
		Assert.notNull(name, "cannot get a child with name null");
		for (Container component : children_) {
			if (component.getName().equalsIgnoreCase(name)) {
				return component;
			}
		}

		return null;
	}

	/**
	 * @return List of direct children for this container
	 */
	public List<Container> getChildren() {
		return children_;

	}

	/**
	 * Returns descendant with the specified type. If this container is of the
	 * specified type, will return this container itself
	 * 
	 * @return Descendant with the specified class type
	 */
	@SuppressWarnings("unchecked")
	public <T extends Container> T getDescendentOfType(Class<T> type) {
		Assert.notNull(type, "cannot search a descendent with type null");
		if (type.isAssignableFrom(getClass())) {
			return (T) this;
		} else {
			for (Container child : this.getChildren()) {
				T c = child.getDescendentOfType(type);
				if (c != null) {
					return c;
				}
			}
		}

		return null;
	}

	/**
	 * @return descendant with the specified id. If this container has this
	 *         specified id, will return this container itself. If not container
	 *         found, will return null
	 */
	public Container getDescendentById(String id) {
		Assert.notNull(id, "cannot search a descendent with id null");
		if (id.equals(getId())) {
			return this;
		} else {
			for (Container child : this.getChildren()) {
				Container c = child.getDescendentById(id);
				if (c != null) {
					return c;
				}
			}
		}
		return null;
	}

	/**
	 * @return first descendant with the specified name. If this container has
	 *         the specified name, will return this container itself. Will
	 *         return null if no container found with the specified name
	 */
	public Container getDescendentByName(String name) {
		Assert.notNull(name, "cannot search a descendent with name null");
		if (name.equals(getName())) {
			return this;
		} else {
			for (Container child : this.getChildren()) {
				Container c = child.getDescendentByName(name);
				if (c != null) {
					return c;
				}
			}
		}

		return null;
	}

	/**
	 * Overwrites styleclass definition with the specified value. This method
	 * can be used when we do not know the current css applied on this
	 * container, and need to remove them to add a new one
	 */
	public Container setStyleClass(String styleClass) {
		Assert.notNull(styleClass, "cannot set a styleclass null");
		this.setAttribute("class", styleClass);
		return this;
	}

	/**
	 * @return All the css applied on this container in one single string
	 */
	public String getStyleClass() {
		return this.getAttribute("class");
	}

	/**
	 * checks if the specified child can be added to the current container. By
	 * default, this method returns true. However, for more specialized
	 * components, API user can override this method to make sure only certain
	 * type of container is added to the container.e.g. It would be a good idea
	 * to verify that only ToolbarItems are added to a toolbar
	 * 
	 * @return <code>true</code> if child is valid, <code>false</code> if not
	 *         valid
	 */
	public boolean isValidChild(Container child) {
		return true;
	}

	/**
	 * Removes the child with the specified. Nothing is done if child does not
	 * exist. Exception is thrown if name is null
	 */
	public Container removeChild(String name) {
		Assert.notNull(name, "cannot remove a child with name null");
		for (Container component : children_) {
			if (component.getName().equalsIgnoreCase(name)) {
				children_.remove(component);
				component.remove();
				break;
			}
		}
		return this;
	}

	/**
	 * adds the specified container to this current container. It check if the
	 * component is valid by calling {@link Container#isValidChild(Container)}
	 * before adding the component. The component cannot be null, or else
	 * exception is thrown.
	 */
	public Container addChild(Container component) {
		Assert.notNull(component, "cannot add a component that is null");
		if (isValidChild(component)) {

			this.children_.add(component);

			component.setParent(this);
		} else {
			throw new UIException("the component "
					+ component.getClass().getName()
					+ " cannot be added to the component "
					+ this.getClass().getName());
		}
		return this;
	}

	/**
	 * checks if this container has any children.
	 * 
	 * @return true if has children, false if does not have children
	 */
	@Override
	public boolean hasChildren() {
		if (children_ != null) {
			if (children_.size() > 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @return a map of readonly attributes. These attributes that cannot be set
	 *         into a tag after it is created. The engine will generate these
	 *         attributes together with the tag to create an html to be rendered
	 *         on the browser
	 */
	public Map<String, String> getReadonlyAttributes() {
		return this.readonlyAttributes_;
	}

	/**
	 * Flush the container with unnecessary garbage after is is rendered. This
	 * method is used exclusively internally by the engine. SHOULD NEVER BE
	 * CALLED BY API USER EVEN IF YOU KNOW THE SECRET KEY
	 */
	@Override
	public void flush(int secretKey) {
		super.flush(secretKey);

		for (Container child : children_) {
			child.flush(secretKey);
		}
	}

	/**
	 * Creates a {@link Dimension} instance based on the width style set on this
	 * component. This is a convenient method which can be used to make
	 * mathematical calculations on the dimension of the container
	 * 
	 * @return The width of this container.
	 */
	public Dimension getWidth() {
		return Dimension.parse(getStyle("width"));
	}

	/**
	 * sets the width of the container
	 */
	public Container setWidth(Dimension width) {
		Assert.notNull(width, "cannot set a dimension null");
		setStyle("width", width.toString());
		return this;
	}

	/**
	 * Creates a {@link Dimension} instance based on the height style set on
	 * this component. This is a convenient method which can be used to make
	 * mathematical calculations on the dimension of the container
	 * 
	 * @return The height of this container.
	 */
	public Dimension getHeight() {
		return Dimension.parse(getStyle("height"));
	}

	/**
	 * sets the height of the container
	 */
	public Container setHeight(Dimension height) {
		Assert.notNull(height, "cannot set a dimension null");
		setStyle("height", height.toString());
		return this;
	}

	/**
	 * checks if this container is draggable
	 * 
	 * @return <code>true</code> if draggable, <code>false</code> if not
	 *         draggable
	 */
	public boolean isDraggable() {
		return hasEvent(SimpleDraggableEvent.class, Event.READY)
				|| getClass().isAssignableFrom(Draggable.class);
	}

	/**
	 * checks if has event with the sepecified type
	 * 
	 * @return <code>true</code> if has event, <code>false</code> if does not
	 *         has event
	 */
	public boolean hasEvent(Class<?> event, int type) {
		Assert.notNull(event, "cannot search an event to type null");
		List<Event> events = this.getEvents().get(type);
		if (events != null) {
			for (Event evt : events) {
				if (evt.getClass().isAssignableFrom(event)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Removes the specified event from the container
	 */
	public void removeEvent(Class<?> event, int type) {
		Assert.notNull(event, "cannot remove an event to type null");
		List<Event> events = this.getEvents().get(type);
		if (events != null) {
			for (Event evt : events) {
				if (evt.getClass().isAssignableFrom(event)) {
					events.remove(evt);
					break;
				}
			}
		}

		this.setRendered(false);
	}

	/**
	 * Makes the container draggable. Note that this will only make it
	 * draggable. It will not be possible to control the draggable options using
	 * this method. To have a more advanced draggable feature on the component,
	 * consider using the {@link Draggable} interface. Please look at the
	 * {@link Draggable} javadoc for more information.<br>
	 * You will basically need to make your component implement the draggable
	 * interface
	 */
	public Container setDraggable(boolean draggable) {
		setDraggable(draggable, null);
		return this;
	}

	
	public Container setDraggable(boolean draggable, JMap options) {
		if (draggable) {
			if (!hasEvent(SimpleDraggableEvent.class, Event.READY)) {
				this.addEvent(new SimpleDraggableEvent(options), Event.READY);
			}
		} else {
			removeEvent(SimpleDraggableEvent.class, Event.READY);
		}
		return this;
	}

	public Container setResizable(boolean res) {
		setResizable(res, new JMap());
		return this;
	}

	public Container setResizable(boolean res, JMap options) {
		if (res) {
			if (!hasEvent(SimpleResizableEvent.class, Event.READY)) {
				this.addEvent(new SimpleResizableEvent(options), Event.READY);
			}
		} else {
			removeEvent(SimpleResizableEvent.class, Event.READY);
		}
		return this;
	}

	public Layout getLayout() {
		return this.layout_;
	}

	public Container setLayout(Layout layout) {

		this.layout_ = layout;

		refresh();
		setRendered(false);
		return this;
	}

	public Container setDisplay(boolean display) {
		if (display) {
			if (getTag().equalsIgnoreCase("table"))
				this.setStyle("display", "table");
			else
				this.setStyle("display", "block");
		} else {
			this.setStyle("display", "none");
		}
		return this;
	}

	public Container addStyleSheet(String stylesheeturl) {
		Assert.notNull(stylesheeturl, "cannot add a stylesheet that is null");
		// logger.debug("adding stylesheet " + stylesheeturl);
		if (resources == null) {
			resources = new LinkedHashSet<String>();
		}
		resources.add(stylesheeturl);
		return this;
	}

	public Container addChildAt(Container component, int position) {
		Assert.notNull(component, "cannot add a component that is null");
		if (isValidChild(component)) {

			this.children_.add(position, component);

			component.setParent(this);
		} else {
			throw new UIException("the component "
					+ component.getClass().getName()
					+ " cannot be added to the component "
					+ this.getClass().getName());
		}
		return this;

	}

	public Container removeClass(String sclass) {
		Assert.notNull(sclass, "cannot remove a class null");
		String cls = getAttribute("class");
		String[] asClas = StringUtil.split(cls, " ");
		StringBuilder newClass = new StringBuilder();
		for (String s : asClas) {
			if (!s.trim().equalsIgnoreCase(sclass)) {
				newClass.append(s).append(" ");
			}
		}
		setStyleClass(newClass.toString().trim());
		return this;

	}

	public void onReady(ClientProxy proxy) {

	}

	public void setReadOnlyAttribute(String key, String value) {
		readonlyAttributes_.put(key, value);
	}

	public boolean isVisible() {
		String display = getStyle("display");

		if (StringUtil.isNotEmpty(display) && display.equals("none")) {
			return false;
		} else {
			return true;
		}
	}
}
