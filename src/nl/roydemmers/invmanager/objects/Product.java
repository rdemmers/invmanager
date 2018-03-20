package nl.roydemmers.invmanager.objects;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "inventory")
public class Product {
	@Id
	@GeneratedValue
	private int id;
	@Column(name = "barcode")
	private String barcode;
	@Column(name = "description")
	private String description;
	@Column(name = "deliverytime")
	private int deliveryTime;
	@Column(name = "price")
	private long price;
	@Column(name = "name")
	@Size(min = 1, max = 199, message = "Productnaam mag niet leeg zijn en maximaal 199 tekens")
	private String name;
	@Column(name = "orderquantity")
	private String orderMetric;
	@Column(name = "currentstock")
	private int currentStock;
	@Column(name = "stockminimum")
	private int stockMinimum;
	@Column(name = "attachment")
	private String attachment;
	@ManyToOne
	@JoinColumn(name = "supplierid")
	private Supplier supplierId;
	@JsonIgnore
	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
	private List<Order> orders;

	public Product() {
	}

	private Product(Builder builder) {
		super();
		this.id = builder.id;
		this.barcode = builder.barcode;
		this.description = builder.description;
		this.deliveryTime = builder.deliveryTime;
		this.price = builder.price;
		this.name = builder.name;
		this.orderMetric = builder.orderMetric;
		this.currentStock = builder.currentStock;
		this.stockMinimum = builder.stockMinimum;
		this.attachment = builder.attachment;
		this.supplierId = builder.supplierId;
		this.orders = builder.orders;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderMetric() {
		return orderMetric;
	}

	public void setOrderMetric(String orderQuantity) {
		this.orderMetric = orderQuantity;
	}

	public int getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(int currentStock) {
		this.currentStock = currentStock;
	}

	public int getStockMinimum() {
		return stockMinimum;
	}

	public void setStockMinimum(int stockMinimum) {
		this.stockMinimum = stockMinimum;
	}

	public Supplier getSupplier() {
		return supplierId;
	}

	public void setSupplier(Supplier supplier) {
		this.supplierId = supplier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public static class Builder {
		private final String barcode;
		private final String name;
		private final Supplier supplierId;
		
		private int id;
				
		private String description;
		private int deliveryTime = 0;
		private long price = 0;
		private String orderMetric = "";
		private int currentStock = 0;
		private int stockMinimum = 0;
		private String attachment = "";
		private List<Order> orders;
		
		public Builder(String barcode, String name, Supplier supplier) {
			this.barcode = barcode;
			this.name = name;
			this.supplierId = supplier;
		}
		
		public Builder id(int id) {
			this.id = id;
			return this;
		}
		
		public Builder description(String description) {
			this.description = description;
			return this;
		}
		
		public Builder deliveryTime(int deliveryTime) {
			this.deliveryTime = deliveryTime;
			return this;
		}
		
		public Builder price(long price) {
			this.price = price;
			return this;
		}
		
		public Builder orderMetric(String orderMetric) {
			this.orderMetric = orderMetric;
			return this;
		}
		
		public Builder currentStock(int currentStock) {
			this.currentStock = currentStock;
			return this;
		}
		
		public Builder stockMinimum(int stockMinimum) {
			this.stockMinimum = stockMinimum;
			return this;
		}
		
		public Builder attachment(String attachment) {
			this.attachment = attachment;
			return this;
		}
		public Builder orders(List<Order> orders) {
			this.orders = orders;
			return this;
		}
		
		
		public Product build() {
			return new Product(this);
		}
	}

}
