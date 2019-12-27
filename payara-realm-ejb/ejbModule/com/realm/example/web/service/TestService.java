package com.realm.example.web.service;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
@Stateless
@LocalBean
public class TestService {

	@WebMethod
	@RolesAllowed("ADMIN")
	public String sayHello() {
		return "Hello Admin";
	}

	@WebMethod
	@RolesAllowed("DEFAULT")
	public String sayHi() {
		return "Hi User";
	}
}