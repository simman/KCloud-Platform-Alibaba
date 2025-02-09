/*
 * Copyright (c) 2022 KCloud-Platform-Alibaba Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.laokou.common.core.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpEntityContainer;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author laokou
 */
@Slf4j
public class HttpUtil {

	private static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");

	public static String doGet(String url, Map<String, String> params, Map<String, String> headers) {
		return doGet(url, params, headers);
	}

	@SneakyThrows
	public static String doGet(String url, Map<String, String> params, Map<String, String> headers,
			boolean disableSsl) {
		// 创建HttpClient对象
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		if (disableSsl) {
			disableSsl(httpClientBuilder);
		}
		CloseableHttpClient httpClient = httpClientBuilder.build();
		String resultString = "";
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (!params.isEmpty()) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					builder.addParameter(entry.getKey(), entry.getValue());
				}
			}
			URI uri = builder.build();
			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
			if (headers != null && headers.size() > 0) {
				for (Map.Entry<String, String> e : headers.entrySet()) {
					httpGet.addHeader(e.getKey(), e.getValue());
				}
			}
			httpGet.setHeader(new BasicHeader("Content-Type", "application/json;charset=UTF-8"));
			httpGet.setHeader(new BasicHeader("Accept", "*/*;charset=utf-8"));
			// 执行请求
			resultString = httpClient.execute(httpGet,
					handler -> EntityUtils.toString(handler.getEntity(), StandardCharsets.UTF_8));
		}
		catch (Exception e) {
			log.error("调用失败，错误信息:{}", e.getMessage());
		}
		finally {
			httpClient.close();
		}
		log.info("打印：{}", resultString);
		return resultString;
	}

	public static String doPost(String url, Map<String, String> params, Map<String, String> headers) {
		return doPost(url, params, headers, false);
	}

	@SneakyThrows
	public static String doPost(String url, Map<String, String> params, Map<String, String> headers,
			boolean disableSsl) {
		// 创建HttpClient对象
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		if (disableSsl) {
			disableSsl(httpClientBuilder);
		}
		CloseableHttpClient httpClient = httpClientBuilder.build();
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			if (headers != null && headers.size() > 0) {
				for (Map.Entry<String, String> e : headers.entrySet()) {
					httpPost.addHeader(e.getKey(), e.getValue());
				}
			}
			// 创建参数列表
			if (params != null && params.size() > 0) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, StandardCharsets.UTF_8);
				httpPost.setEntity(entity);
				httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;application/json;charset=UTF-8");
				httpPost.setHeader(new BasicHeader("Accept", "*/*;charset=utf-8"));
				// 执行http请求
				resultString = httpClient.execute(httpPost,
						handler -> EntityUtils.toString(handler.getEntity(), StandardCharsets.UTF_8));
			}
		}
		catch (Exception e) {
			log.error("接口调用失败:{}", e.getMessage());
		}
		finally {
			httpClient.close();
		}
		log.info("打印：{}", resultString);
		return resultString;
	}

	public static String doJsonPost(String url, Object param, Map<String, String> headers) {
		return doJsonPost(url, param, headers, false);
	}

	@SneakyThrows
	public static String doJsonPost(String url, Object param, Map<String, String> headers, boolean disableSsl) {
		// 创建HttpClient对象
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		if (disableSsl) {
			disableSsl(httpClientBuilder);
		}
		CloseableHttpClient httpClient = httpClientBuilder.build();
		RequestConfig requestConfig = RequestConfig.custom().build();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
		headers.forEach(httpPost::setHeader);
		httpPost.setConfig(requestConfig);
		String parameter = JacksonUtil.toJsonStr(param);
		StringEntity se = null;
		try {
			se = new StringEntity(parameter);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		httpPost.setEntity(se);

		String result = null;
		try {
			HttpEntity httpEntity = httpClient.execute(httpPost, HttpEntityContainer::getEntity);
			result = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		log.info("打印：{}", result);
		return result;
	}

	/**
	 * 转换为驼峰json字符串
	 */
	public static String transformerUnderHumpData(String data) {
		data = data.toLowerCase();
		Matcher matcher = LINE_PATTERN.matcher(data);
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	static class DisableValidationTrustManager implements X509TrustManager {

		DisableValidationTrustManager() {
		}

		public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
		}

		public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}

	}

	static class TrustAllHostnames implements HostnameVerifier {

		TrustAllHostnames() {
		}

		public boolean verify(String s, SSLSession sslSession) {
			return true;
		}

	}

	@SneakyThrows
	public static void disableSsl(HttpClientBuilder builder) {
		X509TrustManager disabledTrustManager = new DisableValidationTrustManager();
		TrustManager[] trustManagers = new TrustManager[] { disabledTrustManager };
		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, trustManagers, new SecureRandom());
		SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactoryBuilder.create()
				.setSslContext(sslContext).setHostnameVerifier(new TrustAllHostnames()).build();
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = PoolingHttpClientConnectionManagerBuilder
				.create().setSSLSocketFactory(sslConnectionSocketFactory).build();
		builder.setConnectionManager(poolingHttpClientConnectionManager);
	}

}
