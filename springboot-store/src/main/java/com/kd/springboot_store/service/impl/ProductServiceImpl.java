package com.kd.springboot_store.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.kd.springboot_store.dao.ProductDao;


import com.kd.springboot_store.dao.ProductRepository;
import com.kd.springboot_store.dao.ProductSpecificationRepository;
import com.kd.springboot_store.dto.ProductQueryParams;

import com.kd.springboot_store.dto.ProductRequestDTO;
import com.kd.springboot_store.dto.ProductResponseDTO;
import com.kd.springboot_store.model.Product;
import com.kd.springboot_store.model.QProduct;
import com.kd.springboot_store.service.ProductService;
import com.kd.springboot_store.util.BeanCopyUtils;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService
{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductSpecificationRepository productSpecificationRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams)
    {

        return null;
    }

//    @Override
//    public List<ProductResponseDTO> getProducts(ProductQueryParams productQueryParams)
//    {
//        List<Product> productList=productSpecificationRepository.findAll(new Specification<Product>()
//        {
//            @Override
//            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
//            {
//                // 透過root拿到需要設置條件的字段
//                Path<Objects> category= root.get("category");
//                Path<Objects> productName= root.get("productName");
//                Path<Objects> createdDate= root.get("createdDate");
//
//
//                // 參數1 為哪個字段設置條件， 參數2: 值
//                List<Predicate> predicateList=new ArrayList<>();
//
//
////                if(!StringUtils.isEmpty(productQueryParams.getCategory().name()))
////                {
////                    predicateList.add(criteriaBuilder.equal(category,
////                            productQueryParams.getCategory().name()));
////                }
////
////                if(!StringUtils.isEmpty(productQueryParams.getSearch()))
////                {
////                    predicateList.add(criteriaBuilder.equal(productName,productQueryParams.getSearch()));
////                }
//
//
//                // 透過criteriaBuilder設置不同類型條件
//                if(productQueryParams.getCategory() != null)
//                {
//                    predicateList.add(criteriaBuilder.equal(category,
//                            productQueryParams.getCategory().name()));
//
//                }
//
//                if(!StringUtils.isEmpty(productQueryParams.getSearch()))
//                {
//                    predicateList.add(criteriaBuilder.equal(productName,productQueryParams.getSearch()));
//                }
//
//
//                // 3.組合條件
//                Predicate and=
//                        criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
//
//                Order desc = criteriaBuilder.desc(createdDate);
//
//                return query.where(and).orderBy(desc).getRestriction();
//            }
//        });
//
//        List<ProductResponseDTO> productResponseDTOList= BeanCopyUtils.copyListProperties(productList,
//                ProductResponseDTO::new);
//        System.out.println(productResponseDTOList);
//        return productResponseDTOList;
//    }






    @Override
    public List<ProductResponseDTO> getProducts(ProductQueryParams productQueryParams)
    {
        JPAQueryFactory jpaQueryFactory=new JPAQueryFactory(entityManager);
        QProduct qProduct=QProduct.product;

        //初始化組裝條件(類似where 1=1)
        Predicate predicate = qProduct.isNotNull().or(qProduct.isNull());
        //執行動態條件件拼装
        predicate = productQueryParams.getSearch() == null ? predicate : ExpressionUtils.and(predicate,
                qProduct.productName.like('%' + productQueryParams.getSearch() + '%'));

        predicate = productQueryParams.getCategory() == null ? predicate : ExpressionUtils.and(predicate,
                qProduct.category.eq(productQueryParams.getCategory()));


        //執行拚装好的條件並根據userId排序，根據uIndex分組


        List<Product> productList = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate)
                .offset(productQueryParams.getOffset())
                .limit(productQueryParams.getLimit())
                .fetch();


        List<ProductResponseDTO> productResponseDTOList=BeanCopyUtils.copyListProperties(productList, ProductResponseDTO::new);

        return productResponseDTOList;

    }










    @Override
    public ProductResponseDTO getProductById(Integer productId) {

        Product product=productRepository.findById(productId).orElse(null);

        if(product != null)
        {
            ProductResponseDTO productResponseDTO=new ProductResponseDTO();
            BeanUtils.copyProperties(product, productResponseDTO);
            return productResponseDTO;
        }
        else
        {
            return null;
        }

    }


    @Override
    public Integer createProduct(ProductRequestDTO productRequestDTO)
    {
        Product product=new Product();
        BeanUtils.copyProperties(productRequestDTO, product);
        productRepository.save(product);
        Integer productId=
                productRepository.findById(product.getProductId()).orElse(null).getProductId();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequestDTO productRequestDTO)
    {
        Product product=new Product();
        BeanUtils.copyProperties(productRequestDTO, product);

        // 從資料庫裡取出的資料
        Product product2=
                productRepository.findById(productId).orElse(null);

        if (product2 != null)
        {
            // 更新商品的更新時間
            product2.setLastModifiedDate(new Date());
            BeanUtils.copyProperties(productRequestDTO, product2);
            productRepository.save(product2);
        }

    }

    @Override
    public void deleteProductById(Integer productId) {
        productRepository.deleteById(productId);
    }
}
