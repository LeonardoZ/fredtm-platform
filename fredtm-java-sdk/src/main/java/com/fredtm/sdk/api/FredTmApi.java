package com.fredtm.sdk.api;

import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.fredtm.resources.base.GsonFactory;
import com.fredtm.resources.security.LoginDTO;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class FredTmApi {

	private String token = "";
	private String endpoint;
	private Timer timer = new Timer(true);
	private AuthorizationContext authContext;
	private LoginDTO dto;

	public FredTmApi(String endpoint, LoginDTO dto) {
		super();
		this.endpoint = endpoint;
		this.dto = dto;
		this.authContext = new AuthorizationContext();
		this.authContext.setAccountApi(configAnnonymousAdapter().create(AccountApi.class));
		this.token = authContext.getToken(dto);
		configureTokenRefreshTask();
	}

	public FredTmApi(String endpoint) {
		super();
		this.endpoint = endpoint;
	}

	private void configureTokenRefreshTask() {
		long oneHour = 3_600_000;
		long startTime = new Date().getTime() + oneHour;
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				FredTmApi.this.token = "";
				FredTmApi.this.token = authContext.getToken(dto);

			}
		}, startTime, oneHour);
	}

	public RestAdapter configCompleteAdapter() {
		return configAdapter(true);
	}

	public RestAdapter configAnnonymousAdapter() {
		return configAdapter(false);
	}

	private RestAdapter configAdapter(boolean complete) {
		Gson g = GsonFactory.getGson();
		OkClient client = getOkClient();
		Builder builder = new RestAdapter.Builder().setLogLevel(LogLevel.FULL).setClient(client)
				.setConverter(new GsonConverter(g))
				.setEndpoint(endpoint == null ? "http://fredtm-api.herokuapp.com/fredapi" : endpoint);
		if (complete) {
			builder.setRequestInterceptor(new RequestInterceptor() {
				@Override
				public void intercept(RequestFacade request) {
					request.addHeader("Authorization", token);
				}
			});
		}
		return builder.build();
	}

	public <T> T getComplete(Class<T> apiDef) {
		return configCompleteAdapter().create(apiDef);
	}

	public <T> T getAnnonymous(Class<T> apiDef) {
		return configAnnonymousAdapter().create(apiDef);
	}

	public static OkHttpClient getUnsafeOkHttpClient() {

		try {
			// Create a trust manager that does not validate certificate chains
			final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			} };

			// Install the all-trusting trust manager
			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			// Create an ssl socket factory with our all-trusting manager
			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			OkHttpClient okHttpClient = new OkHttpClient();
			okHttpClient.setSslSocketFactory(sslSocketFactory);
			okHttpClient.setHostnameVerifier(new HostnameVerifier() {

				@Override
				public boolean verify(String hostname, SSLSession session) {
					// for test purpose
					return true;
				}
			});

			return okHttpClient;
		} catch (

		Exception e)

		{
			throw new RuntimeException(e);
		}

	}

	public static OkClient getOkClient() {
		OkHttpClient client1 = new OkHttpClient();
		client1 = getUnsafeOkHttpClient();
		OkClient _client = new OkClient(client1);
		return _client;
	}

}
