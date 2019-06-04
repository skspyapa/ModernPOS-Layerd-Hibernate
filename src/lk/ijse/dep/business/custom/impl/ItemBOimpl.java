package lk.ijse.dep.business.custom.impl;

import lk.ijse.dep.business.custom.ItemBO;
import lk.ijse.dep.dao.DAOFactory;
import lk.ijse.dep.dao.DAOTypes;
import lk.ijse.dep.dao.custom.ItemDAO;
import lk.ijse.dep.dbpos.JpaUtil;
import lk.ijse.dep.dto.ItemDTO;
import lk.ijse.dep.entity.Item;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBOimpl implements ItemBO {
    private ItemDAO itemDAO =  DAOFactory.getInstance().getDAO(DAOTypes.ITEM);

public List<ItemDTO> getAll() throws Exception {
    EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
        itemDAO.setEntityManager(em);
        List<ItemDTO> collect = itemDAO.findAll().stream().map(item -> new ItemDTO(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand())).collect(Collectors.toList());
        em.getTransaction().commit();
        em.close();
        return collect;

}
    public boolean save(ItemDTO dto) throws Exception {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            itemDAO.setEntityManager(em);
            itemDAO.save(new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand()));
            em.getTransaction().commit();
            em.close();
            return true;
    }

    public boolean remove(String  code) throws Exception {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            itemDAO.setEntityManager(em);
            itemDAO.delete(code);
            em.getTransaction().commit();
            em.close();
            return true;
    }

    public boolean update(ItemDTO dto) throws Exception {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            itemDAO.setEntityManager(em);
            itemDAO.update(new Item(dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyOnHand()));
            em.getTransaction().commit();
            em.close();
            return true;
    }
    public ItemDTO get(String code) throws Exception {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            itemDAO.setEntityManager(em);
            Item item = itemDAO.find(code);
            em.getTransaction().commit();
            em.close();
            return new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());


    }

    public String getMaxItemCode() throws Exception {
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            itemDAO.setEntityManager(em);
            String maxItemCode = itemDAO.findMaxItemCode();
            em.getTransaction().commit();
            em.close();
         return maxItemCode;
    }
}
