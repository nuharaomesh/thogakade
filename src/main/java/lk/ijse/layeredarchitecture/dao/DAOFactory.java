package lk.ijse.layeredarchitecture.dao;

import lk.ijse.layeredarchitecture.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.layeredarchitecture.dao.custom.impl.ItemDAOImpl;
import lk.ijse.layeredarchitecture.dao.custom.impl.OrderDAOImpl;
import lk.ijse.layeredarchitecture.dao.custom.impl.OrderDetailsDAOImpl;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DARTypes {
        CUSTOMER,ITEM,ORDER,ORDER_DETAIL
    }

    public SuperDAO getTypes(DARTypes darTypes) {

        switch (darTypes) {

            case CUSTOMER:
                return new CustomerDAOImpl();

            case ITEM:
                return new ItemDAOImpl();

            case ORDER:
                return new OrderDAOImpl();

            case ORDER_DETAIL:
                return new OrderDetailsDAOImpl();

            default:
                return null;
        }
    }
}
