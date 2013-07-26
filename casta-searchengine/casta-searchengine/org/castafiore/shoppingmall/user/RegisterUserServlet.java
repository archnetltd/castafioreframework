package org.castafiore.shoppingmall.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.castafiore.persistence.Dao;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.web.servlet.AbstractCastafioreServlet;
import org.hibernate.criterion.Restrictions;

public class RegisterUserServlet extends AbstractCastafioreServlet {

	@Override
	public void doService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String secret = request.getParameter("ss");
		SecurityService service = SpringUtil.getSecurityService();
		Dao dao =SpringUtil.getBeanOfType(Dao.class);
		List users = dao.getSession().createCriteria(User.class).add(Restrictions.eq("secret", secret)).list();
		
		if(users.size() > 0){
			User u = (User)users.get(0);
			u.setEnabled(true);
			service.saveOrUpdateUser(u);
			
			
			
		}response.sendRedirect("/mall/logout.jsp");
		
	}

}
