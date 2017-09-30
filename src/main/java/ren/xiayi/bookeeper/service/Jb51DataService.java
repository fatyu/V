package ren.xiayi.bookeeper.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ren.xiayi.bookeeper.dao.QueryDao;
import ren.xiayi.bookeeper.dao.book.BookDao;
import ren.xiayi.bookeeper.dao.book.BookUrlDao;
import ren.xiayi.bookeeper.dao.book.CategoryIndexDao;
import ren.xiayi.bookeeper.entity.book.Book;
import ren.xiayi.bookeeper.entity.book.BookUrl;
import ren.xiayi.bookeeper.entity.book.CategoryIndex;
import ren.xiayi.bookeeper.utils.JsonUtils;
import ren.xiayi.bookeeper.utils.JsoupUtils;

@Component
public class Jb51DataService {
	private static final Logger logger = LoggerFactory.getLogger(Jb51DataService.class);
	@Autowired
	private CategoryIndexDao categoryIndexDao;
	@Autowired
	private BookDao bookDao;

	@Autowired
	private QueryDao queryDao;

	@Autowired
	private BookUrlDao bookUrlDao;

	/**
	 * 按类别爬取获取图书id,地址信息
	 */
	public void fetchBooks() {
		Iterable<CategoryIndex> allIndex = categoryIndexDao.findAll();
		for (CategoryIndex index : allIndex) {
			String url = index.getUrl();
			fetchUrl(url);
		}
	}

	private void fetchUrl(String url) {
		try {
			Connection directConnection = JsoupUtils.getDirectConnection(url, 5000, "http://www.jb51.net");

			Response response = directConnection.execute();
			int statusCode = response.statusCode();
			if (statusCode == 200) {
				Document doc = directConnection.get();
				//获取基本信息  获取列表信息,并保存数据库
				Elements oldClassData = doc.getElementsByClass("sbox");//评分
				Elements newClassData = doc.getElementsByClass("cur-cat-list");
				if (null != oldClassData && !oldClassData.isEmpty()) {
					//old class process
					Element element = oldClassData.get(0);
					Elements datas = element.getElementsByTag("dl");
					for (Element data : datas) {
						Element href = data.getElementsByTag("dt").get(0).getElementsByTag("a").get(0);
						String bookUrl = "http://www.jb51.net" + href.attr("href");
						String title = href.text();
						Book book = new Book();
						book.setTitle(title);
						book.setUrl(bookUrl);
						book = bookDao.save(book);
						logger.error(JsonUtils.objectToString(book) + "          ====== 保存成功");
					}
				}

				if (null != newClassData && !newClassData.isEmpty()) {
					//new class process
					Element element = newClassData.get(0);
					Elements datas = element.getElementsByTag("li");
					for (Element data : datas) {
						Element href = data.getElementsByTag("dl").get(0).getElementsByTag("dt").get(0)
								.getElementsByTag("a").get(0);
						String bookUrl = "http://www.jb51.net" + href.attr("href");
						String title = href.attr("title");
						Book book = new Book();
						book.setTitle(title);
						book.setUrl(bookUrl);
						bookDao.save(book);
						logger.error(JsonUtils.objectToString(book) + "          ====== 保存成功");
					}
				}

				try {
					Thread.sleep(RandomUtils.nextInt(3000));
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				int page = NumberUtils
						.toInt(StringUtils.substringBeforeLast(StringUtils.substringAfterLast(url, "_"), "."));
				String nextUrll = StringUtils.substringBeforeLast(url, "_") + "_" + (page + 1) + ".html";
				logger.info("完成{" + url + "}抓取工作,进行下一个页面{" + nextUrll
						+ "}抓取\n---------------------------------------------\n");
				fetchUrl(nextUrll);
			} else {
				logger.error("完成{" + url + "}抓取工作,此类别结束.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

	public void fetchBookDetail(int type) {

		int startPage = 1;
		long count;
		if (type == 0) {
			count = bookDao.count();
		} else {
			count = ((Number) queryDao
					.query("select count(1) from z_book_info where id not in (select book_id  from z_download_url )  "))
							.longValue();
		}

		int totalPage = (int) count / 5 + 1;

		for (int i = startPage; i <= totalPage; i++) {
			List<Map<String, Object>> books = queryPage(i, type);

			if (CollectionUtils.isNotEmpty(books)) {
				for (Map<String, Object> index : books) {
					String url = index.get("url").toString();
					Book book = bookDao.findOne(NumberUtils.toLong(index.get("id").toString()));
					fetchDetail(book, url);
				}
			}
		}

	}

	private List<Map<String, Object>> queryPage(int page, int type) {
		if (type == 0) {

			return queryDao.queryMap("select id,url from z_book_info   limit " + (page - 1) * 5 + ",5");
		}
		return queryDao
				.queryMap("select * from z_book_info where id not in (select book_id  from z_download_url )  limit "
						+ (page - 1) * 5 + ",5");
	}

	private void fetchDetail(Book book, String url) {
		try {
			Connection directConnection = JsoupUtils.getDirectConnection(url, 5000, "http://www.jb51.net");
			Document doc = directConnection.get();
			//获取基本信息  获取列表信息,并保存数据库

			Elements remark = doc.getElementsByClass("main-info");
			if (remark != null && !remark.isEmpty()) {
				String remarkData = remark.get(0).text();
				book.setRemark(remarkData);
			}

			String wxKeyWord = null;
			Element weixinElement = doc.getElementById("banquan");
			if (null != weixinElement) {
				book.setType(2);//设置微信类型
				wxKeyWord = StringUtils.substringBetween(url, "books/", ".html");
			} else {
				book.setType(1);//设置直接下载
			}
			bookDao.save(book);
			Elements urls = doc.getElementsByClass("ul_Address");
			if (null != urls && !urls.isEmpty()) {
				Element ul = urls.get(0);
				Elements datas = ul.getElementsByTag("li");
				for (Element data : datas) {
					Element href = data.getElementsByTag("a").get(0);
					String downloadUrl = href.attr("href");
					BookUrl bookUrl = new BookUrl();
					bookUrl.setBookId(book.getId());
					bookUrl.setUrl(downloadUrl);
					bookUrl.setWxKeyword(wxKeyWord);
					bookUrlDao.save(bookUrl);
					logger.error(JsonUtils.objectToString(bookUrl) + "          ====== 保存成功");
					if (book.getType() == 2) {
						break;
					}
				}
			}
			try {
				Thread.sleep(RandomUtils.nextInt(1500));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

	public void htmlsnippet() {
		String sql = "select concat('<a href=\"https://www.baidu.com/s?wd=',b.title,'\">',b.title,'</a>') search,concat('<a href=\"',d.url,'\">',d.url,'</a>') baiduurl,concat('<a href=\"',b.url,'\">',b.url,'</a>') url from z_download_url d left join z_books b on b.id = d.book_id where  d.wx_keyword is not null and d.baidu_password is null limit 1000";
		List<Map<String, Object>> queryMap = queryDao.queryMap(sql);

		for (Map<String, Object> map : queryMap) {
			System.out.println("<tr>			<td class='normal' valign='top'>" + map.get("search").toString()
					+ "</td>			<td class='normal' valign='top'>" + map.get("baiduurl").toString()
					+ "</td>			<td class='normal' valign='top'>" + map.get("url").toString()
					+ "</td>			</tr>");
		}
	}

	public void fetchNewBookDetail() {
		try {
			Connection directConnection = JsoupUtils.getDirectConnection("http://www.jb51.net/do/book.html", 5000,
					"http://www.jb51.net");
			Response response = directConnection.execute();
			int statusCode = response.statusCode();
			if (statusCode == 200) {
				Document doc = directConnection.get();
				//获取基本信息  获取列表信息,并保存数据库
				Element ulDiv = doc.getElementById("newmore");
				if (null != ulDiv) {
					Elements elements = ulDiv.getElementsByTag("ul").get(0).getElementsByTag("li");
					for (Element data : elements) {
						Element href = data.getElementsByTag("a").get(1);
						String bookUrl = "http://www.jb51.net" + href.attr("href");
						String title = href.text();
						Book book = bookDao.findByUrl(bookUrl);
						if (book == null) {
							book = new Book();
						}
						book.setTitle(title);
						book.setUrl(bookUrl);
						book = bookDao.save(book);
						logger.error(JsonUtils.objectToString(book) + "          ====== 保存成功");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

}
