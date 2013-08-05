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

package org.castafiore.ui;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JMap;

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


public interface Container extends DynamicHTMLTag {

	/**
	 * Entry point to add javascripts into the component when it is ready to
	 * manipulate
	 * @param A {@link ClientProxy} for this {@link Container} itself
	 */
	public void onReady(ClientProxy proxy);

	/**
	 * Adds a style class to the container. It is safe to add the same style
	 * class several times, only once will be rendered. The style class is
	 * concatenated to the current style class
	 * 
	 * @return This {@link Container} itself
	 */
	public Container addClass(String styleClass);

	/**
	 * Removes the specified class from the tag
	 * 
	 * @param sclass
	 *            The style class to remove
	 */
	public Container removeClass(String sclass);

	/**
	 * @return All the css applied on this container in one single string
	 */
	public String getStyleClass();

	/**
	 * Overwrites styleclass definition with the specified value. This method
	 * can be used when we do not know the current css applied on this
	 * container, and need to remove them to add a new one
	 * 
	 * @param styleClass
	 *            The style class to apply
	 */
	public Container setStyleClass(String styleClass);

	/**
	 * Removes the child with the specified. Nothing is done if child does not
	 * exist. Exception is thrown if name is null
	 * 
	 * @param name
	 *            The name of the child to remove
	 */
	public Container removeChild(String name);

	/**
	 * checks if the specified child can be added to the current container. By
	 * default, this method returns true. However, for more specialized
	 * components, API user can override this method to make sure only certain
	 * type of container is added to the container.e.g. It would be a good idea
	 * to verify that only ToolbarItems are added to a toolbar
	 * 
	 * @return <code>true</code> if child is valid, <code>false</code> if not
	 *         valid
	 * 
	 * @param child
	 *            The child to verify if it is valid or not
	 */
	public boolean isValidChild(Container child);

	/**
	 * @return List of direct children for this container
	 */
	public List<Container> getChildren();

	/**
	 * Returns the direct child with the specified name. name cannot be null.
	 * Returns null if no child found
	 * 
	 * @return The direct child with the specified name
	 */
	public Container getChild(String name);

	/**
	 * Adds the specified container to this current container. It check if the
	 * component is valid by calling {@link Container#isValidChild(Container)}
	 * before adding the component. The component cannot be null, or else
	 * exception is thrown. For this implementation, the framework will
	 * automatically search natural place to render this component
	 * 
	 * @param component
	 *            The container to add as child
	 */
	public Container addChild(Container component);

	/**
	 * Adds a component in the specified index in the children list.
	 * @param component The component to add
	 * @param position The position to add it
	 * @return This updated {@link Container}
	 */
	public Container addChildAt(Container component, int position);

	/**
	 * @return a map of read-only attributes. These attributes that cannot be
	 *         set into a tag after it is created. The engine will generate
	 *         these attributes together with the tag to create an HTML to be
	 *         rendered on the browser
	 */
	public Map<String, String> getReadonlyAttributes();

	/**
	 * Flush the container with unnecessary garbage after is is rendered. This
	 * method is used exclusively internally by the engine. SHOULD NEVER BE
	 * CALLED BY API USER EVEN IF YOU KNOW THE SECRET KEY
	 */
	@Override
	public void flush(int secretKey);

	/**
	 * the unique id generated by container
	 * 
	 * @return
	 */
	public String getId();

	/**
	 * returns the name of the component
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * sets the name of the component
	 * 
	 * @param name
	 */
	public Container setName(String name);

	/**
	 * remove the component upon removal of a component,
	 */
	public void remove();

	/**
	 * returns ancestor of type
	 * 
	 * @param classType
	 * @return
	 */

	public <T extends Container> T getAncestorOfType(Class<T> type);

	/**
	 * returns the anscestor with the specified id
	 * 
	 * @param id
	 * @return
	 */
	public Container getAncestorById(String id);

	/**
	 * returns the ancestor with the specified name
	 * 
	 * @param name
	 * @return
	 */
	public Container getAncestorByName(String name);

	/**
	 * returns the parent
	 * 
	 * @return
	 */
	public Container getParent();

	/**
	 * Makes the container draggable. Note that this will only make it
	 * draggable. It will not be possible to control the draggable options using
	 * this method. To have a more advanced draggable feature on the component,
	 * consider using the {@link Draggable} interface. Please look at the
	 * {@link Draggable} javadoc for more information.<br>
	 * You will basically need to make your component implement the draggable
	 * interface
	 */
	public Container setDraggable(boolean draggable);

	/**
	 * Makes this {@link Container} draggable together with more options. This
	 * method is very convenient since it allows a great deal of flexibility <a
	 * href="http://jqueryui.com/">jquery ui</a> has been used to implement the
	 * draggable feature under the hood<br>
	 * Please refer to this <a
	 * href="http://api.jqueryui.com/draggable/">http://api
	 * .jqueryui.com/draggable/</a> for a list of available options
	 */
	public Container setDraggable(boolean draggable, JMap options);

	/**
	 * Makes the {@link Container} resizable. Note that this will only make it
	 * resizable. It will not be possible to control the resizable options using
	 * this method. To have a more advanced resizable feature on the component,
	 * consider using the overloaded method (
	 * {@link #setResizable(boolean, JMap)}
	 * 
	 * @param bool
	 *            to enable or disable resizable feature
	 */
	public Container setResizable(boolean bool);

	/**
	 * Makes this {@link Container} resizable together with more options. This
	 * method is very convenient since it allows a great deal of flexibility <a
	 * href="http://jqueryui.com/">jquery ui</a> has been used to implement the
	 * resizable feature under the hood<br>
	 * Please refer to this <a
	 * href="http://api.jqueryui.com/resizable/">http://api
	 * .jqueryui.com/resizable/</a> for a list of available options
	 * 
	 * @param bool
	 *            to enable or disable resizable feature
	 */
	public Container setResizable(boolean res, JMap options);

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
	public Application getRoot();

	/**
	 * 
	 * @return if the component is rendered or not
	 */
	public boolean rendered();

	/**
	 * reset the redered value
	 * 
	 * @param rendered
	 */
	public Container setRendered(boolean rendered);

	/**
	 * Checks if this container has any children.
	 * 
	 * @return true if has children, false if does not have children
	 */
	public boolean hasChildren();

	/**
	 * @return The direct child by index
	 * 
	 */
	public Container getChildByIndex(int index);

	/**
	 * sets the parent of the container
	 * 
	 * @param container
	 */
	public void setParent(Container container);

	/**
	 * @return first descendant with the specified name. If this container has
	 *         the specified name, will return this container itself. Will
	 *         return null if no container found with the specified name
	 */
	public Container getDescendentByName(String name);

	/**
	 * @return descendant with the specified id. If this container has this
	 *         specified id, will return this container itself. If no container
	 *         found, will return null
	 */
	public Container getDescendentById(String id);

	/**
	 * Returns descendant with the specified type. If this container is of the
	 * specified type, will return this container itself
	 * 
	 * @return Descendant with the specified class type
	 */
	public <T extends Container> T getDescendentOfType(Class<T> type);

	/**
	 * sets the component visible or invisible
	 * 
	 * @param display
	 */
	public Container setDisplay(boolean display);

	/**
	 * Checks if the container is visible or not. This is based only on the
	 * styles set via {@link Container#setStyle(String, String)} If the
	 * component is made invisible via animations or via client side script, the
	 * method will still return <code>true</code>
	 * 
	 * @return <code>true</code> if visible <code>false</code> if not visible
	 */
	public boolean isVisible();

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
	public Container addScript(String scripturl);

	/**
	 * adds a css file to this container<br>
	 * This file will be loaded on the browser only when this container is being
	 * rendered<br>
	 * It is safe to add many times the same file, it will be delivered to the
	 * browser only once<br>
	 * Please not that, the css will NOT be removed from the browser when this
	 * container is removed.
	 * 
	 * @return This container itself
	 * 
	 * @param The
	 *            style sheet url
	 */
	public Container addStyleSheet(String stylesheeturl);

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
	public Set<String> getResources();

	/**
	 * Sets the width of the container
	 * 
	 * @param width
	 *            The width of the {@link Container}
	 */
	public Container setWidth(Dimension dimension);

	/**
	 * Sets the height of the container
	 * 
	 * @param height
	 *            The height to apply to this {@link Container}
	 */
	public Container setHeight(Dimension dimension);

	/**
	 * Creates a {@link Dimension} instance based on the width style set on this
	 * component. This is a convenient method which can be used to make
	 * mathematical calculations on the dimension of the container
	 * 
	 * @return The width of this container.
	 */
	public Dimension getWidth();

	/**
	 * Creates a {@link Dimension} instance based on the height style set on
	 * this component. This is a convenient method which can be used to make
	 * mathematical calculations on the dimension of the container
	 * 
	 * @return The height of this container.
	 */
	public Dimension getHeight();

	/**
	 * Checks if this container is draggable
	 * 
	 * @return <code>true</code> if draggable, <code>false</code> if not
	 *         draggable
	 */
	public boolean isDraggable();

	/**
	 * Checks if has event with the sepecified type
	 * 
	 * @return <code>true</code> if has event, <code>false</code> if does not
	 *         has event
	 * 
	 * @param event
	 *            The class type of the event
	 * @param type
	 *            The type of event
	 * @see {@link Event}
	 */
	public boolean hasEvent(Class<?> event, int type);

	/**
	 * Removes the specified event from the container
	 * 
	 * @param event
	 *            The class type of the event
	 * @param type
	 *            The type of event
	 * @see {@link Event}
	 */
	public void removeEvent(Class<?> event, int type);

	/**
	 * Refreshes the layout of this container if ever a layout is applied
	 */
	public void refresh();

	public void setReadOnlyAttribute(String key, String value);

}
