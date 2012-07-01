package com.sksamuel.camelwatch;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.sksamuel.camelwatch.consumer.Consumer;
import com.sksamuel.camelwatch.consumer.ConsumerFactory;
import com.sksamuel.camelwatch.consumer.ConsumerOperations;
import com.sksamuel.camelwatch.consumer.ConsumerOperationsJmxImpl;
import com.sksamuel.camelwatch.route.Route;
import com.sksamuel.camelwatch.route.RouteFactory;
import com.sksamuel.camelwatch.route.RouteOperations;
import com.sksamuel.camelwatch.route.RouteOperationsJmxImpl;

/**
 * @author Stephen K Samuel samspade79@gmail.com 29 Jun 2012 00:26:13
 * 
 */
public class CamelJmxConnection implements CamelConnection {

	private final MBeanServerConnection	conn;

	public CamelJmxConnection(String url) throws IOException, MalformedObjectNameException, NullPointerException {
		Map<String, String[]> env = new HashMap<String, String[]>();
		env.put(JMXConnector.CREDENTIALS, new String[] { "user", "pass" });

		JMXServiceURL address = new JMXServiceURL(url);
		JMXConnector connector = JMXConnectorFactory.connect(address, env);
		conn = connector.getMBeanServerConnection();

	}

	CamelBean getCamelBean(String type, String name) throws Exception {
		ObjectInstance instance = getObjectInstance(type, name);
		MBeanInfo info = conn.getMBeanInfo(instance.getObjectName());
		CamelBean bean = new CamelBeanFactory().build(instance, conn, info);
		return bean;
	}

	List<CamelBean> getCamelBeans(String type) throws IOException, MalformedObjectNameException, InstanceNotFoundException,
			IntrospectionException, ReflectionException, Exception {
		Set<ObjectInstance> beans = getObjectInstances(type);
		List<CamelBean> endpoints = Lists.newArrayList();
		for (ObjectInstance instance : beans) {
			MBeanInfo info = conn.getMBeanInfo(instance.getObjectName());
			CamelBean endpoint = new CamelBeanFactory().build(instance, conn, info);
			endpoints.add(endpoint);
		}
		return endpoints;
	}

	@Override
	public Consumer getConsumer(String consumerName) throws Exception {
		ObjectInstance instance = getObjectInstance("consumers", consumerName);
		MBeanInfo info = conn.getMBeanInfo(instance.getObjectName());
		return new ConsumerFactory().buildConsumer(instance, conn, info);
	}

	@Override
	public List<CamelBean> getProcessors() throws Exception {
		return getCamelBeans("processors");
	}

	@Override
	public Collection<CamelBean> getProcessors(final String routeId) throws Exception {
		List<CamelBean> camelBeans = getCamelBeans("processors");
		return Collections2.filter(camelBeans, new Predicate<CamelBean>() {

			@Override
			public boolean apply(CamelBean input) {
				return routeId.equals(input.getProperties().get("RouteId"));
			}
		});
	}

	@Override
	public ConsumerOperations getConsumerOperations(String consumerId) throws Exception {
		ObjectInstance instance = getObjectInstance("consumers", consumerId);
		MBeanInfo info = conn.getMBeanInfo(instance.getObjectName());
		return new ConsumerOperationsJmxImpl(conn, instance, consumerId, info);
	}

	@Override
	public List<Consumer> getConsumers() throws Exception {
		Set<ObjectInstance> beans = getObjectInstances("consumers");
		List<Consumer> consumers = Lists.newArrayList();
		for (ObjectInstance instance : beans) {
			MBeanInfo info = conn.getMBeanInfo(instance.getObjectName());
			Consumer consumer = new ConsumerFactory().buildConsumer(instance, conn, info);
			consumers.add(consumer);
		}

		return consumers;
	}

	@Override
	public List<CamelBean> getContexts() throws Exception {
		return getCamelBeans("context");
	}

	@Override
	public List<CamelBean> getEndpoints() throws Exception {
		return getCamelBeans("endpoints");
	}

	@Override
	public CamelBean getErrorHandler(String errorHandlerName) throws Exception {
		return getCamelBean("errorhandlers", "\"" + errorHandlerName + "\"");
	}

	@Override
	public List<CamelBean> getErrorHandlers() throws Exception {
		return getCamelBeans("errorhandlers");
	}

	ObjectInstance getObjectInstance(String type, String name) throws MalformedObjectNameException, NullPointerException, IOException {
		Set<ObjectInstance> beans = conn.queryMBeans(new ObjectName("org.apache.camel:type=" + type + ",name=" + name + ",*"), null);
		return beans.isEmpty() ? null : beans.iterator().next();
	}

	Set<ObjectInstance> getObjectInstances(String type) throws IOException, MalformedObjectNameException {
		Set<ObjectInstance> beans = conn.queryMBeans(new ObjectName("org.apache.camel:type=" + type + ",*"), null);
		return beans;
	}

	@Override
	public Route getRoute(String routeId) throws Exception {
		ObjectInstance instance = getObjectInstance("routes", "\"" + routeId + "\"");
		MBeanInfo info = conn.getMBeanInfo(instance.getObjectName());
		return new RouteFactory().buildRoute(instance, conn, info);
	}

	@Override
	public RouteOperations getRouteOperations(String routeId) throws Exception {
		ObjectInstance instance = getObjectInstance("routes", "\"" + routeId + "\"");
		MBeanInfo info = conn.getMBeanInfo(instance.getObjectName());
		return new RouteOperationsJmxImpl(conn, instance, routeId, info);
	}

	@Override
	public List<Route> getRoutes() throws Exception {

		Set<ObjectInstance> beans = getObjectInstances("routes");

		List<Route> routes = Lists.newArrayList();
		for (ObjectInstance instance : beans) {
			MBeanInfo info = conn.getMBeanInfo(instance.getObjectName());
			Route route = new RouteFactory().buildRoute(instance, conn, info);
			routes.add(route);
		}

		return routes;
	}

}
