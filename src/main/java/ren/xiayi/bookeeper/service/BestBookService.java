package ren.xiayi.bookeeper.service;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.springframework.stereotype.Component;

import ren.xiayi.bookeeper.utils.JsoupUtils;

@Component
public class BestBookService {
	//	private static final Logger logger = LoggerFactory.getLogger(BestBookService.class);
	//	@Autowired
	//	private BookDao bookDao;

	public void fetchBooks() {
		fetchUrl("http://bestcbooks.com/");
	}

	private void fetchUrl(String url) {
		try {
			Connection directConnection = JsoupUtils.getDirectConnection(url, 5000, "http://bestcbooks.com");

			Response response = directConnection.execute();
			int statusCode = response.statusCode();
			if (statusCode == 200) {
				//				Document doc = directConnection.get();
				//TODO 实现解析网页地址,获取图书信息功能
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

}
