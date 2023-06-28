/*
 * Created by Sachin
 */
package com.ecomm.shopping.eShop.controller.admin;

import com.ecomm.shopping.eShop.entity.order.OrderHistory;
import com.ecomm.shopping.eShop.entity.product.Category;
import com.ecomm.shopping.eShop.entity.user.UserInfo;
import com.ecomm.shopping.eShop.entity.user.Wallet;
import com.ecomm.shopping.eShop.entity.user.WalletHistory;
import com.ecomm.shopping.eShop.enums.OrderStatus;
import com.ecomm.shopping.eShop.enums.OrderType;
import com.ecomm.shopping.eShop.service.OrderHistoryService;
import com.ecomm.shopping.eShop.service.UserInfoService;
import com.ecomm.shopping.eShop.service.WalletHistoryService;
import com.ecomm.shopping.eShop.service.WalletService;
import com.ecomm.shopping.eShop.worker.DateFormatter;
import com.ecomm.shopping.eShop.worker.ReportGenerator;
import com.ecomm.shopping.eShop.worker.SaveAnonUserDetails;
import com.ecomm.shopping.eShop.worker.UsernameProvider;
import com.itextpdf.text.DocumentException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    WalletService walletService;
    @Autowired
    ReportGenerator reportGenerator;
    @Autowired
    OrderHistoryService orderHistoryService;
    @Autowired
    UsernameProvider usernameProvider;
    @Autowired
    SaveAnonUserDetails saveAnonUserDetails;
    @Autowired
    WalletHistoryService walletHistoryService;



    @GetMapping("/users")
   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String users(Model model,
                        String keyword,
                        @PageableDefault(size = 15, sort = "username") Pageable pageable,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "username") String field,
                        @RequestParam(defaultValue = "ASC") String sort){

        pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));

        Page<UserInfo> users;
        if(keyword == null){
             users = userInfoService.loadAllUsers(pageable);
            model.addAttribute("users",users);
        }
        else{
            users = userInfoService.search(keyword, pageable);
            model.addAttribute("users",users);
        }

        //Pagination Values
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("field", field);
        model.addAttribute("sort", sort);
        model.addAttribute("pageSize", size);
        int startPage = Math.max(0, page - 1);
        int endPage = Math.min(page + 1, users.getTotalPages() - 1);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("empty", users.getTotalElements() == 0);



        return "dashboard/html/user/UsersList";
    }

    @GetMapping("/users/{uuid}")
   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String viewUser(@PathVariable UUID uuid, Model model,
                           @PageableDefault(size = 10, sort = "createdAt") Pageable pageable,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           @RequestParam(defaultValue = "createdAt") String field,
                           @RequestParam(defaultValue = "ASC") String sort){

        pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));


        UserInfo user = userInfoService.getUser(uuid);

        model.addAttribute("deleted",user.isDeleted());
        model.addAttribute("uuid",uuid);
        model.addAttribute("firstName",user.getFirstName());
        model.addAttribute("lastName",user.getLastName());
        model.addAttribute("username",user.getUsername());
        model.addAttribute("email",user.getEmail());
        model.addAttribute("phone",user.getPhone());
        model.addAttribute("role",user.getRole());
        model.addAttribute("enabled",user.isEnabled());
        if(user.getWallet() == null){
            System.out.println("No wallet found for user, creating new wallet");
            Wallet wallet = new Wallet();
            wallet.setUserInfo(user);
            wallet.setBalance(0F);
            wallet = walletService.save(wallet);
            user.setWallet(wallet);
        }

        Page<WalletHistory> transactions = walletHistoryService.findByUserInfoPaged(user, pageable);

        if(user.getTransactions().isEmpty()){
            model.addAttribute("transactionsEmpty", true);
        }else{
            model.addAttribute("transactionsEmpty", false);

//            // Sorting the transactions by createdAt field
//            List<WalletHistory> sortedTransactions = user.getTransactions().stream()
//                    .sorted(Comparator.comparing(WalletHistory::getCreatedAt).reversed())
//                    .toList();

            model.addAttribute("transactions", transactions);
        }
        model.addAttribute("walletBalance", user.getWallet().getBalance());

        //Pagination Values
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", transactions.getTotalPages());
        model.addAttribute("field", field);
        model.addAttribute("sort", sort);
        model.addAttribute("pageSize", size);
        int startPage = Math.max(0, page - 1);
        int endPage = Math.min(page + 1, transactions.getTotalPages() - 1);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("empty", transactions.getTotalElements() == 0);



        return "dashboard/html/user/UserDetailView";


    }

    @GetMapping("/users/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteUserById(@PathVariable("id") UUID uuid){

        UserInfo user = userInfoService.getUser(uuid);
        user.setDeleted(true);
        user.setEnabled(false);
        user.setDeletedAt(new Date());
        user.setDeletedBy(usernameProvider.get());

        System.out.println("Soft deleting user"+user.getUsername());
        userInfoService.save(user);

//        userInfoService.delete(uuid);
        return "redirect:/dashboard/users";
    }

    //REPORTS

    @GetMapping("/admin")
   // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String admin(Model model, HttpServletRequest request,
                        @RequestParam(name = "filter", required = false, defaultValue = "") String filter,
                        @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                        @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws IOException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.equals("anonymousUser")) {
            saveAnonUserDetails.save(request.getSession().getId(), request.getRemoteAddr());
        }

        String period;

        switch (filter) {
            case "week" -> {
                period="week";
                // Get the starting date of the current week
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                startDate = calendar.getTime();
                // Get today's date
                endDate = new Date();
            }
            case "month" -> {
                period="month";
                // Get the starting date of the current month
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                startDate = calendar.getTime();
                // Get today's date
                endDate = new Date();
            }
            case "day" -> {
                period = "day";
                // Get today's date
                LocalDate today = LocalDate.now();
                // Set the start date to 12:00:00 AM
                LocalDateTime startDateTime = today.atStartOfDay();
                // Set the end date to 11:59:59 PM
                LocalDateTime endDateTime = today.atTime(23, 59, 59);

                // Convert to Date objects
                ZoneId zone = ZoneId.systemDefault();
                startDate = Date.from(startDateTime.atZone(zone).toInstant());
                endDate = Date.from(endDateTime.atZone(zone).toInstant());
            }

            case "year" -> {
                period="year";
                // Get the starting date of the current year
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_YEAR, 1);
                startDate = calendar.getTime();
                // Get today's date
                endDate = new Date();
            }
            default -> {
                // Default case: filter
                period="Custom Date Range";
                System.out.println("order search");
                //for testing
                // Get the starting date of the current week
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                startDate = calendar.getTime();
                // Get today's date
                endDate = new Date();
            }
        }

        List<OrderHistory> orders = orderHistoryService.findOrdersByDate(startDate, endDate);

        model.addAttribute("numberOfOrders", orders.size());

        long revenue = orders.stream()
                .mapToLong(OrderHistory::getGrossLong)
                .sum();
        model.addAttribute("revenue",revenue);

        Float taxSum = orders.stream()
                .map(OrderHistory::getTax)
                .reduce(0F, Float::sum);


        model.addAttribute("taxSum", taxSum);

        AtomicReference<Float> totalProfit = new AtomicReference<>(0F);
        orders.forEach(order -> {
            order.getItems().forEach(item -> {
                totalProfit.updateAndGet(value -> value + item.getOrderPrice() - item.getVariant().getWholesalePrice());
            });
        });


        model.addAttribute("totalProfit", totalProfit);


        Map<Category,Integer> catCount = new HashMap<>();
        orders.forEach(order ->{
           order.getItems().forEach(item ->{
               Category category = item.getVariant().getProductId().getCategory();
               catCount.put(category, catCount.getOrDefault(category, 0) + 1);
           });
        });

        model.addAttribute("categoryCount", catCount);

        Map<Date, Float> revenueMap = new HashMap<>();

        orders.forEach(order->{
            Date date = order.getCreatedAt();
            Calendar calendar = Calendar.getInstance();

            // Set the Date object to be modified
                        calendar.setTime(date);

            // Set the desired time component
                        calendar.set(Calendar.HOUR_OF_DAY, 12);
                        calendar.set(Calendar.MINUTE, 00);
                        calendar.set(Calendar.SECOND, 00);
                        calendar.set(Calendar.MILLISECOND, 00);

            // Get the modified Date object
                        Date modifiedDate = calendar.getTime();
                        order.setCreatedAt(modifiedDate);


            revenueMap.put(order.getCreatedAt(), revenueMap.getOrDefault(order.getCreatedAt(), 0F)+order.getGross());
        });

        model.addAttribute("revenueMap",revenueMap);

        Map<OrderStatus, Long> orderStatusCounts = orders.stream()
                .collect(Collectors.groupingBy(OrderHistory::getOrderStatus, Collectors.counting()));

        model.addAttribute("orderStatusCounts", orderStatusCounts);

        Map<OrderType, Long> orderTypeCounts = orders.stream()
                .collect(Collectors.groupingBy(OrderHistory::getOrderType, Collectors.counting()));

        model.addAttribute("orderTypeCounts", orderTypeCounts);


        long couponsUsed = orders.stream()
                .filter(order -> order.getCoupon() != null)
                .count();

        model.addAttribute("couponsUsed", couponsUsed);


        int totalItemCount = orders.stream()
                .mapToInt(order -> order.getItems().size())
                .sum();


        model.addAttribute("totalItemCount", totalItemCount);

        //Recent 5 transactions
        model.addAttribute("lastFiveOrders",orderHistoryService.getLastFiveOrders());



        model.addAttribute("range", "From "+DateFormatter.format(startDate) + " to " + DateFormatter.format(endDate));
        model.addAttribute("period", period);
    //    model.addAttribute("orders", orders);
        return "dashboard/html/dashboard";
    }


    @PostMapping("/generateReport")
  //  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> salesReportGenerator(@RequestBody Map<String, Object> requestData ) throws ParseException, IOException, DocumentException {
        String report = (String) requestData.get("report");
        String type = (String) requestData.get("type");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = dateFormat.parse((String) requestData.get("from"));
        Date toDate = dateFormat.parse((String) requestData.get("to"));

        String generatedFile="";
        switch (report){
            case "orders" ->{
                List<OrderHistory> orders = orderHistoryService.findOrdersByDate(fromDate, toDate);
                if(type.equals("csv")){
                    generatedFile = reportGenerator.generateOrderHistoryCsv(orders);
                }else {
                    generatedFile = reportGenerator.generateOrderHistoryPdf(orders, (String) requestData.get("from"), (String) requestData.get("to"));
                }
            }
        }
        File requestedFile = new File(generatedFile);
        ByteArrayResource resource = new ByteArrayResource(FileUtils.readFileToByteArray(requestedFile));
        HttpHeaders headers = new HttpHeaders();

        if(type.equals("csv")){
            headers.setContentType(MediaType.parseMediaType("text/csv"));
        }else{
            headers.setContentType(MediaType.APPLICATION_PDF);
        }
        headers.setContentDispositionFormData("attachment", generatedFile);
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);

    }




}
