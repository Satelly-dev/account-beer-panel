package services.transaction;

import domains.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static database.DBConnection.getConnection;

public class TransactionDAO implements ITransactionDAO{
    @Override
    public List<Transaction> findByAccountId(int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE account_id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, accountId);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getInt("id"));
                    transaction.setTotal(rs.getDouble("total"));
                    transaction.setAmount(rs.getInt("amount"));
                    transaction.setUnitPrice(rs.getDouble("unit_price"));
                    transaction.setStockAmount(rs.getInt("stock_amount"));
                    transaction.setCreatedAt(rs.getTimestamp("created_At"));
                    transaction.setProductId(rs.getInt("product_Id"));
                    transaction.setAccountId(rs.getInt("Account_Id"));
                    transactions.add(transaction);
                }
            }
        }catch (Exception e) {
            System.out.println("❌ Error al realizar la busqueda: " + e.getMessage());
        }
        return transactions;
    }

    @Override
    public void save(Transaction transaction) {
        String sql = "INSERT INTO transaction(total, amount, unit_price, stock_amount, product_Id, account_Id) VALUES(?, ?, ?, ?, ?, ?)";
        try(Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setDouble(1, transaction.getTotal());
            ps.setInt(2, transaction.getAmount());
            ps.setDouble(3, transaction.getUnitPrice());
            ps.setInt(4, transaction.getStockAmount());
            ps.setInt(5, transaction.getProductId());
            ps.setInt(6, transaction.getAccountId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0)
                System.out.println("✅ Compra realizada con exito!.\n");
        }
        catch (Exception e) {
            System.out.println("❌ Error al realizar la compra: " + e.getMessage());
        }
    }

    @Override
    public int delete(int accountId) {
        String selectSql = "SELECT id, total FROM transaction WHERE account_id = ? ORDER BY created_at DESC LIMIT 1";
        String deleteSql = "DELETE FROM transaction WHERE id = ?";
        int points = 0;

        try (Connection con = getConnection();
             PreparedStatement psSelect = con.prepareStatement(selectSql)) {

            psSelect.setInt(1, accountId);

            try (ResultSet rs = psSelect.executeQuery()) {
                if (rs.next()) {
                    int transactionId = rs.getInt("id");
                    double total = rs.getDouble("total");
                    points = (int) Math.round(total * 5);
                    try (PreparedStatement psDelete = con.prepareStatement(deleteSql)) {
                        psDelete.setInt(1, transactionId);
                        psDelete.executeUpdate();
                        System.out.println("✅ Compra cancelada correctamente.");
                    }
                } else {
                    System.out.println("⚠️ No existen compras que cancelar.");
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Error al cancelar la transacción: " + e.getMessage());
        }

        return points;
    }

}
