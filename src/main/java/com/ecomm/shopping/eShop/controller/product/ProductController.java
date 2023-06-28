package com.ecomm.shopping.eShop.controller.product;

import com.ecomm.shopping.eShop.entity.product.Category;
import com.ecomm.shopping.eShop.entity.product.Image;
import com.ecomm.shopping.eShop.entity.product.Product;
import com.ecomm.shopping.eShop.service.impl.CategoryServiceImpl;
import com.ecomm.shopping.eShop.service.impl.ImageServiceImpl;
import com.ecomm.shopping.eShop.service.impl.ProductServiceImpl;
import com.ecomm.shopping.eShop.worker.UsernameProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    ImageServiceImpl imageService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    UsernameProvider usernameProvider;


    @GetMapping("/all")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String all(@RequestParam(required = false,defaultValue = "") String filter,
                      @RequestParam(required = false, defaultValue = "") String keyword,
                      @RequestParam(required = false, defaultValue = "ASC") String sort,
                      @RequestParam(required = false, defaultValue = "name") String field,
                      @RequestParam(required = false, defaultValue = "0") int page,
                      @RequestParam(required = false, defaultValue = "10") int size,
                      Model model){

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));

        Page<Product> products = Page.empty();;

        if(!filter.equals("")){
            products = productService.findByCategory(filter, pageable);
        } else if (!keyword.equals("")) {
            products = productService.findByNameLikePaged(keyword, pageable);
        } else{
            products = productService.findAllPaged(pageable);
        }


        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products",products);

        //Pagination Values
        model.addAttribute("filter", filter);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("field", field);
        model.addAttribute("sort", sort);
        model.addAttribute("pageSize", size);
        int startPage = Math.max(0, page - 1);
        int endPage = Math.min(page + 1, products.getTotalPages() - 1);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("empty", products.getTotalElements() == 0);


        return "dashboard/html/product/ProductList";
    }
    @GetMapping("/{uuid}")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String viewProduct(@PathVariable UUID uuid, Model model){
        Product product = productService.getProduct(uuid);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
//        return "viewProduct";
        return "dashboard/html/product/ProductDetailView";
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createProduct(Model model) throws JsonProcessingException {
        //get brands and cats and attach

        Product product = new Product();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);


        return "dashboard/html/product/CreateProduct";
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveProduct(@RequestParam("images") List<MultipartFile> imageFiles,
                              @RequestParam("name") String name,
                              @RequestParam("categoryUuid") String categoryUuid,
                              @RequestParam("description") String description) throws IOException {

        Product product = new Product();
        product.setName(name);
        product = productService.save(product);
        List<Image> images = new ArrayList<>();
        if(!imageFiles.get(0).getOriginalFilename().equals("")){
            for (MultipartFile image : imageFiles) {
                String fileLocation = handleFileUpload(image); // Save the image and get its file location
                Image imageEntity = new Image(fileLocation,product); // Create an Image entity with the file location
                imageEntity = imageService.save(imageEntity);
                images.add(imageEntity); // Add the Image entity to the Product's list of images
            }
        }



        product.setDescription(description);
        if(!imageFiles.get(0).getOriginalFilename().equals("")){
            product.setImages(images);
        }
        Category category = categoryService.getCategory(UUID.fromString(categoryUuid));
        product.setCategory(category);

        product = productService.save(product);

        return "redirect:/product/"+product.getUuid();
    }


    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateProduct(@RequestParam("productUuid") UUID productUuid,
                                @RequestParam("name") String name,
                                @RequestParam("categoryUuid") UUID categoryUuid,
                                @RequestParam("description") String description,
                                @RequestParam("newImages") List<MultipartFile> newImages,
                                @RequestParam("imagesToRemove") String[] imagesToRemove
                                ) throws IOException {
     //delete removed images from database;
        for(String image : imagesToRemove){
            if(!image.equals("")){
               Image deletedImage = imageService.findFileNameById(UUID.fromString(image));
               handleDelete(deletedImage.getFileName());
               imageService.delete(UUID.fromString(image));
            }
        }

        Product updatedProduct = new Product();
        updatedProduct.setUuid(productUuid);
        updatedProduct.setName(name);
        updatedProduct.setCategory(categoryService.getCategory(categoryUuid));
        updatedProduct.setDescription(description);
        productService.save(updatedProduct);
        //save new images


                for (MultipartFile image : newImages) {
                    if(image.getOriginalFilename()!="")  {
                        String fileLocation = handleFileUpload(image); // Save the image and get its file location
                        Image imageEntity = new Image(fileLocation, productService.getProduct(productUuid)); // Create an Image entity with the file location
                        imageService.save(imageEntity);
                    }
                }




        return "redirect:/product/"+productUuid;

    }

    @PostMapping(value = "/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> uploadImage(@RequestPart("image") MultipartFile imageFile,
                                              @RequestPart("pUuid") String pUuid) throws IOException {

        Product product = productService.getProduct(UUID.fromString(pUuid));
        Image image = new Image();
        image.setProduct_id(product);
        image.setFileName(handleFileUpload(imageFile));
        image = imageService.save(image);

        return ResponseEntity.ok("Image uploaded successfully");
    }


    //delete from dashboard
    @GetMapping("/delete/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable UUID uuid){

        Product product = productService.getProduct(uuid);
        product.setDeletedBy(usernameProvider.get());
        product.setDeleted(true);
        product.setEnabled(false);
        product.setDeletedAt(new Date());

        System.out.println("SOft deleting product "+product.getName());
        productService.save(product);


//        Product product = productService.getProduct(uuid);
//        //delete all images of the product first
//        for(Image image : product.getImages()){
//            imageService.delete(image.getUuid());
//        }
//        productService.delete(product.getUuid());
        return "redirect:/product/all";
    }

    private String handleFileUpload(MultipartFile file) throws IOException {
        // Define the directory to save the file in
        String rootPath = System.getProperty("user.dir");
        String uploadDir = rootPath + "/src/main/resources/static/uploads";


        // Create the directory if it doesn't exist
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Generate a unique file name for the uploaded file
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // Save the file to the upload directory
        String filePath = uploadDir + "/" + fileName;
        Path path = Paths.get(filePath);
        System.out.println(path);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // Return the file path
        return fileName;
    }
    private void handleDelete(String fileName) throws IOException {
        // Define the directory
        String rootPath = System.getProperty("user.dir");
        String uploadDir = rootPath + "/src/main/resources/static/uploads";

        // Get the file path
        String filePath = uploadDir + "/" + fileName;

        // Create a file object for the file to be deleted
        File file = new File(filePath);

        // Check if the file exists
        if (file.exists()) {
            // Delete the file
            file.delete();
            System.out.println("File deleted successfully!");
        } else {
            System.out.println("File not found!");
        }
    }

    //Enable/Disable
    @GetMapping("/toggleStatus")
    public String toggleStatus(@RequestParam("uuid") UUID uuid){
        Product product = productService.getProduct(uuid);
        product.setEnabled(!product.isEnabled());
        productService.save(product);
        return "redirect:/product/all";
    }
}
