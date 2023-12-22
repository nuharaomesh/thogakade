package lk.ijse.layeredarchitecture.entity;

import java.math.BigDecimal;

public class OrderDetail {

    private String oId;
    private String itemCode;
    private int qty;
    private BigDecimal unitPrice;

    public OrderDetail(String oId, String itemCode, int qty, BigDecimal unitPrice) {
        this.oId = oId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getItemCode() {
        return itemCode;
    }
    public String getoId() {
        return oId;
    }
    public void setoId(String oId) {
        this.oId = oId;
    }
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "oId='" + oId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
