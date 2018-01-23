package nl.roydemmers.invmanager.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nl.roydemmers.invmanager.dao.InventoryDao;
import nl.roydemmers.invmanager.dao.InventoryLogItemDao;
import nl.roydemmers.invmanager.objects.InventoryItem;
import nl.roydemmers.invmanager.objects.InventoryItemDoubleValue;
import nl.roydemmers.invmanager.objects.InventoryLogItem;

@Service("inventoryService")
public class InventoryService {
	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	private InventoryLogItemDao inventoryLogItemDao;
	@Autowired
	private InventoryMailService inventoryMailService;


	
	public List<InventoryItem> getAllInventoryItems() {
		return inventoryDao.getInventoryList();
	}
	
	public List<InventoryItem> getItemsWithAttachment(){
		List<InventoryItem> inventoryItems = this.getAllInventoryItems();
		List<InventoryItem> inventoryAttachments = new ArrayList<>();
		
		for(InventoryItem inventoryItem : inventoryItems) {
			if(inventoryItem.getAttachment().length() > 4) {
				inventoryAttachments.add(inventoryItem);
			}
		}
		
		return inventoryAttachments;
		
	}

	// To display a List with items under the stock minimum
	public List<InventoryItem> getLowInventoryItems() {
		List<InventoryItem> inventoryList = inventoryDao.getInventoryList();
		List<InventoryItem> lowList = new ArrayList<>();

		for (int i = 0; i < inventoryList.size(); i++) {
			if (inventoryList.get(i).getCurrentStock() <= inventoryList.get(i).getStockMinimum()) {
				lowList.add(inventoryList.get(i));
			}
		}
		return lowList;
	}

	// Display the last x changes in inventory amount
	public List<InventoryLogItem> getRecentChanges(boolean getAll) {

		List<InventoryLogItem> inventoryLogList = inventoryLogItemDao.getQuantityChangesHistory();

		// Try - catch statement in case of an empty database
		try {
			Collections.reverse(inventoryLogList);
			
			if (!getAll) {
				this.shortenInventoryLogList(inventoryLogList, 19);
			}

			for (InventoryLogItem inventoryLogItem : inventoryLogList) {
				InventoryItem inventoryItem = this.getInventoryItem(inventoryLogItem.getItemID());
				inventoryLogItem.setProductName(inventoryItem.getName());
				inventoryLogItem.setActualChange(inventoryLogItem.getNewQuantity() - inventoryLogItem.getOldQuantiy());
				inventoryLogItem.setBarcode(inventoryItem.getBarcode());
			}
		} catch (NullPointerException e) {
			System.out.println(e);
		}

		return inventoryLogList;
	}

	public List<InventoryLogItem> shortenInventoryLogList(List<InventoryLogItem> inventoryLogList, int listLength) {

		if (inventoryLogList.size() > listLength) {
			inventoryLogList = inventoryLogList.subList(0, listLength);
		}

		return inventoryLogList;
	}

	public void deleteInventoryItem(int id) {
		inventoryDao.delete(id);
	}

	public void create(InventoryItem inventoryItem) {
		inventoryDao.create(inventoryItem);
	}

	public InventoryItem getInventoryItem(int id) {
		return inventoryDao.getInventoryItem(id);
	}

	public void updateInventoryItem(InventoryItem inventoryItem) {
		inventoryDao.update(inventoryItem);
	}

	public void checkStockForMail(int id) {
		InventoryItem inventoryItem = this.getInventoryItem(id);
		if (inventoryItem.getCurrentStock() <= inventoryItem.getStockMinimum()) {
			inventoryMailService.createNotificationMail(inventoryItem);
		}

	}

	// Since log items aren't stored with all their properties this function appends the object
	// with values from the database
	public void fillLogItemWithItemProps(InventoryLogItem logItem, InventoryItem inventoryItem) {

		logItem.setProductName(inventoryItem.getName());
		logItem.setBarcode(inventoryItem.getBarcode());
		logItem.setQuantityMinimum(inventoryItem.getStockMinimum());
		logItem.setSupplier(inventoryItem.getSupplier().getName());
		if (logItem.getNewQuantity() < logItem.getQuantityMinimum()) {
			logItem.setUnderMinimum(true);
		} else {
			logItem.setUnderMinimum(false);
		}
	}
	
	// Used to circumvent issues with double values in money and makes it easier to manipulate objects through the front end.
	public InventoryItem convertTempItemToFullObject(InventoryItemDoubleValue inventoryItemTemp, InventoryItem currentItem) {
		InventoryItem inventoryItem = inventoryItemTemp.convertPriceToLong();
		
		inventoryItem.setId(currentItem.getId());
		inventoryItem.setSupplier(currentItem.getSupplier());
		inventoryItem.setAttachment(currentItem.getAttachment());
		
		return inventoryItem;
	}
}
