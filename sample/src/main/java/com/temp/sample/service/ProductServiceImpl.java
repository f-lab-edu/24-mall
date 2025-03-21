package com.temp.sample.service;

import com.temp.sample.dao.ImageRepository;
import com.temp.sample.dao.ProductOptionRepository;
import com.temp.sample.dao.ProductRepository;
import com.temp.sample.entity.ImageInfo;
import com.temp.sample.entity.Product;
import com.temp.sample.entity.ProductOption;
import com.temp.sample.external.ImageClient;
import com.temp.sample.external.ImageRequest;
import com.temp.sample.external.ImageResponse;
import com.temp.sample.service.request.ProductRequest;
import com.temp.sample.service.request.ProductRequest.Image;
import com.temp.sample.service.request.ProductRequest.Option;
import com.temp.sample.service.response.ProductResponse;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private final ProductOptionRepository productOptionRepository;

  private final ImageRepository imageRepository;

  private final ImageClient imageClient;

  @Override
  @Transactional(readOnly = true)
  public ProductResponse read(Long id) {
    return ProductResponse.from(productRepository.findById(id).orElseThrow());
  }

  @Override
  @Transactional(readOnly = true)
  public ProductResponse readAll() {
    List<Product> all = productRepository.findAll();
    return ProductResponse.builder()
        .product(all)
        .productCount(all.size())
        .build();
  }

  @Override
  @Transactional
  public ProductResponse create(ProductRequest req) {

    // 상품등록
    Product savedProduct = productRepository.save(
        Product.create(req.getName(), req.getPrice(), req.getStock(), req.getMerchantId()));

    // 이미지 등록
    ImageResponse imageResponse = imageClient.uploadImage(new ImageRequest());
    imageRepository.save(ImageInfo.create(imageResponse.getName(), imageResponse.getUrl()));

    // 옵션 등록
    List<ProductOption> productOptions = req.getOptions().stream().map(
        option -> ProductOption.create(option.getName(), option.getPrice(), option.getDiscount(),
            savedProduct.getId())).toList();
    productOptionRepository.saveAll(productOptions);

    return null;
  }

  @Override
  @Transactional
  public ProductResponse update(ProductRequest req) {

    Product product = productRepository.findById(req.getProductId())
        .orElseThrow(() -> new NoSuchElementException("Product not found"));


    // 상품 변경
    product.setName(req.getName());
    product.setPrice(req.getPrice());
    product.setDiscountPrice(req.getDiscount());
    product.setStock(req.getStock());

    // 상품 옵션 변경
    List<ProductOption> productOptions = productOptionRepository.findAllById(
        req.getOptions().stream().map(Option::getId).toList());

    for (ProductOption productOption : productOptions) {
      for (Option reqOption : req.getOptions()) {
        if (productOption.getId().equals(reqOption.getId())) {
          productOption.setName(reqOption.getName());
          productOption.setPrice(reqOption.getPrice());
          productOption.setDiscountPrice(reqOption.getDiscount());
        }
      }
    }

    // 상품이미지 변경
    List<ImageInfo> allById = imageRepository.findAllById(
        req.getImage().stream().map(Image::getId).toList());
    for (ImageInfo imageInfo : allById) {
      for (Image reqImage : req.getImage()) {
        if (imageInfo.getId().equals(reqImage.getId())) {
          ImageResponse imageResponse = imageClient.modifyImage(new ImageRequest());
          imageInfo.setName(reqImage.getName());
          imageInfo.setUrl(imageResponse.getUrl());
        }
      }
    }

    return null;
  }

  @Override
  @Transactional
  public ProductResponse delete(ProductRequest req) {

    productRepository.deleteById(req.getProductId());
    productOptionRepository.deleteAllById(req.getOptions().stream().map(Option::getId).toList());
    imageClient.deleteImage(new ImageRequest());
    imageRepository.deleteAllById(req.getImage().stream().map(Image::getId).toList());

    return null;
  }
}
