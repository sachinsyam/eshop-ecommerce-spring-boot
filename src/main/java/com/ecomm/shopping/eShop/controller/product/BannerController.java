package com.ecomm.shopping.eShop.controller.product;

import com.ecomm.shopping.eShop.entity.product.Banner;
import com.ecomm.shopping.eShop.entity.product.Product;
import com.ecomm.shopping.eShop.service.impl.BannerServiceImpl;
import com.ecomm.shopping.eShop.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    BannerServiceImpl bannerService;
    @Autowired
    ProductServiceImpl productService;

    @GetMapping("/all")
  //  @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String all(Model model){
        List<Banner> banners = bannerService.findAll();
        model.addAttribute("banners",banners);
        return "dashboard/html/banner/BannerList";
    }

    @GetMapping("/{uuid}")
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String viewBanner(@PathVariable UUID uuid, Model model){
        Banner banner = bannerService.getBanner(uuid);
        List<Product> products = productService.findAll();
        model.addAttribute("banner", banner);
        model.addAttribute("products", products);
        return "dashboard/html/banner/BannerDetailView";
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createBanner(Model model) throws JsonProcessingException {
        Banner banner = new Banner();
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("banner", banner);

        return "dashboard/html/banner/CreateBanner";
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveBanner(@RequestParam("bannerImage") MultipartFile bannerImage,
                             @RequestParam("name") String name,
                             @RequestParam("description1") String description1,
                             @RequestParam("description2") String description2,
                             @RequestParam("description3") String description3,
                             @RequestParam("productUuid") String productUuid) throws IOException {

        Banner banner = new Banner();
        banner.setName(name);
        banner.setDescription1(description1);
        banner.setDescription2(description2);
        banner.setDescription3(description3);
        banner.setProduct(productService.getProduct(UUID.fromString(productUuid)));
        String fileLocation = handleFileUpload(bannerImage); // Save the image and get its file location
        banner.setBannerFile(fileLocation);

        banner = bannerService.save(banner);

        return "redirect:/banner/"+banner.getUuid();
    }


    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateBanner(@RequestParam("bannerImage") MultipartFile bannerImage,
                               @RequestParam("bannerUuid") String uuid,
                               @RequestParam("name") String name,
                               @RequestParam("description1") String description1,
                               @RequestParam("description2") String description2,
                               @RequestParam("description3") String description3,
                               @RequestParam("productUuid") String productUuid) throws IOException {
        Banner banner = bannerService.getBanner(UUID.fromString(uuid));
        banner.setName(name);
        banner.setDescription1(description1);
        banner.setDescription2(description2);
        banner.setDescription3(description3);
        banner.setProduct(productService.getProduct(UUID.fromString(productUuid)));

        if(bannerImage.getOriginalFilename() != "") {
            //delete old image if it exists
            if(banner.getBannerFile() != ""){
               handleDelete(banner.getBannerFile());
           }
            String fileLocation = handleFileUpload(bannerImage); // Save the image and get its file location
            banner.setBannerFile(fileLocation);
        }

        bannerService.save(banner);

        return "redirect:/banner/"+uuid;
    }

    @GetMapping("/delete/{uuid}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable UUID uuid) throws IOException {

        Banner banner = bannerService.getBanner(uuid);
        //delete image file
        handleDelete(banner.getBannerFile());

        bannerService.delete(banner);

        return "redirect:/banner/all";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    String deleteFromModel(@ModelAttribute Banner banner) throws IOException {
        //delete image file
        Banner tempBanner = bannerService.findById(banner.getUuid());
        handleDelete(banner.getBannerFile());
        bannerService.delete(banner);
        return "redirect:/banner/all";
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


}
