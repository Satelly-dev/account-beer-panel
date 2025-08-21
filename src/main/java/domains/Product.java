package domains;

import java.util.Objects;

public class Product {
    private Integer id;
    private String name;
    private Double price;
    private Integer amount;

    public Product() {}
    public Product(Integer id) {this.id = id;}
    public Product(String name, Double price, Integer amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
    public Product(Integer id, String name, Double price, Integer amount) {
        this(name, price, amount);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public static String header() {
        return String.format(
                "| %-5s | %-18s |  %-12s | %-9s |",
                "ID", "NAME", "PRICE", "AMOUNT"
        );
    }

    @Override
    public String toString() {
        return String.format(
                "| %-5s | %-18s | $%-12s | %-9s |",
                id,
                name,
                price,
                amount
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(amount, product.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, amount);
    }
}
