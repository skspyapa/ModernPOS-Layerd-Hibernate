package lk.ijse.dep.business.custom.impl;

import lk.ijse.dep.business.custom.CustomerBO;
import lk.ijse.dep.dao.DAOFactory;
import lk.ijse.dep.dao.DAOTypes;
import lk.ijse.dep.dao.custom.CustomerDAO;
import lk.ijse.dep.dbpos.JpaUtil;
import lk.ijse.dep.dto.CustomerDTO;
import lk.ijse.dep.entity.Customer;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerBOimpl implements CustomerBO {
    private CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);

    public List<CustomerDTO> getAll() throws Exception {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            customerDAO.setEntityManager(em);
            List<CustomerDTO> collect = customerDAO.findAll().stream().map(customer -> new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress(),customer.getSalary())).collect(Collectors.toList());
            em.getTransaction().commit();
            em.close();
            return collect;
        
}
            public boolean save(CustomerDTO dto) throws Exception {
                EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
                EntityManager em = emf.createEntityManager();
                    em.getTransaction().begin();
                    customerDAO.setEntityManager(em);
                    customerDAO.save(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
                    em.getTransaction().commit();
                    em.close();
                    return true;
            }

            public boolean remove(String  id) throws Exception {
                EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
                EntityManager em = emf.createEntityManager();
                    em.getTransaction().begin();
                    customerDAO.setEntityManager(em);
                    customerDAO.delete(id);
                    em.getTransaction().commit();
                    em.close();
                    return true;
            }

            public boolean update(CustomerDTO dto) throws Exception {
                EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                    customerDAO.setEntityManager(em);
                    customerDAO.update(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
                    em.getTransaction().commit();
                    em.close();
                    return true;
            }
            public CustomerDTO get(String id) throws Exception {
                EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                    customerDAO.setEntityManager(em);
                    Customer customer = customerDAO.find(id);
                    em.getTransaction().commit();
                    em.close();
                    return new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress(),customer.getSalary());
            }
            public List<CustomerDTO> getCustomerId() throws Exception {
                EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                    customerDAO.setEntityManager(em);
                    List<CustomerDTO> collect = customerDAO.findAll().stream().map(customer -> new CustomerDTO(customer.getId())).collect(Collectors.toList());
                    em.getTransaction().commit();
                    em.close();
                    return collect;
            }
            public String getMaxCustId() throws Exception {
                EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                    customerDAO.setEntityManager(em);
                    String maxCustId = customerDAO.findMaxCustId();
                    em.getTransaction().commit();
                    em.close();
                    return maxCustId;
            }
}
