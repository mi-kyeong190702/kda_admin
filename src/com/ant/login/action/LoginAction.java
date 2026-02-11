package com.ant.login.action;

import java.util.HashMap;
import com.ant.login.dao.LoginDao;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LoginAction extends DispatchAction {
	protected static Logger logger = Logger.getRootLogger();
	public static String SUCCESS = "success";
	
	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("into LoginAction ==> Forward Login ");
		return mapping.findForward("login");
	}
	  
	//성공하면 OTP 입력으로
	public ActionForward otp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		LoginDao dao = new LoginDao();
		
		HttpSession session = request.getSession();
		request.setAttribute("loginStatus", "");
		
		LoginManager loginManager = LoginManager.getInstance();
		
		System.out.println("into LoginAction ==> Forward OTP ");
		
		System.out.println("into LoginAction u_id : " + request.getParameter("u_id"));
		System.out.println("into LoginAction u_password : " + request.getParameter("u_password"));
		
		map.put("usrId", request.getParameter("u_id"));
		map.put("usrPw", request.getParameter("u_password"));
		
		//기존 로그인 검증
		List result = dao.selectListLogin(map);
		if (result == null || result.size() < 1) {
			// case1. ID가 없을 때
			session.setAttribute("G_ID", "");
			session.setAttribute("G_NAME", "");
			
			request.setAttribute("loginStatus", "Not Found your ID!");
			//return (mapping.findForward("loginFail"));
			return mapping.findForward("loginFail");
	    }
		 
		Object persStateObj = ((Map)result.get(0)).get("pers_state");
		if (persStateObj == null) {
			request.setAttribute("loginStatus", "Pers state is not normal!");
			return mapping.findForward("loginDeny");
		}
		String pers_state = persStateObj.toString();
		
		System.out.println("pers_state====> " + ((!pers_state.equals("01")) && (!pers_state.equals("07"))));
		
		//String db_select_password = result.get(0).get("PWCHK").toString(); 
		Object pwchkObj = ((Map)result.get(0)).get("PWCHK");
		if (pwchkObj == null) {
			request.setAttribute("loginStatus", "No Match your Password!");
			return mapping.findForward("loginDeny");
		}
		String db_select_password = pwchkObj.toString();
		
		if (db_select_password.equals("N")) {
			//case2. 패스워드가 맞지 않을 때 
			session.setAttribute("G_ID", "");
			session.setAttribute("G_NAME", "");
			
			request.setAttribute("loginStatus", "No Match your Password!");
			System.out.println("==========================");
	        //return (mapping.findForward("loginDeny"));
			return mapping.findForward("loginDeny");
			
		} else {
			// case3. ID와 패스워드가 맞을때
			
			session.setAttribute("OTP_FAIL", 1);
			
		    if ((!pers_state.equals("01")) && (!pers_state.equals("07"))) {
		    	request.setAttribute("loginStatus", "Pers state is not normal!");
		    	// return (mapping.findForward("loginDeny"));
		    	return mapping.findForward("loginDeny");
		    }
		    
		    Object saltObj = ((Map) result.get(0)).get("PER_USER_SALT");
		    Object saltChk = ((Map) result.get(0)).get("SALT_CHK");
		    String salt = (saltObj == null) ? null : String.valueOf(saltObj);
		    String saltYn = (saltChk == null) ? "0" : String.valueOf(saltChk);
		    
		    Object userIdObj = ((Map)result.get(0)).get("userid");
		    if (userIdObj == null) {
		    	request.setAttribute("loginStatus", "User ID is missing!");
		    	return mapping.findForward("loginDeny");
		    }
		    String userId = userIdObj.toString(); 
		   
		    
		    if (!loginManager.isUsing(userId)){
		    	//case3-1. 동일 ID로 접속되지 않았을 때
		    	
		    	session.setAttribute("G_ID2", userId);
		    	session.setAttribute("G_PW", request.getParameter("u_password"));
		    	
		    	Object usernameObj = ((Map)result.get(0)).get("username");
		    	Object userbranObj = ((Map)result.get(0)).get("userbran");
		    	session.setAttribute("G_NAME", usernameObj != null ? usernameObj.toString() : "");
		    	session.setAttribute("G_BRAN", userbranObj != null ? userbranObj.toString() : "");
		    	
		    	if (saltObj != null) {
		    		session.setAttribute("SALT", saltObj.toString());
		    	}
		    	session.setAttribute("PER_USER_SALT", salt); // OTP 검증을 위해 세션에 저장
		    	
		    	request.setAttribute("loginStatus", "null");
		    	loginManager.setSession(session, userId);
		    	
		    	System.out.println("==========================");
		    	System.out.println("G_NAME :" + (usernameObj != null ? usernameObj.toString() : ""));
		    	System.out.println("G_BRAN :" + (userbranObj != null ? userbranObj.toString() : ""));
		    	System.out.println("==========================");
		    	
		    	 // 로그인 사용자 체크 후 프로그램 권한을 세션변수에 담아 보낸다.
		    	List userpowerm = dao.userPower(map);
		    	JSONArray userpowerm1 = JSONArray.fromObject(userpowerm);
		    	session.setAttribute("userpowerm", userpowerm1);
		    	
		    	if (userId.equals("dongbucni")){
		    		session.setAttribute("G_ID", userId);
		    		return mapping.findForward("login_ok");
				}

			    /*salt 값이 0이면 최초이므로 QR을 등록하도록 페이지 이동을 한다. */
			    if ("0".equals(saltYn)) {
			    	
			        String secret = com.ant.auth.TotpSecretDeriver.deriveBase32Secret(userId, salt);
			        String uri = com.ant.auth.TotpProvisioning.buildOtpAuthUri("dietitian", userId, secret);

			        request.setAttribute("base32Secret", secret);
			        request.setAttribute("otpUri", uri);
			          
			        // JSP 경로는 프로젝트에 맞게
			        return mapping.findForward("otp_enroll");
			    } 
		    	  
		    	return mapping.findForward("otp");

		    } else {
				//case3-2. 동일 ID로 접속됐을 때
		    	
		    	Object usernameObj2 = ((Map)result.get(0)).get("username");
		    	Object userbranObj2 = ((Map)result.get(0)).get("userbran");
		    	
		    	loginManager.setSession(session, userId);

		        session.setAttribute("G_ID2", userId);
		        session.setAttribute("G_PW", request.getParameter("u_password"));
		        session.setAttribute("G_NAME", usernameObj2 != null ? usernameObj2.toString() : "");
		        session.setAttribute("G_BRAN", userbranObj2 != null ? userbranObj2.toString() : "");
		        if (saltObj != null) {
		        	session.setAttribute("SALT", saltObj.toString());
		        }
		        session.setAttribute("PER_USER_SALT", salt); // OTP 검증을 위해 세션에 저장
		        request.setAttribute("loginStatus", "null");

		        System.out.println("==========================");
		        System.out.println("G_NAME :" + (usernameObj2 != null ? usernameObj2.toString() : ""));
		        System.out.println("G_BRAN :" + (userbranObj2 != null ? userbranObj2.toString() : ""));
		        System.out.println("==========================");
		        
		        // 로그인 사용자 체크 후 프로그램 권한을 세션변수에 담아 보낸다.
		        List userpowerm = dao.userPower(map);
		        JSONArray userpowerm1 = JSONArray.fromObject(userpowerm);
		        session.setAttribute("userpowerm", userpowerm1);
		        request.setAttribute("loginStatus", "Login with same id!");
		        
		        if (userId.equals("dongbucni")){
		        	session.setAttribute("G_ID", userId);
		    		return mapping.findForward("login_ok");
				}

			    /*salt 값이 0이면 최초이므로 QR을 등록하도록 페이지 이동을 한다. */
			    if ("0".equals(saltYn)) {
			    	
			        String secret = com.ant.auth.TotpSecretDeriver.deriveBase32Secret(userId, salt);
			        String uri = com.ant.auth.TotpProvisioning.buildOtpAuthUri("dietitian", userId, secret);

			        System.out.println(uri);
			        request.setAttribute("base32Secret", secret);
			        request.setAttribute("otpUri", uri);

			        // JSP 경로는 프로젝트에 맞게
			        return mapping.findForward("otp_enroll");
			    } 
		    	
		        
		        return mapping.findForward("otp");
		    }
		} 
	}
	
	public ActionForward otpEnrollVerify(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession s = request.getSession(false);
		 
		if (s == null) return mapping.findForward("login");
		
		// 비번 통과했는지(otp 단계인지) 간단 체크: G_ID가 세션에 있어야 함
		 
		String userId = (String) s.getAttribute("G_ID2");
		if (userId == null || userId.length() == 0) { 
			return mapping.findForward("login");
		}
		
		// 잠금/레이트리밋(세션 기반)
		Integer fail = (Integer) s.getAttribute("OTP_FAIL"); if (fail == null) fail = 0;
		Long lock = (Long) s.getAttribute("OTP_LOCK_UNTIL");
		long now = System.currentTimeMillis();
		if (lock != null && now < lock) {
			request.setAttribute("lock", "1");
			return mapping.findForward("otp");
		}
		 
		
		String perUserSalt = (String) s.getAttribute("PER_USER_SALT");
		// 세션에 없으면 DB에서 조회
		if (perUserSalt == null) {
			Map map = new HashMap();
			map.put("usrId", userId);
			LoginDao dao = new LoginDao();
			List result = dao.selectListLogin(map);
			if (result != null && result.size() > 0) {
				Object saltObj = ((Map) result.get(0)).get("PER_USER_SALT");
				perUserSalt = (saltObj == null) ? null : String.valueOf(saltObj);
				s.setAttribute("PER_USER_SALT", perUserSalt);
			}
		}
		String secret = com.ant.auth.TotpSecretDeriver.deriveBase32Secret(userId, perUserSalt);
		String uri    = com.ant.auth.TotpProvisioning.buildOtpAuthUri("dietitian", userId, secret);
		
		// 코드 파싱
		String codeStr = request.getParameter("code");
	    if (codeStr == null || !codeStr.matches("\\d{6}")) {
	        request.setAttribute("e", "1");
	        request.setAttribute("base32Secret", secret);
	        request.setAttribute("otpUri", uri);
	        return mapping.findForward("otp_enroll");
	    }
	    
	    int code = Integer.parseInt(codeStr);
	    
		// TOTP 검증
	    com.warrenstrange.googleauth.GoogleAuthenticatorConfig cfg =
	            new com.warrenstrange.googleauth.GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder()
	                .setWindowSize(3).setTimeStepSizeInMillis(30_000).setCodeDigits(6).build();
	        com.warrenstrange.googleauth.GoogleAuthenticator g =
	            new com.warrenstrange.googleauth.GoogleAuthenticator(cfg);
		
		boolean ok = g.authorize(secret, code);
		  
		if (!ok || (lock != null && now < lock)) {
	        fail++;
	        s.setAttribute("OTP_ENROLL_FAIL", fail);
	        if (fail >= 5) { // 5회 실패 → 5분 잠금
	            s.setAttribute("OTP_ENROLL_LOCK_UNTIL", now + 5 * 60 * 1000L);
	            request.setAttribute("lock", "1");
	        } else {
	            request.setAttribute("e", "1");
	        }
	        request.setAttribute("base32Secret", secret);
	        request.setAttribute("otpUri", uri);
	        return mapping.findForward("otp_enroll");
	    }
		
		// ===== OTP 성공: 최종 로그인 처리 =====
		s.removeAttribute("OTP_FAIL");
		s.removeAttribute("OTP_LOCK_UNTIL");
		
		Map map = new HashMap();
		LoginDao dao = new LoginDao(); 
		
		String userId2 = (String) s.getAttribute("G_ID2");
		s.setAttribute("G_ID", userId2);
		
		map.put("usrId", (String) s.getAttribute("G_ID")); 		
		
		dao.updateSALT(map); 
		request.setAttribute("loginStatus", "null");
		
		s.removeAttribute("OTP_ENROLL_FAIL");
		s.removeAttribute("OTP_ENROLL_LOCK_UNTIL");
		 
		return mapping.findForward("login_ok");
	}
	
	//otp 번호 확인
	public ActionForward otpVerify(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession s = request.getSession(false);
		if (s == null) return mapping.findForward("login");
		
		// 비번 통과했는지(otp 단계인지) 간단 체크: G_ID가 세션에 있어야 함
		 
		String userId = (String) s.getAttribute("G_ID2");
		if (userId == null || userId.length() == 0) { 
			return mapping.findForward("login");
		}
		
		String perUserSalt = (String) s.getAttribute("PER_USER_SALT");
		// 세션에 없으면 DB에서 조회
		if (perUserSalt == null) {
			Map map = new HashMap();
			map.put("usrId", userId);
			LoginDao dao = new LoginDao();
			List result = dao.selectListLogin(map);
			if (result != null && result.size() > 0) {
				Object saltObj = ((Map) result.get(0)).get("PER_USER_SALT");
				perUserSalt = (saltObj == null) ? null : String.valueOf(saltObj);
				s.setAttribute("PER_USER_SALT", perUserSalt);
			}
		}
		String secret = com.ant.auth.TotpSecretDeriver.deriveBase32Secret(userId, perUserSalt);
		String uri    = com.ant.auth.TotpProvisioning.buildOtpAuthUri("dietitian", userId, secret);
		
		
	    
	    
	    // 잠금/레이트리밋(세션 기반)
	    final String ATTR_FAIL = "OTP_FAIL";
	    final String ATTR_LOCK = "OTP_LOCK_UNTIL";

	    Integer fail = (Integer) s.getAttribute(ATTR_FAIL);
	    if (fail == null) fail = 0;
	    
	    
	    Long lock = (Long) s.getAttribute(ATTR_LOCK);
	    long now = System.currentTimeMillis();
	    
	    if (lock != null && now < lock) {
	        request.setAttribute("lock", "1");
	        return mapping.findForward("otp");
	    }
	    
	    // 코드 파싱
 		String codeStr = request.getParameter("code");
 	    if (codeStr == null || !codeStr.matches("\\d{6}")) {
 	        request.setAttribute("e", "1");
 	        request.setAttribute("base32Secret", secret);
 	        request.setAttribute("otpUri", uri);
 	        return mapping.findForward("otp_enroll");
 	    }
	    
	    int code = Integer.parseInt(codeStr);
	    
		// TOTP 검증
	    com.warrenstrange.googleauth.GoogleAuthenticatorConfig cfg =
	            new com.warrenstrange.googleauth.GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder()
	                .setWindowSize(3).setTimeStepSizeInMillis(30_000).setCodeDigits(6).build();
	        com.warrenstrange.googleauth.GoogleAuthenticator g =
	            new com.warrenstrange.googleauth.GoogleAuthenticator(cfg);
		
		boolean ok = g.authorize(secret, code);
		
		
		if (!ok || (lock != null && now < lock)) {
	        fail++;
	        s.setAttribute("OTP_FAIL", fail);
	        if (fail >= 5) { // 5회 실패 → 5분 잠금
	            s.setAttribute("OTP_LOCK_UNTIL", now + 5 * 60 * 1000L);
	            request.setAttribute("lock", "1");
	        } else {
	            request.setAttribute("e", "1");
	        } 
	        return mapping.findForward("otp");
	    }
		
		
		String userId2 = (String) s.getAttribute("G_ID2");
		s.setAttribute("G_ID", userId2);
		s.removeAttribute(ATTR_FAIL);
    	s.removeAttribute(ATTR_LOCK);
	    
		return mapping.findForward("login_ok");  
	}
	
	public ActionForward otp_ok(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return mapping.findForward("loginDeny");
	}
	
	
	//기존 로그인체크
	public ActionForward login_ok(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		LoginDao dao = new LoginDao();
		
		HttpSession session = request.getSession();
		request.setAttribute("loginStatus", "");
		
		LoginManager loginManager = LoginManager.getInstance();
		
		System.out.println("into LoginAction u_id : " + request.getParameter("u_id"));
		System.out.println("into LoginAction u_password : " + request.getParameter("u_password"));
		
		map.put("usrId", request.getParameter("u_id"));
		map.put("usrPw", request.getParameter("u_password"));
		
		//List<Map> result = dao.selectListLogin(map);
		List result = dao.selectListLogin(map);
		
		//2025.02.07 null 처리
		if (result.size() < 1) {
			// case1. ID가 없을 때
			session.setAttribute("G_ID", "");
			session.setAttribute("G_NAME", "");
			
			request.setAttribute("loginStatus", "Not Found your ID!");
			//return (mapping.findForward("loginFail"));
			return mapping.findForward("loginFail");
	    }
		
		//String pers_state = result.get(0).get("pers_state").toString();
		String pers_state = ((Map)result.get(0)).get("pers_state").toString();
		
		System.out.println("pers_state====> " + ((!pers_state.equals("01")) && (!pers_state.equals("07"))));
		
		//String db_select_password = result.get(0).get("PWCHK").toString(); 
		String db_select_password = ((Map)result.get(0)).get("PWCHK").toString();
		
		if (db_select_password.equals("N")) {
			//case2. 패스워드가 맞지 않을 때 
			session.setAttribute("G_ID", "");
			session.setAttribute("G_NAME", "");
			
			request.setAttribute("loginStatus", "No Match your Password!");
			System.out.println("==========================");
	        //return (mapping.findForward("loginDeny"));
			return mapping.findForward("loginDeny");
			
		} else {
			// case3. ID와 패스워드가 맞을때
			
		    if ((!pers_state.equals("01")) && (!pers_state.equals("07"))) {
		    	request.setAttribute("loginStatus", "Pers state is not normal!");
		    	// return (mapping.findForward("loginDeny"));
		    	return mapping.findForward("loginDeny");
		    }

		    /*
				if(!loginManager.isUsing(result.get(0).get("userid").toString())){
					//case3-1. 동일 ID로 접속되지 않았을 때
					session.setAttribute("G_ID",result.get(0).get("userid").toString());
					session.setAttribute("G_PW",request.getParameter("u_password"));
					session.setAttribute("G_NAME",result.get(0).get("username").toString());
				    session.setAttribute("G_BRAN",result.get(0).get("userbran").toString());
				    
			        request.setAttribute("loginStatus" , "null");
				    loginManager.setSession(session, result.get(0).get("userid").toString());
				    System.out.println("==========================");
				    System.out.println("G_NAME :" + result.get(0).get("username").toString());
				    System.out.println("G_BRAN :" + result.get(0).get("userbran").toString());    
				    System.out.println("==========================");

				    // 로그인 사용자 체크 후 프로그램 권한을 세션변수에 담아 보낸다.
				    List<Map> userpowerm = dao.userPower(map);
				    JSONArray userpowerm1=JSONArray.fromObject(userpowerm);
				  	session.setAttribute("userpowerm", userpowerm1);
				  	
				    // 로그인 히스토리 INSERT
				  	this.insertLoginHist(result,request);
				    
				  	return (mapping.findForward("login_ok"));
				}else{
					//case3-2. 동일 ID로 접속됐을 때
					//loginManager.removeSession(result.get(0).get("userid").toString());
					loginManager.setSession(session, result.get(0).get("userid").toString());
					
					session.setAttribute("G_ID",result.get(0).get("userid").toString());
					session.setAttribute("G_PW",request.getParameter("u_password"));
				    session.setAttribute("G_NAME",result.get(0).get("username").toString());
				    session.setAttribute("G_BRAN",result.get(0).get("userbran").toString());				    
			        request.setAttribute("loginStatus" , "null");
				    
				    System.out.println("==========================");
				    System.out.println("G_NAME :" + result.get(0).get("username").toString());
				    System.out.println("G_BRAN :" + result.get(0).get("userbran").toString());    
				    System.out.println("==========================");

				    // 로그인 사용자 체크 후 프로그램 권한을 세션변수에 담아 보낸다.
				    List<Map> userpowerm = dao.userPower(map);
				    JSONArray userpowerm1=JSONArray.fromObject(userpowerm);
				  	session.setAttribute("userpowerm", userpowerm1);
					request.setAttribute("loginStatus" , "Login with same id!");
					return (mapping.findForward("login_ok"));
				}
		     */
		    if (!loginManager.isUsing(((Map)result.get(0)).get("userid").toString())){
		    	//case3-1. 동일 ID로 접속되지 않았을 때
		    	
		    	session.setAttribute("G_ID", ((Map)result.get(0)).get("userid").toString());
		    	session.setAttribute("G_PW", request.getParameter("u_password"));
		    	session.setAttribute("G_NAME", ((Map)result.get(0)).get("username").toString());
		    	session.setAttribute("G_BRAN", ((Map)result.get(0)).get("userbran").toString());
		    	
		    	request.setAttribute("loginStatus", "null");
		    	loginManager.setSession(session, ((Map)result.get(0)).get("userid").toString());
		    	
		    	System.out.println("==========================");
		    	System.out.println("G_NAME :" + ((Map)result.get(0)).get("username").toString());
		    	System.out.println("G_BRAN :" + ((Map)result.get(0)).get("userbran").toString());
		    	System.out.println("==========================");

			    // 로그인 사용자 체크 후 프로그램 권한을 세션변수에 담아 보낸다.
		    	List userpowerm = dao.userPower(map);
		    	JSONArray userpowerm1 = JSONArray.fromObject(userpowerm);
		    	session.setAttribute("userpowerm", userpowerm1);
		    	
		    	return mapping.findForward("login_ok");

		    } else {
				//case3-2. 동일 ID로 접속됐을 때
		    	
		    	loginManager.setSession(session, ((Map)result.get(0)).get("userid").toString());

		        session.setAttribute("G_ID", ((Map)result.get(0)).get("userid").toString());
		        session.setAttribute("G_PW", request.getParameter("u_password"));
		        session.setAttribute("G_NAME", ((Map)result.get(0)).get("username").toString());
		        session.setAttribute("G_BRAN", ((Map)result.get(0)).get("userbran").toString());
		        request.setAttribute("loginStatus", "null");

		        System.out.println("==========================");
		        System.out.println("G_NAME :" + ((Map)result.get(0)).get("username").toString());
		        System.out.println("G_BRAN :" + ((Map)result.get(0)).get("userbran").toString());
		        System.out.println("==========================");

		        // 로그인 사용자 체크 후 프로그램 권한을 세션변수에 담아 보낸다.
		        List userpowerm = dao.userPower(map);
		        JSONArray userpowerm1 = JSONArray.fromObject(userpowerm);
		        session.setAttribute("userpowerm", userpowerm1);
		        request.setAttribute("loginStatus", "Login with same id!");
		        return mapping.findForward("login_ok");
		    }
		}
	}
	
	public ActionForward loginFail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("into LoginAction ==> Forward Login ");
		return mapping.findForward("login");
	}
	
	public ActionForward loginDeny(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("into LoginAction ==> Forward Login ");
		return mapping.findForward("login");
	}
}