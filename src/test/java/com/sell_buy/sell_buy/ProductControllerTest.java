package com.sell_buy.sell_buy;

import com.sell_buy.sell_buy.api.controller.ProductController;
import com.sell_buy.sell_buy.api.service.ProductService;
import com.sell_buy.sell_buy.db.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProductControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testRegisterProduct() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("mem_id", 1L);

        Product product = Product.builder()
                .prod_name("Example Product")
                .prod_disc("This is an example product.")
                .price(100)
                .category("Example Category")
                .is_auction(false)
                .create_date(LocalDateTime.now())
                .build();

        when(productService.registerProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/prod/register")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"prod_name\":\"Example Product\",\"prod_disc\":\"This is an example product.\",\"price\":100,\"category\":\"Example Category\",\"is_auction\":false,\"create_date\":\"2023-10-10T10:00:00\"}"))
                .andExpect(status().isOk());
    }


}
