package com.example.webapp.controller.back;

import com.example.webapp.model.request.OrderRequest;
import com.example.webapp.model.response.ApiResponse;
import com.example.webapp.model.response.Order;
import com.example.webapp.model.response.StatusEnum;
import com.example.webapp.service.OrderDetailService;
import com.example.webapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/back/order")
public class OrderController {

    private static final String REDIRECT_URL = "/back/order";

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping(value = {""})
    public String list(Model model) {
        try {
            List<Order> orderList = orderService.findAll();
            model.addAttribute("orderList", orderList);
            return "back/order_list";
        } catch (Exception exception) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/form/{id}")
    public String edit(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            Order order = orderService.findById(id);
            model.addAttribute("order", order);

            if (action == null) {
                model.addAttribute("messageDTO", null);
            } else {
                model.addAttribute("messageDTO", "messageDTO");
                if (status.equals("success")) {
                    model.addAttribute("status", "success");
                    model.addAttribute("message", "Cập nhật dữ liệu thành công!");
                } else {
                    model.addAttribute("status", "error");
                    model.addAttribute("message", "Vui lòng kiểm tra lại thông tin!");
                }
            }

            return "back/order_form";
        } catch (Exception exception) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping("/form")
    public String save(Model model, OrderRequest order) {
        try {
            ApiResponse<Order> apiResponse = orderService.save(order);
            if (apiResponse.getStatus() == StatusEnum.ERROR) {
                model.addAttribute("order", order);
                return "back/order_form";
            }

            String redirectUrl = "/back/order/form/" + apiResponse.getPayload().getId() + "?action=save&status=success";
            return "redirect:" + redirectUrl;
        } catch (Exception exception) {
            return "redirect:" + REDIRECT_URL;
        }
    }

}
