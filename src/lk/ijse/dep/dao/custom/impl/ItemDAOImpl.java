package lk.ijse.dep.dao.custom.impl;

import lk.ijse.dep.dao.CrudDAO;
import lk.ijse.dep.dao.CrudDAOImpl;
import lk.ijse.dep.dao.custom.ItemDAO;
import lk.ijse.dep.entity.Item;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;

public class ItemDAOImpl extends CrudDAOImpl<Item,String> implements ItemDAO {
    public ItemDAOImpl() {
        super();
    }

    public String findMaxItemCode() throws Exception {

        return (String)entityManager.createNativeQuery("SELECT code from item ORDER BY code DESC LIMIT 1").getSingleResult();
    }
}

