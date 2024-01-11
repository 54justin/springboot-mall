package springboot.mall.service;

import java.util.List;

import springboot.mall.constant.ProductCategory;
import springboot.mall.dto.ProductRequest;
import springboot.mall.model.Product;

public interface ProductService {
    
    List<Product> getProducts(ProductCategory category, String search);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);    
}
