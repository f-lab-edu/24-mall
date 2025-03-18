package com.temp.sample.dao;

import com.temp.sample.config.DataSourceConfig;
import com.temp.sample.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(DataSourceConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DataSource dataSource;

    @Test
    void sample(){
        List<Product> products = productRepository.findAll();

        assertThat(products).isNotNull();

    }

}