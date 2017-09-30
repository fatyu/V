package ren.xiayi.bookeeper.entity.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ren.xiayi.bookeeper.entity.BaseEntity;

@Entity
@Table(name = "z_books")
public class Book extends BaseEntity {

	private static final long serialVersionUID = -2094617561504569070L;
	private String title;// varchar(256)  utf8_general_ci  NO              (NULL)                   select,insert,update,references  标题
	private String remark;//text          utf8_general_ci  YES             (NULL)                   select,insert,update,references  介绍
	private Integer type;// tinyint(4)    (NULL)           YES             (NULL)                   select,insert,update,references  1直接下载 2微信获取下载码
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
