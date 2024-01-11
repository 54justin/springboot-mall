package springboot.mall.dao;

import java.util.List;

import springboot.mall.dto.ProductRequest;
import springboot.mall.model.Product;

public interface ProductDao {

    Integer countProduct(ProductQueryParams productQueryParams);  

    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Integer getProductCount();
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);    
}
