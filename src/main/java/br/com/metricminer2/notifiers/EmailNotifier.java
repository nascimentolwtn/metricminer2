package br.com.metricminer2.notifiers;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Authenticator;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailNotifier implements NotifierWhenDone {
	
	private static final int DEFAULT_SMTP_PORT = 25;
	
	private String from;
	private List<String> receivers;
	private String subject;

	private String smtpHostName;
	private boolean sslOnConnect;
	private int smtpPort;
	private Authenticator authenticator;

	private EmailNotifier() {
		from = "metricminer@metricminer.org.br";
		subject = "MetricMiner2 - Study Done";
		this.receivers = new ArrayList<String>();
		
		sslOnConnect = true;
		smtpPort = DEFAULT_SMTP_PORT;
	}
	
	public EmailNotifier(String smtpHostName, String userName, String password, String requiredReceiver, String... receivers) {
		this();
		
		this.receivers.add(requiredReceiver);
		for (String receiver : receivers) {
			this.receivers.add(receiver);
		}
		
		this.authenticator = new DefaultAuthenticator(userName, password);
		this.smtpHostName = smtpHostName;
	}

	@Override
	public void notifyAfterMining(String message) throws NotifyMechanismException {
		try {
			Email email = new SimpleEmail();
			
			email.setHostName(smtpHostName);
			email.setSmtpPort(smtpPort);
			email.setAuthenticator(authenticator);
			email.setSSLOnConnect(sslOnConnect);
			email.setFrom(from);
			email.setSubject(subject);
			email.setMsg(message);
			
			for (String receiver : receivers) {
				email.addTo(receiver);
			}
			
			email.send();
		} catch (EmailException e) {
			throw new NotifyMechanismException(e);
		}
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public Authenticator getAuthenticator() {
		return authenticator;
	}

	public boolean isSSLOnConnect() {
		return sslOnConnect;
	}

	public String getSubject() {
		return subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}
	
	public void addReceiver(String receiver) {
		this.receivers.add(receiver);
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setSmtpHostName(String smtpHostName) {
		this.smtpHostName = smtpHostName;
	}

	public void setSslOnConnect(boolean sslOnConnect) {
		this.sslOnConnect = sslOnConnect;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}
	
}
