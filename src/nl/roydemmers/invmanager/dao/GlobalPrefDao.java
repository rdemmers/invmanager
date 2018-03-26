package nl.roydemmers.invmanager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.roydemmers.invmanager.objects.GlobalPref;

@Transactional
@Component("globalPrefDao")
public class GlobalPrefDao extends AbstractDao {
	
	
	public GlobalPref get(String name) {
		
		Criteria crit = session().createCriteria(GlobalPref.class);
		crit.add(Restrictions.eq("name", name));
		return (GlobalPref)crit.uniqueResult();
		
	}
	
	public void set(GlobalPref globalPref) {
		session().update(globalPref);
		
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, GlobalPref> getAll(){
		List<GlobalPref> preferenceList = session().createQuery("from GlobalPref").list();
		Map<String,GlobalPref> preferenceMap = new HashMap<>();
		for(GlobalPref globalPref : preferenceList) {
			preferenceMap.put(globalPref.getName(), globalPref);
		}
		
		return preferenceMap;
	}
	
}