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
	@Column(name="received")
	private boolean received;
	@Column(name="comments")
	private String comments;
	@Column(name="date", insertable = false, updatable = false)
	private Date date;
	
	public Order() {
		
	}
	
	public Order(int id, Product productId, int quantityMultiplier, boolean received, String comments) {
		super();
		this.id = id;
		this.productId = productId;
		this.quantityMultiplier = quantityMultiplier;
		this.received = received;
		this.comments = comments;
	}
	
	
	public Order(Product productId, int quantityMultiplier, boolean received, String comments) {
		super();
		this.productId = productId;
		this.quantityMultiplier = quantityMultiplier;
		this.received = received;
		this.comments = comments;
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}


	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", productId=" + productId + ", quantityMultiplier=" + quantityMultiplier + ", received=" + received + ", comments=" + comments + ", date=" + date + "]";
	}
	
	
	
}
