package cc.notalk.v.web.controller.book;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

import cc.notalk.v.dao.book.BookDao;
import cc.notalk.v.entity.book.Book;
import cc.notalk.v.service.book.Jb51DataService;
import cc.notalk.v.utils.JsonUtils;
import cc.notalk.v.utils.JsoupUtils;
import cc.notalk.v.web.controller.BaseController;

/**
 * 标记书籍是否需要下载Controller
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
	 * 
	 * @param id
	 * @param need
	 *            1 需要下载 0不需要下载
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

	/**
	 * 清理书籍链接无效页面地址
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "clearLink")
	@ResponseBody
	public String flag() throws IOException {
		List<String> result = Lists.newArrayList();
		List<String> readLines = FileUtils.readLines(new File("d:\\file.txt"), Charsets.UTF_8);
		for (String linkText : readLines) {
			if (StringUtils.isNotBlank(linkText)) {
				String[] texts = StringUtils.split(linkText, "|");
				String link = StringUtils.trim(texts[0]);
				if (StringUtils.isNotBlank(link)) {
					if (!containsDelete(link)) {
						System.out.println(linkText);
						result.add(linkText);
					}
				}
			}
		}
		return JsonUtils.objectToString(result);
	}

	private boolean containsDelete(String link) {
		try {
			Connection directConnection = JsoupUtils.getDirectConnection(link, 5000, "http://bestcbooks.com");

			Response response = directConnection.execute();
			int statusCode = response.statusCode();
			if (statusCode == 200) {
				Document doc = directConnection.get();
				if (StringUtils.contains(doc.text(), "下载链接已删除")) {
					return true;
				}
			}
			Thread.sleep(RandomUtils.nextLong(300, 1000));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return false;

	}
}
