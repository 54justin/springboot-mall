package springboot.mall.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import springboot.mall.dao.ProductDao;
import springboot.mall.dto.ProductRequest;
import springboot.mall.model.Product;
import springboot.mall.rowmapper.ProductRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class ProductDaoImpl implements ProductDao{
 
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate JdbcTemplate;

    @Override
    public Product getProductById(Integer productId){
        String sql = "SELECT product_id,product_name, category, image_url, price, stock, description, created_date, last_modified_date FROM product WHERE product_id = :productId ";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productList.size() > 0){
            return productList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer getProductCount(){
        Integer count = 0;
        String sql = "SELECT COUNT(*) FROM product";

        count = JdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest){
        String sql = "INSERT INTO product (product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date) " + 
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, " +
                "sysdate, sysdate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());                                
        
        // Date now = new Date();
        // map.put("createdDate", now);
        // map.put("lastModifiedDate", now);

        // 使KeyHolder取得流水號(自動生成的ID)
        //KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, map);
        //namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Integer productId = this.getProductCount();

        return productId;
    }    
}
