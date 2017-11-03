package cc.notalk.v.service.site;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.htmlparser.Parser;
import org.htmlparser.visitors.TextExtractingVisitor;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import cc.notalk.v.dao.QueryDao;
import cc.notalk.v.dao.site.SiteDao;
import cc.notalk.v.entity.site.Site;
import cc.notalk.v.utils.JsonUtils;
import cc.notalk.v.utils.JsoupUtils;

@Component
public class UrlService {
	@Autowired
	SiteDao siteDao;
	@Autowired
	QueryDao queryDao;

	/**
	 * 获取网站地址中a标签地址
	 *
	 * @param depth
	 *            页面深度
	 * @param filterTagAString
	 *            过滤a标签
	 */
	public void fetchSite(String uri, String filterTagAString, String depth) {
		try {
			Connection directConnection = JsoupUtils.getDirectConnection(uri, 5000, "http://www.baidu.com");
			Response response = directConnection.execute();
			int statusCode = response.statusCode();
			if (statusCode == 200) {
				Document doc = directConnection.get();
				Elements elements = doc.getElementsByTag("a");
				for (Element a : elements) {
					String url = a.attr("href");
					String name = StringUtils.trim(a.text());
					if (StringUtils.isBlank(name)) {
						name = a.attr("title");
					}
					if (StringUtils.equals(depth, "true")) {
						if (StringUtils.contains(name, filterTagAString)
								|| StringUtils.contains(url, filterTagAString)) {
							if (StringUtils.contains(url, "http://") || StringUtils.contains(url, "https://")) {

								fetchSite(url);
							} else {
								URL urlData = new URL(uri);
								String host = urlData.getHost();
								String protocol = urlData.getProtocol();

								if (org.apache.commons.lang.StringUtils.endsWith(uri, "/")
										|| StringUtils.startsWith(url, "/")) {
									url = protocol + "://" + host + url;
								} else {
									url = protocol + "://" + host + "/" + url;
								}
								fetchSite(url);
							}
						}

					} else {
						if (!StringUtils.contains(name, filterTagAString)
								|| !StringUtils.contains(url, filterTagAString)) {
							if (StringUtils.startsWith(url, "http")) {
								Site site = new Site();
								site.setUrl(url);
								site.setName(name);
								site = siteDao.save(site);
								System.out.println(JsonUtils.objectToString(site));
							}
						} else {
							Site site = new Site();
							site.setUrl(url);
							site.setName(name);
							System.out.println("链接内容存在过滤关键字:" + JsonUtils.objectToString(site));
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	public void fetchSite(String uri) {
		System.out.println("url is >" + uri);
		try {
			Connection directConnection = JsoupUtils.getDirectConnection(uri, 5000, "https://www.notalk.cc");
			Response response = directConnection.execute();
			int statusCode = response.statusCode();
			if (statusCode == 200) {
				Document doc = directConnection.get();
				Elements elements = doc.getElementsByTag("a");
				for (Element a : elements) {
					String url = a.attr("href");
					String name = StringUtils.trim(a.text());
					if (StringUtils.isBlank(name)) {
						name = a.attr("title");
					}
					Site site = new Site();
					site.setUrl(url);
					site.setName(name);
					siteDao.save(site);
					System.out.println(JsonUtils.objectToString(site));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	//网页内容抓取
	public void fetchSingleUrlContent(Site site, String fileDir) {
		try {
			if (StringUtils.startsWithIgnoreCase(site.getUrl(), "https://")) {
				httpsUrlContent(site, fileDir);
			} else {
				httpUrlContent(site, fileDir);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
		}
	}

	private void httpUrlContent(Site site, String fileDir) throws IOException {
		Connection directConnection = JsoupUtils.getDirectConnection(site.getUrl(), 5000, "https://www.notalk.cc");
		Response response = directConnection.execute();
		int statusCode = response.statusCode();
		if (statusCode == 200) {
			Document doc = directConnection.get();
			String text = doc.toString();
			File file = new File(new File(fileDir), site.getId().toString() + ".html");
			FileUtils.writeStringToFile(file, text, Charsets.UTF_8);
		} else {
			System.err.println(JsonUtils.objectToString(site) + ">>>>读取数据异常");
		}
	}

	ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2,
			new ThreadFactoryBuilder().setNameFormat("网页内容抓取线程-->").build());

	public void allUrlFetch(String fileDir) {
		Iterable<Site> sites = siteDao.findAll();
		for (Site site : sites) {
			executors.execute(() -> {
				fetchSingleUrlContent(site, fileDir);
			});
		}

	}

	static void trustAllHttpsCertificates() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[1];
		TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	}

	static class miTM implements TrustManager, X509TrustManager {
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(X509Certificate[] certs) {
			return true;
		}

		@Override
		public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
			return;
		}

		@Override
		public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
			return;
		}
	}

	/**
	 * 忽略HTTPS请求的SSL证书
	* @throws Exception
	 */
	void ignoreSsl() throws Exception {
		HostnameVerifier hv = (urlHostName, session) -> {
			System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
			return true;
		};
		trustAllHttpsCertificates();
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}

	public void httpsUrlContent(Site site, String fileDir) {
		URL uri;
		try {
			uri = new URL(site.getUrl());
			if ("https".equalsIgnoreCase(uri.getProtocol())) {
				ignoreSsl();
			}
			Parser parser = new Parser(uri.toString());
			parser.setEncoding("UTF-8");
			TextExtractingVisitor tev = new TextExtractingVisitor();
			parser.visitAllNodesWith(tev);
			String body = tev.getExtractedText();
			File file = new File(new File(fileDir), site.getId().toString() + ".html");
			FileUtils.writeStringToFile(file, body, Charsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Map<String, Object>> list() {
		String sql = " select id, name,  url from v.v_site where status is null limit 20";
		return queryDao.queryMap(sql);
	}

}
