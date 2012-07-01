package com.sksamuel.camelwatch.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sksamuel.camelwatch.CamelConnection;
import com.sksamuel.camelwatch.CamelConnectionFactory;
import com.sksamuel.camelwatch.Endpoint;
import com.sksamuel.camelwatch.Route;

/**
 * @author Stephen K Samuel samspade79@gmail.com 29 Jun 2012 00:47:18
 * 
 */
@Controller
public class ManagerController {

	@Autowired
	private CamelConnectionFactory	connectionFactory;

	@RequestMapping("manager")
	public String list(ModelMap map) throws Exception {

		CamelConnection conn = connectionFactory.getConnection();

		List<Route> routes = conn.getRoutes();
		map.put("routes", routes);

		List<Endpoint> endpoints = conn.getEndpoints();
		map.put("endpoints", endpoints);

		return "views/manager";
	}
}
