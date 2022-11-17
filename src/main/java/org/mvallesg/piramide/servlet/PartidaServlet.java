package org.mvallesg.piramide.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mvallesg.piramide.Partida;
import org.mvallesg.piramide.model.Jugador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/partida")
public class PartidaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        HttpSession sessio = req.getSession();
        int nJug = (int)sessio.getAttribute("nJugadors");
        ArrayList<Jugador> llistaJugadors = new ArrayList<>();
        for(int i=0; i<nJug; i++){
            llistaJugadors.add(new Jugador(req.getParameter("j"+(i+1))));
        }
        Partida partida = new Partida(nJug, llistaJugadors);//.initPartida(nJug, llistaJugadors);
        sessio.setAttribute("partida", partida);

        req.setAttribute("partida", partida);
        // El método forward() sirve para direccionar o cargar el jsp.
        req.getServletContext().getRequestDispatcher("/partida.jsp").forward(req, resp);

//        try(PrintWriter out = resp.getWriter()){
//            out.println("<%@page contentType=\"text/html\" pageEncoding=\"UTF-8\"%>");
//            out.println("<%@page import=\"jakarta.servlet.http.HttpSession, org.mvallesg.piramide.Partida, java.util.ArrayList, org.mvallesg.piramide.model.Jugador, org.mvallesg.piramide.model.Carta, java.lang.String\"%>");
//            out.println("<%HttpSession sessio=(HttpSession) request.getSession();\n" +
//                        "Partida partida = sessio.getAttribute(\"partida\");\n" +
//                        "ArrayList<Jugador> llistaJugadors = partida.getLlistaJugadors();%>");
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("    <head>");
//            out.println("        <meta charset=\"UTF-8\" />");
//            out.println("        <link rel=\"stylesheet\" href=\"styles/styles.css\" type=\"text/css\">");
//            out.println("        <title>Partida de La Piramide</title>");
//            out.println("    </head>");
//            out.println("    <body>");
//            out.println("        <div id=\"general\">");
//
//            out.println("            <div id=\"players-output\">");
//            out.println("                <div class=\"player-name\">");
//            out.println("                <%for(int i=0; i<llistaJugadors.size(); i++){%>");
//            out.println("                    <label><%out.print(llistaJugadors.get(i).getNom());%>:</label>");
//            out.println("                    <div class=\"player-cards\">");
//            out.println("                    <%!ArrayList<Carta> cartesJugador = llistaJugadors.get(i).getCartes();%>\n" +
//                        "                      <%for(int j=0; j<cartesJugador.size(); j++){%>\n" +
//                        "                         <!%Carta carta = llistaJugadors.get(i).getCartes().get(i);%>\n" +
//                        "                         <!%String palo = carta.getPalo();%>\n" +
//                        "                         <!%String numero = carta.getNumero();%>\n" +
//                        "                         <%   switch(palo){\n" +
//                        "                                  case \"0\":\n" +
//                        "                                      palo=\"oros\";\n" +
//                        "                                      break;\n" +
//                        "                                  case \"1\":\n" +
//                        "                                      palo=\"copes\";\n" +
//                        "                                      break;\n" +
//                        "                                  case \"2\":\n" +
//                        "                                      palo=\"espases\";\n" +
//                        "                                      break;\n" +
//                        "                                  case \"3\":\n" +
//                        "                                      palo=\"bastos\";\n" +
//                        "                                      break;\n" +
//                        "                              }%>\n" +
//                        "                              <!%String rutaCarta = \"\\img\\\" + palo + \"\\\" + numero + \".PNG\";%>");
//            out.println("                        <img src=\"<%out.print(rutaCarta);%>\">");
//            out.println("                    <%}%>");
//            out.println("                    </div>");
//            out.println("                <%}%>");
//            out.println("                </div>");
//            out.println("            </div>");
//
//            out.println("            <div id=\"piramide-output\">");
//            out.println("            </div>");
//            out.println("            <div id=\"console-output\">");
//            out.println("            </div>");
//
//            out.println("        </div>");
//            out.println("    </body>");
//            out.println("</html>");
//        }
    }
}
