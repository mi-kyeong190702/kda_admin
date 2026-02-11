package com.ant.common.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

public class EmailSender {
    
	private static Logger logger = Logger.getRootLogger();    
    private Properties props;
	 /**
     * 작성자: 박상모
     * 내용: 이메일 전송을 위한 메소드
     */
	public EmailSender(Properties props){
		this.props=props;
	}

	public void sendEmail(String from, String fr_name, String to, String subj, String content,String upYN,String realFolder,String filename){
		//EmailAuthenticator auth=new EmailAuthenticator();
		//Session session=Session.getInstance(props,auth);
		Session session=Session.getInstance(props,null);
		
		try{
			MimeMessage msg=new MimeMessage(session);
			msg.setFrom(new InternetAddress(from, fr_name, "UTF-8"));  //보내는 사람 메일 주소
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); //받는 사람 메일 주소
			msg.setSubject(subj); //메일 제목
			msg.setContent(content,"text/html; charset=UTF-8"); //메일 내용			
			msg.setSentDate(new Date());  //보낸 날짜			
			
			MimeBodyPart mbp=new MimeBodyPart();
			mbp.setContent(content,"text/html; charset=UTF-8");
			MimeMultipart mp=new MimeMultipart();
			mp.addBodyPart(mbp);
			if(upYN.equals("y")){
				MimeBodyPart att=new MimeBodyPart();
				att.setDataHandler(new DataHandler(new FileDataSource(realFolder+"/"+filename)));		
				//att.setFileName(filename);
				att.setFileName(MimeUtility.encodeText(filename,"KSC5601","B"));
				mp.addBodyPart(att);
			}
			msg.setContent(mp);
			
			session.setDebug(true);
			Transport.send(msg);
			System.out.println("메일발송 완료.");
		}catch(MessagingException e){
			e.printStackTrace();
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		File f=new File(realFolder+"/"+filename);
		if(f!=null&&f.exists()){
			f.delete();
		}
	}
	
	class EmailAuthenticator extends Authenticator{
		private String id;
		private String pw;
		
		public EmailAuthenticator(){
			this.id="cocone";
			this.pw="qkrtkdah78";
		}
		
		public EmailAuthenticator(String id, String pw){
			this.id=id;
			this.pw=pw;
		}
		protected PasswordAuthentication getPasswordAuthentication(){
			return new PasswordAuthentication(id, pw);
		}
	}
	/*20160204 email send test*/
	public static void main(String args[]){
		String filename="";
		String upYN="";
		Properties mailProps=new Properties();
		//gmail을 이용할 경우 
		//mailProps.put("mail.smtp.host", "smtp.gmail.com");
		//mailProps.put("mail.smtp.port", "587");
		//mailProps.put("mail.smtp.auth", "true");
		//mailProps.put("mail.smtp.starttls.enable", "true");
		mailProps.put("mail.smtp.host", "localhost");
		EmailSender esender=new EmailSender(mailProps);
		esender.sendEmail("test", "test1", "hoperoid@naver.com", "testsubjects", "testcontent","","","");
		System.out.println("메일발송 테스트 완료.");

	}
	
}
