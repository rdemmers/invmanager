package nl.roydemmers.invmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.dao.GlobalPrefDao;


/**
 * @author Roy Demmers
 *
 */
@Service("globalPrefService")
public class PreferenceService {
	@Autowired
	private GlobalPrefDao globalPrefDao;

	
	/**Look up a preference value based on the name of the preference in the DB
	 * 
	 * 
	 * @param name the preference name
	 * @return The preference value
	 */
	public String getPreferenceValue(String name) {
		return globalPrefDao.get(name).getValue();
	}
	

}

