package keysmith.service.core;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PUBLIC_KEY")
public class SimpleKey {

	@Id 
	@GeneratedValue
	Integer id;

	@Basic
	String ukey;
	
	@Basic
	@NotNull
	@Column(length=1024)
	String data;

	public SimpleKey() {
		super();
	}

	public SimpleKey(Integer id, String data) {
		super();
		this.id = id;
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUkey() {
		return ukey;
	}

	public void setUkey(String ukey) {
		this.ukey = ukey;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SimpleKey [").append(id).append("=").append(data)
				.append("]");
		return builder.toString();
	}

}
