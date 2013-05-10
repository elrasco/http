package it.lucarasconi.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Request implements Serializable {
	
	private static final long serialVersionUID = -1782143790648398671L;
	private String uri;
	
	String charset = "UTF-8";
	int readTimeout = 10000;
	int connTimeout = 1000; 
	
	private final Map<String, List<String>> parameters = new HashMap<String, List<String>>();
	private final Map<String, String> headers = new HashMap<String, String>();
	private List<String> cookies;

	public Request(String uri) {
		this.uri = uri;
	}
	
	public Request(String uri, Request request) {
		this.uri = uri;
		this.cookies = request.getCookies();
		this.readTimeout = request.getReadTimeout();
		this.connTimeout = request.getConnTimeout();
		this.charset = request.getCharset();
	}
	
	public Request(Request request) {
		this.uri = request.getUri();
		this.cookies = request.getCookies();
		this.readTimeout = request.getReadTimeout();
		this.connTimeout = request.getConnTimeout();
		this.charset = request.getCharset();
	}
	
	public Request() {
		this.parameters.clear();
	}
	
	public Response get() throws IOException {
		Response response = new Response();
		String queryString = getQueryString();
		try {
			if (this.uri == null) {
				throw new NullPointerException();
			}
			if (queryString.length() > 0) {
				if (this.uri.endsWith("?") || this.uri.endsWith("&")) {
					this.uri = this.uri + queryString;
				} else if (uri.indexOf("&") != -1) {
					this.uri = this.uri + "&" + queryString;
				} else {
					this.uri = this.uri + "?" + queryString;
				}
			}
			return call(openConnection(new URL(this.uri)));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw e;
		}
		return response;
	}
	
	public Response get(String uri) throws IOException {
		this.uri = uri;
		return get();
	}

	public Response post() throws IOException {
		Response response = new Response();
		OutputStream output = null;
		try {
			if (this.uri == null) {
				throw new NullPointerException();
			}
			URLConnection connection = openConnection(new URL(this.uri));
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
			output = connection.getOutputStream();
			output.write(getQueryString().getBytes(charset));
			return call(connection);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw e;
		} finally {
			if (output != null) {
				output.close();
			}
		}
		return response;
	}
	
	public Response post(String uri) throws IOException {
		this.uri = uri;
		return post();
	}
	
	public void addParam(String name, String value) {
		if (name == null || value == null) {
			return;
		}
		name = name.trim();
		if (parameters.get(name) == null) {
			parameters.put(name, new ArrayList<String>());
		} 
		parameters.get(name).add(value);
	}
	
	public void addHeader(String name, String value) {
		if (name == null || value == null) {
			return;
		}
		headers.put(name, value);
	}
	
	private String getQueryString() {
		StringBuilder queryString = new StringBuilder();
		if (parameters != null) {
			boolean first = true;
			for (Entry<String, List<String>> prm : parameters.entrySet()) {
				List<String> values = prm.getValue();
				for (String value : values) {
					try {
						queryString.append(first ? "" : "&").append(prm.getKey()).append("=").append(URLEncoder.encode(value, charset));
					} catch (UnsupportedEncodingException e) {
					}
					first = false;
				}
			}
		} 
		return queryString.toString();
	}
	
	private URLConnection openConnection(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		if (cookies != null && !cookies.isEmpty()) {
			for (String cookie : cookies) {
			    connection.addRequestProperty("Cookie", cookie.split(";", 2)[0]);
			}
		}
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setConnectTimeout(connTimeout);
		connection.setReadTimeout(readTimeout);
		return connection;
	}
	
	private Response call(URLConnection connection) throws IOException {
		Response response = new Response();
		response.setStatus(((HttpURLConnection) connection).getResponseCode());
		if (response.getStatus() == HttpURLConnection.HTTP_OK) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
			try {
				response.setContentType(connection.getHeaderField("Content-Type"));
				StringBuilder sb = new StringBuilder();
				boolean first = true;
				for (String line; (line = reader.readLine()) != null;) {
					sb.append(first ? "" : System.getProperty("line.separator")).append(line);
					first = false;
				}
				response.setContent(sb.toString());
			} finally {
				if (reader != null) {
					reader.close();
				}
				List<String> _cookies = connection.getHeaderFields().get("Set-Cookie");
				if (_cookies != null) {
					if (cookies == null) {
						cookies = new ArrayList<String>();
					} 
					cookies.addAll(_cookies);
				}	
			}
		}
		return response;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getConnTimeout() {
		return connTimeout;
	}

	public void setConnTimeout(int connTimeout) {
		this.connTimeout = connTimeout;
	}

	public List<String> getCookies() {
		return cookies;
	}

	public void setCookies(List<String> cookies) {
		this.cookies = cookies;
	}
}
