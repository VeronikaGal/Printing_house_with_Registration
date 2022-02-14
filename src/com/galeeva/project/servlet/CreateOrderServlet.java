package com.galeeva.project.servlet;

import com.galeeva.project.dto.CreateOrderDto;
import com.galeeva.project.entity.OrderDelivery;
import com.galeeva.project.entity.OrderPaperType;
import com.galeeva.project.exeption.ValidationException;
import com.galeeva.project.service.OrderDataService;
import com.galeeva.project.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/createOrder")
public class CreateOrderServlet extends HttpServlet {

    public final OrderDataService orderDataService = OrderDataService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("paperType", OrderPaperType.values());
        req.setAttribute("delivery", OrderDelivery.values());

        req.getRequestDispatcher(JspHelper.getPath("createOrder"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        var orderDto = CreateOrderDto.builder()
                .file(req.getParameter("file"))
                .paperType(req.getParameter("paperType"))
                .quantity(Integer.valueOf(req.getParameter("quantity")))
                .delivery(req.getParameter("delivery"))
                .build();
        try {
            orderDataService.create(orderDto);
            resp.sendRedirect("/newOrder");
        } catch (ValidationException exception) {
            req.setAttribute("errors", exception.getErrors());
            doGet(req, resp);
        }
    }
}
