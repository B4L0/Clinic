package me.sobolewski.clinic.model.util;

import lombok.experimental.UtilityClass;
import me.sobolewski.clinic.manager.DBManager;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@UtilityClass
public class EntityUtils {
    
    private final Session session = DBManager.getSessionFactory().openSession();
    
    public <T> List<T> getList(Class<T> entity) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(entity);
        criteria.from(entity);
        
        return session.createQuery(criteria).getResultList();
    }
    
    public <T> T getByID(Class<T> entity, Serializable id) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(entity);
        Root<T> root = criteria.from(entity);
        criteria.select(root).where(builder.equal(root.get("id"), id));
        
        return session.createQuery(criteria).getSingleResult();
    }
    
    public <T> void save(T entity) {
        session.getTransaction().begin();
        session.save(entity);
        session.getTransaction().commit();
    }
    
    public <T> void delete(T entity){
        session.getTransaction().begin();
        session.delete(entity);
        session.getTransaction().commit();
    }
    
    public <T> void update(T entity){
        session.getTransaction().begin();
        session.update(entity);
        session.getTransaction().commit();
    }
    
}
