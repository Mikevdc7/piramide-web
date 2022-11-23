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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/partida")
public class PartidaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String nJugadors;
        int nJug = 0;
        if (req.getParameter("jugadors") != null) {
            nJugadors = req.getParameter("jugadors");
            try {
                nJug = Integer.parseInt(nJugadors);
            } catch (NumberFormatException e) {
                nJug = 2;
            }
        }

        Jugador[] llistaJugadorsEstatica = new Jugador[nJug];
        ArrayList<Jugador> llistaJugadors = new ArrayList<>();
        Map<String, String> errors = new HashMap<>();

        for(int i=0; i<nJug; i++){
            String nomJugador = req.getParameter("j"+(i+1));
            if(nomJugador==null || nomJugador.isBlank()){
               errors.put("nomJugador"+(i+1), "Has de posar el nom del jugador " + (i+1));
               llistaJugadorsEstatica[i] = null;
            } else{
                llistaJugadorsEstatica[i] = new Jugador(nomJugador);
                llistaJugadors.add(new Jugador(nomJugador));
            }
        }
        HttpSession sessio = req.getSession();
        if(errors.isEmpty()){
            Partida partida = new Partida(nJug, llistaJugadors);//.initPartida(nJug, llistaJugadors);
            sessio.setAttribute("partida", partida);
            sessio.setAttribute("pisoCartaActual", partida.getPiramide().size()-1);
            sessio.setAttribute("cartaPisoActual", 0);
            // El método forward() sirve para direccionar o cargar el jsp.
            req.getServletContext().getRequestDispatcher("/partida.jsp").forward(req, resp);
        } else{
            req.setAttribute("errors", errors);
            req.setAttribute("llistaJugadors", llistaJugadorsEstatica);
            req.getServletContext().getRequestDispatcher("/config").forward(req, resp);
        }
    }
}