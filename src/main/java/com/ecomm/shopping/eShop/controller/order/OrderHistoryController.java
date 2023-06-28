package com.ecomm.shopping.eShop.controller.order;

import com.ecomm.shopping.eShop.dto.OrderDto;
import com.ecomm.shopping.eShop.entity.order.OrderHistory;
import com.ecomm.shopping.eShop.entity.user.Wallet;
import com.ecomm.shopping.eShop.enums.OrderStatus;
import com.ecomm.shopping.eShop.enums.OrderType;
import com.ecomm.shopping.eShop.service.OrderHistoryService;
import com.ecomm.shopping.eShop.service.WalletService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderHistoryController {
    @Autowired
    OrderHistoryService orderHistoryService;
    @Autowired
    WalletService walletService;

    @GetMapping("all")
    public String all(Model model,
                      @RequestParam(required = false) String keyword,
                      @RequestParam(required = false, defaultValue = "") String filter,
                      @PageableDefault(size = 15, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                      @RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "10") int size,
                      @RequestParam(defaultValue = "createdAt") String field,
                      @RequestParam(defaultValue = "DESC") String sort){

        pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));

        Page<OrderHistory> orders;


            switch (filter){
                case "ONLINE" -> orders = orderHistoryService.findByOrderType(OrderType.ONLINE, pageable);
                case "COD" -> orders = orderHistoryService.findByOrderType( OrderType.COD, pageable);
                case "PROCESSING" -> orders = orderHistoryService.findByOrderStatus( OrderStatus.PROCESSING, pageable);
                case "SHIPPED" -> orders = orderHistoryService.findByOrderStatus(OrderStatus.SHIPPED, pageable);
                case "DELIVERED" -> orders = orderHistoryService.findByOrderStatus(OrderStatus.DELIVERED, pageable);
                case "CANCELLED" -> orders = orderHistoryService.findByOrderStatus(OrderStatus.CANCELLED, pageable);
                case "RETURNED" -> orders = orderHistoryService.findByOrderStatus(OrderStatus.RETURNED, pageable);
                case "REFUNDED" -> orders = orderHistoryService.findByOrderStatus(OrderStatus.REFUNDED, pageable);
                case "PAYMENT_PENDING" -> orders = orderHistoryService.findByOrderStatus(OrderStatus.PAYMENT_PENDING, pageable);
                default -> {
                    if (keyword == null || keyword.equals("")) {
                        orders = orderHistoryService.findAll(pageable);
                    } else {
                        orders = orderHistoryService.findByIdLike(keyword, pageable);
                    }
                }
            }





        model.addAttribute("orders", orders);


        //Pagination Values
        model.addAttribute("filter", filter);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());
        model.addAttribute("field", field);
        model.addAttribute("sort", sort);
        model.addAttribute("pageSize", size);
        int startPage = Math.max(0, page - 1);
        int endPage = Math.min(page + 1, orders.getTotalPages() - 1);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("empty", orders.getTotalElements() == 0);


        return "dashboard/html/order/OrdersList";
    }

    @GetMapping("/{uuid}")
    public String view(@PathVariable UUID uuid, Model model){
        OrderHistory order = orderHistoryService.findById(uuid);
        order.setGross(order.getTax() + order.getTotal());

        if(order.getUserInfo().getWallet() == null){
            Wallet wallet = new Wallet();
            wallet.setBalance(0F);
            wallet.setUserInfo(order.getUserInfo());
            wallet = walletService.save(wallet);
            order.getUserInfo().setWallet(wallet);
        }

        //check if invoice exists


        model.addAttribute("order", order);

        model.addAttribute("statusList", OrderStatus.values());

        model.addAttribute("paymentFailed", order.getOrderStatus().equals(OrderStatus.PAYMENT_PENDING));
        model.addAttribute("online", order.getOrderType().equals(OrderType.ONLINE));

        return "dashboard/html/order/OrderDetailView";
    }

        @PostMapping("/delete")
        public String delete(@RequestParam UUID uuid){
        OrderHistory order = orderHistoryService.findById(uuid);
        order.setDeleted(true);
        orderHistoryService.save(order);
        return "redirect:/order/all";

        }
   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/update")
    public String update(@ModelAttribute OrderDto orderDto){

        OrderHistory order = orderHistoryService.findById(orderDto.getUuid());
        order.setOrderStatus(orderDto.getOrderStatus());

        orderHistoryService.save(order);

        return "redirect:/order/" + order.getUuid();
    }
    @GetMapping("/generateInvoice")
    public String generateInvoice(@RequestParam(name = "uuid") UUID uuid){
        orderHistoryService.generateInvoice(uuid);

        return "redirect:/order/" + uuid;
    }

    @PostMapping("/viewInvoice")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> viewInvoice(@RequestBody String uuid) throws IOException {
        String rootPath = System.getProperty("user.dir");
        String uploadDir = rootPath + "/src/main/resources/static/uploads/invoices/";

        String requestedFileName = uuid + ".pdf";
        File requestedFile = new File(uploadDir+requestedFileName);
        System.out.println("Searching for " + requestedFileName);

        File directory = new File(uploadDir);
        boolean found = false;

        // Check if the directory exists
        if (directory.exists() && directory.isDirectory()) {
            // Get the list of files in the directory
            File[] files = directory.listFiles();
            // Iterate over the files
            for (File file : files) {
                if (file.isFile()) {
                    // Get the file name
                    String fileName = file.getName();
                    if (fileName.equals(requestedFileName)) {
                        requestedFile = file;
                       found = true;
                       break;
                    }
                }
            }
        }


        if(found){
            System.out.println(requestedFileName + " found");
        }else{
            System.out.println(requestedFileName+"+ not Found. Generating...");
            orderHistoryService.generateInvoice(UUID.fromString(uuid));
        }


        ByteArrayResource resource = new ByteArrayResource(FileUtils.readFileToByteArray(requestedFile));

        // Set the content type as application/pdf
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        // Set the file name for download
        headers.setContentDispositionFormData("attachment", requestedFileName);

        // Return the resource as a response with OK status
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}
