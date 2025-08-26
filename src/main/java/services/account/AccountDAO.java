package services.account;

import static database.DBConnection.getConnection;

import domains.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements IAccountDAO {
    @Override
    public Account findById(int id) {
        String sql = "SELECT a.id, a.name, a.phone, a.points, a.verified, a.created_at, " +
                "COUNT(t.id) AS transaction_count, " +
                "COALESCE(SUM(t.stock_amount), 0) AS total_stock " +
                "FROM account a " +
                "LEFT JOIN transaction t ON t.account_id = a.id " +
                "AND t.created_at >= NOW() - INTERVAL 1 DAY " +
                "WHERE a.id = ? " +
                "GROUP BY a.id, a.name, a.phone, a.points, a.verified, a.created_at";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setName(rs.getString("name"));
                    account.setPhone(rs.getString("phone"));
                    account.setPoints(rs.getInt("points"));
                    account.setVerified(rs.getBoolean("verified"));
                    account.setCreatedAt(rs.getDate("created_at"));

                    account.setTransactions(rs.getInt("transaction_count"));
                    account.setStockAmount(rs.getInt("total_stock"));

                    return account;
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error al recuperar cliente por id: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> findByName(String name) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT a.id, a.name, a.phone, a.points, a.verified, a.created_at, " +
                "COUNT(t.id) AS transaction_count, " +
                "COALESCE(SUM(t.stock_amount), 0) AS total_stock " +
                "FROM account a " +
                "LEFT JOIN transaction t ON t.account_id = a.id " +
                "AND t.created_at >= NOW() - INTERVAL 1 DAY " +
                "WHERE a.name LIKE ? " +
                "GROUP BY a.id, a.name, a.phone, a.points, a.verified, a.created_at";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Account account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setName(rs.getString("name"));
                    account.setPhone(rs.getString("phone"));
                    account.setPoints(rs.getInt("points"));
                    account.setVerified(rs.getBoolean("verified"));
                    account.setCreatedAt(rs.getDate("created_at"));

                    account.setTransactions(rs.getInt("transaction_count"));
                    account.setStockAmount(rs.getInt("total_stock"));

                    accounts.add(account);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error al realizar la búsqueda: " + e.getMessage());
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account findByPhone(String phone) {
        String sql = "SELECT a.id, a.name, a.phone, a.points, a.verified, a.created_at, " +
                "COUNT(t.id) AS transaction_count, " +
                "COALESCE(SUM(t.stock_amount), 0) AS total_stock " +
                "FROM account a " +
                "LEFT JOIN transaction t ON t.account_id = a.id " +
                "AND t.created_at >= NOW() - INTERVAL 1 DAY " +
                "WHERE a.phone = ? " +
                "GROUP BY a.id, a.name, a.phone, a.points, a.verified, a.created_at";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, phone);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setId(rs.getInt("id"));
                    account.setName(rs.getString("name"));
                    account.setPhone(rs.getString("phone"));
                    account.setPoints(rs.getInt("points"));
                    account.setVerified(rs.getBoolean("verified"));
                    account.setCreatedAt(rs.getDate("created_at"));

                    account.setTransactions(rs.getInt("transaction_count"));
                    account.setStockAmount(rs.getInt("total_stock"));

                    return account;
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error al recuperar cliente por phone: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        Connection con = getConnection();
        String sql = "SELECT " +
                "a.id, a.name, a.phone, a.points, a.verified, a.created_at, " +
                "COUNT(t.id) AS transaction_count, " +
                "COALESCE(SUM(t.stock_amount), 0) AS total_stock " +
                "FROM account a " +
                "LEFT JOIN transaction t ON t.account_id = a.id " +
                "AND t.created_at >= NOW() - INTERVAL 1 DAY " +
                "GROUP BY a.id, a.name, a.phone, a.points, a.verified, a.created_at " +
                "ORDER BY a.id";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setName(rs.getString("name"));
                account.setPhone(rs.getString("phone"));
                account.setPoints(rs.getInt("points"));
                account.setVerified(rs.getBoolean("verified"));
                account.setCreatedAt(rs.getDate("created_at"));

                account.setTransactions(rs.getInt("transaction_count"));  // cantidad de transacciones
                account.setStockAmount(rs.getInt("total_stock"));        // suma total de stockAmount

                accounts.add(account);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar las cuentas: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { con.close(); }
            catch (Exception e) { System.out.println("❌ Error al cerrar la conexion"); }
        }
        return accounts;
    }


    @Override
    public void save(Account account) {
        PreparedStatement ps;
        Connection con = getConnection();
        var sql = "INSERT INTO account(name, phone) VALUES (?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, account.getName());
            ps.setString(2, account.getPhone());
            ps.execute();
            System.out.println("✅ Cuenta agregada con exito!.\n");
        } catch (Exception e) {
            System.out.println("❌ Error al agregar cuenta: " + e.getMessage() + "\n");
            e.getStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("❌ Error al cerrar la conexion \n");
            }
        }
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE account SET name = ?, phone = ?, points = ?, verified = ? WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, account.getName());
            ps.setString(2, account.getPhone());
            ps.setInt(3, account.getPoints());
            ps.setBoolean(4, account.getVerified());
            ps.setInt(5, account.getId());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("✅ Cuenta actualizada correctamente.");
            } else {
                System.out.println("⚠️ No se encontró la cuenta con ID: " + account.getId());
            }

        } catch (Exception e) {
            System.out.println("`❌ La cuenta no se actualizo.\s" + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM account WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Cuenta eliminada correctamente (ID: " + id + ")\n");
            } else {
                System.out.println("⚠️ No se encontró ninguna cuenta con el ID: " + id + "\n");
            }

        } catch (Exception e) {
            System.out.println("❌ Error al eliminar la cuenta: " + e.getMessage());
            e.getStackTrace();
        }
    }

}
