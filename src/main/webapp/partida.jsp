<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.mvallesg.piramide.Partida, java.util.ArrayList, org.mvallesg.piramide.model.Jugador, org.mvallesg.piramide.model.Carta, java.lang.String"%>
<%
Partida partida = (Partida) session.getAttribute("partida");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <link rel="stylesheet" href="styles/styles.css" type="text/css">
        <title>Partida de La Piramide</title>
    </head>
    <body>
        <div id="general">

            <div id="players-output">
                <div class="player-name">
                <%for(int i=0; i<partida.getLlistaJugadors().size(); i++){%>
                    <label><%out.print(partida.getLlistaJugadors().get(i).getNom());%>:</label>
                    <div class="player-cards">
                    <%ArrayList<Carta> cartesJugador = partida.getLlistaJugadors().get(i).getCartes();%>
                    <%for(int j=0; j<cartesJugador.size(); j++){%>
                       <%Carta carta = partida.getLlistaJugadors().get(i).getCartes().get(j);%>
                        <%String palo = carta.getPalo();%>
                        <%String numero = carta.getNumero();%>
                        <%   switch(palo){
                                 case "0":
                                     palo="oros";
                                      break;
                                 case "1":
                                     palo="copes";
                                     break;
                                 case "2":
                                     palo="espases";
                                     break;
                                 case "3":
                                     palo="bastos";
                                     break;
                             }%>
                             <%String rutaCarta = "img\\" + palo.toLowerCase() + "\\" + numero + ".PNG";%>
                       <img src="<%out.print(rutaCarta);%>">
                    <%}%>
                    </div>
                <%}%>
                </div>
            </div>

            <div id="piramide-output">
            </div>
            <div id="console-output">
            </div>
        </div>
    </body>
</html>