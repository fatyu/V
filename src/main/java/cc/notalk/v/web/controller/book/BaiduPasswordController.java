package cc.notalk.v.web.controller.book;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.notalk.v.dao.QueryDao;
import cc.notalk.v.dao.book.BookUrlDao;
import cc.notalk.v.entity.JsonResponseMsg;
import cc.notalk.v.entity.book.BookUrl;
import cc.notalk.v.service.Jb51DataService;
import cc.notalk.v.web.controller.BaseController;

@Controller
@RequestMapping("/")
public class BaiduPasswordController extends BaseController {

	@Autowired
	Jb51DataService dataService;

	@Autowired
	private BookUrlDao bookUrlDao;

	@Autowired
	private QueryDao queryDao;

	@RequestMapping(value = "baiduid")
	@ResponseBody
	public JsonResponseMsg startBook(String code) {
		if (StringUtils.isEmpty(code)) {
			return new JsonResponseMsg().fill(0, "success", queryDao.query(
					"select wx_keyword from z_book_url where wx_keyword is not null  and baidu_password is null order by id desc limit 1"));
		}
		Number id = queryDao.query(
				"select id from z_book_url where wx_keyword is not null  and baidu_password is null  order by id desc  limit 1");
		BookUrl one = bookUrlDao.findOne(id.longValue());
		one.setBaiduPassword(code);
		bookUrlDao.save(one);
		return new JsonResponseMsg().fill(0, "success", queryDao.query(
				"select wx_keyword from z_book_url where wx_keyword is not null  and baidu_password is null order by id desc limit 1"));
	}

	@RequestMapping(value = "search")
	@ResponseBody
	public JsonResponseMsg search() {
		dataService.htmlsnippet();
		return new JsonResponseMsg().fill(0, "success");
	}

}
