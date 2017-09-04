package ren.xiayi.bookeeper.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ren.xiayi.bookeeper.entity.JsonResponseMsg;
import ren.xiayi.bookeeper.service.Jb51DataService;

@Controller
@RequestMapping("/")
public class WebController extends BaseController {

	@Autowired
	Jb51DataService dataService;

	@RequestMapping(value = "startBookList")
	@ResponseBody
	public JsonResponseMsg startBook() {
		dataService.fetchBooks();
		return new JsonResponseMsg().fill(0, "success");
	}

	@RequestMapping(value = "startBookDetail")
	@ResponseBody
	public JsonResponseMsg startBookDetail() {
		dataService.fetchBookDetail(0);
		return new JsonResponseMsg().fill(0, "success");
	}

	@RequestMapping(value = "missBookDetail")
	@ResponseBody
	public JsonResponseMsg missBookDetail() {
		dataService.fetchBookDetail(1);
		return new JsonResponseMsg().fill(0, "success");
	}

}
