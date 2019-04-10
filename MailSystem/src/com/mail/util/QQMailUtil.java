package com.mail.util;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

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

public class QQMailUtil {
    public static void send(String[] args){
        try {
            String host = "smtp.qq.com";//这是QQ邮箱的smtp服务器地址
            String port = "465"; //端口号
            /*
            *Properties是一个属性对象，用来创建Session对象
            */
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", host);
            props.setProperty("mail.smtp.port", port);
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.ssl.enable", "true");//"true"
            props.setProperty("mail.smtp.connectiontimeout", "5000");
            final String user = "1339102284@qq.com";//用户名
            final String pwd = "iasznpcpvfyxbadf";//密码
            /*
            *Session类定义了一个基本的邮件对话。
            */
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    //登录用户名密码
                    return new PasswordAuthentication(user,pwd);
                }
            });
            session.setDebug(true);
            /*
            *Transport类用来发送邮件。
            *传入参数smtp，transport将自动按照smtp协议发送邮件。
            */
            Transport transport = session.getTransport("smtp");//"smtps"
            transport.connect(host,user,pwd);
            /*
            *Message对象用来储存实际发送的电子邮件信息
            */
            MimeMessage message = new MimeMessage(session);
            message.setSubject("邮件标题");
            //消息发送者接收者设置(发件地址，昵称)，收件人看到的昵称是这里设定的
            message.setFrom(new InternetAddress(user,"二师兄"));
            message.addRecipients(Message.RecipientType.TO,new InternetAddress[]{
                //消息接收者(收件地址，昵称)
                //不过这个昵称貌似没有看到效果
                    new InternetAddress("18570694330@163.com","大师兄"),
            });
            message.saveChanges();

            //设置邮件内容及编码格式
            //后一个参数可以不指定编码，如"text/plain"，但是将不能显示中文字符
            message.setContent("邮件内容..", "text/plain;charset=UTF-8");
            //发送
            //transport.send(message);
            Transport.send(message);
            transport.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
    
    public static void main(String[] args){
        String protocol = "pop3";//使用pop3协议
        boolean isSSL = false;//使用SSL加密
        String host = "shihualin.com";//QQ邮箱的pop3服务器
        int port = 110;//端口
        String username = "suru";//用户名
        String password = "123";//密码

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
            store.connect(username, password);

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
}
