package org.example.FinalProject.controllers;

import jakarta.validation.Valid;
import org.example.FinalProject.dto.ProductDTO;
import org.example.FinalProject.mappers.CategoryMapper;
import org.example.FinalProject.mappers.ProductMapper;
import org.example.FinalProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/assortment")
public class AssortmentController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(
            @PageableDefault (sort = "id", direction = Sort.Direction.ASC, value = 2)
            Pageable pageable,
            Model model,
            @RequestParam ("page") Optional<Integer> page,
            @RequestParam ("size") Optional<Integer> size,
            @RequestParam("categoryId") Optional<Long> categoryId) {
        // Settings of pagination:
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Pageable allProductsPage = PageRequest.of(currentPage - 1, pageSize);

        //  Add Model attribute for view list of category in filter
        model.addAttribute("categories", CategoryMapper.INSTANCE.listDTO(productService.getAllCategories()));
        return categoryId.map(cat -> {
            // Pagination of all products in category filter

            Page<ProductDTO> productInCategory = productService.listProductsByCategory(cat, allProductsPage);
            model.addAttribute("productPage", productInCategory);
            List<Integer> pageNumbers = getPagesCount(productInCategory);
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("categoryName", CategoryMapper.INSTANCE.categoryEntityToDTO(productService.getCategoryById(cat)).getName());
            return "products/indexCategory";
        }).orElseGet(() -> {
            // Pagination of all products
            Page<ProductDTO> productPage = productService.listProducts(allProductsPage);
            model.addAttribute("productPage", productPage);

            // Counting the number of page
            List<Integer> pageNumbers = getPagesCount(productPage);
            model.addAttribute("pageNumbers", pageNumbers);

            return "products/index";
        });
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String findByTitle(
            @PageableDefault (sort = "id", direction = Sort.Direction.ASC, value = 2)
                    Pageable pageable,
            Model model,
            @RequestParam ("page") Optional<Integer> page,
            @RequestParam ("size") Optional<Integer> size,
            @RequestParam ("title") String title,
            @RequestParam ("categoryId") Optional<Long> categoryId) {
        // Settings of pagination:
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Pageable allProductsPage = PageRequest.of(currentPage - 1, pageSize);

        //  Add Model attribute for view list of category in filter
        model.addAttribute("categories", CategoryMapper.INSTANCE.listDTO(productService.getAllCategories()));
        model.addAttribute("title", title);
        return categoryId.map(cat -> {
//            // Pagination of all products in category filter
//
            Page<ProductDTO> productInCategory = productService.listProductsByCategory(cat, allProductsPage);
            model.addAttribute("productPage", productInCategory);
            List<Integer> pageNumbers = getPagesCount(productInCategory);
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("categoryName", CategoryMapper.INSTANCE.categoryEntityToDTO(productService.getCategoryById(cat)).getName());
            return "products/indexCategory";
        }).orElseGet(() -> {
            // Pagination of all products
            Page<ProductDTO> productPage = productService.listProductsByTitle(title,allProductsPage);
            model.addAttribute("productPage", productPage);

            // Counting the number of page
            List<Integer> pageNumbers = getPagesCount(productPage);
            model.addAttribute("pageNumbers", pageNumbers);

            return "products/indexByTitle";
        });
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        ProductDTO product = ProductMapper.INSTANCE.productEntityToDTO(productService.getProductById(id));
        model.addAttribute("product", product);
        model.addAttribute("prodTitle", product.getTitle());
        return "products/show";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", CategoryMapper.INSTANCE.listDTO(productService.getAllCategories()));
        return "products/addProduct";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping()
    public String create(@ModelAttribute("product") @Valid ProductDTO product,
                         BindingResult bindingResult, Model model) {
        model.addAttribute("categories", CategoryMapper.INSTANCE.listDTO(productService.getAllCategories()));
        if (bindingResult.hasErrors()) {
            return "products/addProduct";
        }
        productService.saveProduct(product);
        return "redirect:/assortment";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/{id}/editProduct")
    public String editProduct(Model model, @PathVariable("id") long id) {
        ProductDTO product = ProductMapper.INSTANCE.productEntityToDTO(productService.getProductById(id));
        model.addAttribute("product", product);
        model.addAttribute("prodTitle", product.getTitle());
        model.addAttribute("categories", CategoryMapper.INSTANCE.listDTO(productService.getAllCategories()));
        return "products/editProduct";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PatchMapping("/{id}")
    public String updateProduct(@ModelAttribute("product") @Valid ProductDTO productDTO, BindingResult bindingResult,
                                @PathVariable("id") long id, Model model) {
        model.addAttribute("categories", CategoryMapper.INSTANCE.listDTO(productService.getAllCategories()));
        if (bindingResult.hasErrors()) {
            return "products/editProduct";
        }
        productService.updateProduct(id, productDTO);
        return "redirect:/assortment/{id}";
    }

    // HOW TO LIMIT ACCESS???
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    //??????????????????????? - CORRECT
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
        return "redirect:/assortment";
    }


    // Counting the number of page method
    public List<Integer> getPagesCount(Page<ProductDTO> productPages) {
        int totalPages = productPages.getTotalPages();
        List<Integer> result = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
        return result;
    }
}
