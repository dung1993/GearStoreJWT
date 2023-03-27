package com.cg.controller;


import com.cg.model.Product;
import com.cg.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public String showListPage() {
        return "listProduct";
    }

    @GetMapping("/bill")
    public String showBillPage() {
        return "bill";
    }

//    @GetMapping("/search")
//    public String search(@RequestParam("searchKey") String searchKey, Model model){
//        searchKey = "%" + searchKey + "%";
//
//        List<Product> products = productService.findAllByProductTitleLike(searchKey);
//
//        model.addAttribute("products", products);
//
//        return "listProduct";
//    }
}
