package cc.notalk.v.service.book;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cc.notalk.v.dao.book.BookDao;
import cc.notalk.v.dao.book.BookUrlDao;
import cc.notalk.v.entity.book.Book;
import cc.notalk.v.entity.book.BookUrl;
import cc.notalk.v.utils.JsoupUtils;

@Component
public class JiuaijsjBookService {
	@Autowired
	BookDao bookDao;
	@Autowired
	BookUrlDao bookUrlDao;

	public void fetchBooks() {
		fetchUrl("http://bestcbooks.com/");
	}

	private void fetchUrl(String url) {
		try {
			Connection directConnection = JsoupUtils.getDirectConnection(url, 5000, "http://bestcbooks.com");

			Response response = directConnection.execute();
			int statusCode = response.statusCode();
			if (statusCode == 200) {
				Document doc = directConnection.get();
				Elements div = doc.getElementsByClass("well");
				Elements hrefs = div.get(0).getElementsByTag("a");
				for (Element element : hrefs) {
					String href = element.attr("href");
					href = "http://bestcbooks.com" + href;
					getBooks(href);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

	private void getBooks(String url) {
		try {
			Connection directConnection = JsoupUtils.getDirectConnection(url, 5000, "http://bestcbooks.com");

			Response response = directConnection.execute();
			int statusCode = response.statusCode();
			if (statusCode == 200) {
				Document doc = directConnection.get();
				Elements div = doc.getElementsByClass("categorywell");
				for (Element d : div) {

					// 实现解析网页地址,获取图书信息功能
					Elements as = d.getElementsByTag("h4").get(0).getElementsByTag("a");
					Element aElement = as.get(0);
					String bookUrl = "http://bestcbooks.com/" + aElement.attr("href");
					getBookDetail(bookUrl);
					Thread.sleep(new Random().nextInt(3000));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

	private void getBookDetail(String url) {
		try {
			Connection directConnection = JsoupUtils.getDirectConnection(url, 5000, "http://bestcbooks.com");

			Response response = directConnection.execute();
			int statusCode = response.statusCode();
			if (statusCode == 200) {
				Document doc = directConnection.get();
				Elements div = doc.getElementsByTag("blockquote");
				Element bookTag = div.get(0);
				Elements a = bookTag.getElementsByTag("a");
				String password = bookTag.text();
				password = StringUtils.substring(StringUtils.trim(password), -4, password.length());
				String title = doc.getElementsByClass("entry-title").get(0).text();
				String baiduUrl = a.get(0).attr("href");
				System.out.println(title + ">>>" + baiduUrl + ">>>>" + password);
				Book book = new Book();
				book.setNeed(1);
				book.setTitle(title);
				book.setType(2);
				book = bookDao.save(book);
				BookUrl bookUrl = new BookUrl();
				bookUrl.setBookId(book.getId());
				bookUrl.setUrl(baiduUrl);
				bookUrl.setBaiduPassword(password);
				bookUrlDao.save(bookUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

}
