package it.lucarasconi.common.jaxb;

import java.io.InputStream;


public class NoSchemaJAXBHelper<T> extends JAXBHelper<T> {

	public NoSchemaJAXBHelper(Class<T> clazz) {
		super(clazz);
	}

	@Override
	public InputStream getSchemaLocation() {
		return null;
	}
}
