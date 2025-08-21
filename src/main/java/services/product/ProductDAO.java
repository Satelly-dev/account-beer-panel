package services.product;

import domains.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static database.DBConnection.getConnection;

public class ProductDAO implements IProductDAO {
    @Override
    public Product findById(int id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        try(Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql)
        ){
        ps.setInt(1, id);
        try(ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Product product = new Product(id);
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setAmount(rs.getInt("amount"));
                return product;
            }
        }
        } catch (Exception e) {
            System.out.println("❌ Error al realizar la búsqueda: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE name LIKE ? ORDER BY id";
        try(Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setAmount(rs.getInt("amount"));
                    products.add(product);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error al realizar la búsqueda: " + e.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product ORDER BY id";
        try(Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setAmount(rs.getInt("amount"));
                    products.add(product);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error al realizar la búsqueda: " + e.getMessage());
        }
        return products;
    }

    @Override
    public void save(Product product) {
        String sql = "INSERT INTO product(name, price, amount) VALUES (?,?,?)";
        try(Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getAmount());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0)
                System.out.println("✅ Producto agregado con exito!.\n");
        } catch (Exception e) {
            System.out.println("❌ Error al realizar la búsqueda: " + e.getMessage());
        }
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE product SET name = ?, price = ?, amount = ? WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getAmount());
            ps.setInt(4, product.getId()); // el WHERE id = ?

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0)
                System.out.println("✅ Producto Actualizado con exito!.\n");
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar producto: " + e.getMessage());
        }
    }


    @Override
    public void delete(int id) {
        String sql = "DELETE FROM product WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0)
                System.out.println("✅ Producto Eliminado con exito!.\n");

        } catch (Exception e) {
            System.out.println("❌ Error al eliminar producto: " + e.getMessage());
        }
    }

}
