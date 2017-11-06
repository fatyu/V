package cc.notalk.v.web.controller.book;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cc.notalk.v.dao.book.BookDao;
import cc.notalk.v.entity.book.Book;
import cc.notalk.v.service.book.Jb51DataService;
import cc.notalk.v.web.controller.BaseController;

/**
 *标记书籍是否需要下载Controller
 */
@Controller
@RequestMapping("/")
public class BookFlagController extends BaseController {

	@Autowired
	Jb51DataService dataService;

	@Autowired
	private BookDao bookDao;

	@RequestMapping(value = "flagList")
	public ModelAndView flagList() {
		ModelAndView result = new ModelAndView();
		List<Map<String, Object>> data = dataService.limitFlagData();
		result.addObject("data", data);
		result.setViewName("data");
		return result;
	}

	/**
	 * 需要微信验证码书籍列表
	 */
	@RequestMapping(value = "wxFlagList")
	public ModelAndView wxFlagList() {
		ModelAndView result = new ModelAndView();
		List<Map<String, Object>> data = dataService.limitWxFlagData();
		result.addObject("data", data);
		result.setViewName("data");
		return result;
	}

	/**
	 * 标记书籍是否需要下载
	 * @param id
	 * @param need 1 需要下载 0不需要下载
	 * @return
	 */
	@RequestMapping(value = "flag/{id}/{need}")
	public ModelAndView flag(@PathVariable Long id, @PathVariable Integer need) {
		Book book = bookDao.findOne(id);
		book.setNeed(need);
		bookDao.save(book);
		ModelAndView result = new ModelAndView();
		result.setViewName("result");
		result.addObject("command", "close");
		return result;
	}
}
