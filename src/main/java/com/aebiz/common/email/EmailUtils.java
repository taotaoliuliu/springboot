package com.aebiz.common.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

public class EmailUtils {

	
	
	  private static final String ALIDM_SMTP_HOST = "smtpdm.aliyun.com";
	    private static final int ALIDM_SMTP_PORT = 25;
	 
	    
		  private static final String reciever = "343515512@qq.com";

	    public static void main(String[] args) throws Exception {
	        // 配置发送邮件的环境属性
	        final Properties props = new Properties();
	        // 表示SMTP发送邮件，需要进行身份验证
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.host", ALIDM_SMTP_HOST);
	        props.put("mail.smtp.port", ALIDM_SMTP_PORT);   
	        // 如果使用ssl，则去掉使用25端口的配置，进行如下配置, 
	        // props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        // props.put("mail.smtp.socketFactory.port", "465");
	        // props.put("mail.smtp.port", "465");
	 
	 
	        // 发件人的账号
	        props.put("mail.user", "***");
	        // 访问SMTP服务时需要提供的密码
	        props.put("mail.password", "***");
	 
	        // 使用环境属性和授权信息，创建邮件会话
	        Session mailSession = Session.getDefaultInstance(props, null);
	        // 创建邮件消息
	        MimeMessage message = new MimeMessage(mailSession);
	        // 设置发件人
	        InternetAddress form = new InternetAddress(
	                props.getProperty("mail.user"));
	        message.setFrom(form);
	 
	        // 设置收件人
	        InternetAddress to = new InternetAddress(reciever);
	        message.setRecipient(MimeMessage.RecipientType.TO, to);
	 
	        // 设置邮件标题
	        message.setSubject("测试邮件");
	        // 设置邮件的内容体
	        message.setContent("测试的HTML邮件", "text/html;charset=UTF-8");
	 
	        // 发送邮件
	        Transport.send(message);
	    }
	    
	    
	    @Test
	    public void test() throws Exception{
	    	
	    	String user="lsl38721100@163.com";
	    	String pwd="lsl38721100";
	    	
	    	
	    	// 创建Properties对象
			Properties props = System.getProperties();
			// 创建信件服务器
			props.put("mail.smtp.localhost", "localHostAdress");
			props.put("mail.smtp.auth", "true");
			//######### 阿里云
			props.setProperty("mail.smtp.port", "465");
			 props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			 props.setProperty("mail.smtp.socketFactory.fallback", "false");

			 props.setProperty("mail.smtp.socketFactory.port", "465");
			//######### 阿里云
			// 得到默认的对话对象
			Session session = Session.getDefaultInstance(props, null);
	    	
			MimeMessage msg = createSimpleMail(session);
			// 创建一个消息，并初始化该消息的各项元素
			//Message msg = new MimeMessage(session);
			
			
			// 发送信件
						Transport trans = session.getTransport("smtp");
						trans.connect("smtp.163.com", user, pwd);
						if (trans.isConnected()) {
							trans.sendMessage(msg, msg.getAllRecipients());
							trans.close();
							
						}
	    	
	    }
	    
	    
	    public static MimeMessage createSimpleMail(Session session)
	                 throws Exception {
	    	
	    	String user="lsl38721100@163.com";
	            //创建邮件对象
	             MimeMessage message = new MimeMessage(session);
	             //指明邮件的发件人
	             message.setFrom(new InternetAddress(javax.mail.internet.MimeUtility.encodeText("刘胜利")));
	            //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
	             // 设置收件人
	 	        InternetAddress to = new InternetAddress(reciever);
	 	        message.setRecipient(MimeMessage.RecipientType.TO, to);	             //邮件的标题
	            message.setSubject("改密码提醒");
	            //邮件的文本内容
	             message.setContent("在刚刚哈萨克斯坦国内的一则电视讲话上，自苏联1991年解体以来便一直担任国家领导人的总统纳扎尔巴耶夫宣布自己将辞去总统的职务，并由参议院议长托卡耶夫（Tokayev）在过渡期履行总统职务，之后哈萨克斯坦会举行总统选举，选出新一任总统", "text/html;charset=UTF-8");
	             //返回创建好的邮件对象
	             
	             InternetAddress[] address = { new InternetAddress(user) };

	             message.addRecipients(Message.RecipientType.CC,address); //朝东给自己
	             return message;
	        }
}
