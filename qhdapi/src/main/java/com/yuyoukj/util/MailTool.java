package com.yuyoukj.util;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailTool {
	public static void main(String[] args) {
		String[] rs = { "guozhen.shen@kascend.com", "394619342@qq.com" };
		MailTool cn = new MailTool("smtp.126.com", "", "", rs);
		// 设置发件人地址、收件人地址和邮件标题
		cn.send("警报", "多盟广告填充过低,请确认是否正常。。。。。");
		// cn.send("QQ:"+args[0]+"\tPWD:"+args[1]);
	}

	private String host = "smtp.126.com"; // smtp服务器
	private String user = "sgz1116@126.com"; // 用户名
	private String pwd = "!dahuaxiyou"; // 密码
	private String from = ""; // 发件人地址
	private String[] receivers = {}; // 收件人地址

	public MailTool(String host, String from, String pwd, String[] receivers) {
		this.host = host;
		this.user = from;
		this.pwd = pwd;
		this.from = from;
		this.receivers = receivers;
	}

	public void send(String subject, String txt) {
		Properties props = new Properties();
		// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", host);
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", "true");
		// 用刚刚设置好的props对象构建一个session
		Session session = Session.getDefaultInstance(props);
		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(true);
		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			// 加载发件人地址
			message.setFrom(new InternetAddress(from));
			// 加载收件人地址
			// message.addRecipient(Message.RecipientType.TO, new
			// InternetAddress(to));
			Address[] tos = null;
			// 将要群发的邮箱地址存在了个字符串中 用 ；隔开
			if (receivers != null) {
				// 为每个邮件接收者创建一个地址
				tos = new InternetAddress[receivers.length];
				for (int i = 0; i < receivers.length; i++) {
					String s = receivers[i];
					tos[i] = new InternetAddress(s);
				}
			}
			message.setRecipients(Message.RecipientType.TO, tos);
			// 加载标题
			message.setSubject(subject);
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();
			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(txt);
			multipart.addBodyPart(contentPart);
			// 添加附件
			// BodyPart messageBodyPart = new MimeBodyPart();
			// DataSource source = new FileDataSource(affix);
			// 添加附件的内容
			// messageBodyPart.setDataHandler(new DataHandler(source));
			// 添加附件的标题
			// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
			// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
			// messageBodyPart.setFileName("=?GBK?B?"+
			// enc.encode(affixName.getBytes()()) + "?=");
			// multipart.addBodyPart(messageBodyPart);
			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			transport.connect(host, user, pwd);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
