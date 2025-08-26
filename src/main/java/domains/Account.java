package domains;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Account {
    private Integer id;
    private String name;
    private String phone;
    private Integer points;
    private Boolean verified;
    private Date createdAt;
    private Integer transactions;
    private Integer stockAmount;

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";


    // Constructores
    public Account() {}

    public Account(Integer id) {
        this.id = id;
    }

    public Account(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Account(Integer id, String name, String phone, Integer points, Boolean verified, Date createdAt, int transactions, int stockAmount) {
        this(name, phone);
        this.id = id;
        this.points = points;
        this.verified = verified;
        this.createdAt = createdAt;
        this.transactions = transactions;
        this.stockAmount = stockAmount;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getTransactions() {
        return transactions;
    }

    public void setTransactions(int transactions) {
        this.transactions = transactions;
    }

    public int getStockAmount() {return stockAmount; }

    public void setStockAmount(int stockAmount) {this.stockAmount = stockAmount; }

    public static String header() {
        return String.format(
                "| %-4s | %-10s | %-10s | %-8s | %-8s | %-10s | %-14s | %-14s | %-9s",
                "ID", "NAME", "PHONE", "POINTS", "VERIFIED", "CREATED_AT", "TRANS ON 24HRS", "AMOUNT ON 24HRS", "RISK"
        );
    }



    @Override
    public String toString() {
        boolean riesgo = (transactions > 4 && stockAmount > 50);

        String riskLabel = riesgo
                ? RED + "⚠️ RIESGO" + RESET
                : GREEN + "OK" + RESET;

        return String.format(
                "| %-4s | %-10s | %-10s | %-8s | %-8s | %-10s | %-14s | %-15s | %-9s",
                id,
                name,
                phone,
                points,
                verified,
                createdAt,
                transactions,
                stockAmount,
                riskLabel
        );
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(name, account.name) && Objects.equals(phone, account.phone) && Objects.equals(points, account.points) && Objects.equals(verified, account.verified) && Objects.equals(createdAt, account.createdAt) && Objects.equals(transactions, account.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, points, verified, createdAt, transactions);
    }
}
