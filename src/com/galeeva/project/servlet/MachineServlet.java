package com.galeeva.project.servlet;

import com.galeeva.project.service.MachineService;
import com.galeeva.project.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/machines")
public class MachineServlet extends HttpServlet {

    private final MachineService machineService = MachineService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("machines", machineService.findAll());

        req.getRequestDispatcher(JspHelper.getPath("machines"))
                .forward(req, resp);
    }
}
