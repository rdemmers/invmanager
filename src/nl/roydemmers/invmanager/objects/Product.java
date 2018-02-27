package nl.roydemmers.invmanager.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Entity
@Table(name="inventory")
public class Product {
	@Id
	@GeneratedValue
	private int id;
	@Column(name="barcode")
	private String barcode;
	@Column(name="deliverytime")
	private int deliveryTime;
	@Column(name="price")
	private long price;
	@Column(name="name")
	@Size(min=1, max=199, message="Productnaam mag niet leeg zijn en maximaal 199 tekens")
	private String name;
	@Column(name="orderquantity")
	private int orderQuantity;
	@Column(name="currentstock")
	private int currentStock;
	@Column(name="stockminimum")
	private int stockMinimum;
	@Column(name="attachment")
	private String attachment;
	@ManyToOne
	@JoinColumn(name="supplierid")
	private Supplier supplierId;

	public Product() {}

	public Product(int id, String barcode, int deliveryTime, long price, String name, int orderQuantity,
			int currentStock, int stockMinimum, Supplier supplier, String attachment) {
		this.id = id;
		this.barcode = barcode;
		this.deliveryTime = deliveryTime;
		this.price = price;
		this.name = name;
		this.orderQuantity = orderQuantity;
		this.currentStock = currentStock;
		this.stockMinimum = stockMinimum;
		this.supplierId = supplier;
		this.attachment = attachment;
	}
	
	public Product(String barcode, int deliveryTime, long price, String name, int orderQuantity,
			int currentStock, int stockMinimum, Supplier supplier, String attachment) {
		this.barcode = barcode;
		this.deliveryTime = deliveryTime;
		this.price = price;
		this.name = name;
		this.orderQuantity = orderQuantity;
		this.currentStock = currentStock;
		this.stockMinimum = stockMinimum;
		this.supplierId = supplier;
		this.attachment = attachment;
	}
	
	
	public InventoryItemDoubleValue convertPriceToDouble() {
		InventoryItemDoubleValue conversion = new InventoryItemDoubleValue();
		
		double newPrice = this.price;
		
		newPrice /= 100;
		
		conversion.setBarcode(this.barcode);
		conversion.setId(this.id);
		conversion.setDeliveryTime(this.deliveryTime);
		conversion.setPrice(newPrice);
		conversion.setName(this.name);
		conversion.setOrderQuantity(this.orderQuantity);
		conversion.setCurrentStock(this.currentStock);
		conversion.setStockMinimum(this.stockMinimum);
		conversion.setSupplier(this.supplierId);
		return conversion;
		
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

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
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

	@Override
	public String toString() {
		return "Product [id=" + id + ", barcode=" + barcode + ", deliveryTime=" + deliveryTime + ", price="
				+ price + ", name=" + name + ", orderQuantity=" + orderQuantity + ", currentStock=" + currentStock
				+ ", stockMinimum=" + stockMinimum + ", supplier=" + supplierId + "]";
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	

	
}
