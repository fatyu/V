package ren.xiayi.bookeeper.service;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Iterator;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ren.xiayi.bookeeper.dao.site.SiteDao;
import ren.xiayi.bookeeper.entity.site.Site;

@Component
public class UrlFileService {
	@Autowired
	SiteDao siteDao;

	/**
	 * 获取网站地址
	 */
	@SuppressWarnings("deprecation")
	public void fetchSite() {
		Iterator<File> files = FileUtils.iterateFiles(new File("e:\\favkeeper\\"), new String[] { "html" }, true);

		while (files.hasNext()) {
			File file = files.next();

			try {
				Document doc = Jsoup.parse(file, Charsets.UTF_8.toString());
				Elements as = doc.getElementsByTag("a");
				for (Element e : as) {
					String href = e.attr("href");
					if (null == href) {
						href = e.attr("HREF");
					}
					if (StringUtils.startsWithIgnoreCase(href, "http")) {
						String name = e.text();
						href = "http" + StringUtils.substringAfterLast(href, "http");
						try {
							href = URLDecoder.decode(href);
						} catch (Exception e2) {
							e2.printStackTrace();
						}

						Site site = new Site();
						site.setName(name);
						site.setUrl(href);
						siteDao.save(site);
						System.out.println(file.getAbsolutePath() + ">>>>>" + name + ":" + href);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	//	public static String fileCharset(String fileName) throws Exception {
	//		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
	//		int p = (bin.read() << 8) + bin.read();
	//		String code = null;
	//		switch (p) {
	//		case 0xefbb:
	//			code = "UTF-8";
	//			break;
	//		case 0xfffe:
	//			code = "Unicode";
	//			break;
	//		case 0xfeff:
	//			code = "UTF-16BE";
	//			break;
	//		default:
	//			code = "GBK";
	//		}
	//		return code;
	//	}

	public static void main(String[] args) {
		UrlFileService urlFileService = new UrlFileService();
		urlFileService.fetchSite();
	}

}
