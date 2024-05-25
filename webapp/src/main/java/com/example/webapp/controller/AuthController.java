package com.example.webapp.controller;

import com.example.webapp.model.response.Order;
import com.example.webapp.model.response.OrderDetail;
import com.example.webapp.service.OrderDetailService;
import com.example.webapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping(value = "/login")
    public String login() {
        return "redirect:/back";
    }

    @GetMapping(value = "/profile")
    @ResponseBody
    public OAuth2User profile(@AuthenticationPrincipal OAuth2User principal) {
        return principal;
    }

    @GetMapping(value = "/account")
    public String accountDetail() {
//        OidcUserInfo oidcUserInfo =  oidcUser.getUserInfo();
//        Map<String, Object> claims = oidcUserInfo.getClaims();
//
//        model.addAttribute("account", );
        return "front/account";
    }

    @GetMapping(value = "/order")
    public String order(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
        OidcUserInfo oidcUserInfo =  oidcUser.getUserInfo();
        Map<String, Object> claims = oidcUserInfo.getClaims();

        List<Order> orders = orderService.findByCustomerId(claims.getOrDefault("nickname", "").toString());
        model.addAttribute("orders", orders);
        return "front/order_list";
    }

    @GetMapping(value = "/order/{id}")
    public String orderDetail(Model model, @AuthenticationPrincipal OidcUser oidcUser, @PathVariable String id) {
        Order order = orderService.findById(Long.parseLong(id));
        List<OrderDetail> orderDetails = orderDetailService.findByOrder(id);
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetails);
        return "front/order_detail";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        // Xóa thông tin xác thực khỏi SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/home";
    }

//    @GetMapping("/updateUser")
//    public String updateUser(@RequestParam String userID, @RequestParam String name) {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\"name\":\"testUser\"}");
//        Request request = new Request.Builder()
//                .url("https://dev-y2pjckmg4ou6okpy.us.auth0.com/api/v2/users/" + userID)
//                .method("PATCH", body)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Accept", "application/json")
//                .addHeader("Authorization", "Bearer 🔒")
//                .build();
//        Response response = client.newCall(request).execute();
//        return "redirect:/auth/account";
//    }

}
