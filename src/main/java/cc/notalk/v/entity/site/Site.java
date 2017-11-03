package cc.notalk.v.entity.site;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cc.notalk.v.entity.BaseEntity;

@Entity
@Table(name = "v_site")
public class Site extends BaseEntity {

	private static final long serialVersionUID = 8813251432575386769L;
	private String name;
	private String img;
	private Long categoryId;
	private String url;
	private Integer status;

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private Integer recommend;

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "img")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Column(name = "category_id")
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "recommend")
	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
