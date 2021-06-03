package me.sobolewski.clinic.model.util;

import lombok.experimental.UtilityClass;
import me.sobolewski.clinic.manager.DBManager;
import me.sobolewski.clinic.model.Doctor;
import oracle.jdbc.OracleCallableStatement;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
    
    public <T> void delete(T entity) {
        session.getTransaction().begin();
        session.delete(entity);
        session.getTransaction().commit();
    }
    
    public <T> void update(T entity) {
        session.getTransaction().begin();
        session.update(entity);
        session.getTransaction().commit();
    }
    
    public int getVisitsByDay(LocalDate date, Doctor doctor) {
        AtomicInteger result = new AtomicInteger();
        
        session.doWork(connection -> {
            CallableStatement call = connection.prepareCall("{ ? = call GET_VISIT_COUNT_BY_DAY(?,?) }");
            call.registerOutParameter(1, Types.INTEGER);
            call.setDate(2, Date.valueOf(date));
            call.setLong(3, doctor.getId());
            call.execute();
            result.set(call.getInt(1));
        });
        
        return result.get();
    }
    
    public ResultSet getTopExaminations(Doctor doctor, int amount){
        final AtomicReference<ResultSet> rs = new AtomicReference<>();
        
        session.doWork(connection -> {
            CallableStatement call = connection.prepareCall("{ ? = call GET_TOP_EXAMINATIONS(?, ?) }");
            call.registerOutParameter(1, Types.REF_CURSOR);
            call.setLong(2, doctor.getId());
            call.setInt(3, amount);
            call.execute();
            rs.set(((OracleCallableStatement) call).getCursor(1));
        });
        return rs.get();
    }
    
    public ResultSet getTopDrugs(Doctor doctor, int amount){
        final AtomicReference<ResultSet> rs = new AtomicReference<>();
    
        session.doWork(connection -> {
            CallableStatement call = connection.prepareCall("{ ? = call GET_TOP_DRUGS(?, ?) }");
            call.registerOutParameter(1, Types.REF_CURSOR);
            call.setLong(2, doctor.getId());
            call.setInt(3, amount);
            call.execute();
            rs.set(((OracleCallableStatement) call).getCursor(1));
        });
        return rs.get();
    }
    
}
