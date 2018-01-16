package nl.roydemmers.invmanager.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="suppliers")
public class Supplier {
	
	@Id
	@GeneratedValue
	@Column(name="supplierid")
	private int supplierId;
	@Column(name="name")
	private String name;
	@Column(name="contact")
	private String contact;
	@Column(name="ordermail")
	private String orderMail;
	@Column(name="questionmail")
	private String questionMail;
	@Column(name="phone")
	private String phone;
	@Column(name="totalworth")
	private Long totalWorth;
	
	public Supplier() {
		
	}

	
	public Supplier(int id, String name, String contact, String orderMail, String questionMail, String phone) {
		super();
		this.supplierId = id;
		this.name = name;
		this.contact = contact;
		this.orderMail = orderMail;
		this.questionMail = questionMail;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getOrderMail() {
		return orderMail;
	}

	public void setOrderMail(String orderMail) {
		this.orderMail = orderMail;
	}

	public String getQuestionMail() {
		return questionMail;
	}

	public void setQuestionMail(String questionMail) {
		this.questionMail = questionMail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getTotalWorth() {
		return totalWorth;
	} 

	public void setTotalWorth(long supplierTotalWorth) {
		this.totalWorth = supplierTotalWorth;
	}


	public int getId() {
		return supplierId;
	}


	public void setId(int id) {
		this.supplierId = id;
	}


	public int getSupplierId() {
		return supplierId;
	}


	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}
	
	
}
