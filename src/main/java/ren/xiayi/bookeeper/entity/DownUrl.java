package ren.xiayi.bookeeper.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "z_download_url")
public class DownUrl extends BaseEntity {

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

	private static final long serialVersionUID = -2835433118007761240L;
	private String url;
	private String wxKeyword;
	private Long bookId;

}
