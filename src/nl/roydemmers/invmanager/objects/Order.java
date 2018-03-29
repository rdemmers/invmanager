package nl.roydemmers.invmanager.objects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "productorder")
public class Order {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	@JoinColumn(name = "productid")
	private Product productId;
	@Column(name = "quantity")
	private int quantity;
	@Column(name = "ordered")
	private boolean ordered;
	@Column(name = "received")
	private boolean received;
	@Column(name = "date", insertable = false, updatable = false)
	private Date date;

	public Order() {

	}

	private Order(Builder builder) {
		super();
		this.id = builder.id;
		this.productId = builder.productId;
		this.quantity = builder.quantity;
		this.ordered = builder.ordered;
		this.received = builder.received;
	}

	public boolean isOrdered() {
		return ordered;
	}
	
	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
		
	}

	public int getId() {
		return id;
	}

	public Product getProductId() {
		return productId;
	}

	public int getQuantityMultiplier() {
		return quantity;
	}

	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", productId=" + productId + ", quantity=" + quantity + ", ordered=" + ordered + ", received=" + received + ", date=" + date + "]";
	}

	public static class Builder {
		private final Product productId;

		private int id;
		private int quantity;
		private boolean ordered;
		private boolean received;

		public Builder(Product product) {
			this.productId = product;
		}

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder quantity(int quantity) {
			this.quantity = quantity;
			return this;
		}

		public Builder ordered(boolean ordered) {
			this.ordered = ordered;
			return this;
		}

		public Builder received(boolean received) {
			this.received = received;
			return this;
		}

		public Order build() {
			return new Order(this);
		}

	}

	

}
