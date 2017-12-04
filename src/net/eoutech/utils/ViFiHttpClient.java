package net.eoutech.utils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.collections.comparators.BooleanComparator;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class ViFiHttpClient {
	private HttpClient client = new DefaultHttpClient();

	private CookieStore cookieStore = new BasicCookieStore();
	private HttpContext localContext = new BasicHttpContext();
	private EuFileUtil fileUtil = EuFileUtil.getInstance();
	public ViFiHttpClient() {
		client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
			SSLSocketFactory sf = new SSLSocketFactory(sslcontext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme httpsScheme = new Scheme("https", 443, sf);
			client.getConnectionManager().getSchemeRegistry().register(httpsScheme);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String get ( String url ) {
		HttpGet get = new HttpGet( url );
		try {
			HttpResponse response = client.execute( get );
			return EntityUtils.toString( response.getEntity(), "UTF-8" );
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}


	public String post ( String url, String reqStr ) {
		HttpPost post = new HttpPost( url );
		try {
			if (null != reqStr) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity( reqStr, "UTF-8" );
				entity.setContentEncoding("UTF-8");
				post.setEntity(entity);
			}
			HttpResponse response = client.execute( post );
			return EntityUtils.toString( response.getEntity(), "UTF-8" );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}


	public String get( String url, String referer) {
		try {
			HttpGet httpget = new HttpGet();
			httpget.setURI(new URI(url));
			if (referer.length() > 0) {
				httpget.setHeader("Referer", referer);
			}
			HttpResponse response = client.execute(httpget, localContext);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String returnHtml = fmtResultString(IOUtils.toString(entity.getContent()));
				return returnHtml;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return "";
	}


	public String post(String url, Map<String, Object> params, String referer) {
		try {
			HttpPost post = new HttpPost(url);
			List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			if (params != null) {
				for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
					String key = iterator.next();
					String value = String.valueOf(params.get(key));
					nvps.add(new BasicNameValuePair(key, value));
				}
			}
			post.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
			if (referer.length() > 0) {
				post.setHeader("Referer", referer);
			}
			// Execute request and get the response
			HttpResponse response = client.execute(post, localContext);
			HttpEntity entity = response.getEntity();
			return entity != null ? IOUtils.toString(entity.getContent()) : "";
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	private static TrustManager truseAllManager = new X509TrustManager() {
		public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub
		}

		public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub
		}

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}
	};

	private String fmtResultString(String result) {
		if (result == null) {
			return "";
		}
		if (result.indexOf("CsaDataStart") > -1) {
			result = result.substring(result.indexOf("CsaDataStart:") + 13, result.indexOf(":CsaDataEnd"));
		}
		return result;
	}

}
