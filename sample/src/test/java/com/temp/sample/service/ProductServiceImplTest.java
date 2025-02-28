package com.temp.sample.service;

import com.temp.sample.controller.ApiResponse;
import com.temp.sample.controller.CommonResult;
import com.temp.sample.dao.ProductRepository;
import com.temp.sample.entity.Product;
import com.temp.sample.service.response.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;
    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("조회")
    void deleteShouldMarkDeletedIfHasChildren() {
        // given
        String productName = "배달";
        Long productId = 1l;
        BigDecimal price = BigDecimal.valueOf(1700);
        Product product = createProduct(productId,productName, price);

        given(productRepository.findById(productId))
                .willReturn(Optional.of(product));

//        given(commentRepository.countBy(articleId, commentId, 2L)).willReturn(2L);

//        // when
        productService.read(productId);
//
//        // then
//        verify(comment).delete();

        ProductResponse build = ProductResponse.builder().build();
        ProductResponse build1 = ProductResponse.builder().apiResponse(new ApiResponse(CommonResult.FAIL)).build();

        System.out.println("build1 = " + build);
        System.out.println("build1 = " + build1);

    }

    private Product createProduct(Long productId, String productName, BigDecimal productPrice){
        Product product = mock(Product.class);
//        given(product.getId()).willReturn(productId);
//        given(product.getName()).willReturn(productName);
//        given(product.getPrice()).willReturn(productPrice);
        return product;
    }
}