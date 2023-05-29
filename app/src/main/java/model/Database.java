package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Database {
    private Connection con;
    private Statement stmt;
    private HashMap<String, String> UserInfo = new HashMap<>();
    private final String GRAPHCODEFILEPATH = "./grapheF.txt";

    public Database() {
        try {

            // Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion de BD locale
            // A remplir avec votre sql username et password
            connectionBDlocal("root", "");

            if (stmt != null) {

                // Creer une base de donnée projet
                createProjetDatabase();

                // Creer une tableau nommé User
                createUserTable();

                // create a graph table if it not exists
                createGraphTable();

                // Ajouter un utilisateur admin
                if (getnombreUser() == 0) {
                    signUp("admin", "admin", "admin", "userIcon1.jpg");
                }

            } else {
                if (getNbrUserInFile() == 0) {
                    signUp("admin", "admin", "admin", "userIcon1.jpg");
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // la fonction Login qui prend en argument deux String : userID et mdp
    // elle retourne une message d'erreur si l'user n'existe pas , rien sinon
    public String Login(String userID, String mdp) {

        // si on n'a pas télecharger le server WAMPP

        if (stmt == null) {
            if (userID.length() <= 0 || !estNombre(userID)) {
                return "USERID n'existe pas";
            }

            int UserID = Integer.parseInt(userID);

            if (getNbrUserInFile() < UserID) {
                return "USERID n'existe pas";
            }
            System.out.println("depuis fonction login");
            if (!researchUser(UserID, mdp)) {
                return "PASSWORD incorrect";
            }

            // si on a telecharger le server WAMPP et on a lancé application

        } else {

            if (userID.equals("1")) {
                updateUserBonus(1000);
            }

            String sql = "SELECT * FROM User" +
                    " Where id = '" + userID + "'";
            try {
                ResultSet res = stmt.executeQuery(sql);
                if (!res.next()) {
                    return "USERID n'existe pas";
                } else {
                    if (res.getString("mdp").equals(mdp)) {
                        updateUserInfo(res);

                    } else {
                        return "PASSWORD incorrect";
                    }

                }

            } catch (Exception e) {
                System.out.println(e);

            }

        }

        return "";
    }

    private void updateUserInfo(ResultSet res) {
        try {
            int ID = res.getInt("id");
            String pseudo = res.getString("pseudo");
            int niveau = res.getInt("niveau_actuelle");
            int nbr_indice = res.getInt("nbr_indice");
            UserInfo.put("id", String.valueOf(ID));
            UserInfo.put("pseudo", pseudo);
            UserInfo.put("niveau_actuelle", String.valueOf(niveau));
            UserInfo.put("nbr_indice", String.valueOf(nbr_indice));
            UserInfo.put("img", res.getString("img"));

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public String signUp(String pseudo, String mdp, String confirm_mdp, String img) {

        if (pseudo.length() > 0 &&
                mdp.length() > 0 &&
                img.length() > 0) {
            if (mdp.equals(confirm_mdp)) {

                if (stmt == null) {
                    if (pseudo.equals("admin")) {
                        appendLineInUserFile(pseudo + ";" + mdp + ";" + 0 + ";" + 1000 + ";" + img);
                    } else {
                        appendLineInUserFile(pseudo + ";" + mdp + ";" + 0 + ";" + 0 + ";" + img);
                    }

                } else {

                    addnewUser(pseudo, confirm_mdp, img);
                }
                return "";

            } else {
                return "les deux mot de passe  ne sont pas identique";
            }

        } else {
            return "Il faut remplir tous les champs ";
        }
    }

    private void addnewUser(String pseudo, String mdp, String img) {
        String sql = "INSERT INTO User " +
                "VALUES(0,'" + pseudo + "','" + mdp + "',0,0,'" + img + "')";
        System.out.println(sql);
        try {
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void createUserTable() {
        try {
            // dropTable("user");

            String sql1 = "CREATE TABLE IF NOT EXISTS User " +
                    "(id INTEGER NOT NUll AUTO_INCREMENT," +
                    " pseudo VARCHAR(255)," +
                    " mdp VARCHAR(255), " +
                    " niveau_actuelle INTEGER," +
                    " nbr_indice INTEGER ," +
                    " img VARCHAR(255), " +
                    " PRIMARY KEY( id ))";
            stmt.execute(sql1);
            System.out.println("Un tab User est creer");

        } catch (Exception e) {
            System.out.println("tab User existe deja ");
        }

    }

    public void connectionBDlocal(String user, String password) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306?useSSL=false", user, password);
            stmt = con.createStatement();
            System.out.println("Connexion reussi BD locale");
        } catch (Exception e) {
            System.out.println("Connexion sur BD en TXT");
        }

    }

    public void createProjetDatabase() {
        try {
            String sql = "CREATE DATABASE IF NOT EXISTS projet";
            stmt.execute(sql);
            System.out.println("Le database est creer");

            // Acceder à la base projet
            sql = "USE PROJET";
            stmt.execute(sql);
            System.out.println("Entrer dans la base de donnee projet");
        } catch (Exception e) {
            System.out.println("la base existe deja ");
        }
    }

    public HashMap<String, String> getUserInfo() {
        return this.UserInfo;
    }

    public void closeConnection() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void dropTable(String tablename) {
        try {
            String sql = "DROP TABLE IF EXISTS " + tablename;
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void createGraphTable() {
        try {

            String sql = "CREATE TABLE IF NOT EXISTS graph "
                    + "(id INTEGER NOT NULL AUTO_INCREMENT,"
                    + " Matrice VARCHAR(255),"
                    + " Sommet VARCHAR(255),"
                    + " niveau VARCHAR(255),"
                    + " PRIMARY KEY(id))";
            stmt.execute(sql);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createGraphTextFile() {
        try {
            File file = new File(GRAPHCODEFILEPATH);
            if (file.createNewFile()) {
                System.out.println("graph.txt created");
            } else {
                System.out.println("graph.txt already existed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createFile(String filePath) { // creation de n'importe quel fichier
        try {
            File myObj = new File(filePath);
            if (myObj.createNewFile())
                System.out.println("File created: " + myObj.getName());
            else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la creation du fichier Graphe.txt.");
            e.printStackTrace();
        }
    }

    /*
     * public void writeInUserTxt(String texte) {
     * try {
     * createFile("./User.txt");
     * FileWriter myWriter = new FileWriter("./User.txt");
     * myWriter.write("Files in Java might be tricky, but it is fun enough!");
     * myWriter.close();
     * System.out.println("Successfully wrote to the file.");
     * } catch (IOException e) {
     * System.out.println("An error occurred.");
     * e.printStackTrace();
     * }
     * }
     */

    public static void appendLineInFile(String filePath, String lineToAppend) {
        try {
            createFile(filePath);
            FileWriter fw = new FileWriter(filePath, true);
            lineToAppend = lineToAppend + "\n";
            // lineToAppend = lineToAppend + "\n";
            fw.write(lineToAppend);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // On ajoute chaque ligne du fichier texte dans un arraylist

    public static ArrayList<String> readFile(String filePath) {

        ArrayList<String> info = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(filePath);) {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                info.add(scanner.nextLine());
            }

            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return info;
    }

    public void addGraph(String Matrice, String sommet, String niveau) {
        if (!estGraphexistInFile(Matrice)) {
            // add a new graphCode to File
            appendLineInFile(GRAPHCODEFILEPATH, Matrice);

        }
        String sql = "INSERT INTO graph "
                + "VALUES(0," + ",'" + Matrice + "','" + sommet + "','" + niveau + "')";
        System.out.println("sql  " + sql);
        try {
            stmt.execute(sql);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private boolean estGraphexistInFile(String Matrice) {
        ArrayList<String> contenuText = readFile("./grapheF.txt");
        for (String tmp : contenuText) {
            if (tmp.equals(Matrice)) {
                return true;
            }
        }
        return false;
    }

    private boolean estGraphexistInData(String Matrice) {
        String sql = "SELECT * FROM graph " +
                "Where Matrice= '" + Matrice + "'";
        try {
            ResultSet resul = stmt.executeQuery(sql);
            return resul.next();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;

    }

    public String getGraphByGraphID(int graphID) {
        String sql = "SELECT * FROM Graph " +
                " Where id = '" + graphID + "'";

        try {
            ResultSet resul = stmt.executeQuery(sql);
            while (resul.next()) {
                return resul.getString("Matrice");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public String getGraphPosID(int graphID) {
        String sql = "SELECT * FROM Graph " +
                " Where id = '" + graphID + "'";

        try {
            ResultSet resul = stmt.executeQuery(sql);
            System.out.println(resul);
            while (resul.next()) {
                String s = resul.getString("Sommet");
                System.out.println(s);
                return s;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public HashMap<Integer, String> getAllGraph() {
        String sql = "SELECT * FROM Graph ";
        HashMap<Integer, String> graphs = new HashMap<>();
        try {
            ResultSet resul = stmt.executeQuery(sql);
            while (resul.next()) {
                graphs.put(resul.getInt("id"), resul.getString("Matrice"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return graphs;

    }

    public int getnombreUser() {
        String sql = "SELECT * FROM user ";
        int nbre = 0;
        if (stmt == null) {
            nbre = this.getNbrUserInFile();

        } else {

            try {
                ResultSet resul = stmt.executeQuery(sql);
                while (resul.next()) {
                    nbre++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return nbre;

    }

    public void updateUserBonus(int bonus) {

        UserInfo.replace("nbr_indice", "" + bonus);

        if (stmt == null) {

            ArrayList<String> info = this.readFile("./User.txt");
            FileWriter filewriter;
            try {
                filewriter = new FileWriter("./User.txt");
                filewriter.write("");

                for (int i = 0; i < info.size(); i++) {
                    if (i == (Integer.parseInt(UserInfo.get("id")) - 1)) {
                        String newLine = UserInfo.get("pseudo")
                                + ";" + UserInfo.get("mdp")
                                + ";" + UserInfo.get("niveau_actuelle")
                                + ";" + UserInfo.get("nbr_indice")
                                + ";" + UserInfo.get("img");
                        filewriter.append(newLine + "\n");

                    } else {
                        filewriter.append(info.get(i) + "\n");
                    }
                }

                filewriter.close();

            } catch (IOException e) {

                e.printStackTrace();
            }

        } else {

            String sql = "UPDATE User " +
                    "SET nbr_indice = '" + bonus + "' " +
                    "Where id = '" + (UserInfo.get("id") != null ? UserInfo.get("id") : 1) + "'";
            System.out.println(sql);
            try {
                stmt.execute(sql);

            } catch (Exception e) {
                e.printStackTrace();

            }

        }

    }

    private void appendLineInUserFile(String info) {
        appendLineInFile("./User.txt", info);

    }

    private void appendLineInGraphFFile(String mat, String pos) {
        appendLineInFile("./graphF.txt", pos);
        appendLineInFile("./positionF.txt", pos);
    }

    private void appendLineInGraphMFile(String mat, String pos) {
        appendLineInFile("./graphM.txt", pos);
        appendLineInFile("./positionM.txt", pos);
    }

    private void appendLineInGraphDFile(String mat, String pos) {
        appendLineInFile("./graphD.txt", pos);
        appendLineInFile("./positionD.txt", pos);
    }

    private boolean researchUser(int UserID, String mdp) {
        try {
            FileInputStream file = new FileInputStream("./User.txt");
            Scanner scanner = new Scanner(file);

            // renvoie true s'il y a une autre ligne à lire
            String line = "";
            int comt = 0;
            System.out.println("userID:" + UserID);
            while (scanner.hasNextLine() && comt != UserID) {

                comt++;
                line = scanner.nextLine();
                System.out.println(line);

            }
            scanner.close();
            System.out.println("compteur:" + comt);

            String[] info = line.split(";");

            if (info[1].equals(mdp)) {
                UserInfo.put("id", "" + UserID);
                UserInfo.put("pseudo", info[0]);
                UserInfo.put("mdp", info[1]);
                UserInfo.put("niveau_actuelle", info[2]);
                UserInfo.put("nbr_indice", info[3]);
                UserInfo.put("img", info[4]);

                System.out.println(UserInfo);

            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return true;

    }

    public int getNbrUserInFile() {
        int comt = 0;
        try {
            FileInputStream file = new FileInputStream("./User.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                comt++;
                scanner.nextLine();
            }
            scanner.close();

        } catch (Exception e) {
            System.out.println("erreur de fonction getNBrUserInFile");
        }

        return comt;

    }

    public boolean estNombre(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public static int getNbrGraph(String filename) {
        int comt = 0;

        try {
            FileInputStream file = new FileInputStream(filename);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                comt++;
                scanner.nextLine();
            }
            scanner.close();

        } catch (Exception e) {
            System.out.println("erreur de fonction getNbrGraph");
        }

        return comt;

    }

}
