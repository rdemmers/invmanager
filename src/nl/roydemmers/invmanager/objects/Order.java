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
@Table(name="productorder")
public class Order {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	@JoinColumn(name="productid")
	private Product productId;
	@Column(name="quantity")
	private int quantityMultiplier;
	@Column(name="ordered")
	private boolean ordered;
	@Column(name="received")
	private boolean received;
	@Column(name="date", insertable = false, updatable = false)
	private Date date;
	
	public Order() {
		
	}
	

	

	public Order(Product productId, int quantityMultiplier, boolean ordered, boolean received) {
		super();
		this.productId = productId;
		this.quantityMultiplier = quantityMultiplier;
		this.ordered = ordered;
		this.received = received;
	}




	public Order(int id, Product productId, int quantityMultiplier, boolean ordered, boolean received) {
		super();
		this.id = id;
		this.productId = productId;
		this.quantityMultiplier = quantityMultiplier;
		this.ordered = ordered;
		this.received = received;
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
	public void setId(int id) {
		this.id = id;
	}
	public Product getProductId() {
		return productId;
	}
	public void setProductId(Product productId) {
		this.productId = productId;
	}
	public int getQuantityMultiplier() {
		return quantityMultiplier;
	}
	public void setQuantityMultiplier(int quantityMultiplier) {
		this.quantityMultiplier = quantityMultiplier;
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
		return "Order [id=" + id + ", productId=" + productId + ", quantityMultiplier=" + quantityMultiplier + ", received=" + received + " date=" + date + "]";
	}
	
	
	
}
