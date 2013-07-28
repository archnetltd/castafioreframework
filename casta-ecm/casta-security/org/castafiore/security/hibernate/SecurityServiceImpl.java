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

package org.castafiore.security.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import javax.servlet.http.HttpSession;

import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.castafiore.community.ui.timetable.TimeTable;
import org.castafiore.persistence.Dao;
import org.castafiore.security.Group;
import org.castafiore.security.Role;
import org.castafiore.security.User;
import org.castafiore.security.UserSecurity;
import org.castafiore.security.api.SecurityService;
import org.castafiore.security.api.SessionManager;
import org.castafiore.security.sessions.LoggedSession;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.security.Credential;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class SecurityServiceImpl  implements SecurityService, UserDetailsService , SessionManager{

	
	private Dao dao;
	
	private String superUser;
	
	private Map<String, User> cache_ = new WeakHashMap<String, User>();
	
	private static  Map<String,LoggedSession> LOGGED = new HashMap<String, LoggedSession>();
	
	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	

	public String getSuperUser() {
		return superUser;
	}

	public void setSuperUser(String superUser) {
		this.superUser = superUser;
	}
	
	public User loadUserByEmail(String email){
		
		List l = getDao().getReadOnlySession().createCriteria(User.class).add(Restrictions.eq("email", email)).list();
		if(l.size() > 0){
			return (User)l.get(0);
		}
		return null;
		
	}
	
	public User loadUserByMobilePhone(String phone){
		List l = getDao().getReadOnlySession().createCriteria(User.class).add(Restrictions.eq("mobile", phone)).list();
		if(l.size() > 0){
			return (User)l.get(0);
		}
		return null;
	}
	public Map<String,List<User>> getCollaborators(String username, String organization)throws Exception{
		Map<String,List<User>> result = new HashMap<String, List<User>>();
		List<Group> grps = getGroups(username, "manager");
		for(Group g : grps){
			List<User> us = getUsers("*:" + g.getName(),organization);
			result.put(g.getName(), us);
		}
		
		return result;
		
	}

	public List<Group> getGroups(String username) throws Exception {
		
		List<Group> result = new ArrayList<Group>();
		Criteria crit = dao.getReadOnlySession().createCriteria(UserSecurity.class);
		crit.createCriteria("user").add(Restrictions.eq("username", username));
		List<UserSecurity> uss = crit.list();	
		if(uss != null)
		{
			for(UserSecurity u : uss)
			{
				result.add(u.getGrp());
			}
		}
		
		return result;		
	}

	public boolean isUserAllowed(String username, String role, String group){
		Criteria crit = dao.getReadOnlySession().createCriteria(UserSecurity.class);
		crit.createCriteria("user").add(Restrictions.eq("username", username));
		crit.createCriteria("role").add(Restrictions.eq("name", role));
		crit.createCriteria("grp").add(Restrictions.eq("name", group));
		List lst = crit.setProjection(Projections.rowCount()).list();
		if(lst.size() > 0){
			Integer count = Integer.parseInt(lst.get(0).toString());
			if(count > 0){
				return true;
			}else{
				return false;
			}
		}else{
			throw new RuntimeException("error in method isUserAllowed");
		}
	}
	
	public List<Group> getGroups(String username, String role) throws Exception {
		List<Group> result = new ArrayList<Group>();
		Criteria crit = dao.getReadOnlySession().createCriteria(UserSecurity.class);
		crit.createCriteria("user").add(Restrictions.eq("username", username));
		crit.createCriteria("role").add(Restrictions.eq("name", role));
		List<UserSecurity> uss= crit.list();
		if(uss != null)
		{
			for(UserSecurity u : uss)
			{
				
				result.add(u.getGrp());
			}
		}
		
		return result;
	}
	
	public List<Role> getRolesInGroup(String username, String group)
			throws Exception {
		List<Role> result = new ArrayList<Role>();
		
		Criteria crit = dao.getReadOnlySession().createCriteria(UserSecurity.class);
		crit.createCriteria("user").add(Restrictions.eq("username", username));
		crit.createCriteria("grp").add(Restrictions.eq("name", group));
		List<UserSecurity> uss = crit.setProjection(Projections.rowCount()).list();
		
		if(uss != null)
		{
			for(UserSecurity u : uss)
			{
				result.add(u.getRole());
			}
		}
		
		return result;
	}
	
	
	public User loadUserByUsername(String username)throws UsernameNotFoundException, DataAccessException {
		
		try
		{
			if(!cache_.containsKey(username)){
				Session session =  dao.getReadOnlySession();
				
				Criteria crit = session.createCriteria(User.class).add(Restrictions.eq("username", username));
				 List<User> users = crit.list();//session.createQuery("from " + User.class.getName() + " where username=?").setParameter(0, username).list();
				 if(users.size() == 0){
					 throw new UsernameNotFoundException("the user " + username + " cannot be found"); 
				 }else{
					 cache_.put(username, users.get(0));
					 return users.get(0);
					 
				 }
			}else{
				return cache_.get(username);
			}
		}
		catch(UsernameNotFoundException unfe)
		{
			throw unfe;
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}		
	}

	public boolean login(String username, String password) throws Exception {
		if(isLoggedInFromDifferentLocation(username)){
			throw new Exception("Single sign on exception. The user " + username + " is trying to log in from a different session");
		}
		User user;
		
		user = loadUserByUsername(username);
	
		if(user != null && user.getPassword().equals(password)){
			
			if(canLogFromTimeTable(username, user.getOrganization())){
				try{
				Credential credential = SpringUtil.getBean("credential");
				credential.setRemoteUser(username);
				credential.setOrganization(user.getOrganization());
				
				String sessionid = CastafioreApplicationContextHolder.getCurrentApplication().getSessionId();
				String ip =CastafioreApplicationContextHolder.getCurrentApplication().getRemoteAddress();
				LoggedSession session = new LoggedSession();
				session.setUsername(username);
				session.setIpAddress(ip);
				session.setSessionId(sessionid);
				session.setFullName(user.toString());
				session.setTimeLogin(Calendar.getInstance());
				session.setContext(CastafioreApplicationContextHolder.getCurrentApplication().getConfigContext().get("context"));
				LOGGED.put(username,session);
				}catch(Exception e){
					e.printStackTrace();
				}
				return true;
			}else{
				throw new Exception("You cannot log in at this time. Please try later");
			}
		}
		return false;
	}
	
	private List<TimeTable> getTimeTables(String username , String organization)throws Exception{
		List<Group> groups = getGroups(username);
		
		List<String> grps = new ArrayList<String>();
		for(Group g : groups){
			grps.add(g.getName());
		}
		if(grps.size() > 0){
		List<TimeTable> tables = dao.getSession().createCriteria(TimeTable.class).add(Restrictions.eq("organization", organization)).add(Restrictions.in("grp", grps)).list();
		
		
		return tables;
		}else{
			return new ArrayList<TimeTable>();
		}
		
	}
	
	
	private boolean canLogFromTimeTable(String username, String organization)throws Exception{
		List<TimeTable> tts = getTimeTables(username, organization);
		if(tts.size() == 0){
			return true;
		}
		for(TimeTable t : tts){
			if(canLogIn(t))return true;
		}
		
		return false;
	}
	
	private boolean canLogIn(TimeTable t){
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		
		String notation = t.getMon();
		if(day == Calendar.TUESDAY){
			notation = t.getTue();
		}else if(day == Calendar.WEDNESDAY){
			notation = t.getWed();
		}else if(day == Calendar.THURSDAY){
			notation = t.getThur();
		}else if(day == Calendar.FRIDAY){
			notation = t.getFri();
		}else if(day == Calendar.SATURDAY){
			notation = t.getSat();
		}else if(day == Calendar.SUNDAY){
			notation = t.getSun();
		}
		
		if(StringUtil.isNotEmpty(notation)){
			if(notation.equalsIgnoreCase("*") || notation.equalsIgnoreCase("full")){
				return true;
			}else if(notation.contains("-")){
				String[] parts = StringUtil.split(notation, "-");
				
				int curHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				try{
					int start = Integer.parseInt(parts[0]);
					int end = Integer.parseInt(parts[1]);
					
					if(curHour >= start && curHour < end){
						return true;
					}else{
						return false;
					}
					
				}catch(Exception e){
					throw new RuntimeException(e);
				}
				
			}else{
				return true;
			}
			
		}else{
			return true;
		}
	}
	
	public void logout(String sessionid){
		Iterator<String> iter = LOGGED.keySet().iterator();
		List<String> toremove = new ArrayList<String>(); 
		//String sessionid = CastafioreApplicationContextHolder.getCurrentApplication().getSessionId();
		while(iter.hasNext()){
			String key = iter.next();
			String val = LOGGED.get(key).getSessionId();
			if(val.equals(sessionid)){
				toremove.add(key);
			}
		}

		
		for(String s : toremove){
			LoggedSession ls = LOGGED.remove(s);
			try{
				Object o = ls.getContext();
				o.getClass().getMethod("invalidate").invoke(o);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private boolean isLoggedInFromDifferentLocation(String username){
		if(LOGGED.containsKey(username)){
			LoggedSession session = LOGGED.get(username);
			String sessionid = session.getSessionId() + ":" + session.getIpAddress();
			String sid = CastafioreApplicationContextHolder.getCurrentApplication().getSessionId();
			String ip = CastafioreApplicationContextHolder.getCurrentApplication().getRemoteAddress();
			if(ip.equalsIgnoreCase(session.getIpAddress())){
				return false;
			}
			String val = sid + ":" + ip;
			if(sessionid.equals(val)){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	public Collection<LoggedSession> getLoggedSessions(){
		return LOGGED.values();
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void assignSecurity(User user, Role role, Group group)throws Exception {
		Assert.notNull(group, "assignSecurity: group is null");
		Assert.notNull(user, "assignSecurity: user is null");
		Assert.notNull(role, "assignSecurity: role is null");
		if(!isUserAllowed(user.getUsername(),role.getName(),group.getName())){
			UserSecurity security = new UserSecurity();
			security.setUser(user);
			security.setRole(role);
			security.setGrp(group);
			Session s =  dao.getSession();
			
			s.save(security);
			s.flush();
			
		}
	}
	
	public Role getRole(String name)throws Exception{
		try
		{
			List<Role> roles = dao.getReadOnlySession().createCriteria(Role.class).add(Restrictions.eq("organization", Util.getLoggedOrganization())).add(Restrictions.eq("name", name)).list();//dao.getReadOnlySession().createQuery("from " + Role.class.getName() + " where name=?").setParameter(0, name).list();
			if(roles.size() > 0)
			{
				return roles.get(0);
			}else{
				return null;
			}
		}catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
		catch(Exception e){
			throw e;
		}
		
	}
	
	public Group getGroup(String name)throws Exception{
		try
		{
			List<Group> grps =  dao.getReadOnlySession().createCriteria(Group.class).add(Restrictions.eq("name", name)).add(Restrictions.eq("organization", Util.getLoggedOrganization())).list();//getDao().getReadOnlySession().createQuery("from " + Group.class.getName() + " where name=?").setParameter(0, name).list(); 
			if(grps.size() > 0)
				return grps.get(0);
			return null;
		}
		catch(ArrayIndexOutOfBoundsException e){
			return null;
		}catch(Exception e){
			throw e;
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void assignSecurity(String user, String role, String group)	throws Exception {
		
		Group g = getGroup(group);
		Role r = getRole(role);
		User u = (User)loadUserByUsername(user);
		
		assignSecurity(u, r, g);
	
	}
	
	
	public void unAssignSecurity(String user, String role, String group){
		
		Session session = dao.getSession();
		
		session.createSQLQuery("delete from SECU_USERSECURITY where user_id in (select id from SECU_USER where username = ?) and grp_id in (select id from SECU_GROUP where name=?) and role_id in (select id from SECU_ROLE where name=?)")
		.setParameter(0, user).setParameter(1, group).setParameter(2, role).executeUpdate();
		
		session.flush();
		//List result =session.createCriteria(UserSecurity.class).add(Restrictions.eq("user.username", user)).add(Restrictions.eq("grp.name",group)).add( Restrictions.eq("role.name", role) ).list();
		
		
		//dao.getSession().createQuery(hql)
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void assignSecurity(String user, String role, String group,String org, boolean forceCreateGroupAndRole)	throws Exception {
			Group g = getGroup(group);
			if(forceCreateGroupAndRole && g == null){
				g = saveOrUpdateGroup(group, "created automatically", org);
			}
			Role r = getRole(role);
			
			if(forceCreateGroupAndRole && r == null){
				r = saveOrUpdateRole(role, "created automcatically", org);
			}
			User u = (User)loadUserByUsername(user);
			assignSecurity(u, r, g);
	}
	
	public List<Role> getRoles()throws Exception
	{
		List<Role> roles = getDao().getReadOnlySession().createCriteria(Role.class).add(Restrictions.eq("organization", Util.getLoggedOrganization())).list();
		//List<Role> roles = getDao().getReadOnlySession().createQuery("from " + Role.class.getName()).list();
		return roles;
	}
	
	public List<Group> getGroups()throws Exception
	{
		List<Group> groups = getDao().getReadOnlySession().createCriteria(Group.class).add(Restrictions.eq("organization", Util.getLoggedOrganization())).list();//getDao().getReadOnlySession().createQuery("from " + Group.class.getName()).list();
		return groups;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public Group saveOrUpdateGroup(String name, String description, String organization)throws Exception
	{
		Assert.notNull(name, "saveGroup: group is null");
		
		Group g = getGroup(name);
		if(g != null){
			g.setDescription(description);
		}else{
			g = new Group();
			g.setName(name);
			g.setDescription(description);
		}
		g.setOrganization(organization);
		getDao().getSession().saveOrUpdate(g);
		getDao().getSession().flush();
		return g;
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public Role saveOrUpdateRole(String name, String description, String organization)throws Exception
	{
		Assert.notNull(name, "saveRole: role is null");
		
		Role r = getRole(name);
		if(r != null){
			r.setDescription(description);
		}else{
			r = new Role();
			r.setName(name);
			r.setDescription(description);
			
		}
		r.setOrganization(organization);
		getDao().getSession().saveOrUpdate(r);
		getDao().getSession().flush();
		
		return r;
	}

	public boolean isUserAllowed(String accessPermission, String username) throws Exception {
		Assert.notNull(username, "isUserAllowed: username is null");
		Assert.notNull(accessPermission, "isUserAllowed: accessPermission is null");
		return isUserAllowed(new String[]{accessPermission}, username);
	}
	
	
	/**
	 **
	 **
	 *<
	 *
	 *
	 */
	
	
	
	public boolean isUserAllowed(String[] accessPermissions, String username) throws Exception {
		Assert.notNull(username, "isUserAllowed: username is null");
		Assert.notNull(accessPermissions, "isUserAllowed: accessPermissions is null");
		if(username.equals(superUser))
		{
			return true;
		}
		
		if(accessPermissions == null || accessPermissions.length ==0){
			accessPermissions = new String[]{"*:users"};
		}
		String queryFragment = " ";
		int count = 0;
		for(String accessPermission : accessPermissions)
		{
			String[] parts = StringUtil.split(accessPermission, ":");
			
			if(parts != null && parts.length == 2)
			{
				String role = parts[0];
				String group = parts[1];
				if(count > 0)
				{
					queryFragment = queryFragment + " or ";
				}
				if(!"*".equals(role))
					queryFragment = queryFragment + "(grp.name = '" + group + "' and role.name='" + role + "')";
				else
					queryFragment = queryFragment + "(grp.name = '" + group + "')";
			}
			else
			{
				throw new Exception("invalid accessPermission" + " should be like <role>:<group>");
			}
			count++;
		}
		
		String query = "from " + UserSecurity.class.getName() + " where user.organization='"+Util.getLoggedOrganization()+"' and user.username ='" + username + "' and (" + queryFragment + ")";
		
		List<UserSecurity> uss = getDao().getReadOnlySession().createQuery(query).list();
		
		if(uss.size() > 0)
		{
			return true;
		}
		return false;
	}

	public String[] getPermissionSpec(String username) throws Exception {
		Assert.notNull(username, "cannot find permission spec for null username");
		String[] system = new String[]{"*"};
		if(username.equalsIgnoreCase(superUser))
			return system;
		
		String query = "from " + UserSecurity.class.getName() + " where user.username =? and user.organization=?";
		List<UserSecurity> uss = getDao().getReadOnlySession().createQuery(query).setParameter(0, username).setParameter(1, Util.getLoggedOrganization()).list();
		
		String[] result = new String[uss.size()];
		int counter = 0;
		for(UserSecurity f : uss)
		{
			UserSecurity security = f;
			result[counter] = security.getRole().getName() + ":" + security.getGrp().getName();
			
			counter++;
			
		}
		
		return result;
		
		
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void registerUser(User user) throws Exception {
		Assert.notNull(user, "cannot save a null user!!");
		try{
			loadUserByUsername(user.getUsername());
			throw new RuntimeException("User " + user.getUsername() + " already exists");
		}catch(UsernameNotFoundException e){
			
		}
		
		Session session = getDao().getSession();
		
		session.save(user);
		session.flush();
		
	}
	
	public void saveOrUpdateUser(User user){
		dao.getSession().saveOrUpdate(user);
		if(cache_.containsKey(user.getUsername())){
			cache_.remove(user.getUsername());
		}
		dao.getSession().flush();
	}

	public List<User> getUsersForOrganization(String organization) throws Exception {
		Session s =getDao().getReadOnlySession();
		Criteria crit = s.createCriteria(User.class);
		crit.add(Restrictions.eq("organization", organization));
		crit.addOrder(Order.asc("username"));
		return crit.list();
	
		//List<User> users = getDao().getHibernateTemplate().find("from " + User.class.getName() + " where ");
		//return users;
	}

	public void deleteGroup(String name)throws Exception {
		dao.getSession().delete(getGroup(name));
		dao.getSession().flush();
		
	}

	public void deleteRole(String name)throws Exception {
		dao.getSession().delete(getRole(name));
		dao.getSession().flush();
	}

	public void deleteUser(String name)throws Exception {
		
		//delete usersecurity
		String sq1= "delete from SECU_USERSECURITY where USER_ID= (select ID from SECU_USER where username='"+name+"')";
		String sq2= "delete from WFS_FILE where absolutePath like '/root/users/"+name+"/%' or absolutePath = '/root/users/"+name+"';";
		String sq3= "delete from SECU_USER_SECU_ADDRESS where SECU_USER_ID= (select ID from SECU_USER where username='"+name+"');";
		String sq4 ="delete from SECU_USER where username= '"+name+"';";
		//String sql = "delete from SECU_USERSECURITY where USER_ID= (select ID from SECU_USER where username='ssss')";
		Session s = dao.getSession();
		s.createSQLQuery(sq1).executeUpdate();
		s.createSQLQuery(sq2).executeUpdate();
		s.createSQLQuery(sq3).executeUpdate();
		s.createSQLQuery(sq4).executeUpdate();
	}


	public List<User> getUsers(String permission, String organization) throws Exception {
		List<User> result = new ArrayList<User>();
		List<User> users = getUsersForOrganization(organization);
		
		for(User u : users){
			if(isUserAllowed(permission, u.getUsername())){
				result.add(u);
			}
		}
		return result;
	}

	@Override
	public List<User> loadUsers(List<String> usernames) {
		if(usernames.size() > 0){
			Session session =  dao.getReadOnlySession();
			Criteria crit = session.createCriteria(User.class.getName()).add(Restrictions.in("username", usernames));
			
			 List<User> users = crit.list();
			 
		return users;
		}else{
			return new ArrayList<User>();
		}
		
	}

	@Override
	public User hydrate(User user) {
		cache_.remove(user.getUsername());
		return loadUserByUsername(user.getUsername());
	}

}
