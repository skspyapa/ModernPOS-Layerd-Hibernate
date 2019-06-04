package lk.ijse.dep.dao.custom.impl;

import lk.ijse.dep.dao.CrudDAOImpl;
import lk.ijse.dep.dao.custom.ItemDetailDAO;
import lk.ijse.dep.entity.ItemDetail;
import lk.ijse.dep.entity.ItemDetailPK;

public class ItemDetailDAOImpl extends CrudDAOImpl<ItemDetail,ItemDetailPK> implements ItemDetailDAO {

    public ItemDetailDAOImpl() {
        super();
    }
}


