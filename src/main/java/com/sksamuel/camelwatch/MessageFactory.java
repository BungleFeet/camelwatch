package com.sksamuel.camelwatch;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;

import com.google.common.collect.Lists;

/**
 * @author Stephen K Samuel samspade79@gmail.com 3 Jul 2012 14:51:01
 * 
 */
public class MessageFactory {

	public Message build(Element e) {
		Message m = new Message();
		m.setExchangeId(e.getAttributeValue("exchangeId"));
		for (Element header : e.getChildren("header")) {
			m.getHeaders().put(header.getAttributeValue("key"), header.getTextTrim());
		}
		return m;
	}

	public List<Message> buildList(Document doc) {
		List<Message> results = Lists.newArrayList();
		for (Element messageElement : doc.getRootElement().getChildren("message")) {
			results.add(build(messageElement));
		}
		return results;
	}

}
