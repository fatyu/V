package cc.notalk.v.web.controller.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.notalk.v.entity.JsonResponseMsg;
import cc.notalk.v.service.book.BestBookService;
import cc.notalk.v.service.book.JiuaijsjBookService;
import cc.notalk.v.web.controller.BaseController;

/**
 * http://www.jiuaijsj.com/books?field_category_tid=All 书籍信息采集controller
 */
@Controller
@RequestMapping("/")
public class JiuanjisuanjiBookController extends BaseController {

	@Autowired
	JiuaijsjBookService dataService;

	@RequestMapping(value = "jiuaijsj")
	@ResponseBody
	public JsonResponseMsg books() {
		dataService.fetchBooks();
		return new JsonResponseMsg().fill(0, "success");
	}
}
