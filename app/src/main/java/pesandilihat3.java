import android.app.Activity;
import java.util.Date;
import java.util.List;

public class pesandilihat3 extends Activity {


    public class Order {
        private String orderId;
        private String orderNumber;
        private Date purchaseDate;
        private String status;
        private List<OrderItem> items;
        private double totalPrice;
        private String customerName;
        private String customerAddress;

        // Constructors
        public Order() {
        }

        public Order(String orderId, String orderNumber, Date purchaseDate, String status,
                     List<OrderItem> items, double totalPrice) {
            this.orderId = orderId;
            this.orderNumber = orderNumber;
            this.purchaseDate = purchaseDate;
            this.status = status;
            this.items = items;
            this.totalPrice = totalPrice;
        }

        // Getters and Setters
        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public Date getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(Date purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<OrderItem> getItems() {
            return items;
        }

        public void setItems(List<OrderItem> items) {
            this.items = items;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerAddress() {
            return customerAddress;
        }

        public void setCustomerAddress(String customerAddress) {
            this.customerAddress = customerAddress;
        }

        // Helper methods
        public String getFormattedPrice() {
            return String.format("Rp %,.0f", totalPrice);
        }

        public String getStatusColor() {
            switch (status.toLowerCase()) {
                case "pesanan diterima":
                    return "#35c759";
                case "dalam proses":
                    return "#FF9800";
                case "dikirim":
                    return "#2196F3";
                case "belum bayar":
                    return "#F44336";
                default:
                    return "#757575";
            }
        }

        public boolean isPaid() {
            return !status.equalsIgnoreCase("belum bayar");
        }

        public int getItemCount() {
            return items != null ? items.size() : 0;
        }
    }

    // OrderItem class - Made public and moved to separate file or static inner class
    public static class OrderItem {
        private String productId;
        private String productName;
        private String productImage;
        private String unit;
        private double price;
        private int quantity;
        private double totalPrice;

        // Constructors
        public OrderItem() {
        }

        public OrderItem(String productId, String productName, String productImage,
                         String unit, double price, int quantity) {
            this.productId = productId;
            this.productName = productName;
            this.productImage = productImage;
            this.unit = unit;
            this.price = price;
            this.quantity = quantity;
            this.totalPrice = price * quantity;
        }

        // Getters and Setters
        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
            this.totalPrice = this.price * this.quantity;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
            this.totalPrice = this.price * this.quantity;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }

        // Helper methods
        public String getFormattedPrice() {
            return String.format("Rp %,.0f", price);
        }

        public String getFormattedTotalPrice() {
            return String.format("Rp %,.0f", totalPrice);
        }

        public String getQuantityText() {
            return String.format("Qty %dx", quantity);
        }

    }

