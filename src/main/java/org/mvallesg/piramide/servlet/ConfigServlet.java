package org.mvallesg.piramide.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/config")
public class ConfigServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        String nJugadors = req.getParameter("jugadors");
        int nJug;
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <meta charset=\"UTF-8\" />");
            out.println("        <title>Partida de La Piramide</title>");
            out.println("    </head>");
            out.println("    <body>");
            out.println("            <h2>A continuació has d'indicar el nom dels jugadors</h2>");
            try {
                nJug = Integer.parseInt(nJugadors);
            } catch (NumberFormatException e) {
                nJug = 2;
            }
            out.println("            <form action=\"/piramide-web/partida\" method=\"post\">");
            for (int i = 0; i < nJug; i++) {
                out.println("            <div>");
                out.println("                <label for=\"j" + (i+1) + "\">Jugador " + (i+1) + ":</label>");
                out.println("                <div>");
                out.println("                    <input type=\"text\" name=\"j" + (i+1) + "\" id=\"j" + (i+1) + "\">");
                out.println("                </div>");
                out.println("            </div>");
            }
            out.println("                <input type=\"submit\" value=\"Per començar clica aci\">");
            out.println("            </form>");
            out.println("    </body>");
            out.println("</html>");

            HttpSession sessio = req.getSession();
            sessio.setAttribute("nJugadors", nJug);
        }
    }
}