package springboot.mall.service;

import springboot.mall.dto.ProductRequest;
import springboot.mall.model.Product;

public interface ProductService {
    
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);    
}
