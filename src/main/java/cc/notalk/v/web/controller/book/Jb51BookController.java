package cc.notalk.v.web.controller.book;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cc.notalk.v.entity.JsonResponseMsg;
import cc.notalk.v.service.book.Jb51DataService;
import cc.notalk.v.web.controller.BaseController;

/**
 * 针对jb51的电子书籍的处理controller
 * @author fatyu
 */
@Controller
@RequestMapping("/")
public class Jb51BookController extends BaseController {

	@Autowired
	Jb51DataService jb51DataService;

	/**
	 * 根据分类索引页面,进行书籍基本信息采集
	 */
	@RequestMapping(value = "getAll")
	@ResponseBody
	public JsonResponseMsg getAll() {
		jb51DataService.fetchBooks();
		return new JsonResponseMsg().fill(0, "success");
	}

	/**
	 * 获取书籍详情(下载地址,书籍基本信息介绍)
	 * @return
	 */
	@RequestMapping(value = "getDetail")
	@ResponseBody
	public JsonResponseMsg getDetail() {
		jb51DataService.fetchBookDetail(0);
		return new JsonResponseMsg().fill(0, "success");
	}

	/**
	 * 补充没有书籍详情信息
	 */
	@RequestMapping(value = "missBookDetail")
	@ResponseBody
	public JsonResponseMsg missBookDetail() {
		jb51DataService.fetchBookDetail(1);
		return new JsonResponseMsg().fill(0, "success");
	}

	/**
	 * 更新书籍信息采集
	 */
	@RequestMapping(value = "newBooks")
	@ResponseBody
	public JsonResponseMsg fetchNewBooks() {
		jb51DataService.fetchNewBookDetail();
		return new JsonResponseMsg().fill(0, "success");
	}

	/**
	 * 删除重复书籍
	 */
	@RequestMapping(value = "removeDuplicateBook")
	@ResponseBody
	public JsonResponseMsg removeDuplicateBook() {
		jb51DataService.removeDuplicateBook();
		return new JsonResponseMsg().fill(0, "success");
	}

	/**
	 * 直接下载书籍(非百度云分享链接)
	 */
	@RequestMapping(value = "downloadFile")
	@ResponseBody
	public JsonResponseMsg downloadFile() {
		jb51DataService.downloadFile();
		return new JsonResponseMsg().fill(0, "success");
	}

	/**
	 * 百度云(无密码)分享链接书籍列表
	 * @return
	 */
	@RequestMapping(value = "baiduList")
	public ModelAndView baiduList() {
		ModelAndView result = new ModelAndView();
		result.setViewName("baidubook");
		result.addObject("data", jb51DataService.baiduDownload());
		return result;
	}

	/**
	 * 更新百度链接不存在书籍状态
	 * @return
	 */
	@RequestMapping(value = "baiduNo")
	public ModelAndView baiduNo() {
		ModelAndView result = new ModelAndView();
		result.setViewName("baidubook");
		result.addObject("data", jb51DataService.operateBookUrlStatus());
		return result;
	}

	@RequestMapping(value = "wxbooks")
	public ModelAndView bookFix() {
		ModelAndView result = new ModelAndView();
		List<Map<String, Object>> data = jb51DataService.limit5Data();
		result.addObject("data", data);
		result.setViewName("needWx");
		return result;
	}

	/**
	 * 标记书籍需要密码
	 * @return
	 */
	@RequestMapping(value = "needPassword/{id}")
	public ModelAndView needPassword(@PathVariable Long id) {
		ModelAndView result = new ModelAndView();
		jb51DataService.updateWxKeyword(id);
		result.setViewName("result");
		result.addObject("command", "close");
		return result;
	}

	/**
	 * 标记书籍需要密码
	 * @return
	 */
	@RequestMapping(value = "downloaded/{id}")
	public ModelAndView downloaded(@PathVariable Long id) {
		ModelAndView result = new ModelAndView();
		jb51DataService.updateBookDownloaded(id);
		result.setViewName("result");
		result.addObject("command", "close");
		return result;
	}

}