package com.triolabs.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.ArrayList;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.util.Log;


public class RESTClient {

	public static enum RequestMethod {
		GET, POST
	}

	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;
	public String post;
	private String url;

	private int responseCode;
	private String message;
	private String response;

/*
 * default methods to manage the restclient
 * */
	public String getResponse() {
		return response;
	}
	public String getErrorMessage() {
		return message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public RESTClient(String url) {
		this.url = url;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}

	public void AddParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void AddHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}
/*
 * exceute the post defined to the url defined
 * 
 * */
	public void Execute(RequestMethod method) throws Exception {
		switch (method) {
		case GET: {
			// add parameters
			String combinedParams = "";
			if (!params.isEmpty()) {
				combinedParams += "?";
				for (NameValuePair p : params) {
					String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(), "UTF-8");
					if (combinedParams.length() > 1) {
						combinedParams += "&" + paramString;
					} else {
						combinedParams += paramString;
					}
				}
			}
			Log.i("RestClient","GET  :"+url + combinedParams);
			HttpGet request = new HttpGet(url + combinedParams);
			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
			}
			executeRequest(request, url);
			break;
		}
		case POST: {			
			HttpPost request = new HttpPost(url);
			//request.setHeader("Content-type","application/json");
			request.setHeader("Content-type","application/x-www-form-urlencoded");
			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
				//Log.i("headeradding",""+h.getName()+","+h.getValue());
			}		
			if (!params.isEmpty()) {
				//request.setEntity(new ByteArrayEntity(post.toString().getBytes("UTF8")));
				request.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
				Log.i("posting",request.getEntity().toString()+" in "+url );
			}		
			
			executeRequest(request, url);
			break;
		}
		}
	}
/*
 * executing recuest from post in execute restclient
 * 
 * */
	private void executeRequest(HttpUriRequest request, String url) throws Exception{	   
		HttpClient client =this.getNewHttpClient();//; new DefaultHttpClient(httpParams);
		HttpResponse httpResponse;
		try {
			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				response = convertStreamToString(instream);
				instream.close();
			}
		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
			Log.i("RestClientExecption","protocol error at:"+this.url);
			e.printStackTrace();
			throw new Exception();		
		} catch (IOException e) {
			Log.i("RestClientExecption","I/O error at:"+this.url);
			client.getConnectionManager().shutdown();
			e.printStackTrace();
			throw new Exception();	
		}
	}
/*
 * 
 * convert the Stream from the request in a String readeable to the rest client
 * 
 * */
	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	public HttpClient getNewHttpClient() {
	    try {
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        trustStore.load(null, null);
	        MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); 
	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
	        HttpConnectionParams.setConnectionTimeout(params, 30000);
	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));
	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
	        return new DefaultHttpClient(ccm, params);
	    } catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	}
}
