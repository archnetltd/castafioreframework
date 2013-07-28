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
 package org.castafiore.blog;

public interface BlogStatisticService {
	
	public final static int POSTS_IN_MONTH = 0;
	
	public final static int POSTS_IN_BLOG = 1;
	
	public final static int POSTS_IN_CATEGORY = 2;
	
	public final static int POSTS_BY_USER = 3;
	
	public final static int COMMENTS_IN_BLOG = 4;
	
	
	
	public void updateStatsOnAddComment();
	
	
	public void updateStatsOnAddPost();

}
