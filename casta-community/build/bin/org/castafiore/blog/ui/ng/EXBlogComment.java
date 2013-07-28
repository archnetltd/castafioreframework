/*
 * Copyright (C) 2007-2010 Castafiore
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
 package org.castafiore.blog.ui.ng;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;

public class EXBlogComment extends EXXHTMLFragment {

	private int blodId;

	private final static SimpleDateFormat format = new SimpleDateFormat(
			"MMM ddd, yyyy  hh:mm");

	public EXBlogComment(String name, int blogId) {
		super(name, ResourceUtil.getDownloadURL("classpath",
				"org/castafiore/blog/ui/ng/resources/EXBlogComment.xhtml"));
		// <img height="48" width="48" src="#" name="avatar"></img>
		EXContainer avatar = new EXContainer("avatar", "img");
		avatar.setAttribute("height", "48");
		avatar.setAttribute("width", "48");
		avatar
				.setAttribute(
						"src",
						"http://www.gravatar.com/avatar/ee5dc0d7c0114ed21b83a1450b890c70?s=48&d=http%3A%2F%2Fwww.gravatar.com%2Favatar%2Fad516503a11cd5ca435acc9bb6523536%3Fs%3D48&r=R");
		addChild(avatar);

		// <a href="#" name="user">KURT AVISH</a>
		EXContainer user = new EXContainer("user", "a");
		user.setAttribute("href", "#");
		user.setText("Anonymous");
		addChild(user);

		// <a title="" href="#" name="date">June 19th, 2009 at 17:42</a>
		EXContainer date = new EXContainer("date", "a");
		date.setAttribute("href", "#");
		date.setText(format.format(Calendar.getInstance().getTime()));
		addChild(date);

		EXContainer comment = new EXContainer("comment", "div");
		addChild(comment);
		this.blodId = blogId;
	}

	public int getBlodId() {
		return blodId;
	}

	public void setAvatar(String avatarImgUrl) {
		getChild("avatar").setAttribute("src", avatarImgUrl);
	}

	public void setUser(String user) {
		getChild("user").setText(user);
	}

	public void setDate(Calendar calendar) {
		getChild("date").setText(format.format(calendar.getTime()));
	}

	public void setComment(String comment) {
		getChild("comment").setText(comment);
	}

}
