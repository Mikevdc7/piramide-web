package org.mvallesg.piramide;

import org.mvallesg.piramide.model.Carta;
import org.mvallesg.piramide.model.Jugador;

import java.util.*;

public class Partida {

    static Scanner input = new Scanner(System.in);
    private ArrayList<Jugador> llistaJugadors;
    private Joc joc;
    private SortedMap<Integer, List<Carta>> piramide; // Key: Piso en el que ens trobem; Value: Cartes d'eixe piso
    private List<Carta> llistaCartesPisoActual;
    private int pisoActual;
    private List<Carta> llistaCartes;
    private Iterator<Map.Entry<Integer, List<Carta>>> iteratorCartesPisoActual = null;

    public Partida(int nJugadors, ArrayList<Jugador> llistaJugadors) {
        this.llistaJugadors = llistaJugadors;
        //this.piramide = new TreeMap<>(Collections.reverseOrder());
        this.piramide = new TreeMap<>(); // Crec que per a que es pinte bé La Piràmide, el map ha d'estar de llista més menuda a llista més gran
        this.pisoActual = piramide.size()-1;
        initPartida();
    }

    public ArrayList<Jugador> getLlistaJugadors() {
        return llistaJugadors;
    }

    public Joc getJoc() {
        return joc;
    }

    public SortedMap<Integer, List<Carta>> getPiramide() {
        return piramide;
    }

    public int getPisoActual() {
        return pisoActual;
    }

    public List<Carta> getLlistaCartes() {
        return llistaCartes;
    }

    public void initPartida() {
        System.out.println("----------Arrancant el joc de La Piramide...----------\n");
        this.joc = new Joc();
        joc.setJugadors(llistaJugadors);
        System.out.println("----------S'esta creant la baralla...----------\n");
        joc.initBaralla();
        System.out.println("----------Repartint les cartes...----------\n");
        joc.repartir();
        // Barallem les cartes i montem un treemap amb les cartes que formaran la piramide
        this.llistaCartes = barallarCartes(joc);
        System.out.println("----------Posant les cartes al tapet...----------\n");
        montaPiramide();
        System.out.println("----------Ja estan les cartes al tapet!----------\n");
        //System.out.println("----------Partida finalitzada! espero que s'hageu acalentat mes que menos ;P----------");
    }

    public List<Carta> barallarCartes(Joc joc) {
        List<Carta> llistaCartes = new ArrayList<>();
        Carta carta;
        Map<String, List<String>> baralla = joc.getBaralla();
        for (Map.Entry<String, List<String>> element : baralla.entrySet()) {
            for (int i = 0; i < element.getValue().size(); i++) {
                carta = new Carta();
                carta.setPalo(element.getKey());
                carta.setNumero(element.getValue().get(i));
                llistaCartes.add(carta);
            }
        }

        // Knuth Shuffle (Mescla de Knut)
        Random random = new Random();
        Carta swap1, swap2;
        int r;
        for (int i = llistaCartes.size() - 1; i >= 0; i--) {
            r = random.nextInt(0, llistaCartes.size());
            swap1 = llistaCartes.get(i);
            swap2 = llistaCartes.get(r);
            llistaCartes.set(i, swap2);
            llistaCartes.set(r, swap1);
        }
        return llistaCartes;
    }

    public String buscaCartaCoincident(Carta cartaAlzada, ArrayList<Jugador> llistaJugadors, int piso) {
        boolean alguLaTe = false;
        String text = "";
        for (Jugador llistaJugador : llistaJugadors) {
            for (int j = 0; j < llistaJugador.getCartes().size(); j++) {
                if (cartaAlzada.getNumero().equals(llistaJugador.getCartes().get(j).getNumero())) {
                    text = "\t\tAtenció! " + llistaJugador.getNom() + " té un " + cartaAlzada.getNumero() + " i ";
                    if ((piramide.size()-piso) % 2 != 0) {
                        text += "ha de beure " + (piramide.size()-piso) + " trago";

                    } else {
                        text += "fa beure " + (piramide.size()-piso) + " trago";
                    }
                    if ((piramide.size()-1-piso) > 0) {
                        text += "s";
                    }
                    text += "!\n";
                    alguLaTe = true;
                }
            }
        }
        if (!alguLaTe) {
            text = "\t\tNingú la té.\n";
        }
        return text;
    }

    public Carta getCarta(int piso, int indCarta){
        Carta carta;
        try{
            carta = piramide.get(piso).get(indCarta);
        } catch(Exception e){
            return null;
        }
        return carta;
    }

    /*
    En este punto se está montando la pirámide (por deficiencias de esta metodología, se llama a este método
    cada vez que se quiera destapar una carta del centro).
    Es decir, cada vez que se destape una carta, se montará la pirámide de nuevo, y se mostrarán destapadas las
    que ya han sido previamente destapadas.

    pisoPiramide: piso que estamos montando en este momento
    indCartaPiramide: índice de la carta que estamos montando en este momento, dentro del piso que estamos montando
    pisoCartaActual: piso en el que se encuentra la carta que queremos destapar
    indCartaPisoActual: índice de la carta que queremos destapar, dentro del piso en el que se encuentra
     */
    public String consultaCarta(int pisoPiramide, int indCartaPiramide, int pisoCartaActual, int indCartaPisoActual) {
        String rutaCarta = "img\\carta_reves.png"; // Ruta de la carta antes de ser destapada
        if((pisoPiramide>pisoCartaActual) || (pisoPiramide==pisoCartaActual && indCartaPiramide<=indCartaPisoActual)){
            List<Carta> cartesPisoActual = piramide.get(pisoPiramide);
            Carta carta = cartesPisoActual.get(indCartaPiramide);
            String palo = carta.getPalo();
            String numero = carta.getNumero();
            switch (palo) {
                case "0" -> palo = "oros";
                case "1" -> palo = "copes";
                case "2" -> palo = "espases";
                case "3" -> palo = "bastos";
            }
            rutaCarta = "img\\" + palo.toLowerCase() + "\\" + numero + ".PNG";
        }
        return rutaCarta;
    }

    public Carta novaRonda(int indCartaPiso) {
        Carta carta = null;
        if (llistaCartesPisoActual != null && llistaCartesPisoActual.size() > indCartaPiso) {
            carta = llistaCartesPisoActual.get(indCartaPiso);
        } else {

            Map.Entry<Integer, List<Carta>> element = null;
            if (piramide != null && iteratorCartesPisoActual == null) {
                iteratorCartesPisoActual = piramide.entrySet().iterator();
            }
            if (iteratorCartesPisoActual != null && iteratorCartesPisoActual.hasNext()) {
                element = iteratorCartesPisoActual.next();
                llistaCartesPisoActual = element.getValue();
                pisoActual--;
                carta = llistaCartesPisoActual.get(0);
            }
        }
        return carta;
    }

    public void montaPiramide() {
        List<Carta> cartesPiso;
        Carta carta;
        int cartesPisoActual = 0;
        int pisoActualMontaPiramide = 0;
        for (int i = 0; i < llistaCartes.size(); i += cartesPisoActual) {
            cartesPisoActual = pisoActualMontaPiramide + 1;
            cartesPiso = new ArrayList<>();
            for (int j = 0; j < cartesPisoActual; j++) {
                if ((i + j) < llistaCartes.size()) {
                    carta = llistaCartes.get(i + j);
                    cartesPiso.add(carta);
                }
            }
            piramide.put(pisoActualMontaPiramide++, cartesPiso);
        }
        if (piramide.size() > 1 && piramide.get(piramide.size() - 1).size() <= piramide.get(piramide.size() - 2).size()) {
            List<Carta> llistaSolta = piramide.get(piramide.size() - 1);
            for (int i = 0; i < llistaSolta.size(); i++) {
                piramide.get(piramide.size() - i - 2).add(llistaSolta.get(i));
            }
            piramide.remove((piramide.size() - 1));
        }
    }
}