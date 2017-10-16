package cc.notalk.v.entity.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cc.notalk.v.entity.BaseEntity;

@Entity
@Table(name = "v_book_info")
public class Book extends BaseEntity {

	private static final long serialVersionUID = -2094617561504569070L;
	private String title;// 标题
	private String remark;//介绍
	private Integer type;//1直接下载 2微信获取下载码
	private String url;

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
