<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.castafiore.spring.SpringUtil"%>
<%@page import="org.castafiore.wfs.service.QueryParameters"%>
<%@page import="org.castafiore.shoppingmall.merchant.Merchant"%>
<%@page import="org.hibernate.criterion.Order"%>
<%@page import="java.util.List"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="description" content="emallofmauritius.com, Mauritius's vital online shopping mall. emallofmauritius allows mauritians to buy online quickly and safely, The first complete online shopping mall for mauritius">
<meta name="keywords" content="mauritius online shopping mall buy credit card phone safely"> 

<title>The mauritian emall</title>
<link rel="stylesheet" href="blueprint/screen.css" type="text/css" media="screen, projection"></link>
<link rel="stylesheet" href="blueprint/plugins/fancy-type/screen.css" type="text/css" media="screen, projection"></link>
<link rel="stylesheet" href="blueprint/plugins/buttons/screen.css" type="text/css" media="screen, projection"></link>
<link rel="stylesheet" href="blueprint/googlelike.css" type="text/css" media="screen, projection"></link> 
<link rel="stylesheet" href="css/themes/smoothness/theme.css" type="text/css"></link>
<link rel="stylesheet" href="css/themes/EXFinder.css" type="text/css"></link>

<%
	
QueryParameters params = new QueryParameters();
params.setEntity(Merchant.class).addOrder(Order.asc("title"));

	List l = SpringUtil.getRepositoryService().executeQuery(params, "anonymous");
%>
</head>
<body>
	<iframe src="http://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fplatform&amp;width=292&amp;colorscheme=light&amp;show_faces=true&amp;border_color&amp;stream=true&amp;header=true&amp;height=427" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:292px; height:427px;" allowTransparency="true"></iframe>
	<div id="searchengine">
		<div id="root_searchengine">
			<div style="width: 970px;" class=" container" >
				<div class=" mall">
					<div style="height: 24px; visibility: hidden;" class=" top">
						<a 	class=" mine" href="#">Inbox</a>
					</div>
					<div class="prepend-top EXSearchBar">
						<div class="logoarea span-4"></div>
					</div>
					<div class=" prepend-top">
						<div class="mallresultpanel last span-14" style="padding-left: 200px">
							<div style="display: block;">
								<div class="shopSearchResults">
									<div style="display: block;">
										<%
											for(Object o : l){
												Merchant m = (Merchant)o;
										%>
										<div class="shopEntry">
											<div>
												 <h3 name="companyName"><a href="/mall/ecommerce.jsp?m=<%=m.getUsername() %>"> <%=m.getCompanyName() %></a></h3>
											</div>
											<div class="body">
												<div class="top">
													<div class="logo">
														<%
															if(m.getLogoUrl() == null || m.getLogoUrl().length() <=0){
														%>
																<img name="logo" src="http://www.settle.org.uk/directory/themes/default//images/default_logo.jpg">
														<%
															}else{
														%>
																<img name="logo" src="<%=m.getLogoUrl()!=null?m.getLogoUrl():""%>">
														<%
															}
														%>
													</div>
													<div class="categories">
														<div><span name="category"><%=m.getCategory()==null?"": m.getCategory() %></span></div>
														<div><span name="category_1"><%=m.getCategory_1()==null?"": m.getCategory_1() %></span></div>
														<div><span name="category_2"><%=m.getCategory_2()==null?"": m.getCategory_2() %></span></div>
														<div><span name="category_3"><%=m.getCategory_3()==null?"": m.getCategory_3() %></span></div>
														<div><span name="category_4"><%=m.getCategory_4()==null?"": m.getCategory_4() %></span></div>
													</div>
													<div>
														<p name="summary"><%=m.getSummary() %></p>
													</div>
												</div>
												<div class="bottom">	
													<div class="detail">
														<div><label name="addLine1"><%=m.getAddressLine1()==null?"": m.getAddressLine1() %></label></div>
														<div><label name="addLine2"><%=m.getAddressLine2()==null?"":m.getAddressLine2() %></label></div>
														<div><label name="addLine3"><%=m.getCity()==null?"":m.getCity() %></label></div>
														<div><label name="country">Mauritius</label></div>
													</div>
													<div class="contactinfo">
														<div>
															<div><label name="phone" ><%=m.getPhone()==null?"":m.getPhone() %></label></div>
															<div><label name="mobilePhone"><%=m.getMobilePhone()==null?"":m.getMobilePhone() %></label></div>
															<div><label name="fax"></label><%=m.getFax()==null?"":m.getFax() %></div>
														</div>
														<div>
															<label  name="email" class=" email"><%=m.getEmail()==null?"":m.getEmail() %></label>
														</div>
														<div>
															<a name="website" target="_blank" href=""><%=m.getWebsite()==null?"":m.getWebsite() %></a>
														</div>
													</div>
												</div>
											</div>
										</div>
										<%
											}
										%>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">
		<h5 class="accessAid">More Information</h5>
		<ul>
			<li><a href="http://www.archnetltd.com">About Us</a></li>
			<li><a href="privacy.html">Privacy</a></li>
			<li><a href="terms.html">Terms and conditions</a></li>
			<li><a href="terms.html#copyright">Copyright</a></li>
			<li><a href="directory.jsp">Merchant Directory</a></li>
			<li><a href="#">FaceBook</a></li>
			<li><a href="http://twitter.com/#!/ArchNetLtd">Twitter</a></li>
			<li><a href="os.jsp">Merchant workspace</a></li>
		</ul>
		<p id="legal">Copyright &copy; 1999-2011 ArchNet ltd. All rights reserved.<br>Users are advised to read the <a href="terms.html">terms and conditions</a> carefully.</p>
	</div>
</body>
</html>