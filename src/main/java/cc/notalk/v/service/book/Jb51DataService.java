package cc.notalk.v.service.book;

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

import cc.notalk.v.dao.QueryDao;
import cc.notalk.v.dao.book.BookDao;
import cc.notalk.v.dao.book.BookIndexDao;
import cc.notalk.v.dao.book.BookUrlDao;
import cc.notalk.v.entity.book.Book;
import cc.notalk.v.entity.book.BookIndex;
import cc.notalk.v.entity.book.BookUrl;
import cc.notalk.v.service.FileDownloadService;
import cc.notalk.v.utils.JsonUtils;
import cc.notalk.v.utils.JsoupUtils;

@Component
public class Jb51DataService {
	private static final Logger logger = LoggerFactory.getLogger(Jb51DataService.class);
	@Autowired
	private BookIndexDao bookIndexDao;
	@Autowired
	private BookDao bookDao;

	@Autowired
	private QueryDao queryDao;

	@Autowired
	private BookUrlDao bookUrlDao;

	@Autowired
	private FileDownloadService fileDownloadService;

	/**
	 * 按类别爬取获取图书id,地址信息
	 */
	public void fetchBooks() {
		Iterable<BookIndex> allIndex = bookIndexDao.findAll();
		for (BookIndex index : allIndex) {
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
					.query("select count(1) from v_book_info where id not in (select book_id  from v_book_url )  "))
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

			return queryDao.queryMap("select id,url from v_book_info   limit " + (page - 1) * 5 + ",5");
		}
		return queryDao.queryMap("select * from v_book_info where id not in (select book_id  from v_book_url )  limit "
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
					if (book.getType() == 2) {
						Element href = data.getElementsByTag("a").get(0);
						String downloadUrl = href.attr("href");
						BookUrl bookUrl = new BookUrl();
						bookUrl.setBookId(book.getId());
						bookUrl.setUrl(downloadUrl);
						bookUrl.setWxKeyword(wxKeyWord);
						bookUrlDao.save(bookUrl);
						logger.error(JsonUtils.objectToString(bookUrl) + "          ====== 保存成功");
						break;
					} else {
						Element href = data.getElementsByTag("a").get(0);
						String downloadUrl = href.attr("href");
						System.out.println(downloadUrl);
						if (StringUtils.contains(downloadUrl, "www.jb51.net/do/softdown.php")) {
							continue;
						}
						if (StringUtils.contains(downloadUrl, "do/plus/download1")) {
							continue;
						}
						if (bookUrlDao.findByUrl(downloadUrl) != null) {
							continue;
						}
						BookUrl bookUrl = new BookUrl();
						bookUrl.setBookId(book.getId());
						bookUrl.setUrl(downloadUrl);
						bookUrl.setWxKeyword(wxKeyWord);
						bookUrlDao.save(bookUrl);
						logger.error(JsonUtils.objectToString(bookUrl) + "          ====== 保存成功");
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

	public List<Map<String, Object>> limit5Data() {
		String sql = "select b.title ,d.id,d.url,d.wx_keyword,d.baidu_password from v_book_url d left join v_book_info b on b.id = d.book_id "
				+ "where  d.wx_keyword is not null  and downloaded is null and fatyu_baidu_url is null order by d.wx_keyword desc limit 1000";
		List<Map<String, Object>> data = queryDao.queryMap(sql);
		return data;
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
						List<Book> books = bookDao.findByUrl(bookUrl);
						if (CollectionUtils.isNotEmpty(books)) {
							continue;
						}
						Book book = new Book();
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

	public void removeDuplicateBook() {
		String sql = "select url from v_book_info where url in (select url from v_book_info group by url having count(id )>1)";
		List<Map<String, Object>> urls = queryDao.queryMap(sql);
		if (CollectionUtils.isNotEmpty(urls)) {
			for (Map<String, Object> url : urls) {
				List<Book> books = bookDao.findByUrl(url.get("url").toString());
				if (CollectionUtils.isNotEmpty(books)) {
					for (int i = 1; i < books.size(); i++) {
						Book book = books.get(i);
						logger.error("delete duplicate book :" + JsonUtils.objectToString(book));
						bookDao.delete(book);
					}
				}
			}
		}

	}

	public void downloadFile() {
		String sql = "select   bu.url,  bi.title,bu.id from  v_book_url bu  left join v_book_info bi    on bu.book_id = bi.id where (bu.downloaded is null  or downloaded <> 1 ) and bu.wx_keyword is null  and  bu.url not like '%baidu%' and bu.url like '%jb51.net:81%' ";
		List<Map<String, Object>> datas = queryDao.queryMap(sql);
		for (Map<String, Object> data : datas) {
			boolean finished = fileDownloadService.downLoad(data.get("url").toString(), "e:\\data\\ebook\\jb51",
					data.get("title").toString());
			BookUrl bookUrl = bookUrlDao.findOne(NumberUtils.toLong(data.get("id").toString()));
			if (finished) {
				bookUrl.setDownloaded(1);
				bookUrlDao.save(bookUrl);
			}
		}
	}

	public List<Map<String, Object>> limitFlagData() {
		String sql = "select id ,title,url from v_book_info where type =1 and need is null  order by id desc limit 10";
		List<Map<String, Object>> data = queryDao.queryMap(sql);
		return data;
	}

	public List<Map<String, Object>> limitWxFlagData() {
		String sql = "select id ,title,url from v_book_info where type =2 and need is null  order by id desc limit 10";
		List<Map<String, Object>> data = queryDao.queryMap(sql);
		return data;
	}

	public List<Map<String, Object>> baiduDownload() {
		String sql = "select * from v_book_url where"
				+ "   downloaded  is null and url not like '%baidu%'";
		List<Map<String, Object>> datas = queryDao.queryMap(sql);
		for(Map<String, Object> data :datas				){
			String url = data.get("url").toString();
			url = StringUtils.replace(url, "s/1", "share/init?surl=");
			if(url.startsWith("https")){
				
			}else{
				url = StringUtils.replace(url, "http", "https");
			}
			data.put("url", url);
		}
		return datas;
	}

	public void updateWxKeyword(Long id) {
		BookUrl bookUrl = bookUrlDao.findOne(id);
		Long bookId = bookUrl.getBookId();
		Book book = bookDao.findOne(bookId);
		String data = StringUtils.substringAfterLast(book.getUrl(), "/");
		bookUrl.setWxKeyword(StringUtils.substringBefore(data, "."));
		bookUrlDao.save(bookUrl);
	}

	public void updateMissStatus(Long id) {
		BookUrl bookUrl = bookUrlDao.findOne(id);
		bookUrl.setDownloaded(-1);
		bookUrlDao.save(bookUrl);
	}

	public void updateBookDownloaded(Long id) {
		BookUrl bookUrl = bookUrlDao.findOne(id);
		bookUrl.setDownloaded(1);
		bookUrlDao.save(bookUrl);
	}

	public boolean operateBookUrlStatus() {
		String sql = "select b.title ,d.id ,d.url from v_book_url d left join v_book_info b on b.id = d.book_id  where  d.wx_keyword is  null  and baidu_password is null and d.url like  '%baidu.com%'  and downloaded is null  order by d.book_id desc limit 30";
		List<Map<String, Object>> data = queryDao.queryMap(sql);
		for (Map<String, Object> d : data) {
			Long id = NumberUtils.toLong(d.get("id").toString());
			String url = d.get("url").toString();

			try {
				Connection directConnection = JsoupUtils.getDirectConnection(url, 5000, "pan.baidu.com");
				Document doc = directConnection.get();
				System.out.println(doc.html());
				System.out.println(doc.text());
				if (StringUtils.contains(doc.text(), "链接错误没找到文件")) {
					BookUrl urlData = bookUrlDao.findOne(id);
					urlData.setDownloaded(-1);
					bookUrlDao.save(urlData);
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
		return false;
	}

}
