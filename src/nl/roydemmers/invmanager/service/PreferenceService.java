package nl.roydemmers.invmanager.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.roydemmers.invmanager.dao.GlobalPrefDao;
import nl.roydemmers.invmanager.objects.GlobalPref;

// Service that communicates with the preference DAO 

@Service("globalPrefService")
public class PreferenceService {
	@Autowired
	private GlobalPrefDao globalPrefDao;

	
	public GlobalPref getPreference(String name) {
		return globalPrefDao.getPreference(name);
		 
	}
	
	public String getPreferenceValue(String name) {
		return globalPrefDao.getPreference(name).getValue();
	}
	
	public void updateGlobalPreference(GlobalPref preference) {
		globalPrefDao.setPreference(preference);
	}
	
	public Map<String, GlobalPref> getPreferences(){
		return globalPrefDao.getAllPreferences();
	}
	
	public Map<String, String> getPreferenceGroup(String group){
		return globalPrefDao.getPreferenceGroup(group);
	}
	
	public List<GlobalPref> getPreferenceGroupList(String group){
		return globalPrefDao.getPreferenceGroupList(group);
	}
	
	public void updateAllPreferencesInDb(Map<String, String> preferenceMap) {
		for(Map.Entry<String, String> entry : preferenceMap.entrySet()){
			  GlobalPref preference =  this.getPreference(entry.getKey());
			  preference.setValue(entry.getValue());
			  this.updateGlobalPreference(preference);  
			}	
	}
}

