package nl.roydemmers.invmanager.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "suppliers")
public class Supplier {

	@Id
	@GeneratedValue
	@Column(name = "supplierid")
	private int supplierId;
	@Column(name = "name")
	private String name;
	@Column(name = "contact")
	private String contact;
	@Column(name = "ordermail")
	private String orderMail;
	@Column(name = "questionmail")
	private String questionMail;
	@Column(name = "phone")
	private String phone;
	@Column(name = "totalworth")
	private Long totalWorth;

	public Supplier() {

	}
	
	

	private Supplier(Builder builder) {
		super();
		this.supplierId = builder.supplierId;
		this.name = builder.name;
		this.contact = builder.contact;
		this.orderMail = builder.orderMail;
		this.questionMail = builder.questionMail;
		this.phone = builder.phone;
		this.totalWorth = builder.totalWorth;
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

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public static class Builder {
		private final String name;
		private final String orderMail;

		private int supplierId;
		private String contact;
		
		private String questionMail;
		private String phone;
		private Long totalWorth;

		public Builder(String name, String orderMail) {
			this.name = name;
			this.orderMail = orderMail;
		}

		public Builder supplierId(int supplierId) {
			this.supplierId = supplierId;
			return this;
		}
		
		public Builder contact(String contact) {
			this.contact = contact;
			return this;
		}
		
		public Builder questionMail(String questionMail) {
			this.questionMail = questionMail;
			return this;
		}
		
		public Builder phone(String phone) {
			this.phone = phone;
			return this;
		}
		
		public Builder totalWorth(long totalWorth) {
			this.totalWorth = totalWorth;
			return this;
		}

		public Supplier build() {
			return new Supplier(this);
		}
	}

	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", name=" + name + ", contact=" + contact + ", orderMail=" + orderMail + ", questionMail=" + questionMail + ", phone=" + phone + ", totalWorth="
				+ totalWorth + "]";
	}
	
	
}