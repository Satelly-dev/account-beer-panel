package domains;

import java.sql.Timestamp;

public class Transaction {
    private Integer id;
    private double total;
    private Integer amount;
    private Timestamp createdAt;
    private Integer accountId;

    public Transaction() {}

    public Transaction(Integer id) {
        this.id = id;
    }

    public Transaction(double total, int amount, int accountId) {
        this.total = total;
        this.amount = amount;
        this.accountId = accountId;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Integer getAccountId() { return accountId; }
    public void setAccountId(Integer accountId) { this.accountId = accountId; }
}
