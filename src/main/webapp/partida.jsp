<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.mvallesg.piramide.Partida, java.util.*, org.mvallesg.piramide.model.Jugador, org.mvallesg.piramide.model.Carta, java.lang.String"%>
<%
Partida partida = (Partida) session.getAttribute("partida");
int pisoCartaActual = (int) session.getAttribute("pisoCartaActual");
int cartaPisoActual = (int) session.getAttribute("cartaPisoActual");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <link rel="stylesheet" href="styles/styles.css" type="text/css">
        <link rel="icon" href="img/oros/1.PNG">
        <title>Partida de La Piramide</title>
    </head>
    <body>
        <div id="general">

            <div id="players-output">
                <div class="player-name">
                <%for(int i=0; i<partida.getLlistaJugadors().size(); i++){%>
                    <label class="nom-jugador"><%out.print(partida.getLlistaJugadors().get(i).getNom());%>:</label>
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
                <div id="piramide-fila">
                    <%for(Map.Entry<Integer, List<Carta>> pisoPiramide: partida.getPiramide().entrySet()){
                        ArrayList<Carta> cartesPiso = (ArrayList<Carta>) pisoPiramide.getValue();%>
                        <div class="card-img">
                        <%for(int i=0; i<cartesPiso.size(); i++){%>
                            <%String rutaCarta = partida.consultaCarta(pisoPiramide.getKey(), i, pisoCartaActual, cartaPisoActual);%>
                            <img src="<%out.print(rutaCarta);%>">
                        <%}%>
                        </div>
                    <%}%>
                </div>
            </div>

            <div id="console-output">
                <div id="info">
                  <%if(partida.getCarta(pisoCartaActual, cartaPisoActual)!=null){%>
                    <label class="label"><%out.print(partida.buscaCartaCoincident(partida.getCarta(pisoCartaActual, cartaPisoActual), partida.getLlistaJugadors(), pisoCartaActual));%></label>
                  <%} else{%>
                      <label class="label">Final de la partida!</label>
                  <%}%>
                </div>
                <div id="botonera">
                <%if(partida.getCarta(pisoCartaActual, cartaPisoActual)!=null){%>
                    <a class="boto" href="/piramide-web/siguiente-carta">Al??a una carta</a>
                <%} else{%>
                    <a class="boto" href="/piramide-web/restart">Nova partida</a>
                <%}%>
                 </div>
            </div>
        </div>
    </body>
</html>