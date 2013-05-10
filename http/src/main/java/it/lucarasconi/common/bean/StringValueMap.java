package it.lucarasconi.common.bean;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Data la rappresentazione "Map" di una riga di una tabella dove la chiave � il nome del campo, 
 * StringValueMap restituisce il valore del campo nella sua versione String
 * 
 * @author luca.rasconi
 *
 */
public class StringValueMap extends HashMap<String, Object> {

	private static final long serialVersionUID = -4279080663145271523L;
	
	public StringValueMap() {
		super();
	}
	
	public String toString(String key) {
		return toString(key, "yyyyMMddHHmmss");
	}
	
	public String toString(String key, String format) {
		Object o = this.get(key);
		if (o != null) {
			if ( (o instanceof Timestamp || o instanceof Date) && format != null && !format.equals("") ) {
				SimpleDateFormat sdf =  new SimpleDateFormat(format);
				return sdf.format(o);
			} else {
				return this.get(key).toString();
			}
		}
		return "";
	}
}
