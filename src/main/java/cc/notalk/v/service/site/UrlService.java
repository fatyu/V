package cc.notalk.v.service.site;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.notalk.v.dao.site.SiteDao;
import cc.notalk.v.entity.site.Site;
import cc.notalk.v.utils.JsonUtils;
import cc.notalk.v.utils.JsoupUtils;

@Component
public class UrlService {
	@Autowired
	SiteDao siteDao;

	/**
	 * 获取网站地址中a标签地址
	 * @param depth 页面深度
	 * @param filterTagAString 过滤a标签
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
							Site site = new Site();
							site.setUrl(url);
							site.setName(name);
							site = siteDao.save(site);
							System.out.println(JsonUtils.objectToString(site));
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
			Connection directConnection = JsoupUtils.getDirectConnection(uri, 5000, "http://www.quqiaoqiao.com");
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
}
