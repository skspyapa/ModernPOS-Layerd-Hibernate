package lk.ijse.dep.dao;

import lk.ijse.dep.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.dep.dao.custom.impl.ItemDAOImpl;
import lk.ijse.dep.dao.custom.impl.ItemDetailDAOImpl;
import lk.ijse.dep.dao.custom.impl.OrdersDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){

    }
    public static DAOFactory getInstance(){
        if(daoFactory==null){
            daoFactory=new DAOFactory();
        }
        return daoFactory;
    }
    public <T extends SuperDAO>T getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
               return (T) new CustomerDAOImpl();
            case ITEM:
                return (T)new ItemDAOImpl();
            case ORDER:
                return (T)new OrdersDAOImpl();
            case ITEM_DETAIL:
                return (T)new ItemDetailDAOImpl();
                default:
                    return null;
        }
    }
}
