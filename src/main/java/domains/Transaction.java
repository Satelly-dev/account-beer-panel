package domains;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class Transaction {
    private Integer id;
    private double total;
    private Integer amount;
    private double unitPrice;
    private Integer stockAmount;
    private Timestamp createdAt;
    private Integer productId;
    private Integer accountId;

    public Transaction() {}

    public Transaction(Integer id) {
        this.id = id;
    }

    public Transaction(double total, int amount, double unitPrice, int stockAmount, int accountId, int productId) {
        this.total = total;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.stockAmount = stockAmount;
        this.productId = productId;
        this.accountId = accountId;
    }

    public Transaction(int id, double total, int amount, double unitPrice, int stockAmount, int accountId, int productId) {
        this(total, amount, unitPrice, stockAmount, accountId, productId);
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(Integer stockAmount) {
        this.stockAmount = stockAmount;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


//    @Override
//    public String toString() {
//        return "Transaction{" +
//                "id=" + id +
//                ", total=" + total +
//                ", amount=" + amount +
//                ", unitPrice=" + unitPrice +
//                ", stockAmount=" + stockAmount +
//                ", createdAt=" + createdAt +
//                ", productId=" + productId +
//                ", accountId=" + accountId +
//                '}';
//    }

    public static String header() {
        return String.format(
                "| %-5s |  %-10s | %-10s | %-17s | %-16s | %-29s | %-11s | %-11s |",
                "ID", "TOTAL", "CANTIDAD", "PRECIO UNITARIO", "CANTIDAD STOCK", "COMPRADO EL", "ID PRODUCTO", "ID CUENTA"
        );
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy, HH:mm", Locale.forLanguageTag("es-ES"));
        String fecha = createdAt.toLocalDateTime().format(formatter);

        return String.format(
                "| %-5s | $%-10s | %-10s | %-17s | %-16s | %-29s | %-11s | %-11s |",
                id,
                total,
                amount,
                unitPrice,
                stockAmount,
                fecha,
                productId,
                accountId
        );
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(total, that.total) == 0 && Double.compare(unitPrice, that.unitPrice) == 0 && Objects.equals(id, that.id) && Objects.equals(amount, that.amount) && Objects.equals(stockAmount, that.stockAmount) && Objects.equals(createdAt, that.createdAt) && Objects.equals(productId, that.productId) && Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, total, amount, unitPrice, stockAmount, createdAt, productId, accountId);
    }
}
