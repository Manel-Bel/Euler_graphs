package model;

import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class Graphe {
    static int iden;
    private int nbSommets; // le nombre de sommets
    private int[][] matrice;
    private boolean oriente; // savoir si le graphe est orienté ou pas
    public int in[];
    public int out[];
    public int edges = 0;

    public Graphe(boolean oriente, String matriceS) {
        this.oriente = oriente;
        iden++;
        stringToMatrice(matriceS);
        // this.nbSommets = matrice.length;
        in = new int[nbSommets];
        out = new int[nbSommets];

    }

    // methode pour ajouter un nouveau sommet

    // public void addSommet(Sommet<T> sommet) {
    //
    // }

    private void stringToMatrice(String matriceS) {
        System.out.println(matriceS);
        if (matriceS != "") {
            String nbSommeS = "";
            int k = matriceS.length() - 1;
            while (matriceS.charAt(k) != ';') {
                nbSommeS = matriceS.charAt(k) + nbSommeS;
                k--;
            }

            // --------le k maintenant est a la position du dernier ";"
            this.nbSommets = Integer.parseInt(nbSommeS);
            System.out.println("on a recupe " + nbSommeS);

            matrice = new int[nbSommets][nbSommets];
            int ligne = 0, colonne = 0;
            int i = 0;
            while (i < k) {
                if (matriceS.charAt(i) == ';') {
                    i++;
                    colonne = 0;
                    ligne++;
                } else {
                    if (matriceS.charAt(i) != ',') {
                        matrice[ligne][colonne] = Integer.parseInt(String.valueOf(matriceS.charAt(i)));
                        colonne++;
                    }
                    i++;
                }
            }
            System.out.println(toString());
            // return matrice;
        }
        // return null;

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nbSommets; i++) {
            for (int j = 0; j < nbSommets; j++) {
                builder.append(matrice[i][j] + " ");
            }
            builder.append("\n");
        }
        return (builder.toString());
    }

    public void addArrete(int source, int destination, boolean oriente) {
        this.matrice[source][destination] = 1;
        if (!oriente) {
            this.matrice[destination][source] = 1;
        }
    }

    public int nbArete(int source) {
        int somme = 0;
        for (int j = 0; j < nbSommets; j++) {
            somme = somme + matrice[source][j];
        }
        return somme;
    }

    public boolean checkArrete(int source, int destination) { // pour voir si il existe un chemin entre la source et la
                                                              // destinantion
        return matrice[source][destination] == 1;
    }

    public int isEulirian() {
        int impaire = 0;
        if (!allConnected())
            return -1; // faux
        for (int i = 0; i < nbSommets; i++) { // on check si le nombre d'arrtes pour chaque sommet est pair ou on
                                              // trouv
                                              // impaires
            if (nbArete(i) % 2 != 0) {
                impaire++;
            }
            if (impaire > 2) {
                return -1;
            }
        }
        if (impaire == 2) {
            return 1; // y'a 2 sommets avec nb impair d'aretes
        }
        return 0; // tous paire

    }

    public boolean allConnected() { // pour checké si tous nos sommets sont bien relies entre eux
        boolean[] visite = new boolean[nbSommets];
        int node = trouveSommetConnecte();

        if (node == -1)
            return true;
        // ------ DFS -------------
        DFS(node, visite);
        for (int i = 0; i < nbSommets; i++) {
            System.out.println("sommet i " + i + " visite " + visite[i]);
        }

        for (int i = 0; i < nbSommets; i++) {
            if (!visite[i]) { // si un sommet n'a pas ete visite et il y'a un chemin retourner
                // && nbArete(i) > 0
                // faux
                return false;
            }
        }
        return true;
    }

    private int trouveSommetConnecte() {
        for (int i = 0; i < nbSommets; i++) { // on prend le premier sommet qui n'est pas tout seule
            for (int j = 0; j < nbSommets; j++) {
                if (matrice[i][j] == 1) {
                    System.out.println("le premier node " + i);
                    return i;
                }
            }
        }
        return -1;
    }

    private void DFS(int node, boolean[] v) { // Depth First Search
        v[node] = true;
        for (int j = 0; j < nbSommets; j++) {
            if (j != node && checkArrete(node, j) && !v[j]) {
                DFS(j, v);
            }
        }
    }

    public LinkedList<Integer> trouveCheminEulerian() {
        Stack<Integer> pile = new Stack<>();
        LinkedList<Integer> path = new LinkedList<>();
        int[][] mat = getMatrice();
        int courant = 0;
        if (isEulirian() == 1) { // si ils ne sont pas tous pairs on recherche le premier un nombre imapire
            System.out.println("impaaair");
            for (int s = 0; s < nbSommets; s++) {
                if (nbArete(s) % 2 != 0) {
                    courant = s;
                    break;
                }
            }
        }
        while (!pile.isEmpty() || nbArete(courant) != 0) {
            if (nbArete(courant) == 0) {
                path.add(courant);
                courant = pile.pop();
            } else {
                for (int i = 0; i < nbSommets; i++) {
                    if (checkArrete(courant, i)) {
                        pile.add(courant);
                        matrice[courant][i] = 0;
                        matrice[i][courant] = 0;
                        courant = i;
                        break;
                    }
                }
            }
        }
        for (int ele : path)
            System.out.print(ele + " -> ");
        // On ajoute aussi le sommet courant ;
        path.add(courant);
        System.out.println(courant);
        matrice = mat;
        System.out.println(this.toString());
        return path;

    }

    public int getNbSommets() {
        return this.nbSommets;
    }

    public int[][] getMatrice() {
        int[][] mat = new int[nbSommets][nbSommets];
        for (int i = 0; i < nbSommets; i++) {
            for (int j = 0; j < nbSommets; j++) {
                mat[i][j] = matrice[i][j];
            }
        }
        return mat;
    }

    public String getMatriceStr() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nbSommets; i++) {
            for (int j = 0; j < nbSommets; j++) {
                builder.append(matrice[i][j] + ",");
            }
            builder.append("/");
        }
        return (builder.toString());
    }

    public boolean isOriented() {
        return oriente;
    }

    // fonction qui compte le degreeIn et degreeOut de chaque sommet

    public void countInOutDegrees() {
        for (int i = 0; i < matrice.length; i++) {

            for (int j = 0; j < matrice.length; j++) {
                if (matrice[i][j] > 0) {
                    // System.out.println(matrice[i][j]);
                    out[i] += matrice[i][j];
                    edges += matrice[i][j];

                }
                if (matrice[j][i] > 0) {
                    in[i] += matrice[j][i];
                }
            }
        }
        System.out.println("tab de arc sortant");

        afficheTab(out);
        System.out.println("tab de arc entrant");
        afficheTab(in);
        System.out.println(edges);

    }

    // fonction qui affiche le contenu d'un tableau .

    public void afficheTab(int[] tab) {
        for (int i = 0; i < nbSommets; i++) {
            System.out.println(tab[i] + " ");
        }
    }

    // fonction qui verifie s'il existe un chemin eulerien dans un graph orienté
    //
    // Retourne true si il existe au plus un sommet i avec out[i]-in[i]==1 et au
    // plus un sommet j qui a in[j]-out[j]==1 et tous les restes ont out[i]=in[i].

    // On a un circuit eulerien si tous les sommets ont leur out[i]=in[i].

    // Retourne false sinon
    public boolean isEulerianDG() {
        int vertexOut = 0;
        int vertexIn = 0;

        for (int i = 0; i < matrice.length; i++) {
            if (in[i] - out[i] > 1 || out[i] - in[i] > 1) {
                System.out.println("numero de sommet : " + i + " nombre de sommet in et out" + in[i] + " " + out[i]);
                System.out.println(1);
                return false;
            }

            if (in[i] - out[i] == 1) {
                vertexIn++;
            }
            if (out[i] - in[i] == 1) {
                vertexOut++;
            }
            if (vertexIn > 1 || vertexOut > 1) {
                System.out.println("erreur:" + 2);
                return false;
            }

        }
        if ((vertexIn == 1 && vertexOut == 0) || (vertexOut == 1 && vertexIn == 0)) {
            System.out.println("erreur:" + 3 + "vertexin et vertxou" + vertexIn + " " + vertexOut);

            return false;

        }

        return true;

    }

    // fonction pour trouver le sommet de depart dans un graphe orienté
    // si il y a un sommet i où le degreeout - degreein =1 retourne i
    // sinon retourne un sommet quelconque.
    public int findStartNode() {
        int start = 0;
        for (int i = 0; i < nbSommets; i++) {
            if (out[i] - in[i] == 1) {
                return i;
            }
            if (out[i] > 0)
                start = i;
        }
        return start; // si c'est un circuit eulerien ,on retourne un sommet qui a au moins un arc
                      // sortant

    }

    public LinkedList<Integer> findEulerianPathDG() {
        countInOutDegrees();
        if (!isEulerianDG()) {
            System.out.println("ne contient pas de chemin eulerien");
            return null;
        }
        LinkedList<Integer> path = new LinkedList<>();

        dfsDG(findStartNode(), getMatrice(), path);
        if (path.size() == edges + 1)
            return path;
        return null;

    }

    private void dfsDG(int node, int[][] matrice, LinkedList<Integer> path) {
        while (out[node] != 0) {
            int succ = successeurs(node, matrice);
            dfsDG(succ, matrice, path);

        }
        path.addFirst(node);
    }

    private int successeurs(int pred, int[][] matrice) {
        for (int j = 0; j < nbSommets; j++) {
            if (matrice[pred][j] > 0) {
                matrice[pred][j]--;
                out[pred]--;
                return j;
            }

        }
        return -1; // s'il y a plus de successeur

    }

}
