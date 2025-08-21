package services.product;

import domains.Product;

import java.util.List;

public interface IProductDAO {
    Product findById(int id);
    List<Product> findByName(String name);
    List<Product> findAll();
    void save(Product product);
    void update(Product product);
    void delete(int id);
}
