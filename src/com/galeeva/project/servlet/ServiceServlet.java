package com.galeeva.project.servlet;

import com.galeeva.project.service.ServiceService;
import com.galeeva.project.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/services")
public class ServiceServlet extends HttpServlet {

    private final ServiceService serviceService = ServiceService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("services", serviceService.findAll());

        req.getRequestDispatcher(JspHelper.getPath("services"))
                .forward(req, resp);
    }
}


