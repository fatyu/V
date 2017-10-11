package cc.notalk.v.web.controller.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.notalk.v.entity.JsonResponseMsg;
import cc.notalk.v.service.site.UrlFileService;
import cc.notalk.v.web.controller.BaseController;

@Controller
@RequestMapping("/")
public class SiteController extends BaseController {

	@Autowired
	UrlFileService usrFileService;

	@RequestMapping(value = "fetchSite")
	@ResponseBody
	public JsonResponseMsg fetchSite() {
		usrFileService.fetchSite();
		return new JsonResponseMsg().fill(0, "success");
	}

	@RequestMapping(value = "site/index")
	@ResponseBody
	public JsonResponseMsg site() {
		usrFileService.fetchSite();
		return new JsonResponseMsg().fill(0, "success");
	}

}
