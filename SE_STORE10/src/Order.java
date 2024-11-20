public class Order {
    private String orderID;
    private String customerID;
    private String productID;
    private String quantity;
    private String priceOnOrder;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPriceOnOrder() {
        return priceOnOrder;
    }

    public void setPriceOnOrder(String priceOnOrder) {
        this.priceOnOrder = priceOnOrder;
    }
}
