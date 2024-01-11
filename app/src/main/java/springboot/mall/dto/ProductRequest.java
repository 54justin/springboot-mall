package springboot.mall.dto;

import org.springframework.lang.NonNull;

import springboot.mall.constant.ProductCategory;

public class ProductRequest {

    @NonNull
    private String productName;
    @NonNull    
    private ProductCategory category;
    @NonNull    
    private String imageUrl;
    @NonNull    
    private Integer price;
    @NonNull    
    private Integer stock;
    
    private String description;
    
    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductCategory getCategory() {
        return this.category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
