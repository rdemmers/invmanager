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

	public Product(int id, String barcode, String description, int deliveryTime, long price, @Size(min = 1, max = 199, message = "Productnaam mag niet leeg zijn en maximaal 199 tekens") String name,
			String orderQuantity, int currentStock, int stockMinimum, String attachment, Supplier supplierId, List<Order> orders) {
		super();
		this.id = id;
		this.barcode = barcode;
		this.description = description;
		this.deliveryTime = deliveryTime;
		this.price = price;
		this.name = name;
		this.orderMetric = orderQuantity;
		this.currentStock = currentStock;
		this.stockMinimum = stockMinimum;
		this.attachment = attachment;
		this.supplierId = supplierId;
		this.orders = orders;
	}

	public Product(int id, String barcode, String description, int deliveryTime, long price, @Size(min = 1, max = 199, message = "Productnaam mag niet leeg zijn en maximaal 199 tekens") String name,
			String orderQuantity, int currentStock, int stockMinimum, String attachment, Supplier supplierId) {
		super();
		this.id = id;
		this.barcode = barcode;
		this.description = description;
		this.deliveryTime = deliveryTime;
		this.price = price;
		this.name = name;
		this.orderMetric = orderQuantity;
		this.currentStock = currentStock;
		this.stockMinimum = stockMinimum;
		this.attachment = attachment;
		this.supplierId = supplierId;
	}

	public Product(String barcode, String description, int deliveryTime, long price, @Size(min = 1, max = 199, message = "Productnaam mag niet leeg zijn en maximaal 199 tekens") String name,
			String orderQuantity, int currentStock, int stockMinimum, String attachment, Supplier supplierId) {
		super();
		this.barcode = barcode;
		this.description = description;
		this.deliveryTime = deliveryTime;
		this.price = price;
		this.name = name;
		this.orderMetric = orderQuantity;
		this.currentStock = currentStock;
		this.stockMinimum = stockMinimum;
		this.attachment = attachment;
		this.supplierId = supplierId;
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

	@Override
	public String toString() {
		return "Product [id=" + id + ", barcode=" + barcode + ", deliveryTime=" + deliveryTime + ", price=" + price + ", name=" + name + ", orderMetric=" + orderMetric + ", currentStock="
				+ currentStock + ", stockMinimum=" + stockMinimum + ", supplier=" + supplierId + "]";
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

}
