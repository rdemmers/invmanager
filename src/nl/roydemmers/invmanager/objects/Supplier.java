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

	// constructor without ID, for Junit testing
	public Supplier(String name, String contact, String orderMail, String questionMail, String phone) {
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orderMail == null) ? 0 : orderMail.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((questionMail == null) ? 0 : questionMail.hashCode());
		result = prime * result + supplierId;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Supplier other = (Supplier) obj;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderMail == null) {
			if (other.orderMail != null)
				return false;
		} else if (!orderMail.equals(other.orderMail))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (questionMail == null) {
			if (other.questionMail != null)
				return false;
		} else if (!questionMail.equals(other.questionMail))
			return false;
		if (supplierId != other.supplierId)
			return false;
		return true;
	}
	
	
	
}
