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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .prodName("Example Product")
                .prodDisc("This is an example product.")
                .price(100)
                .category(1L)
                .isAuction(false)
                .createDate(LocalDateTime.now())
                .build();

        when(productService.registerProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/prod")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"prodName\":\"Example Product\",\"prodDisc\":\"This is an example product.\",\"price\":100,\"category\":1,\"isAuction\":false,\"createDate\":\"2023-10-10T10:00:00\"}"))
                .andExpect(status().isOk());


    }

    @Test
    public void testUpdateProduct() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("mem_id", 1L);

        Product product = Product.builder()
                .prodId(1L)
                .prodName("Example Product")
                .prodDisc("This is an example product.")
                .price(100)
                .category(1L)
                .isAuction(false)
                .createDate(LocalDateTime.now())
                .build();

        when(productService.existsById(1L)).thenReturn(true);
        when(productService.updateProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(patch("/prod/1")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"prodName\":\"Example Product\",\"prodDisc\":\"This is an example product.\",\"price\":100,\"category\":1,\"isAuction\":false,\"createDate\":\"2023-10-10T10:00:00\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("mem_id", 1L);

        Product product = Product.builder()
                .prodId(1L)
                .sellerId(1L)
                .prodName("Example Product")
                .prodDisc("This is an example product.")
                .price(100)
                .category(1L)
                .isAuction(false)
                .createDate(LocalDateTime.now())
                .build();

        when(productService.existsById(1L)).thenReturn(true);
        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(delete("/prod/1")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"prodName\":\"Example Product\",\"prodDisc\":\"This is an example product.\",\"price\":100,\"category\":1,\"isAuction\":false,\"createDate\":\"2023-10-10T10:00:00\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProductById() throws Exception {
        Product product = Product.builder()
                .prodId(1L)
                .sellerId(1L)
                .prodName("Example Product")
                .prodDisc("This is an example product.")
                .price(100)
                .category(1L)
                .isAuction(false)
                .createDate(LocalDateTime.now())
                .build();

        when(productService.existsById(1L)).thenReturn(true);
        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/prod/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteProductNotFound() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("mem_id", 1L);

        when(productService.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/prod/1")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"prodName\":\"Example Product\",\"prodDisc\":\"This is an example product.\",\"price\":100,\"category\":1,\"isAuction\":false,\"createDate\":\"2023-10-10T10:00:00\"}"))
                .andExpect(status().is(410));
    }

    @Test
    public void testUpdateProductNotFound() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("mem_id", 1L);

        Product product = Product.builder()
                .prodId(1L)
                .prodName("Example Product")
                .prodDisc("This is an example product.")
                .price(100)
                .category(1L)
                .isAuction(false)
                .createDate(LocalDateTime.now())
                .build();

        when(productService.existsById(1L)).thenReturn(false);

        mockMvc.perform(patch("/prod/1")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"prodName\":\"Example Product\",\"prodDisc\":\"This is an example product.\",\"price\":100,\"category\":1,\"isAuction\":false,\"createDate\":\"2023-10-10T10:00:00\"}"))
                .andExpect(status().is(410));
    }

    @Test
    public void testRegisterProductNoSession() throws Exception {
        mockMvc.perform(post("/prod")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"prodName\":\"Example Product\",\"prodDisc\":\"This is an example product.\",\"price\":100,\"category\":1,\"isAuction\":false,\"createDate\":\"2023-10-10T10:00:00\"}"))
                .andExpect(status().is(411));
    }

    @Test
    public void testDeleteProductNoSession() throws Exception {
        when(productService.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/prod/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"prodName\":\"Example Product\",\"prodDisc\":\"This is an example product.\",\"price\":100,\"category\":1,\"isAuction\":false,\"createDate\":\"2023-10-10T10:00:00\"}"))
                .andExpect(status().is(411));
    }
}
