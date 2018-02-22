package nl.roydemmers.invmanager.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.dao.GlobalPrefDao;
import nl.roydemmers.invmanager.objects.GlobalPref;

// Service that communicates with the preference DAO 

@Service("globalPrefService")
public class PreferenceService {
	@Autowired
	private GlobalPrefDao globalPrefDao;

	
	public GlobalPref getPreference(String name) {
		return globalPrefDao.get(name);
		 
	}
	
	public String getPreferenceValue(String name) {
		return globalPrefDao.get(name).getValue();
	}
	
	@Secured("ROLE_ADMIN")
	public void updateGlobalPreference(GlobalPref preference) {
		globalPrefDao.set(preference);
	}
	
	@Secured("ROLE_ADMIN")
	public Map<String, GlobalPref> getPreferences(){
		return globalPrefDao.getAll();
	}
	
	@Secured("ROLE_ADMIN")
	public Map<String, String> getPreferenceGroup(String group){
		return globalPrefDao.getGroup(group);
	}
	
	@Secured("ROLE_ADMIN")
	public List<GlobalPref> getPreferenceGroupList(String group){
		return globalPrefDao.getList(group);
	}
	
	@Secured("ROLE_ADMIN")
	public void updateAllPreferencesInDb(Map<String, String> preferenceMap) {
		for(Map.Entry<String, String> entry : preferenceMap.entrySet()){
			  GlobalPref preference =  this.getPreference(entry.getKey());
			  preference.setValue(entry.getValue());
			  this.updateGlobalPreference(preference);  
			}	
	}
}

