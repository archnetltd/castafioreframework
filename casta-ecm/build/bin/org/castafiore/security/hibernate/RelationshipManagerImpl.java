package org.castafiore.security.hibernate;

import java.util.List;

import org.castafiore.persistence.Dao;
import org.castafiore.security.Relationship;
import org.castafiore.security.api.RelationshipManager;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class RelationshipManagerImpl implements RelationshipManager{

	private Dao dao;
	
	
	private String[] defaultRelations = new String[]{"Customer", "Supplier", "Agent", "Service Provider"};
	
	
	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}


	@Override
	public List<Relationship> getRelationships(String organization) {
		return dao.getReadOnlySession().createCriteria(Relationship.class).add(Restrictions.eq("firstOrganization", organization)).list();
	}

	@Override
	public void removeRelationships(String firstOrganization,
			String secondOrganization, String relationship) {
		String query ="delete from SECU_RELATIONSHIP where firstOrganization=:first and secondOrganization=:second and relationship=:third";
		dao.getSession().createSQLQuery(query).setParameter("first", firstOrganization).setParameter("second", secondOrganization).setParameter("third", relationship).executeUpdate();
	}

	@Override
	public void removeRelationships(String firstOrganization,String secondOrganization) {
		String query ="delete from SECU_RELATIONSHIP where firstOrganization=:first and secondOrganization=:second";
		dao.getSession().createSQLQuery(query).setParameter("first", firstOrganization).setParameter("second", secondOrganization).executeUpdate();
		
	}

	@Override
	public void removeRelationships(String firstOrganization) {
		String query ="delete from SECU_RELATIONSHIP where firstOrganization=:first";
		dao.getSession().createSQLQuery(query).setParameter("first", firstOrganization).executeUpdate();
		
	}

	@Override
	public void saveRelationship(String firstOrganization,
			String secondOrganization, String relationship) {
		Session session = dao.getSession();
		
		Relationship r = (Relationship)session.createCriteria(Relationship.class).add(Restrictions.eq("firstOrganization", firstOrganization)).add(Restrictions.eq("secondOrganization", secondOrganization)).add(Restrictions.eq("relationship", relationship)).uniqueResult();
		
		
		if(r== null){
			r = new Relationship();
			
		}
		r.setFirstOrganization(firstOrganization);
		r.setSecondOrganization(secondOrganization);
		r.setRelationship(relationship);
		session.saveOrUpdate(r);
		
	}

	@Override
	public List<String> getDistinctRelations(String organization) {
		String sql = "select distinct relationship from SECU_RELATIONSHIP where firstOrganization=:first";
		
		List l = dao.getReadOnlySession().createSQLQuery(sql).setParameter("first", organization).list();
		for(String d : defaultRelations){
			if(!l.contains(d)){
				l.add(d);
			}
		}
		return l;
		
	}

	@Override
	public List<String> getRelatedOrganizations(String organization) {
		String sql = "select distinct secondOrganization from SECU_RELATIONSHIP where firstOrganization=:first";
		return dao.getReadOnlySession().createSQLQuery(sql).setParameter("first", organization).list();
	}
	
	@Override
	public List<String> getRelatedOrganizations(String organization, String relationship) {
		String sql = "select distinct secondOrganization from SECU_RELATIONSHIP where firstOrganization=:first and relationship=:second";
		return dao.getReadOnlySession().createSQLQuery(sql).setParameter("first", organization).setParameter("second", relationship).list();
	}

	@Override
	public boolean hasRelationship(String firstOrganization,String secondOrganization, String relationship) {
		String sql = "select relationship from SECU_RELATIONSHIP where firstOrganization=:first and secondOrganization=:second and relationship=:third";
		return dao.getReadOnlySession().createSQLQuery(sql).setParameter("first", firstOrganization).setParameter("second", secondOrganization).setParameter("third", relationship).list().size() > 0;
	}

	@Override
	public boolean hasRelationship(String firstOrganization,String secondOrganization) {
		String sql = "select relationship from SECU_RELATIONSHIP where firstOrganization=:first and secondOrganization=:second";
		return dao.getReadOnlySession().createSQLQuery(sql).setParameter("first", firstOrganization).setParameter("second", secondOrganization).list().size() > 0;
	}

}
