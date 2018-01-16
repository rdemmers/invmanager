package nl.roydemmers.invmanager.objects;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Size;

public class InventoryItemDoubleValue {

		
		private int id;
		private String barcode;
		private int deliveryTime;
		private double price;		
		private String name;
		private int orderQuantity;
		private int currentStock;
		private int stockMinimum;
		private Supplier supplier;
		
		public InventoryItemDoubleValue() {}

		public InventoryItemDoubleValue(int id, String barcode, int deliveryTime, double price, String name, int orderQuantity,
				int currentStock, int stockMinimum, Supplier supplier) {
			this.id = id;
			this.barcode = barcode;
			this.deliveryTime = deliveryTime;
			this.price = price;
			this.name = name;
			this.orderQuantity = orderQuantity;
			this.currentStock = currentStock;
			this.stockMinimum = stockMinimum;
			this.supplier = supplier;
		}
		
		
		public InventoryItem convertPriceToLong() {
			
			
			InventoryItem conversion = new InventoryItem();
			
			DecimalFormat df = new DecimalFormat("#.00"); 
			String priceConversion = df.format(price);
			
			String removeDot = priceConversion.replaceAll("\\.", "");
			String removeComma = removeDot.replaceAll(",", "");
			
			long newPrice = Long.parseLong(removeComma);
			
			
			conversion.setBarcode(this.barcode);
			conversion.setId(this.id);
			conversion.setDeliveryTime(this.deliveryTime);
			conversion.setPrice(newPrice);
			conversion.setName(this.name);
			conversion.setOrderQuantity(this.orderQuantity);
			conversion.setCurrentStock(this.currentStock);
			conversion.setStockMinimum(this.stockMinimum);
			conversion.setSupplier(this.supplier);
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

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
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
			return supplier;
		}

		public void setSupplier(Supplier supplier) {
			this.supplier = supplier;
		}

		@Override
		public String toString() {
			return "InventoryItem [id=" + id + ", barcode=" + barcode + ", deliveryTime=" + deliveryTime + ", price="
					+ price + ", name=" + name + ", orderQuantity=" + orderQuantity + ", currentStock=" + currentStock
					+ ", stockMinimum=" + stockMinimum + ", supplier=" + supplier + "]";
		}

		

}
