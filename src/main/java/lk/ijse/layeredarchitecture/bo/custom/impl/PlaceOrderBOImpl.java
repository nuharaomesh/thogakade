package lk.ijse.layeredarchitecture.bo.custom.impl;

import lk.ijse.layeredarchitecture.bo.custom.PlaceOrderBO;
import lk.ijse.layeredarchitecture.dao.DAOFactory;
import lk.ijse.layeredarchitecture.dao.custom.CustomerDAO;
import lk.ijse.layeredarchitecture.dao.custom.ItemDAO;
import lk.ijse.layeredarchitecture.dao.custom.OrderDAO;
import lk.ijse.layeredarchitecture.dao.custom.OrderDetailsDAO;
import lk.ijse.layeredarchitecture.db.DBConnection;
import lk.ijse.layeredarchitecture.entity.Customer;
import lk.ijse.layeredarchitecture.entity.Item;
import lk.ijse.layeredarchitecture.entity.OrderDetail;
import lk.ijse.layeredarchitecture.entity.Orders;
import lk.ijse.layeredarchitecture.model.CustomerDTO;
import lk.ijse.layeredarchitecture.model.ItemDTO;
import lk.ijse.layeredarchitecture.model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {


    //Factory Design pattern;
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getTypes(DAOFactory.DARTypes.ITEM);
    private CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getTypes(DAOFactory.DARTypes.CUSTOMER);
    private OrderDAO ordDao = (OrderDAO) DAOFactory.getDaoFactory().getTypes(DAOFactory.DARTypes.ORDER);
    private OrderDetailsDAO ordDetDao = (OrderDetailsDAO) DAOFactory.getDaoFactory().getTypes(DAOFactory.DARTypes.ORDER_DETAIL);

    public boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException{

        /*Transaction*/
        Connection connection= DBConnection.getDbConnection().getConnection();

        /*if order id already exist*/
        if (ordDao.exist(orderId)) {
            return false;
        }

        connection.setAutoCommit(false);

        //Save the Order to the order table
        if (!ordDao.save(new Orders(orderId, orderDate, customerId))) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        // add data to the Order Details table

        for (OrderDetailDTO detail : orderDetails) {

            if (!ordDetDao.saveOrdDet(new OrderDetail(detail.getoId(), detail.getItemCode(), detail.getQty(), detail.getUnitPrice()))) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            ItemDTO item = findItem(detail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

            //update item
            if (!itemDAO.update(new Item(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice()))) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    private ItemDTO findItem(String itemCode) throws SQLException, ClassNotFoundException {

        Item entity = itemDAO.search(itemCode);
        return new ItemDTO(entity.getCode(), entity.getDescription(), entity.getQtyOnHand(), entity.getUnitPrice());
    }

    @Override
    public CustomerDTO searchCustomer(String newValue) throws SQLException, ClassNotFoundException {

        Customer entity = customerDAO.search(newValue);
        return new CustomerDTO(entity.getId(), entity.getName(), entity.getAddress());
    }

    @Override
    public ItemDTO searchItem(String newItemCode) throws SQLException, ClassNotFoundException {

        Item entity = itemDAO.search(newItemCode);
        return new ItemDTO(entity.getCode(), entity.getDescription(), entity.getQtyOnHand(), entity.getUnitPrice());
    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String genOrderId() throws SQLException, ClassNotFoundException {
        return ordDao.genId();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {

        ArrayList<Customer> cus = customerDAO.getAll();
        ArrayList<CustomerDTO> dto = new ArrayList<>();

        for (Customer entity: cus) {
            dto.add(
                    new CustomerDTO(
                            entity.getId(),
                            entity.getName(),
                            entity.getAddress()
                    )
            );
        }
        return dto;
    }

    @Override
    public ArrayList<ItemDTO> getAllItemCode() throws SQLException, ClassNotFoundException {

        ArrayList<Item> items = itemDAO.getAll();
        ArrayList<ItemDTO> dto = new ArrayList<>();

        for (Item entity: items) {
            dto.add(
                    new ItemDTO(
                            entity.getCode(),
                            entity.getDescription(),
                            entity.getQtyOnHand(),
                            entity.getUnitPrice()
                    )
            );
        }
        return dto;
    }
}
