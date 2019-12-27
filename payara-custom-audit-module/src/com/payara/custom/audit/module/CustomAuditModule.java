package com.payara.custom.audit.module;

import java.text.MessageFormat;
import java.time.LocalDateTime;

import org.apache.logging.log4j.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

import com.payara.jdbc.module.CloudDatabaseModule;
import com.sun.appserv.security.AuditModule;

public class CustomAuditModule extends AuditModule {

	private static final Logger logger = LogManager.getLogger(CustomAuditModule.class);

	@Override
	public void authentication(String user, String realm, boolean success) {
		if (!success) {
			this.appendAudit(MessageFormat.format(
					"The user {0} has made an invalid authentication attempt at {1} from realm ({2}). ", user,
					LocalDateTime.now(), realm));
		}
	}

	@Override
	public void webInvocation(String user, HttpServletRequest request, String type, boolean success) {
		if (!success) {
			this.appendAudit(MessageFormat.format(
					"The user {0} has made an invalid authorization attempt at {1} , trying to access resource {2}  on method {3}  from realm ({4}).",
					user, LocalDateTime.now(), request.getRequestURI(), request.getMethod()));
		}
	}

	@Override
	public void ejbInvocation(String user, String ejb, String method, boolean success) {
		if (!success) {
			this.appendAudit(MessageFormat.format(
					"The user {0}  has made an invalid authorization attempt at {1} , trying to access EJB {2} , method {3} . ",
					user, LocalDateTime.now(), ejb, method));
		}
	}

	private void appendAudit(String audit) {
		appendAuditWithLogging(audit);
		appendAuditWithJDBC(audit);
	}

	private void appendAuditWithJDBC(String audit) {
		CloudDatabaseModule module = new CloudDatabaseModule();
		module.appendInvalidLogin(audit, CustomAuditModule.class.getName(), Level.WARN.name());
	}

	private void appendAuditWithLogging(String audit) {
		logger.warn(audit);
	}

}