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
    private List<Transaction> transactions;

    // Constructores
    public Account() {}

    public Account(Integer id) {
        this.id = id;
    }

    public Account(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Account(Integer id, String name, String phone, Integer points, Boolean verified, Date createdAt) {
        this(name, phone);
        this.id = id;
        this.points = points;
        this.verified = verified;
        this.createdAt = createdAt;
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public static String header() {
        return String.format(
                "| %-5s | %-10s | %-10s | %-8s | %-8s | %-12s | %-12s |",
                "ID", "NAME", "PHONE", "POINTS", "VERIFIED", "CREATED_AT", "TRANSACTIONS"
        );
    }


    @Override
    public String toString() {
        return String.format(
                "| %-5s | %-10s | %-10s | %-8s | %-8s | %-12s | %-12s |",
                id,
                name,
                phone,
                points,
                verified,
                createdAt,
                transactions
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
