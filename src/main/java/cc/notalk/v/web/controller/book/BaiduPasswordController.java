package cc.notalk.v.web.controller.book;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cc.notalk.v.dao.book.BookUrlDao;
import cc.notalk.v.entity.book.BookUrl;
import cc.notalk.v.service.book.Jb51DataService;
import cc.notalk.v.web.controller.BaseController;

@Controller
@RequestMapping("/")
public class BaiduPasswordController extends BaseController {

	@Autowired
	Jb51DataService dataService;

	@Autowired
	private BookUrlDao bookUrlDao;

	@RequestMapping(value = "bf")
	public ModelAndView bookFix() {
		ModelAndView result = new ModelAndView();
		List<Map<String, Object>> data = dataService.limit5Data();
		//		if (StringUtils.isEmpty(code)) {
		//			return new JsonResponseMsg().fill(0, "success", queryDao.query(
		//					"select wx_keyword from v_book_url where wx_keyword is not null  and baidu_password is null limit 1"));
		//		}
		//		Number id = queryDao
		//				.query("select id from v_book_url where wx_keyword is not null  and baidu_password is null   limit 1");
		//		BookUrl one = bookUrlDao.findOne(id.longValue());
		//		one.setBaiduPassword(code);
		//		bookUrlDao.save(one);
		//		return new JsonResponseMsg().fill(0, "success", queryDao.query(
		//				"select wx_keyword from v_book_url where wx_keyword is not null  and baidu_password is  limit 1"));
		result.addObject("data", data);
		result.setViewName("data");
		return result;
	}

	@RequestMapping(value = "fix")
	@ResponseBody
	public String fix(String c, String u, Long id) {
		BookUrl bookUrl = bookUrlDao.findOne(id);
		if (StringUtils.isNotBlank(c)) {
			bookUrl.setBaiduPassword(c);
		}
		if (StringUtils.isNotBlank(u)) {
			bookUrl.setBaiduUrl(u);
		}
		bookUrlDao.save(bookUrl);
		return "success";
	}
}
