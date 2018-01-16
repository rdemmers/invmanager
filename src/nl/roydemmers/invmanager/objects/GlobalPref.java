package nl.roydemmers.invmanager.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="preferences")
public class GlobalPref {
	@Id
	@Column(name="prefname")
	private String name;
	@Column(name="prefvalue")
	private String value;
	@Column(name="prefgroup")
	private String group;
	
	public GlobalPref() {
		
	}
	
	public GlobalPref(String name, String value, String group) {
		super();
		this.name = name;
		this.value = value;
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "GlobalPref [name=" + name + ", value=" + value + ", group=" + group + "]";
	}
	
	
	
}
