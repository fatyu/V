package ren.xiayi.bookeeper.web.controller.book;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ren.xiayi.bookeeper.dao.QueryDao;
import ren.xiayi.bookeeper.dao.book.DownUrlDao;
import ren.xiayi.bookeeper.entity.JsonResponseMsg;
import ren.xiayi.bookeeper.entity.book.DownUrl;
import ren.xiayi.bookeeper.service.Jb51DataService;
import ren.xiayi.bookeeper.web.controller.BaseController;

@Controller
@RequestMapping("/")
public class BaiduPasswordController extends BaseController {

	@Autowired
	Jb51DataService dataService;

	@Autowired
	private DownUrlDao downUrlDao;

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
		DownUrl one = downUrlDao.findOne(id.longValue());
		one.setBaiduPassword(code);
		downUrlDao.save(one);
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
