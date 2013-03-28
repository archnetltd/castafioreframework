package org.castafiore.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/castafiore/resource")
public class CastafioreController {
	
	
	@RequestMapping("1.jsp")
	public void doServlet(HttpServletRequest request, HttpServletResponse response){
		
	}
	
	
	@RequestMapping("resource/*")
	public void doResource(HttpServletRequest request, HttpServletResponse response){
		System.out.println("entered here");
	}
	
	
	@RequestMapping("method/*")
	public void doMethod(HttpServletRequest request, HttpServletResponse response){
		
	}

}
