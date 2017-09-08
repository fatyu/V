package ren.xiayi.bookeeper.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ren.xiayi.bookeeper.entity.JsonResponseMsg;
import ren.xiayi.bookeeper.service.UrlFileService;

@Controller
@RequestMapping("/")
public class SiteController extends BaseController {

	@Autowired
	UrlFileService usrFileService;

	@RequestMapping(value = "site")
	@ResponseBody
	public JsonResponseMsg site() {
		usrFileService.fetchSite();
		return new JsonResponseMsg().fill(0, "success");
	}

}
