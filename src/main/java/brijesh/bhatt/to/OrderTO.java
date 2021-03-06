package brijesh.bhatt.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

public class OrderTO {

    private int orderId;

    @NotBlank(message = "customerName is mandatory.")
    private String customerName;

    @NotBlank(message = "shippingAddress is mandatory.")
    private String shippingAddress;

    @Positive(message = "total can not be negative.")
    private int total;

    private List<ItemTO> itemTOList = new ArrayList<>();

    public OrderTO(int orderId, @NotBlank(message = "customerName is mandatory.") String customerName, @NotBlank(message = "shippingAddress is mandatory.") String shippingAddress,
                   @Positive(message = "total can not be negative.") int total) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.total = total;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<brijesh.bhatt.to.ItemTO> getItemTOList() {
        return itemTOList;
    }

    public void setItemTOList(List<brijesh.bhatt.to.ItemTO> itemTOList) {
        this.itemTOList = itemTOList;
    }
}
