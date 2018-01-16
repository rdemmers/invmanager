package nl.roydemmers.invmanager.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.dao.InventoryDao;
import nl.roydemmers.invmanager.dao.InventoryLogItemDao;
import nl.roydemmers.invmanager.objects.InventoryItem;
import nl.roydemmers.invmanager.objects.InventoryLogItem;

@Service("inventoryService")
public class InventoryService extends AbstractService {

	private InventoryDao inventoryDao;
	private InventoryLogItemDao inventoryLogItemDao;

	public InventoryDao getInventoryDao() {
		return inventoryDao;
	}

	@Autowired
	public void setInventoryDao(InventoryDao inventoryDao) {
		this.inventoryDao = inventoryDao;
	}

	@Autowired
	public void setInventoryLogItemDao(InventoryLogItemDao inventoryLogItemDao) {
		this.inventoryLogItemDao = inventoryLogItemDao;
	}

	public List<InventoryItem> getAllInventoryItems() {

		return inventoryDao.getInventoryList();

	}

	public List<InventoryItem> getLowInventoryItems() {

		List<InventoryItem> inventoryList = inventoryDao.getInventoryList();
		List<InventoryItem> lowList = new ArrayList<InventoryItem>();

		for (int i = 0; i < inventoryList.size(); i++) {
			if (inventoryList.get(i).getCurrentStock() <= inventoryList.get(i).getStockMinimum()) {
				lowList.add(inventoryList.get(i));
			}
		}

		return lowList;

	}

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
			inventoryMailService.sendMessageWithAttachment(inventoryItem.getAttachment(), inventoryItem);
		}

	}

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

	// Checks if the last change in the database is less than x ms ago.
	// Returns a new Object if the creation was too long ago, returns the passed
	// logItem if it was recent enough
	// Used to determine the time the "Last changed" notification pops up
	// TODO add setting to admin dashboard and create an entry for it in the
	// database.
	public InventoryLogItem checkIfObjectChangeIsLessThanXMs(InventoryLogItem logItem, long miliSeconds) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		long currentTime = timestamp.getTime();
		long itemTime = logItem.getTimeStamp().getTime();
		if (currentTime - itemTime < miliSeconds) {
			return logItem;
		} else {
			return new InventoryLogItem();
		}
	}
}
