package ren.xiayi.bookeeper.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "z_url_index")
public class UrlIndex extends BaseEntity {
	private static final long serialVersionUID = -4311683593514115919L;
	private String url;

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
