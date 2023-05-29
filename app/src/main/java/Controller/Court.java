package Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

import model.*;

public class Court {
    private Player p;
    private Graphe graphe;
    private int depart;
    private boolean[][] matriceAretes;
    private LinkedList<Integer> sauv; // pour sauvegarder chaque mouvement du joueur
    private int mode, nbSommet, gra;

    public int[][] graphWeight;
    private int[] dist;// pour enregistrer les distances , puis la plus courte est à l'inidice de end
    private boolean[] visited;//
    private int start = -1, end = -1;
    private Database database;
    private String difficulteFile;
    private String positionFile;
    private boolean oriente;

    public Court(Database data, int mode, char niveau, int gra) {
        // this.players = new LinkedList<>();
        setDifficultePosi(niveau);
        this.gra = gra;
        database = data;
        String pseudo = data.getUserInfo().get("pseudo") != null ? data.getUserInfo().get("pseudo") : "Player";
        String id = data.getUserInfo().get("id") != null ? data.getUserInfo().get("id") : "0";
        String scr = data.getUserInfo().get("nbr_indice") != null ? data.getUserInfo().get("nbr_indice") : "100";
        // String gData; = data.getGraphByGraphID(difficulte, gra)
        p = new Player(Integer.parseInt(id), pseudo, this);
        p.setScore(Integer.parseInt(scr));

        // -------Recupe les matrices et les positions des bons fichiers -------
        ArrayList<String> gMatrices = Database.readFile(difficulteFile);

        // -------- avec stringToMatrice on a la matrice et nbSommet------

        this.graphe = new Graphe(oriente, gMatrices.get(gra));
        nbSommet = this.graphe.getNbSommets();

        this.mode = mode;
        this.matriceAretes = new boolean[nbSommet][nbSommet];
        this.depart = -1;
        this.sauv = new LinkedList<>();
        // longestPath();
        if (mode == 2) {
            this.graphWeight = grapheWeight();
            System.out.println("avant");
            // int[] longestPath = longestPath();
            // this.start = longestPath[0];
            // this.end = longestPath[1];
            int [] l =find(graphe.getMatrice(),graphWeight);
            this.start = l[0];
            this.end = l[1];
            this.depart = start;
            System.out.println(start + " " + end);
        }

        // int [] longestPath = longestPath();

        // this.start =longestPath[0];
        // this.end =longestPath[1];
        // int [] pathL = longestPath();
        // start = pathL[0];
        // end = pathL[1] ;
        // this.start =0;
        // this.end =nbSommet-1;
    }






    private int[] find(int[][] graph, int[][] graphWeight) {
        int n = graph.length;
        int maxWeight = Integer.MIN_VALUE;
        int[] maxWeightVertices = new int[2];
    
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (graph[i][j] == 0 && graph[j][i] == 0 && graphWeight[i][j] > maxWeight) {
                    maxWeight = graphWeight[i][j];
                    maxWeightVertices[0] = i;
                    maxWeightVertices[1] = j;
                }
            }
        }
        if(maxWeightVertices[0] == maxWeightVertices[1]) {
            maxWeightVertices[0] = 0 ;
            maxWeightVertices[1] = graph.length -2;
        }
        // System.out.println()
        return maxWeightVertices;
    }
    
    private void setDifficultePosi(char niv) {
        if (niv == 'd') {
            difficulteFile = "grapheD.txt";
            positionFile = "PositionsD.txt";
            oriente = true;
        } else {
            if (niv == 'm') {
                difficulteFile = "grapheM.txt";
                positionFile = "PositionsM.txt";
            } else {
                difficulteFile = "grapheF.txt";
                positionFile = "PositionsF.txt";
            }
            oriente = false;
        }
    }

    public Player getPlayerI(int id) { // recherche d'un joueur d'apres son id et de le renvoyer
        return this.p;
    }

    public boolean fini() {
        if (mode == 1) {
            for (int i = 0; i < graphe.getNbSommets(); i++) {
                for (int j = 0; j < graphe.getNbSommets(); j++) {
                    if (graphe.checkArrete(i, j) && !isRoadToken(i, j))
                        return false;
                }
            }
        } else {
            if (mode == 2) {
                // for(int i = 0 ; i<dijkstra().size()-1;i++) {
                // if(!isRoadToken(i, i+1))return false;
                // }
                LinkedList<Integer> d = dijkstra();
                int i = 0;
                while (i < d.size() - 1) {
                    if (!isRoadToken(d.get(i), d.get(i + 1))){
                        return false;
                    }
                    i += 1;
                }

            }

        }
        return true;
    }

    public int getPlayerScore() {
        return p.getScore();
    }

    public Graphe getGraphe() {
        return this.graphe;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public String getPositionString() {
        ArrayList<String> gPositions = Database.readFile(positionFile);
        return gPositions.get(gra);
    }

    public boolean updateSommet(int sId) {
        System.out.println(" id source " + sId);
            if (depart == -1) {
                if(mode != 1){
                    if (sId == start) {
                        depart = start;
                    }else {
                        return false;
                    }
                }
                else{
                    depart = sId;
                }

                sauv.add(depart);
                System.out.println("depart : " + depart);
            } else {
                if (graphe.checkArrete(depart, sId) && !isRoadToken(depart, sId)) {
                    this.matriceAretes[depart][sId] = true;
                    if (!graphe.isOriented()){
                        this.matriceAretes[sId][depart] = true;
                    }  
                    depart = sId;
                    sauv.addLast(depart);
                    System.out.println("arrive");
                    return true;
                }
            }
        return false;
    }


    public boolean isRoadToken(int s, int d) {
        return this.matriceAretes[s][d]; // si retourne faux == le joueur n'est pas encore passer
    }

    // public boolean fini() {
    // for(int i = 0 ; i<dijkstra().size()-1;i++) {
    // if(!isRoadToken(i, i+1))return false;
    // }
    // return true;
    // }

    public boolean indice() {
        if (p.getScore() >= 50) {
            p.incScore(-50);

            if (mode == 1)
                graphe.trouveCheminEulerian();
            else
                System.out.println(dijkstra());
            return true;
        }
        return false;
    }

    public boolean prec() {
        if (!fini()) {
            if (sauv.size() > 1) {
                System.out.println(sauv.size());
                int arr = sauv.removeLast();
                int dep = sauv.getLast();
                System.out.println("le depart " + dep + " arrive " + arr);
                this.matriceAretes[dep][arr] = false;
                if (!graphe.isOriented())
                    this.matriceAretes[arr][dep] = false;
                this.depart = dep;
                return true;
            }
            if (sauv.size() == 1) {
                sauv.pop();
                this.depart = -1;
            }
        }
        return false;
    }

    public void annuler() {
        depart = -1;
        this.matriceAretes = new boolean[graphe.getNbSommets()][graphe.getNbSommets()];
        this.sauv.removeAll(this.sauv);
        this.sauv = new LinkedList<>();
    }

    public LinkedList<Integer> getCheminEuclerien() {

        if (!graphe.isOriented())
            return graphe.trouveCheminEulerian();
        return graphe.findEulerianPathDG();

    }

    public void setGrapheWeight(int[][] w) {
        this.graphWeight = w;
    }

    public void initStartEnd(int s, int e) {
        this.start = s;
        this.end = e;
    }

    public void reinitialierStartEnd() {
        this.start = -1;
        this.end = -1;
    }

    public LinkedList<Integer> dijkstra() {
        boolean oriente = graphe.isOriented();
        LinkedList<Integer> path = new LinkedList<>();
        dist = new int[graphe.getNbSommets()];
        int[] sommets = new int[graphe.getNbSommets()];
        Arrays.fill(sommets, -1);
        // boolean ajouter;
        visited = new boolean[graphe.getNbSommets()];
        Arrays.fill(dist, Integer.MAX_VALUE);// l'infini
        dist[start] = 0;
        for (int i = 0; i < graphWeight.length; i++) {
            int curr = getMinVertex();
            // ajouter = false;
            visited[curr] = true;
            for (int j = 0; j < graphWeight.length; j++) {
                int weight = graphWeight[curr][j];
                if (weight > 0 && !visited[j]) {
                    int newDist = dist[curr] + weight;
                    if (newDist < dist[j]) {
                        dist[j] = newDist;
                        sommets[j] = curr;
                    }
                }
                // si il est non-oriente 
                // if(!oriente && weight > 0 || (oriente && weight != 0 && !visited[curr])) {
                //     int newDist = dist[j] + weight ;
                //     if(newDist < dist[curr]) {
                //         dist[curr] = newDist ; 
                //         sommets[curr] = j;
                //     }
                // }
                // si il est non-oriente
                if (!oriente && weight > 0 || (oriente && weight != 0 && !visited[curr])) {
                    int newDist = dist[j] + weight;
                    if (newDist < dist[curr]) {
                        dist[curr] = newDist;
                        sommets[curr] = j;
                    }
                }
            }
        }
        return this.getPath(sommets);
    }

    private int getMinVertex() {
        int minVertex = -1;
        for (int i = 0; i < dist.length; i++) {
            if (!visited[i] && (minVertex == -1 || dist[i] < dist[minVertex])) {
                minVertex = i;
            }
        }
        return minVertex;
    }

    private LinkedList<Integer> getPath(int[] s) {
        LinkedList<Integer> path = new LinkedList<Integer>();
        int curr = end;
        while (curr != start) {
            path.addFirst(curr);
            curr = s[curr];
        }
        path.addFirst(start);
        return path;
    }

    private int[][] positions() {
        int i = 0;
        String position = getPositionString();
        int[][] coordonees = new int[graphe.getNbSommets()][2];
        while (i < position.length()) {
            String id = "", x = "", y = "";
            if (position.charAt(i) == '(') {
                i++;
                while (position.charAt(i) != ';') {
                    id += position.charAt(i);
                    i++;
                }
                i++;
                while (position.charAt(i) != ';') {
                    x += position.charAt(i);
                    i++;
                }
                i++;
                while (position.charAt(i) != ')') {
                    y += position.charAt(i);
                    i++;
                }
                coordonees[Integer.parseInt(String.valueOf(id))][0] = Integer.parseInt(String.valueOf(x));
                coordonees[Integer.parseInt(String.valueOf(id))][0] = Integer.parseInt(String.valueOf(y));
            }
            i++;
        }
        return coordonees;
    }

    private int[] getCoords(int indice) {
        return positions()[indice];
    }

    private int weightArrete(int i, int j) {
        int[] coordsI = getCoords(i);
        int[] coordsJ = getCoords(j);
        int dx = coordsI[0] - coordsJ[0];
        int dy = coordsI[1] - coordsJ[1];
        return (int) (Math.sqrt((dx * dx) + (dy * dy)));
    }

    private int[][] grapheWeight() {
        int[][] weight = new int[graphe.getNbSommets()][graphe.getNbSommets()];
        for (int i = 0; i < weight.length; i++) {
            for (int j = i + 1; j < weight.length; j++) {
                if (graphe.checkArrete(i, j)) {
                    if (graphe.isOriented()) {
                        weight[i][j] = weightArrete(i, j);
                    } else {
                        weight[i][j] = weightArrete(i, j);
                        weight[j][i] = weight[i][j];
                    }
                }
            }
        }
        return weight;
    }

    public int getWeight(int i, int j) {
        return graphWeight[i][j];
    }

    public void affiche(int[][] graphWeight) {
        for (int i = 0; i < graphWeight.length; i++) {
            for (int j = 0; j < graphWeight.length; j++) {
                System.out.print(graphWeight[i][j]);
            }
            System.out.println();
        }
    }

    public int getShortestDistance() {
        return dist[end];
    }

    public int getMode() {
        return this.mode;
    }

    public void updateBonus(int nbr_indice) {
        if (database != null)
            database.updateUserBonus(nbr_indice);
    }

    public int getNbSommets() {
        return graphe.getNbSommets();
    }

    public boolean isgGrapheOriented() {
        return graphe.isOriented();
    }

    

}
