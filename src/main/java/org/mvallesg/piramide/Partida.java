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

    public ArrayList<Jugador> getLlistaJugadors() {
        return llistaJugadors;
    }

    public Joc getJoc() {
        return joc;
    }

    public Partida(int nJugadors, ArrayList<Jugador> llistaJugadors){
        this.llistaJugadors = llistaJugadors;
        this.piramide = new TreeMap<>(Collections.reverseOrder());
        this.pisoActual = 0;
        initPartida();
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
        for(Map.Entry<String, List<String>> element : baralla.entrySet()){
            for(int i=0; i<element.getValue().size(); i++){
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
        for(int i=llistaCartes.size()-1; i>=0; i--){
            r = random.nextInt(0, llistaCartes.size());
            swap1 = llistaCartes.get(i);
            swap2 = llistaCartes.get(r);
            llistaCartes.set(i, swap2);
            llistaCartes.set(r, swap1);
        }
        return llistaCartes;
    }

    public static void buscaCartaCoincident(Carta cartaAlzada, ArrayList<Jugador> llistaJugadors, int piso){
        boolean alguLaTe = false;
        for(int i=0; i<llistaJugadors.size(); i++){
            for(int j=0; j<llistaJugadors.get(i).getCartes().size(); j++){
                if(cartaAlzada.getNumero().equals(llistaJugadors.get(i).getCartes().get(j).getNumero())){
                    System.out.print("\t\tAtencio! " + llistaJugadors.get(i).getNom() + " te un " + cartaAlzada.getNumero() + " i ");
                    if(piso%2==0){
                        System.out.print("ha de beure " + (piso-1) + " trago");

                    } else{
                        System.out.print("fa beure " + (piso-1) + " trago");
                    }
                    if((piso-1)>1){
                        System.out.print("s");
                    }
                    System.out.println("!\n");
                    alguLaTe = true;
                }
            }
        }
        if(!alguLaTe){
            System.out.println("\t\tNingu la te.\n");
        }
    }

    public Carta novaRonda(int indCartaPiso){
        Carta carta = null;
        if(llistaCartesPisoActual!=null && llistaCartesPisoActual.size()>indCartaPiso){
            carta = llistaCartesPisoActual.get(indCartaPiso);
        } else{
            Iterator<Map.Entry<Integer, List<Carta>>> iterator = null;
            Map.Entry<Integer, List<Carta>> element = null;
            if(piramide!=null && piramide.entrySet()!=null){
                iterator = piramide.entrySet().iterator();
            }
            if(iterator!=null && iterator.hasNext()){
                element = iterator.next();
                llistaCartesPisoActual=element.getValue();
                pisoActual++;
                carta = llistaCartesPisoActual.get(0);
            }
        } return carta;
    }

    public void montaPiramide(){
        List<Carta> cartesPiso;
        Carta carta;
        int cartesPisoActual=0;
        int pisoActualMontaPiramide=0;
        for (int i = 0; i < llistaCartes.size(); i+=cartesPisoActual) {
            cartesPisoActual=pisoActualMontaPiramide+1;
            cartesPiso = new ArrayList<>();
            for (int j = 0; j < cartesPisoActual; j++) {
                if((i+j)<llistaCartes.size()){
                    carta = llistaCartes.get(i+j);
                    cartesPiso.add(carta);
                }
            }
            piramide.put(pisoActualMontaPiramide++, cartesPiso);
        }
        if(piramide.size()>1 && piramide.get(piramide.size()-1).size()<=piramide.get(piramide.size()-2).size()){
            List<Carta> llistaSolta = piramide.get(piramide.size()-1);
            for(int i=0; i<llistaSolta.size(); i++){
                piramide.get(piramide.size()-i-2).add(llistaSolta.get(i));
            }
            piramide.remove((piramide.size()-1));
        }
    }
}