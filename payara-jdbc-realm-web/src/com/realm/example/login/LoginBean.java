package com.realm.example.login;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


@Named
@RequestScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	public void login() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try {
			request.login(username, password);
			Principal principal = request.getUserPrincipal();
			if (null != principal) {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/securedPages/secure.html");
				System.err.println(principal.getName());
			}
		} catch (ServletException e) {
			e.printStackTrace();
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
