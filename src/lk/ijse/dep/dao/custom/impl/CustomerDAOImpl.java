package lk.ijse.dep.dao.custom.impl;

import lk.ijse.dep.dao.CrudDAOImpl;
import lk.ijse.dep.dao.custom.CustomerDAO;
import lk.ijse.dep.entity.Customer;

public class CustomerDAOImpl extends CrudDAOImpl<Customer,String> implements CustomerDAO {
    public CustomerDAOImpl() {
        super();
    }

    public String findMaxCustId() throws Exception {

        return (String)entityManager.createNativeQuery("SELECT id from customer ORDER BY id DESC LIMIT 1").getSingleResult();
    }
}
