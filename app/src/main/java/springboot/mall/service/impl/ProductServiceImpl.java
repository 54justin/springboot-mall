package springboot.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import springboot.mall.constant.ProductCategory;
import springboot.mall.dao.ProductDao;
import springboot.mall.dto.ProductRequest;
import springboot.mall.model.Product;
import springboot.mall.service.ProductService;

@Component
public class ProductServiceImpl implements ProductService{
    
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductCategory category, String search){
        return productDao.getProducts(category,search);
    }

    @Override
    public Product getProductById(Integer productId){
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest){
        Integer productId = productDao.createProduct(productRequest);
        return productId;
    }    

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest){
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId){
        productDao.deleteProductById(productId);
    }     
}
