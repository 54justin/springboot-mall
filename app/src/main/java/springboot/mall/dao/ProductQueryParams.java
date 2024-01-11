package springboot.mall.dao;

import springboot.mall.constant.ProductCategory;

public class ProductQueryParams {
    ProductCategory category;
    String search;
    String orderBy;
    String sort;
    Integer limit;           //取得幾筆數據
    Integer offset;           //跳過幾筆    

    public String getOrderBy() {
        return this.orderBy;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

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
