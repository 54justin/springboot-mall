package springboot.mall.dao;

import springboot.mall.dto.ProductRequest;
import springboot.mall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
    Integer getProductCount();
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);    
}
