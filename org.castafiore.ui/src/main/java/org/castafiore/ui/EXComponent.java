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

import org.springframework.util.Assert;

/**
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com June 27 2008
 */
public abstract class EXComponent implements Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id_ = null;

	private Container parent_;

	private String name_;

	private boolean rendered = false;

	public EXComponent(String name) {
		this.name_ = name;
	}

	@SuppressWarnings("unchecked")
	public <T extends Container> T getAncestorOfType(Class<T> classType) {
		Assert.notNull(classType, "cannot find ancestor of type null");
		if (classType.isAssignableFrom(getClass()))
			return (T) this;

		if (parent_ == null) {
			return null;
		}
		if (classType.isAssignableFrom(parent_.getClass())) {
			return (T) parent_;
		} else {
			return parent_.getAncestorOfType(classType);
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Container getAncestorById(String id) {
		Assert.notNull(id, "cannot find ancestor with id null");
		if (getId().equals(id))
			return (Container) this;

		if (parent_ == null) {
			return null;
		}

		return parent_.getAncestorById(id);

	}

	public Container getAncestorByName(String name) {
		Assert.notNull(name, "cannot find ancestor with name null");
		if (getName().equals(name))
			return (Container) this;

		if (parent_ == null) {
			return null;
		}

		return parent_.getAncestorByName(name);
	}

	public String getId() {
		if (id_ == null)
			id_ = this.hashCode() + "";
		return id_;
	}

	public Container getParent() {
		return this.parent_;
	}

	public void remove() {
		parent_.getChildren().remove(this);
		parent_.setRendered(false);
	}

	public String getName() {
		return name_;
	}

	public Container setName(String name) {
		Assert.notNull(name, "cannot set a name null");
		this.name_ = name;
		return this;
	}

	public boolean rendered() {
		return this.rendered;
	}

	public Container setRendered(boolean rendered) {
		this.rendered = rendered;

		setRendered(rendered, (Container) this);

		return this;
	}

	private void setRendered(boolean rendered, Container container) {

		for (Container child : container.getChildren()) {
			child.setRendered(rendered);
		}
	}

	public boolean hasChildren() {
		return false;
	}

	public void setParent(Container container) {
		this.parent_ = container;
	}
}
