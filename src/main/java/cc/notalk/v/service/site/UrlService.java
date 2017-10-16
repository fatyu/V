package cc.notalk.v.service.site;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.notalk.v.dao.site.SiteDao;
import cc.notalk.v.entity.site.Site;
import cc.notalk.v.utils.JsoupUtils;

@Component
public class UrlService {
	@Autowired
	SiteDao siteDao;

	/**
	 * 获取网站地址中a标签地址
	 */
	public void fetchSite(String uri) {

		try {
			Connection directConnection = JsoupUtils.getDirectConnection(uri, 5000, "http://www.baidu.com");
			Response response = directConnection.execute();
			int statusCode = response.statusCode();
			if (statusCode == 200) {
				Document doc = directConnection.get();
				Elements elements = doc.getElementsByTag("a");
				for (Element a : elements) {
					String url = a.attr("href");
					String name = a.text();
					Site site = new Site();
					site.setUrl(url);
					site.setName(name);
					siteDao.save(site);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

}
