package springboot.mall.dao;

import springboot.mall.constant.ProductCategory;

public class ProductQueryParams {
    ProductCategory category;
    String search;

    public ProductCategory getCategory() {
        return this.category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

}
