package it.lucarasconi.common.jaxb.adapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

class MapElements {
	
	@XmlAttribute
	public String key;
	@XmlValue
	public String value;

	MapElements() {
	} // Required by JAXB

	public MapElements(String key, String value) {
		this.key = key;
		this.value = value;
	}
}
