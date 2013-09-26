package org.castafiore.ui.dynaform;

/**
 * A Container that implements this interface can receive keyboard focus.
 */
public interface Focusable {

  /**
   * Gets the Container's position in the tab index.
   * 
   * @return the Container's tab index
   */
  int getTabIndex();

  /**
   * Sets the Container's 'access key'. This key is used (in conjunction with a
   * browser-specific modifier key) to automatically focus the Container.
   * 
   * @param key the Container's access key
   */
  void setAccessKey(char key);

  /**
   * Explicitly focus/unfocus this Container. Only one Container can have focus at a
   * time, and the Container that does will receive all keyboard events.
   * 
   * @param focused whether this Container should take focus or release it
   */
  void setFocus(boolean focused);

  /**
   * Sets the Container's position in the tab index. If more than one Container has
   * the same tab index, each such Container will receive focus in an arbitrary
   * order. Setting the tab index to <code>-1</code> will cause this Container to
   * be removed from the tab order.
   * 
   * @param index the Container's tab index
   */
  void setTabIndex(int index);
}