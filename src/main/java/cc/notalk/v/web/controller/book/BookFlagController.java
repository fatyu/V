package cc.notalk.v.web.controller.book;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cc.notalk.v.dao.book.BookDao;
import cc.notalk.v.entity.book.Book;
import cc.notalk.v.service.FileDownloadService;
import cc.notalk.v.service.book.Jb51DataService;
import cc.notalk.v.web.controller.BaseController;

@Controller
@RequestMapping("/")
public class BookFlagController extends BaseController {

	@Autowired
	Jb51DataService dataService;
	@Autowired
	FileDownloadService fileDownloadService;

	@Autowired
	private BookDao bookDao;

	@RequestMapping(value = "flag")
	public ModelAndView flag() {
		ModelAndView result = new ModelAndView();
		List<Map<String, Object>> data = dataService.limit100FlagData();
		result.addObject("data", data);
		result.setViewName("data");
		return result;
	}

	@RequestMapping(value = "flag/{id}/{status}")
	@ResponseBody
	public ModelAndView fix(@PathVariable Long id, @PathVariable Integer status) {
		Book book = bookDao.findOne(id);
		book.setNeed(status);
		bookDao.save(book);
		ModelAndView result = new ModelAndView();
		result.setViewName("result");
		result.addObject("command", "close");
		return result;
	}

	@RequestMapping(value = "wxflag")
	public ModelAndView wxflag() {
		ModelAndView result = new ModelAndView();
		List<Map<String, Object>> data = dataService.limitWx100FlagData();
		result.addObject("data", data);
		result.setViewName("data");
		return result;
	}
}
