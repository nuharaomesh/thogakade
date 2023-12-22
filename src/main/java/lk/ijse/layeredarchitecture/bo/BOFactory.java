package lk.ijse.layeredarchitecture.bo;

import lk.ijse.layeredarchitecture.bo.custom.impl.CustomerBOImpl;
import lk.ijse.layeredarchitecture.bo.custom.impl.ItemBOImpl;
import lk.ijse.layeredarchitecture.bo.custom.impl.PlaceOrderBOImpl;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory() {

    }

    public static BOFactory getDaoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum DARTypes {
        CUSTOMER,ITEM,PLACE_ORDER
    }

    public SuperBO getTypes(BOFactory.DARTypes darTypes) {

        switch (darTypes) {

            case CUSTOMER:
                return new CustomerBOImpl();

            case ITEM:
                return new ItemBOImpl();

            case PLACE_ORDER:
                return new PlaceOrderBOImpl();

            default:
                return null;
        }
    }
}
