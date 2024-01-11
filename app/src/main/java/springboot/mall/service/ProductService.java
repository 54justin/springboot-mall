package springboot.mall.service;

import springboot.mall.model.Product;

public interface ProductService {
    
    Product getProductById(Integer productId);
}
