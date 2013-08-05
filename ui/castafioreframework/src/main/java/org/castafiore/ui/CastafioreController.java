package org.castafiore.ui;

import org.springframework.web.servlet.mvc.Controller;

/**
 * Interface to be implemented by Containers if the container is to act as a
 * {@link Controller}
 * @see Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 * @author arossaye
 * 
 */
public interface CastafioreController extends Controller, Container {

}
