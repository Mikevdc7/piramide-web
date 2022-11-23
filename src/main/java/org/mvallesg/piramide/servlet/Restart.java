package org.mvallesg.piramide.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/restart")
public class Restart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sessio = req.getSession();
        sessio.removeAttribute("pisoCartaActual");
        sessio.removeAttribute("cartaPisoActual");
        sessio.removeAttribute("partida");
        req.getServletContext().getRequestDispatcher("/index.html").forward(req, resp);
    }
}