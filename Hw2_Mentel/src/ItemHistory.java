import java.util.NoSuchElementException;

public class ItemHistory {

    Item item;

    private int totalItems;

    private int quantity;

    public ItemHistory(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return item.name;
    }

    public double getPrice() {
        return item.unitPrice;
    }

    public boolean hasSalesTax(){
        return item.salesTax;
    }


}

