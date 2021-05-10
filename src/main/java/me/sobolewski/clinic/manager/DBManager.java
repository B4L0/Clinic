package me.sobolewski.clinic.manager;

import lombok.experimental.UtilityClass;
import me.sobolewski.clinic.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class DBManager {
    
    public static SessionFactory getSessionFactory() {
        
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(Doctor.class);
        configuration.addAnnotatedClass(Examination.class);
        configuration.addAnnotatedClass(Patient.class);
        configuration.addAnnotatedClass(Prescription.class);
        configuration.addAnnotatedClass(Visit.class);
        configuration.addAnnotatedClass(Drug.class);
        configuration.configure();
        
        StandardServiceRegistryBuilder registryBuilder =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        
        return configuration.buildSessionFactory(registryBuilder.build());
    }
}