package com.ant.common.filter;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class ForwardFilter implements Filter {

    private static Logger logger = Logger.getRootLogger();
    protected FilterConfig config = null;


    @Override
	public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    @Override
	public void destroy() {
        this.config = null;
    }

    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		
		String url = req.getContextPath() + req.getServletPath();
		
		logger.debug("===========[FORWARD] start " + url  + "?" + req.getQueryString());		
		// parameter
//		if(isMultipartRequest(req)) {
//		    try {
//                HashMap<Object, Object> formMap = CommonUtil.getRequestForm(req);
//                
//                Iterator it = formMap.keySet().iterator();
//                
//                while(it.hasNext()) {
//                    Object key = it.next();
//                    Object valueObject = formMap.get(key);
//                    
//                    if(valueObject != null) {
//                        if(valueObject.getClass().isArray()) {
//                            Object[] valueArray = (Object[])valueObject;
//                            
//                            StringBuffer valueString = new StringBuffer();
//                            valueString.append("[");
//                            for(Object value : valueArray) {
//                                valueString.append(CommonUtil.toString(value));
//                                valueString.append(",");
//                            }
//                            valueString.append("]");
//                            logger.debug("[[ " + url + "[MP]==>[P]" + CommonUtil.toString(key) + ":" + valueString.toString());
//                        }
//                        else {
//                            logger.debug("[[ " + url + "[MP]==>[P]" + CommonUtil.toString(key) + ":" + CommonUtil.toString(valueObject));
//                        }
//                    }
//                }
//            }
//            catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//        else {
    		Enumeration<String> paramkeys = req.getParameterNames();
    		while(paramkeys.hasMoreElements()) {
    			String key = paramkeys.nextElement();
    			String[] paramValues = req.getParameterValues(key);
    			StringBuffer value = new StringBuffer();
    			for(int i=0; i < paramValues.length; i++) {
    				if(i > 0) {
    					value.append(",");
    				}
    				value.append(paramValues[i]);
    			}
    			logger.debug("[[ " + url + "==>[P]" + key + ":" + value.toString());
    		}	
//        }
		// request
		Enumeration<String> reqKeys = req.getAttributeNames();
		while(reqKeys.hasMoreElements()) {
			String key = reqKeys.nextElement();
			Object reqValue = req.getAttribute(key);
			logger.debug("[[ " + url + "==>[R]" + key + ":" + reqValue);
		}	
		
		
		
		// session
		HttpSession session = req.getSession();
		Enumeration<String> sesKeys = session.getAttributeNames();
		while(sesKeys.hasMoreElements()) {
			String key = sesKeys.nextElement();
			Object sesValue = session.getAttribute(key);
			logger.debug("[[ " + url + "==>[S]" + key + ":" + sesValue);
		}
		
		chain.doFilter(request, response);
		logger.debug("===========[FORWARD] end " + url  + "?" + req.getQueryString());
    }
}