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

package org.castafiore.ui.dynaform;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.castafiore.ui.ex.form.EXInput;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Field {

	/**
	 * the position of the field in the form
	 * 
	 * @return
	 */
	public int position();

	/**
	 * the name of the field
	 * 
	 * @return
	 */
	public String name();

	/**
	 * the label for the field
	 * 
	 * @return
	 */
	public String label();

	/**
	 * the class type to be instantated for the field The class type should be
	 * an instance of StateFullComponent and also should have a constructor with
	 * single parameter as the name
	 * 
	 * @return
	 */
	public Class<?> fieldType() default EXInput.class;

	/**
	 * used to provide list data for select components Class should be instance
	 * of list provider
	 * 
	 * @return
	 */
	public Class<?> listProvider() default void.class;

	/**
	 * configurations for validation
	 * 
	 * @return
	 */
	public ValidatorConfig[] validator() default {};

	/**
	 * checks if this field should be readonly
	 * 
	 * @return
	 */
	public boolean readonly() default false;

}
