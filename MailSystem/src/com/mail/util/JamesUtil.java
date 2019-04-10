package com.mail.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
 
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
 
public class JamesUtil
{
	public static void sendMail(String user, String password, String[] to, String subject, String text) throws Exception
	{
		String host = "WDNMD.com";
		//final String from = "priyu";
		//final String password = "sakura233";
		String from =user+"@WDNMD.com";
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.transport.protocol", "smtp");
 
		Session session = Session.getDefaultInstance(properties,
				new Authenticator()
				{
					@Override
					public PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(from, password);
					}
				});
 
		session.setDebug(true);
 
		Message msg = new MimeMessage(session);
		msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
 
		msg.setSubject(subject);
		msg.setText(text);
 
		Transport transport = session.getTransport();
 
		// 端口为25
		transport.connect(host, 25, from, password);
		Address address[]=new Address[to.length];
		for(int i=0;i<to.length;i++) {
			address[i]=new InternetAddress(to[i]+"@WDNMD.com");
		}
		transport.sendMessage(msg, address);
		transport.close();
		System.out.println("发好了！");
	}
	
	public static void receiveMail(String user, String password)
	   {   
		 String protocol = "pop3";//使用pop3协议
	        boolean isSSL = false;//使用SSL加密
	        String host = "WDNMD.com";//QQ邮箱的pop3服务器
	        int port = 110;//端口
	       // String username = "suru";//用户名
	       // String password = "123";//密码

	        /*
	        *Properties是一个属性对象，用来创建Session对象
	        */
	        Properties props = new Properties();
	        //props.put("mail.pop3.ssl.enable", isSSL);
	        props.put("mail.pop3.host", host);
	        props.put("mail.pop3.port", port);
	        /*
	        *Session类定义了一个基本的邮件对话。
	        */
	        Session session = Session.getDefaultInstance(props);

	        /*
	         * Store类实现特定邮件协议上的读、写、监视、查找等操作。
	         * 通过Store类可以访问Folder类。 
	         * Folder类用于分级组织邮件，并提供照Message格式访问email的能力。
	         */
	        Store store = null;
	        Folder folder = null;
	        try {
	            store = session.getStore(protocol);
	            store.connect(user, password);

	            folder = store.getFolder("INBOX");
	            folder.open(Folder.READ_ONLY);//在这一步，收件箱所有邮件将被下载到本地

	            int size = folder.getMessageCount();//获取邮件数目
	            Message message = folder.getMessage(size);//取得最新的那个邮件
	            MimeMessage msg = (MimeMessage) message; 

	            //解析邮件内容
	            //String from = message.getFrom()[0].toString();
	            InternetAddress address = (InternetAddress) message.getFrom()[0]; 
	            String person = address.getPersonal(); 
	            if (person != null) { 
	                person = MimeUtility.decodeText(person) + " "; 
	            } else { 
	                person = ""; 
	            } 
	            String from = person + "<" + address.getAddress() + ">"; 
	            String subject = message.getSubject();
	            Date date = message.getSentDate();
	            
	            StringBuffer content = new StringBuffer(30); 
	            getMailTextContent(msg, content); 
	            System.out.println("邮件正文：" + content); 

	            System.out.println("From: " + from);
	            System.out.println("Subject: " + subject);
	            System.out.println("Date: " + date);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (folder != null) {
	                    folder.close(false);
	                }
	                if (store != null) {
	                    store.close();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        System.out.println("接收完毕！");
	  }
	
	public static void getMailTextContent(Part part, StringBuffer content) throws MessagingException, IOException { 
        //如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断 
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;  
        if (part.isMimeType("text/*") && !isContainTextAttach) { 
            content.append(part.getContent().toString()); 
        } else if (part.isMimeType("message/rfc822")) {  
            getMailTextContent((Part)part.getContent(),content); 
        } else if (part.isMimeType("multipart/*")) { 
            Multipart multipart = (Multipart) part.getContent(); 
            int partCount = multipart.getCount()-1; 
            for (int i = 0; i < partCount; i++) { 
                BodyPart bodyPart = multipart.getBodyPart(i); 
                getMailTextContent(bodyPart,content); 
            } 
        } 
    } 
 /*
	public static void receiveMail() throws Exception
	{
		String host = "WDNMD.com";
		final String username = "suru";
		final String password = "123";
 
		Properties properties = new Properties();
		properties.setProperty("mail.pop3.host", host);
		properties.setProperty("mail.pop3.auth", "true");
		properties.setProperty("mail.transport.protocol", "pop3");
 
		Session session = Session.getDefaultInstance(properties,
				new Authenticator()
				{
					@Override
					public PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(username, password);
					}
				});
 
		try
		{
			Store store = session.getStore("pop3");
			store.connect(host, username, password);
 
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY); // 打开
 
			Message message[] = folder.getMessages();
			int n = message.length;
			System.out.println("一共有 " + n + " 封");
			if (n > 0)
			{
				for (Message m : message)
				{
					System.out.println(m.getFrom()[0] + "-----------"
							+ m.getSubject());
					try
					{
						m.writeTo(System.out);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			folder.close(false);
			store.close();
 
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
		System.out.println("收好了！！");
	}*/
 
	public static void main(String[] args) throws Exception
	{
		String to[]= {"suru"};
		sendMail("priyu","sakura233",to,"hello","nihao!");
		 //receiveMail();
	}
}

