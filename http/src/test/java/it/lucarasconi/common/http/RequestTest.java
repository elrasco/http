package it.lucarasconi.common.http;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RequestTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		String baseuri = "http://www.infoimprese.it/impr/ricerca/";
		
		Request req = new Request(baseuri + "lista_globale.jsp");
		req.setCharset("ISO-8859-1");
		req.addParam("cer", "1");
		req.addParam("pagina", "1");
		req.addParam("flagDove", "true");
		req.addParam("ricerca", "roven");
		req.addParam("dove", "te");
		Response res = null;
		try {
			res = req.post();
		} catch (IOException e) {
			fail(e.getMessage());
		}
		assertEquals(HttpURLConnection.HTTP_OK, res.getStatus());
		//System.out.println(req.getUri());
		//System.out.println(res.getContent());
		
		req = new Request(baseuri + "risultati_globale.jsp", req);
		req.addParam("cer", "1");
		req.addParam("statistiche", "1");
		req.addParam("tipoRicerca", "0");
		
		try {
			res = req.post();
			
			String html = res.getContent();
			Document doc = Jsoup.parse(html);
			Elements els = doc.select("table a[onclick^=popupVetrina]");
			Map<String, String> results = new HashMap<String, String>();
			for (Element el : els) {
				String onclickattr = el.attr("onclick");
				int firstapos = onclickattr.indexOf("'");
				int secondapos = onclickattr.indexOf("'", firstapos + 1);
				
				results.put(onclickattr.substring(firstapos + 1, secondapos), onclickattr.substring(firstapos + 1, secondapos));
			}
			System.out.println(results.size());
			
			if (results.size() > 1) {
				fail();
			}
			
			req = new Request(baseuri + results.values().iterator().next(), req);
			res = req.get();
			
			doc = Jsoup.parse(res.getContent());
			els = doc.select("table TR TD B");
			for (Element e : els) {
				if (e.text().equals("Attività")) {
					Element after = e.parent().parent().child(1);
					System.out.println(after.text());
				}
			}
		} catch (IOException e) {
			fail(e.getMessage());
		}
		System.out.println(req.getUri());
	}

}
