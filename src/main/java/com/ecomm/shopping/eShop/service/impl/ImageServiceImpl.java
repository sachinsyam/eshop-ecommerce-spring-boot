package com.ecomm.shopping.eShop.service.impl;

import com.ecomm.shopping.eShop.entity.product.Banner;
import com.ecomm.shopping.eShop.entity.product.Image;
import com.ecomm.shopping.eShop.repository.ImageRepository;
import com.ecomm.shopping.eShop.service.BannerService;
import com.ecomm.shopping.eShop.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    BannerService bannerService;


    public Image save(Image image) {
        return imageRepository.save(image);
    }
    public List<Image> findImagesByProductId(UUID uuid){
        return imageRepository.findByProductId(uuid);
    }

    @Override
    public void delete(UUID uuid) {
         imageRepository.deleteById(uuid);
    }

    public Image findFileNameById(UUID uuid){
       return imageRepository.findById(uuid).orElse(null);

    }
/*
"0 50 12 * * *" specifies the schedule as follows:
second 0, minute 50, hour 12 (12 PM), any day of the month, any month, any day of the week.
 */
    @Override
  //  @Scheduled(cron = "15 21 13 * * *") // Run at 12 AM every day
    @Scheduled(cron = "0 0 21 * * *")
    public void deleteUnusedImages() {

        System.out.println("\n" +
                "  ______             __                        __            __                  __          __                          __       \n" +
                " /      \\           |  \\                      |  \\          |  \\                |  \\        |  \\                        |  \\      \n" +
                "|  $$$$$$\\  _______ | $$____    ______    ____| $$ __    __ | $$  ______    ____| $$       _| $$_     ______    _______ | $$   __ \n" +
                "| $$___\\$$ /       \\| $$    \\  /      \\  /      $$|  \\  |  \\| $$ /      \\  /      $$      |   $$ \\   |      \\  /       \\| $$  /  \\\n" +
                " \\$$    \\ |  $$$$$$$| $$$$$$$\\|  $$$$$$\\|  $$$$$$$| $$  | $$| $$|  $$$$$$\\|  $$$$$$$       \\$$$$$$    \\$$$$$$\\|  $$$$$$$| $$_/  $$\n" +
                " _\\$$$$$$\\| $$      | $$  | $$| $$    $$| $$  | $$| $$  | $$| $$| $$    $$| $$  | $$        | $$ __  /      $$ \\$$    \\ | $$   $$ \n" +
                "|  \\__| $$| $$_____ | $$  | $$| $$$$$$$$| $$__| $$| $$__/ $$| $$| $$$$$$$$| $$__| $$        | $$|  \\|  $$$$$$$ _\\$$$$$$\\| $$$$$$\\ \n" +
                " \\$$    $$ \\$$     \\| $$  | $$ \\$$     \\ \\$$    $$ \\$$    $$| $$ \\$$     \\ \\$$    $$         \\$$  $$ \\$$    $$|       $$| $$  \\$$\\\n" +
                "  \\$$$$$$   \\$$$$$$$ \\$$   \\$$  \\$$$$$$$  \\$$$$$$$  \\$$$$$$  \\$$  \\$$$$$$$  \\$$$$$$$          \\$$$$   \\$$$$$$$ \\$$$$$$$  \\$$   \\$$\n" +
                "                                                                                                                                  \n" +
                "                                                                                                                                  \n" +
                "                                                                                                                                  \n");

        System.out.println("scheduled task execution started\ndeleting all unused images");
        List<Image> productImages = imageRepository.findAll();
        List<Banner> bannerImages = bannerService.findAll();
        Set<String> imagesInUse = new HashSet<>();
        for(Image image : productImages){
            imagesInUse.add(image.getFileName());
        }
        for(Banner banner : bannerImages){
            imagesInUse.add(banner.getBannerFile());
        }

        String rootPath = System.getProperty("user.dir");
        String uploadDir = rootPath + "/src/main/resources/static/uploads";

        File directory = new File(uploadDir);


        // Check if the directory exists
        if (directory.exists() && directory.isDirectory()) {

            // Get the list of files in the directory
            File[] files = directory.listFiles();

            // Iterate over the files
            for (File file : files) {
                if (file.isFile()) {
                    // Get the file name
                    String fileName = file.getName();

                    if (!imagesInUse.contains(fileName)) {
                        // Call your handleDelete method with the file name
                        try {
                            handleDelete(fileName);
                        } catch (IOException e) {
                            // Handle any exceptions
                            e.printStackTrace();
                        }
                    }

                }
            }
        } else {
            System.out.println("Directory not found!");
        }


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
            System.out.println(fileName + " deleted");
        } else {
            System.out.println("File not found!");
        }
    }


}
