import javax.smartcardio.Card;
import java.sql.*;
import java.util.ArrayList;

public class Database {
    String userName = "root";
    String password = "untitled123";
    String url = "jdbc:mysql://127.0.0.1:3306/scadb?autoReconnect=true&useSSL=false";
    Connection conn;

    public ArrayList<String> mateDB = new ArrayList<>();
    public ArrayList<String> infoDB = new ArrayList<>();
    public ArrayList<String> bioDB = new ArrayList<>();
    public ArrayList<String> inginerieDB = new ArrayList<>();
    public ArrayList<String> mateSCA = new ArrayList<>();
    public ArrayList<String> infoSCA = new ArrayList<>();
    public ArrayList<String> bioSCA = new ArrayList<>();
    public ArrayList<String> inginerieSCA = new ArrayList<>();



    public void connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url,userName,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getNoteDupaMaterie(String studentID, String materie){
        Statement statement = null;

        try {
            statement = conn.createStatement();

        String queryString = "select * from materii m join student s where s.idstudent =\""+studentID+"\" and m.codmaterie = \""+materie+"\";";
        ResultSet rs = statement.executeQuery(queryString);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getNumberOfNoteForMaterie(String studentID, String materie){
        Statement statement = null;

        try {
            statement = conn.createStatement();

            String queryString = "select count(*) from materii m join student s where s.idstudent =\""+studentID+"\" and m.codmaterie = \""+materie+"\";";
            ResultSet rs = statement.executeQuery(queryString);
            while (rs.next()) {
                return Integer.parseInt(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void insertNotaForStudent(String codMaterie , String denumireMaterie, Integer nota, String studentID,String ziua, String luna, String anul, int taxaPlatita, CardFunctions command){

        if(getNumberOfNoteForMaterie(studentID,codMaterie) == 0 || getNumberOfNoteForMaterie(studentID,codMaterie) == 1){
            Statement statement = null;

            try {
                statement = conn.createStatement();

                String queryString = "insert into materii values("+studentID+",\""+denumireMaterie+"\","+codMaterie+","+nota+","+ziua+","+luna+","+anul+","+taxaPlatita+");";
                boolean rs = statement.execute(queryString);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(getNumberOfNoteForMaterie(studentID,codMaterie) == 2){
            if(taxaPlatita == 1){
                Statement statement = null;

                try {
                    statement = conn.createStatement();

                    String queryString = "insert into materii values("+studentID+",\""+denumireMaterie+"\","+codMaterie+","+nota+","+ziua+","+luna+","+anul+","+taxaPlatita+");";
                    boolean rs = statement.execute(queryString);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else if(taxaPlatita == 0){
                int codEroareValidareNota = 11;
                Statement statement = null;

                try {
                    statement = conn.createStatement();

                    String queryString = "insert into materii values("+studentID+",\""+denumireMaterie+"\","+codMaterie+","+codEroareValidareNota+","+ziua+","+luna+","+anul+","+taxaPlatita+");";
                    boolean rs = statement.execute(queryString);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                command.AddMarkToCard(codMaterie,11,ziua,luna,anul);
            }
        }
    }

    public void showAllStudentsAndIDs(){
        Statement statement = null;

        try {
            statement = conn.createStatement();

            String queryString = "select distinct numestudent, idstudent from student;";
            ResultSet rs = statement.executeQuery(queryString);
            while (rs.next()) {
                System.out.println("Numele studentului: " + rs.getString(1));
                System.out.println("ID-ul studentului: " + rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void getNoteForEachMaterieFromDB(String studentID, CardFunctions command){
        mateDB.clear();infoDB.clear();bioDB.clear();inginerieDB.clear();
        fillArraysOfNoteFromDB(studentID,"10");
        fillArraysOfNoteFromDB(studentID,"11");
        fillArraysOfNoteFromDB(studentID,"12");
        fillArraysOfNoteFromDB(studentID,"13");
        fillArraysOfNoteFromSCA(command);
        System.out.println(mateDB);
        System.out.println(infoDB);
        System.out.println(bioDB);
        System.out.println(inginerieDB);
        System.out.println(mateSCA);
        System.out.println(infoSCA);
        System.out.println(bioSCA);
        System.out.println(inginerieSCA);
    }

    public int fillArraysOfNoteFromDB(String studentID, String materie){
        Statement statement = null;


        try {
            statement = conn.createStatement();

            String queryString = "select * from materii where idstudent =\""+studentID+"\" and codmaterie = \""+materie+"\";";
            ResultSet rs = statement.executeQuery(queryString);
            while (rs.next()) {
                if(materie == "10"){
                    mateDB.add(rs.getString(4));
                    mateDB.add(rs.getString(5));
                    mateDB.add(rs.getString(6));
                    mateDB.add(rs.getString(7));
                }
                if(materie == "11"){
                    infoDB.add(rs.getString(4));
                    infoDB.add(rs.getString(5));
                    infoDB.add(rs.getString(6));
                    infoDB.add(rs.getString(7));
                }
                if(materie == "12"){
                    bioDB.add(rs.getString(4));
                    bioDB.add(rs.getString(5));
                    bioDB.add(rs.getString(6));
                    bioDB.add(rs.getString(7));
                }
                if(materie == "13"){
                    inginerieDB.add(rs.getString(4));
                    inginerieDB.add(rs.getString(5));
                    inginerieDB.add(rs.getString(6));
                    inginerieDB.add(rs.getString(7));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void fillArraysOfNoteFromSCA(CardFunctions command){
        mateSCA.clear();infoSCA.clear();bioSCA.clear();inginerieSCA.clear();
        mateSCA.addAll(command.GetMarkFromCard("10")) ;
        infoSCA.addAll(command.GetMarkFromCard("11")) ;
        bioSCA.addAll(command.GetMarkFromCard("12")) ;
        inginerieSCA.addAll(command.GetMarkFromCard("13")) ;

    }
}