package ren.xiayi.bookeeper.entity.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ren.xiayi.bookeeper.entity.BaseEntity;

@Entity
@Table(name = "z_book_index")
public class CategoryIndex extends BaseEntity {
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
