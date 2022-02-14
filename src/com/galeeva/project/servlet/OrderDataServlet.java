package com.galeeva.project.servlet;

import com.galeeva.project.service.OrderDataService;
import com.galeeva.project.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/orders")
public class OrderDataServlet extends HttpServlet {

    private final OrderDataService orderDataService = OrderDataService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long serviceId = Long.valueOf(req.getParameter("serviceId"));
        req.setAttribute("orders", orderDataService.findAllByServiceid(serviceId));

        req.getRequestDispatcher(JspHelper.getPath("orders"))
                .forward(req, resp);
    }
}

