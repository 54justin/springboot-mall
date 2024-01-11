package springboot.mall.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import springboot.mall.constant.ProductCategory;
import springboot.mall.model.Product;

public class ProductRowMapper implements RowMapper<Product>{
    
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setProductId(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));

        //¨Ï¥ÎEnum
        String categoryStr = resultSet.getString("category");
        ProductCategory category =  ProductCategory.valueOf(categoryStr);
        product.setCategory(category);
        //product.setCategory(ProductCategory.valueOf(resultSet.getString("category")));


        product.setImageUrl(resultSet.getString("image_url"));
        product.setPrice(resultSet.getInt("price"));
        product.setStock(resultSet.getInt("stock"));
        product.setDescription(resultSet.getString("description"));
        product.setCreatedDate(resultSet.getTimestamp("created_date"));
        product.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));                

        return product;
    }
}
