package springboot.mall.dao;

import springboot.mall.dto.ProductRequest;
import springboot.mall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
    Integer getProductCount();
    Integer createProduct(ProductRequest productRequest);    
}
