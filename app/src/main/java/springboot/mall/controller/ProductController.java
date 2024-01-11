package springboot.mall.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.mall.constant.ProductCategory;
import springboot.mall.dao.ProductQueryParams;
import springboot.mall.dto.ProductRequest;
import springboot.mall.model.Product;
import springboot.mall.service.ProductService;
import springboot.mall.util.Page;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            // �d�߱��� Filtering
            @RequestParam(required = false) ProductCategory category,   //category�i��
            @RequestParam(required = false) String search,

            // �Ƨ� Sorting
            @RequestParam(defaultValue = "created_date") String orderBy,  //�ƧǤ覡(�w�]"�̷s")
            @RequestParam(defaultValue = "desc") String sort,             //�ƧǨ̾����(�w�]�jTO�p)

            // ���� Pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,  //���o�X���ƾ�,�n�[@Validated �I�I�I
            @RequestParam(defaultValue = "0") @Min(0) Integer offset             //���L�X��,�n�[@Validated �I�I�I
    ){
        // ²�ưѼƶǻ�
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);        
        productQueryParams.setLimit(limit);   
        productQueryParams.setOffset(offset);                   

        // ���o product list
        List<Product> productList = productService.getProducts(productQueryParams);

        // ���o product �`��
        Integer total = productService.countProduct(productQueryParams);

        //�����A�զX�n�^�ǵ��e�ݪ�json��
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);   //�d�ߥ����A�h�T�w�^200�A�Y�Ť]�������\
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        
        Product product = productService.getProductById(productId);
        if (product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  //�d�߳�ӡA�n�ˬd�O�_�s�b�A�_�h�^404
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);
        if (product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }    

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, 
                                                 @RequestBody @Valid ProductRequest productRequest){
        
        Product product = productService.getProductById(productId);
        if (product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // �ק�ӫ~���ƾ�
        productService.updateProduct(productId, productRequest);

        product = productService.getProductById(productId);
        if (product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    } 
    
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Integer productId){
    
        // �R���ӫ~���ƾ�
        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }     
}
