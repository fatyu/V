package cc.notalk.v.entity.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cc.notalk.v.entity.BaseEntity;

@Entity
@Table(name = "v_book_url")
public class BookUrl extends BaseEntity {
	private static final long serialVersionUID = -2835433118007761240L;
	private String url;
	private String wxKeyword;
	private Long bookId;
	private String baiduPassword;
	private String baiduUrl;

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "wx_keyword")
	public String getWxKeyword() {
		return wxKeyword;
	}

	public void setWxKeyword(String wxKeyword) {
		this.wxKeyword = wxKeyword;
	}

	@Column(name = "book_id")
	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	@Column(name = "baidu_password")
	public String getBaiduPassword() {
		return baiduPassword;
	}

	public void setBaiduPassword(String baiduPassword) {
		this.baiduPassword = baiduPassword;
	}

	@Column(name = "fatyu_baidu_url")
	public String getBaiduUrl() {
		return baiduUrl;
	}

	public void setBaiduUrl(String baiduUrl) {
		this.baiduUrl = baiduUrl;
	}

}
