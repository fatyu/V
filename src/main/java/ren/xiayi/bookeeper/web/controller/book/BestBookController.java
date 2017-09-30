package ren.xiayi.bookeeper.web.controller.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ren.xiayi.bookeeper.entity.JsonResponseMsg;
import ren.xiayi.bookeeper.service.BestBookService;
import ren.xiayi.bookeeper.web.controller.BaseController;

@Controller
@RequestMapping("/")
public class BestBookController extends BaseController {

	@Autowired
	BestBookService dataService;

	@RequestMapping(value = "books")
	@ResponseBody
	public JsonResponseMsg books() {
		//http://bestcbooks.com/
		dataService.fetchBooks();
		return new JsonResponseMsg().fill(0, "success");
	}
}
