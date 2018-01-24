package nl.roydemmers.invmanager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.roydemmers.invmanager.objects.GlobalPref;
import nl.roydemmers.invmanager.objects.User;

@Transactional
@Component("globalPrefDao")
public class GlobalPrefDao extends AbstractDao {
	
	
	@SuppressWarnings("unchecked")
	public GlobalPref getPreference(String name) {
		
		Criteria crit = session().createCriteria(GlobalPref.class);
		crit.add(Restrictions.eq("name", name));
		GlobalPref globalPref = (GlobalPref)crit.uniqueResult();
		return globalPref;
		
	}
	
	@Secured("ROLE_ADMIN")
	public void setPreference(GlobalPref globalPref) {
		session().update(globalPref);
		
	}
	
	@Secured("ROLE_ADMIN")
	@SuppressWarnings("unchecked")
	public Map<String, GlobalPref> getAllPreferences(){
		List<GlobalPref> preferenceList = session().createQuery("from GlobalPref").list();
		Map<String,GlobalPref> preferenceMap = new HashMap<String,GlobalPref>();
		for(GlobalPref globalPref : preferenceList) {
			preferenceMap.put(globalPref.getName(), globalPref);
		}
		
		return preferenceMap;
	}
	
	@Secured("ROLE_ADMIN")
	@SuppressWarnings("unchecked")
	public Map<String, String> getPreferenceGroup(String group){
		Map<String,String> preferenceMap = new HashMap<String,String>();
		
		Criteria crit = session().createCriteria(GlobalPref.class);
		crit.add(Restrictions.eq("group", group));
		List<GlobalPref> globalPrefList = crit.list();
		
		for(GlobalPref globalPref : globalPrefList) {
			preferenceMap.put(globalPref.getName(), globalPref.getValue());
		}
		
		return preferenceMap;
	}
	
	@Secured("ROLE_ADMIN")
	@SuppressWarnings("unchecked")
	public List<GlobalPref> getPreferenceGroupList(String group){
		
		Criteria crit = session().createCriteria(GlobalPref.class);
		crit.add(Restrictions.eq("group", group));
		List<GlobalPref> globalPrefList = crit.list();
		
		
		return globalPrefList;
	}
	
}