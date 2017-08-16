package com.example.gurjeet.inventoryapp.data;

/**
 * Created by Gurjeet on 06-Mar-17.
 */

public class InventoryItem {
    private final String productName;
        private final String price;
        private final String supname;
        private final int quantity;
        private final String image;

        public InventoryItem(String productName,String sname, String price, int quantity, String image) {
            this.productName = productName;
            this.price = price;
            this.supname=sname;
            this.quantity = quantity;
            this.image = image;
        }

        public String getSupname() { return supname;}
        public String getProductName() {
            return productName;
        }

        public String getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getImage() {
            return image;
        }
        @Override
        public String toString() {
            return "StockItem{" +
                    "productName='" + productName + '\'' +
                    ",supplierName='"+ supname+'\''+
                    ", price='" + price + '\'' +
                    ", quantity=" + quantity +
                    '}';
        }

}
