package services;

import static database.DBConnection.getConnection;

import com.mysql.cj.xdevapi.PreparableStatement;
import domains.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements IAccountDAO {
    @Override
    public Account findById(int id) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConnection();
        var sql = "SELECT * FROM account WHERE id = ?";
        try {
            Account account = new Account(id);
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                account.setName(rs.getString("name"));
                account.setPhone(rs.getString("phone"));
                account.setPoints(rs.getInt("points"));
                account.setVerified(rs.getBoolean("verified"));
                account.setCreatedAt(rs.getDate("created_at"));
                return account;
            }
        } catch (Exception e) {
            System.out.println("❌ Error al recuperar cliente por id\s" + e.getMessage());
            e.getStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("❌ Error al cerrar la conexion");
            }
        }
        return null;
    }

    @Override
    public List<Account> findByName(String name) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account WHERE name LIKE ?";

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
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConnection();
        var sql = "SELECT * FROM account WHERE phone = ?";
        try {
            Account account = new Account();
            ps = con.prepareStatement(sql);
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if (rs.next()) {
                account.setId(rs.getInt("id"));
                account.setName(rs.getString("name"));
                account.setPhone(rs.getString("phone"));
                account.setPoints(rs.getInt("points"));
                account.setVerified(rs.getBoolean("verified"));
                account.setCreatedAt(rs.getDate("created_at"));
                return account;
            }
        } catch (Exception e) {
            System.out.println("❌ Error al recuperar cliente por id\s" + e.getMessage());
            e.getStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("❌ Error al cerrar la conexion");
            }
        }
        return null;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConnection();
        var sql = "SELECT * FROM account ORDER BY id";
        try {
            ps = con.prepareCall(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setName(rs.getString("name"));
                account.setPhone(rs.getString("phone"));
                account.setPoints(rs.getInt("points"));
                account.setVerified(rs.getBoolean("verified"));
                account.setCreatedAt(rs.getDate("created_at"));
                accounts.add(account);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al listar las cuentas: " + e.getMessage());
            e.getStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("❌ Error al cerrar la conexion");
            }
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

    }

    @Override
    public void delete(int id) {

    }
}
