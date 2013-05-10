package it.lucarasconi.common.jaxb;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

public abstract class JAXBHelper<T> {
	protected Logger logger = Logger.getLogger(getClass());
	private static Map<String,JAXBContext> jc;
	
	protected Class<T> clazz;
	
	protected JAXBHelper(Class<T> clazz) {
		this.clazz = clazz;
		jc = new HashMap<String,JAXBContext>();
	}
	
	public final JAXBContext getJAXBContext() throws InstantiationException, IllegalAccessException, JAXBException {
		//String context = clazz.getPackage().getName();
		String context = clazz.getName();
		if (jc.get(context) == null) {
			//jc.put(context, JAXBContext.newInstance(context));
			jc.put(context, JAXBContext.newInstance(new Class[] {clazz}) );
		}
		return jc.get(context);
	}
	
	public T unmarshal(File f) throws JAXBException {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("unmarshalling file %s", f));
		}
		if (f == null) {
			throw new NullPointerException();
		}
		javax.xml.bind.Unmarshaller unmarshaller = null;
		
		try {
			unmarshaller = getJAXBContext().createUnmarshaller();
			return (T) unmarshaller.unmarshal(f);
		} catch (InstantiationException e) {
			throw new JAXBException(e);
		} catch (IllegalAccessException e) {
			throw new JAXBException(e);
		}
	}
	
	public T unmarshal(String xml) throws JAXBException {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("unmarshalling xml %s", xml));
		}
		if (xml == null || xml.trim().length() == 0) {
			throw new NullPointerException();
		}
		javax.xml.bind.Unmarshaller unmarshaller = null;
		
		try {
			unmarshaller = getJAXBContext().createUnmarshaller();
			return (T) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			throw new JAXBException(e);
		} catch (InstantiationException e) {
			throw new JAXBException(e);
		} catch (IllegalAccessException e) {
			throw new JAXBException(e);
		}
	}
	
	public String marshall(T object) throws JAXBException {
		return marshall(object, true);
	}
	
	/**
	 * 
	 * @param object
	 * @param xmlDecl se false non verra' generare la dichiarazione xml <? xml...
	 * @return
	 * @throws JAXBException
	 */
	public String marshall(T object, boolean xmlDecl) throws JAXBException {
		javax.xml.bind.Marshaller marshaller = null;
		
		if (object == null) {
			throw new NullPointerException();
		}
		try {
			marshaller = getJAXBContext().createMarshaller();
			if (!xmlDecl) {
				marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
			}
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		} catch (InstantiationException e) {
			throw new JAXBException(e);
		} catch (IllegalAccessException e) {
			throw new JAXBException(e);
		}
		StringWriter writer = new StringWriter();
		marshaller.marshal(object, writer);
		return writer.toString();
	}
	
	public boolean isValid(T object) {
		try {
			return isValid(marshall(object));
		} catch (JAXBException e) {
			logger.error(String.format("xml is not valid", ""), e);
			throw new RuntimeException(e);
		}
	}
	
	public boolean isValid(String xml) {
		
		if (xml == null || xml.trim().length() == 0) {
			throw new NullPointerException();
		}
		
		Source source = new StreamSource(new StringReader(xml));
		try {
			Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new StreamSource(getSchemaLocation()));
			schema.newValidator().validate(source);
			return true;
		} catch (SAXException e) {
			logger.error(String.format("xml is not valid", ""), e);
		} catch (IOException e) {
			logger.error(String.format("xml is not valid", ""), e);
			throw new RuntimeException(e);
		}
		return false;
	}
	
	public boolean isValid(File xml) {
		
		if (xml == null) {
			throw new NullPointerException();
		}
		if (getSchemaLocation() == null) {
			logger.warn(String.format("Impossible validate xml: no schema file"));
			return true;
		}
		Source source = new StreamSource(xml);
		try {
			Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new StreamSource(getSchemaLocation()));
			schema.newValidator().validate(source);
			return true;
		} catch (SAXException e) {
			logger.error(String.format("xml is not valid", ""), e);
		} catch (IOException e) {
			logger.error(String.format("xml is not valid", ""), e);
			throw new RuntimeException(e);
		}
		return false;
	}
	
	public abstract InputStream getSchemaLocation();

}
