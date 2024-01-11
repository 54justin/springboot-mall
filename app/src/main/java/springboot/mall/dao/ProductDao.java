package springboot.mall.dao;

import springboot.mall.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
