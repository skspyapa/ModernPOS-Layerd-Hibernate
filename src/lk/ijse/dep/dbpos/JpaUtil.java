package lk.ijse.dep.dbpos;


import lk.ijse.dep.entity.Customer;
import lk.ijse.dep.entity.Item;
import lk.ijse.dep.entity.ItemDetail;
import lk.ijse.dep.entity.Orders;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JpaUtil {

    private static EntityManagerFactory entityManagerFactory=buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {

        try {
            File propFile = new File("D:\\Java\\MyPosSystemHibernate\\PosSystem\\resources\\application.properties");
            Properties properties = new Properties();
            FileReader fileReader = new FileReader(propFile);
            properties.load(fileReader);
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit1", properties);
            fileReader.close();
            return emf;
        } catch (FileNotFoundException e) {
            Logger.getLogger("lk.ijse.dep").log(Level.SEVERE,null,e);
            return null;
        } catch (IOException e) {
            Logger.getLogger("lk.ijse.dep").log(Level.SEVERE,null,e);
            return null;
        }

    }

    public static EntityManagerFactory getEntityManagerFactory() {

        return entityManagerFactory;
    }

}
