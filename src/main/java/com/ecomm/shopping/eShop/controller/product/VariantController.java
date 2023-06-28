package com.ecomm.shopping.eShop.controller.product;

import com.ecomm.shopping.eShop.entity.product.Product;
import com.ecomm.shopping.eShop.entity.product.Variant;
import com.ecomm.shopping.eShop.service.impl.ProductServiceImpl;
import com.ecomm.shopping.eShop.service.impl.VariantServiceImpl;
import com.ecomm.shopping.eShop.worker.ReportGenerator;
import com.ecomm.shopping.eShop.worker.UsernameProvider;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/variant")
public class VariantController {
    @Autowired
    ProductServiceImpl productService;


    @Autowired
    VariantServiceImpl variantService;
    @Autowired
    ReportGenerator reportGenerator;
    @Autowired
    UsernameProvider usernameProvider;

    @GetMapping("/generateStockReport")
    public ResponseEntity<ByteArrayResource> generateStockReport() throws IOException {
        String fileName = reportGenerator.generateStockReportPdf(variantService.findAll());


        File requestedFile = new File(fileName);

        ByteArrayResource resource = new ByteArrayResource(FileUtils.readFileToByteArray(requestedFile));

        // Set the content type as application/pdf
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        // Set the file name for download
        headers.setContentDispositionFormData("attachment", fileName);

        // Return the resource as a response with OK status
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }


    //show view to create a variant
    @GetMapping("create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(Model model, @RequestParam(name = "product", required = false, defaultValue = "") UUID uuid ){

        List<Product> products = productService.findAll();

        model.addAttribute("products",products);

        model.addAttribute("selectedProduct", uuid);

        if(uuid == null){
            model.addAttribute("product", "");
            model.addAttribute("noVariant", true);
        }else{
            model.addAttribute("product", productService.getProduct(uuid));
            model.addAttribute("noVariant", productService.getProduct(uuid).getVariants().size()==0);
        }



        return "dashboard/html/variant/CreateVariant";
    }
    //api to save new variant
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String add(@ModelAttribute Variant variant){
        Variant savedVariant = variantService.save(variant);
        return "redirect:/variant/"+savedVariant.getUuid();
    }

    @GetMapping("/all")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String all(@RequestParam(required = false,defaultValue = "") String filter,
                      @RequestParam(required = false, defaultValue = "") String keyword,
                      @RequestParam(required = false, defaultValue = "ASC") String sort,
                      @RequestParam(required = false, defaultValue = "modifiedAt") String field,
                      @RequestParam(required = false, defaultValue = "0") int page,
                      @RequestParam(required = false, defaultValue = "10") int size,
                      Model model){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));

        Page<Variant> variants = Page.empty();
        if(!filter.equals("")){
            variants = variantService.findByProductPaged(filter, pageable);
        }else if(keyword != null || !keyword.equals("")){
            variants = variantService.findByNameLike(keyword, pageable);
        }else{
            variants = variantService.findAllPaged(pageable);
        }



        model.addAttribute("products", productService.findAll());
        model.addAttribute("variants",variants);

        //Pagination Values
        model.addAttribute("filter", filter);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", variants.getTotalPages());
        model.addAttribute("field", field);
        model.addAttribute("sort", sort);
        model.addAttribute("pageSize", size);
        int startPage = Math.max(0, page - 1);
        int endPage = Math.min(page + 1, variants.getTotalPages() - 1);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("empty", variants.getTotalElements() == 0);


        return "dashboard/html/variant/VariantList";
    }


    @GetMapping("/{uuid}")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String get(@PathVariable UUID uuid, Model model){
        Variant variant = variantService.findById(uuid);


        model.addAttribute("wishlistCount",variant.getWishlists().size());
        model.addAttribute("productName",variant.getProductId().getName());

        model.addAttribute("variant", variant);

//        model.addAttribute("uuid",uuid);
//        model.addAttribute("name",variant.getName());
//        model.addAttribute("price",variant.getPrice());
//        model.addAttribute("sellingPrice",variant.getSellingPrice());
//        model.addAttribute("stock",variant.getStock());
//        model.addAttribute("wholesalePrice", variant.getWholesalePrice());

        return "dashboard/html/variant/VariantDetailView";
    }
    //delete from uuid
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteById(@PathVariable("id") UUID uuid){
        Variant variant = variantService.findById(uuid);
        variant.setDeleted(true);
        variant.setEnabled(false);
        variant.setDeletedAt(new Date());
        variant.setDeletedBy(usernameProvider.get());

        variantService.save(variant);

        System.out.println("Soft deleting variant"+variant.getName()+" "+variant.getProductId().getName());

       // variantService.delete(uuid);
        return "redirect:/variant/all";
    }
    //delete form variant
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@ModelAttribute Variant variantToBeDeleted){
        Variant variant = variantService.findById(variantToBeDeleted.getUuid());
        variant.setDeleted(true);
        variant.setEnabled(false);
        variant.setDeletedAt(new Date());
        variant.setDeletedBy(usernameProvider.get());

        variantService.save(variant);

        System.out.println("Soft deleting variant"+variant.getName()+" "+variant.getProductId().getName());

       // variantService.delete(variant.getUuid());
        return "redirect:/variant/all";
    }

    //Update variant
    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@ModelAttribute Variant updatedvariant){
        Variant existingVariant = variantService.findById(updatedvariant.getUuid());
        existingVariant.setStock(updatedvariant.getStock());
        existingVariant.setName(updatedvariant.getName());
        existingVariant.setPrice(updatedvariant.getPrice());
        existingVariant.setSellingPrice(updatedvariant.getSellingPrice());
        existingVariant.setWholesalePrice(updatedvariant.getWholesalePrice());
        existingVariant.setEnabled(updatedvariant.isEnabled());


        variantService.update(existingVariant);

        return "redirect:/variant/"+existingVariant.getUuid();

    }
    //update all variants of a product
    @GetMapping("/updateVariants/{uuid}")
    public String updateVariants(@PathVariable("uuid") UUID uuid, Model model){
        Product product = productService.getProduct(uuid);
        List<Variant> variants = product.getVariants();

        model.addAttribute("variants", variants);
        model.addAttribute("product", product);

        return "dashboard/html/variant/VariantsUpdate";
    }
    @GetMapping("/toggleStatus")
    public String toggleStatus(@RequestParam("uuid") UUID uuid){
        Variant variant = variantService.findById(uuid);
        variant.setEnabled(!variant.isEnabled());
        variantService.save(variant);
        return "redirect:/variant/updateVariants/"+variant.getProductId().getUuid();
    }
}
