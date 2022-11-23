package org.mvallesg.piramide.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mvallesg.piramide.model.Jugador;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/config")
public class ConfigServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        String nJugadors = "0";
        int nJug = 0;
        if (req.getParameter("jugadors") != null) {
            nJugadors = req.getParameter("jugadors");
            try {
                nJug = Integer.parseInt(nJugadors);
            } catch (NumberFormatException e) {
                nJug = 2;
            }
        }

        Map<String, String> errors = null;
        if(req.getAttribute("errors")!=null){
            errors = (Map<String, String>) req.getAttribute("errors");
        }
        Jugador[] llistaJugadors = null;
        if(req.getAttribute("llistaJugadors")!=null){
            llistaJugadors = (Jugador[]) req.getAttribute("llistaJugadors");
        }
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("    <head>");
            out.println("        <meta charset=\"UTF-8\" />");
            out.println("        <title>Partida de La Piramide</title>");
            out.println("    </head>");
            out.println("    <body>");
            out.println("            <h2>A continuació has d'indicar el nom dels jugadors</h2>");
            out.println("            <form action=\"/piramide-web/partida?jugadors=" + nJug + "\" method=\"post\">");
            for (int i = 0; i < nJug; i++) {
                out.println("            <div>");
                out.println("                <label for=\"j" + (i+1) + "\">Jugador " + (i+1) + ":</label>");
                out.println("                <div>");
                if(llistaJugadors!=null && llistaJugadors[i]!=null && llistaJugadors[i].getNom()!=null && !llistaJugadors[i].getNom().isBlank()){
                    out.println("                    <input type=\"text\" name=\"j" + (i+1) + "\" id=\"j" + (i+1) + "\" value=\"" + llistaJugadors[i].getNom() + "\">");
                } else{
                    out.println("                    <input type=\"text\" name=\"j" + (i+1) + "\" id=\"j" + (i+1) + "\">");
                }
                out.println("                </div>");
                out.println("            </div>");
                if(errors!=null && errors.containsKey("nomJugador"+(i+1))){
                    out.println("            <div><small style='color: red;'>" + errors.get("nomJugador"+(i+1)) + "</small></div>");
                }
            }
            out.println("                <input type=\"submit\" value=\"Per començar clica aci\">");
            out.println("            </form>");
            out.println("    </body>");
            out.println("</html>");
        }
    }
}