package lk.ijse.dep.dao;


import javax.persistence.EntityManager;

public interface SuperDAO {
    public void getSession(EntityManager entityManager);
}
