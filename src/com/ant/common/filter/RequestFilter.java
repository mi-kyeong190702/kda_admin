package com.ant.common.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
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
import com.ant.common.util.CommonUtil;


public class RequestFilter implements Filter {

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
		
		
		logger.debug("===========[REQUEST] start " + url + "?" + req.getQueryString());	
		
		// parameter
		if(isMultipartRequest(req)) {
            try {
                HashMap<Object, Object> formMap = CommonUtil.getRequestForm(req);
                
                Iterator it = formMap.keySet().iterator();
                
                while(it.hasNext()) {
                    Object key = it.next();
                    Object valueObject = formMap.get(key);
                    
                    if(valueObject != null) {
                        if(valueObject.getClass().isArray()) {
                            Object[] valueArray = (Object[])valueObject;
                            
                            StringBuffer valueString = new StringBuffer();
                            valueString.append("[");
                            for(Object value : valueArray) {
                                valueString.append(CommonUtil.toString(value));
                                valueString.append(",");
                            }
                            valueString.append("]");
                            logger.debug("[[ " + url + "[MP]==>[P]" + CommonUtil.toString(key) + ":" + valueString.toString());
                        }
                        else {
                            logger.debug("[[ " + url + "[MP]==>[P]" + CommonUtil.toString(key) + ":" + CommonUtil.toString(valueObject));
                        }
                    }
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
		else {
    		Enumeration<String> paramkeys = req.getParameterNames();
    		while(paramkeys.hasMoreElements()) {
    			String key = paramkeys.nextElement();
    			String[] paramValues = req.getParameterValues(key);
    			StringBuffer value = new StringBuffer();
    			value.append("(length=" + paramValues.length + ")");
    			for(int i=0; i < paramValues.length; i++) {
    				if(i > 0) {
    					value.append(",");
    				}
    				
    				
    				if(paramValues[i].getClass().isArray()) {
    				    value.append("Array=>" + paramValues[i]);
    				}
    				else {
    				    value.append(paramValues[i]);
    				}
    			}
    			logger.debug("<< " + url + "==>[P]" + key + ":" + value.toString());
    		}	
		}
		// request
		Enumeration<String> reqKeys = req.getAttributeNames();
		while(reqKeys.hasMoreElements()) {
			String key = reqKeys.nextElement();
			Object reqValue = req.getAttribute(key);
			logger.debug("<< " + url + "==>[R]" + key + ":" + reqValue);
		}	
		
		
		
		// session
		HttpSession session = req.getSession();
		Enumeration<String> sesKeys = session.getAttributeNames();
		while(sesKeys.hasMoreElements()) {
			String key = sesKeys.nextElement();
			Object sesValue = session.getAttribute(key);
			logger.debug("<< " + url + "==>[S]" + key + ":" + sesValue);
		}
		
		chain.doFilter(request, response);
		
		logger.debug("===========[REQUEST] end " + url  + "?" + req.getQueryString());
    }
    
    
    public boolean isMultipartRequest(HttpServletRequest request) {
        String contentType = request.getHeader("Content-Type");
        return (contentType != null && contentType.indexOf("multipart/form-data") != -1);
    }
    
    public void getFormValue(HttpServletRequest req, HashMap<Object, Object> map) {
        String param = "";
        String[] values;
        try {
            Enumeration paramNames = req.getParameterNames();
            if (paramNames != null) {
                while (paramNames.hasMoreElements()) {
                    param = (String) paramNames.nextElement();
                    values = req.getParameterValues(param);

                    if (values != null) {
                        if (values.length == 1)
                            map.put(param, values[0]);
                        else if (values.length > 1)
                            map.put(param, values);
                        else if (values.length < 1)
                            map.put(param, "");
                    } else
                        map.put(param, "");
                }
            }

        } catch (Exception ex) {
            logger.error(this.getClass().getName() + " getFormValue() Error occurred while process action: " + ex.getMessage(), ex);
        }
    }
}