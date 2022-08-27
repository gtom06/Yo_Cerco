package model.Order;

import java.sql.Timestamp;
import java.util.ArrayList;

public class OrderPreview {
    private int shopId;
    private Timestamp collectionTimestamp;
    private double totalAmount;
    private int orderTotalQuantity;
    private ArrayList<OrderItem> orderItemArrayList;

    public OrderPreview(int shopId, Timestamp collectionTimestamp, double totalAmount, int orderTotalQuantity, ArrayList<OrderItem> orderItemArrayList) {
        this.shopId = shopId;
        this.collectionTimestamp = collectionTimestamp;
        this.totalAmount = totalAmount;
        this.orderTotalQuantity = orderTotalQuantity;
        this.orderItemArrayList = orderItemArrayList;
    }
}
