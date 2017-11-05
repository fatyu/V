package cc.notalk.v.web.controller.site;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cc.notalk.v.dao.site.SiteDao;
import cc.notalk.v.entity.JsonResponseMsg;
import cc.notalk.v.entity.site.Site;
import cc.notalk.v.service.site.FileService;
import cc.notalk.v.service.site.UrlService;
import cc.notalk.v.web.controller.BaseController;

@Controller
@RequestMapping("/")
public class SiteController extends BaseController {

	@Autowired
	FileService fileService;

	@Autowired
	UrlService urlService;

	@RequestMapping(value = "site/file2db")
	@ResponseBody
	public JsonResponseMsg file2db() {
		fileService.fetchSite();
		return new JsonResponseMsg().fill(0, "success");
	}

	/**
	 * @param url 地址url
	 * @param filterTagAString支持过滤a标签内容
	 * @param currentPageValid 是否解析当前页面
	 * @param depth 深度为1的页面加载
	 * @return
	 */
	@RequestMapping(value = "site/url")
	@ResponseBody
	public JsonResponseMsg page2db(String url, String filter, String depth) {

		String[] urls = StringUtils.split(url, "|");
		for (String uri : urls) {
			urlService.fetchSite(uri, filter, depth);
		}
		return new JsonResponseMsg().fill(0, "success");

	}

	@RequestMapping(value = "site/content")
	@ResponseBody
	public JsonResponseMsg content2File() {
		urlService.allUrlFetch("E:\\data\\webfile");
		return new JsonResponseMsg().fill(0, "success");
	}

	@RequestMapping(value = "site/flag")
	public ModelAndView flag() {
		ModelAndView result = new ModelAndView();
		List<Map<String, Object>> data = urlService.list();
		result.addObject("data", data);
		result.setViewName("site");
		return result;
	}

	@Autowired
	SiteDao siteDao;

	@RequestMapping(value = "site/flag/{id}/{status}")
	public ModelAndView fix(@PathVariable Long id, @PathVariable Integer status) {
		if (status.intValue() == 1) {
			siteDao.delete(id);
		} else {
			Site site = siteDao.findOne(id);
			site.setStatus(1);
			siteDao.save(site);
		}
		ModelAndView result = new ModelAndView();
		result.setViewName("result");
		result.addObject("command", "close");
		return result;
	}
	@RequestMapping(value = "site/flag/page")
	public ModelAndView flagPage() {
		urlService.flagPage();
		ModelAndView result = new ModelAndView();
		result.setViewName("result");
		result.addObject("command", "close");
		return result;
	}

	//http://www.alexa.cn/siterank/%E5%B9%BF%E4%B8%9C/2

}
