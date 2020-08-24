package brijesh.bhatt.to;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ItemTO {

    private int id;

    @NotBlank(message = "Code is mandatory.")
    private String code;

    @NotBlank(message = "Name is mandatory.")
    private String name;

    @Positive(message = "Quantity can not be negative.")
    @Max(value = 20, message = "quantity can not be greater than 20.")
    private int quantity;

    public ItemTO(int id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
