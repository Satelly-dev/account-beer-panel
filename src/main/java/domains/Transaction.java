package domains;

import java.sql.Timestamp;

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



}
