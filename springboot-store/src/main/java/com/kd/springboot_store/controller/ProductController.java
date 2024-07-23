package com.kd.springboot_store.controller;

import com.kd.springboot_store.constant.ProductCategory;
import com.kd.springboot_store.dto.ProductQueryParams;
import com.kd.springboot_store.dto.ProductRequestDTO;
import com.kd.springboot_store.dto.ProductResponseDTO;
import com.kd.springboot_store.service.ProductService;
import com.kd.springboot_store.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class ProductController
{
    @Autowired
    private ProductService productService;


    // 不管有沒有查詢到Products 都回傳OK
    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponseDTO>> getProducts(
            // 查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category ,
            @RequestParam(required = false) String search,
            // 排序 Sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            // 分頁 Pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0)Integer offset
            )
    {
        ProductQueryParams productQueryParams=new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        //取得 product list
        List<ProductResponseDTO> productList =productService.getProducts(productQueryParams);

        // 商品總筆數是會根據商品種類而不同
//        Integer total=productService.countProduct(productQueryParams);
        Integer total=productList.size();

        // 分頁
        Page<ProductResponseDTO> page=new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Integer productId)
    {
        ProductResponseDTO productResponseDTO=productService.getProductById(productId);

        if(productResponseDTO != null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping("/products")
    public ResponseEntity<ProductResponseDTO> createProduct( @RequestBody @Valid ProductRequestDTO productRequestDTO)
    {

        Integer productId=productService.createProduct(productRequestDTO);
        // 返回新增的資料物件
        ProductResponseDTO productResponseDTO=productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDTO);


    }
    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct( @PathVariable Integer productId,
                                                  @RequestBody @Valid ProductRequestDTO productRequestDTO)
    {
        //檢查product是否存在
        ProductResponseDTO productResponseDTO=productService.getProductById(productId);
        if(productResponseDTO ==null)
        {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // 修改商品數據
        productService.updateProduct(productId, productRequestDTO);
        // 返回新增的資料物件
        ProductResponseDTO updateProduct=productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(updateProduct);


    }

    // 只要確定資料消失不見就表示成功
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct( @PathVariable Integer productId)
    {
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }



}
