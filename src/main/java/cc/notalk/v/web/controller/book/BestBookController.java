package cc.notalk.v.web.controller.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.notalk.v.entity.JsonResponseMsg;
import cc.notalk.v.service.book.BestBookService;
import cc.notalk.v.web.controller.BaseController;

/**
 * http://bestcbooks.com/ 书籍信息采集controller
 */
@Deprecated
@Controller
@RequestMapping("/")
public class BestBookController extends BaseController {

	@Autowired
	BestBookService dataService;

	@RequestMapping(value = "bestcbooks")
	@ResponseBody
	public JsonResponseMsg books() {
		dataService.fetchBooks();
		return new JsonResponseMsg().fill(0, "success");
	}
}
