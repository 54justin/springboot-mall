package springboot.mall.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import springboot.mall.dao.ProductDao;
import springboot.mall.dao.ProductQueryParams;
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
    public Integer countProduct(ProductQueryParams productQueryParams){

        return this.getProductCount(productQueryParams);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams){
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, " +
                     "created_date, last_modified_date " +
                     "FROM product WHERE 1=1";    // WHERE 1=1, 用於後面組合where查詢條件

        Map<String, Object> map = new HashMap<>();

        // 查詢條件
        addFilteringSql(sql,map,productQueryParams);

        // 排序
        sql = sql + " ORDER BY "+ productQueryParams.getOrderBy() + " " + productQueryParams.getSort();  //排序         
        
        // 分頁
        // LIMIT & OFFSET 在oracle實作較複雜，暫不實作productQueryParams.getlimit(), productQueryParams.getoffset()

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;
    }

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
    public Integer getProductCount(ProductQueryParams productQueryParams){
        String sql = "SELECT COUNT(*) FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        // 查詢條件
        addFilteringSql(sql,map,productQueryParams);

        Integer total = JdbcTemplate.queryForObject(sql, Integer.class);
        return total;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest){
        String sql = "INSERT INTO product (product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date) " + 
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, " +
                ":createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());                                
        
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        // 使KeyHolder取得流水號(自動生成的ID)  --MySql用，Oracle會有錯誤
        //KeyHolder keyHolder = new GeneratedKeyHolder();
        //namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        namedParameterJdbcTemplate.update(sql, map);

        Integer productId = this.getProductCount(null);

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest){
        String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl, "+
                     "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate" +
                     " WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());                                
        
        Date now = new Date();
        map.put("lastModifiedDate", now);

        namedParameterJdbcTemplate.update(sql, map);
    }

    public void updateStock(Integer productId, Integer stock){
        String sql = "UPDATE product SET stock = :stock, last_modified_date = :lastModifiedDate " +
                     "WHERE product_id = :productId ";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("stock", stock);
        map.put("lastModifiedDate", new Date());                

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId){
        String sql = "DELETE product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    private String addFilteringSql(String sql, Map<String, Object> map, ProductQueryParams productQueryParams){

        // 查詢條件
        if (productQueryParams.getCategory() != null){
            sql = sql + " AND category = :category";
            map.put("category", productQueryParams.getCategory().name());  //Enum要用.name()取值
        }

        if (productQueryParams.getSearch() != null){
            sql = sql + " AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%");  //%一定要寫在map值裡，不能寫在sql         
        }

        return sql;
    }
}
