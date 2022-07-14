public class Item {
    String code;
    String name;
    double unitPrice;
    boolean salesTax = true;

    public Item(String code, String name, double unitPrice, boolean salesTax) {
        this.code = code;
        this.name = name;
        this.unitPrice = unitPrice;
        this.salesTax = salesTax;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setName(String name){
        this.name = name;
    }

public void setUnitPrice(double price){
        unitPrice = price;
}
    public String toString() {
        return code + " " + name + " " + unitPrice;
    }
}


