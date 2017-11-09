package cc.notalk.v.utils;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 * jsoup相关处理方法
 * 备注:代理使用的阿布云代理
 * @author fatyu
 *
 */
public class JsoupUtils {

	private JsoupUtils() {
		super();
	}

	// 代理隧道验证信息
	final static String ProxyUser = "H3Q4VF69PG1L4G2D";
	final static String ProxyPass = "495F09979E5D2961";

	// 代理服务器
	final static String ProxyHost = "proxy.abuyun.com";
	final static Integer ProxyPort = 9020;

	public static Proxy getProxy() {
		Authenticator.setDefault(new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(ProxyUser, ProxyPass.toCharArray());
			}
		});

		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ProxyHost, ProxyPort));
		return proxy;
	}

	/**
	 * @param url 请求地址
	 * @param timeout 超时时间 单位毫秒
	 * @param host 主机域名
	 * @return
	 */
	public static Connection getDirectConnection(String url, int timeout, String host) {
		Connection connect = Jsoup.connect(url);
		Map<String, String> header = new HashMap<String, String>();
		header.put("Host", host);
		header.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
		header.put("Accept", "	text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		header.put("Accept-Language", "zh-cn,zh;q=0.5");
		header.put("Accept-Charset", "en,zh-CN;q=0.8,zh;q=0.6");
		header.put("Connection", "keep-alive");

		Map<String, String> cookies = null;
		cookies = new HashMap<String, String>();
		cookies.put("PANWEB", "1");
		cookies.put("BAIDUID", "CC18890E24DB989E7118C937E4F974CF:FG=1");
		cookies.put("BIDUPSID", "CC18890E24DB989E7118C937E4F974CF");
		cookies.put("PSTM", "1505181060");
		cookies.put("__cfduid", "d1338c76b15339c5a4094814d29c01b3c1508317084");
		cookies.put("STOKEN", "2e0577f6320ffee04187491bc17b645b19ebab610cb6ed06021d833eb6261f0b");
		cookies.put("SCRC", "c9a13ebbfcbc82dba2aa747f8a78d020");
		cookies.put("Hm_lvt_f5f83a6d8b15775a02760dc5f490bc47", "1509437925");
		cookies.put("BDCLND", "RyvVXfzyE%2BRFR%2F3j%2B46qUNvIvMlZumoij0KLJdj8VCo%3D");
		cookies.put("MCITY", "-%3A");
		cookies.put("Hm_lvt_7a3960b6f067eb0085b7f96ff5e660b0", "1510047843,1510047846,1510117382,1510117570");
		cookies.put("Hm_lpvt_7a3960b6f067eb0085b7f96ff5e660b0", "1510121384");
		cookies.put("PANPSC",
				"18216600002746250947%3AJ3EsYsG7OQk0bNxSJQaph%2B5yB35pE7HkAEKHMFZ5NOZ%2BZPOwo8OyQ9%2BkZjFsrvNx%2FH7twijjwRDththg%2BKSGZaYFLutVRAzJfXOsWjuKC4FuWt7N7jFSoGVWcHP9mn3MPjCB1D%2FVxYyaB5R8hpvpEDWPC7NKcJhVB7o5UgPoMUGwcc33C7HckA%3D%3D");
		cookies.put("panlogin_animate_showed", "1");
		cookies.put("PSINO", "3");
		cookies.put("H_PS_PSSID", "1426_21095_18559_17001_24879_20929");
		connect.cookies(cookies);
		connect = connect.data(header);
		return connect.timeout(timeout);
	}

	/**
	 * @param url 请求地址
	 * @param timeout 超时时间 单位毫秒
	 * @param host 主机域名
	 * @return
	 */
	public static Connection getProxyConnection(String url, int timeout, String host) {
		Connection connect = Jsoup.connect(url);
		Map<String, String> header = new HashMap<String, String>();
		header.put("Host", host);
		header.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
		header.put("Accept", "	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		header.put("Accept-Language", "zh-cn,zh;q=0.5");
		header.put("Accept-Charset", "en,zh-CN;q=0.8,zh;q=0.6");
		header.put("Connection", "keep-alive");
		connect.proxy(getProxy());
		connect = connect.data(header);
		connect = connect.timeout(timeout);
		return connect;
	}
}
