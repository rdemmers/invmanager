package nl.roydemmers.invmanager.objects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="inventorylog")
public class InventoryLogItem {
	
	@Id
	@Column(name="idinventorylog")
	private int mySqlId;
	@Column(name="itemid")
	private int itemID;
	@Column(name="oldquantity")
	private int oldQuantiy;
	@Column(name="newquantity")
	private int newQuantity;
	@Column(name="timestamp")
	private Date timeStamp;
	@Column(name="username")
	private String username;
	@Transient
	private String productName;
	@Transient
	private int actualChange;
	@Transient
	private String barcode;
	@Transient
	private Boolean underMinimum;
	@Transient
	private int quantityMinimum;
	@Transient
	private String supplier;
	
	
	public InventoryLogItem() {
		
	}
	
	public InventoryLogItem(int itemID, int oldQuantiy, int newQuantity, Date timeStamp) {
		super();
		this.itemID = itemID;
		this.oldQuantiy = oldQuantiy;
		this.newQuantity = newQuantity;
		this.timeStamp = timeStamp;
	}
	
	
	
	
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barCode) {
		this.barcode = barCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getActualChange() {
		return actualChange;
	}

	public void setActualChange(int actualChange) {
		this.actualChange = actualChange;
	}

	public int getMySqlId() {
		return mySqlId;
	}

	public void setMySqlId(int mySqlId) {
		this.mySqlId = mySqlId;
	}

	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public int getOldQuantiy() {
		return oldQuantiy;
	}
	public void setOldQuantiy(int oldQuantiy) {
		this.oldQuantiy = oldQuantiy;
	}
	public int getNewQuantity() {
		return newQuantity;
	}
	public void setNewQuantity(int newQuantity) {
		this.newQuantity = newQuantity;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getUnderMinimum() {
		return underMinimum;
	}

	public void setUnderMinimum(Boolean underMinimum) {
		this.underMinimum = underMinimum;
	}

	public int getQuantityMinimum() {
		return quantityMinimum;
	}

	public void setQuantityMinimum(int quantityMinimum) {
		this.quantityMinimum = quantityMinimum;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	
	
	
}
