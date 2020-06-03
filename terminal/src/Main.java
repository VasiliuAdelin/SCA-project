import jdk.nashorn.internal.objects.Global;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        db.connect();
        CardFunctions command = new CardFunctions();
        command.ConnectToCard();
        String studentIDAfterValidation = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Profesor -- 1 \n Student --- 2");
        Boolean quit = false;
        while (!quit) {
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("PROFESOR");
                    System.out.println("Ce doriti sa faceti? \n Adaugati nota pentru studentul prezent -- 1. \n Introduceti nota in javaCard-ul studentului -- 2.\n  Afisati notele de pe javacard a unei materii -- 3. \n Adaugati nota unui student neprezentat la examen -- 4.\n Verificati notele pentru participare la concurs -- 5. \n Anunta incheierea sesiunii -- 6 \n");
                    int teacherChoice = scanner.nextInt();
                    switch (teacherChoice) {
                        case 1:
                            if (true) {
                                //              punctul 1 c
                                String studentIdToAddMarkInDB = studentIDAfterValidation;
                                System.out.println("Denumirea materiei la care introduceti nota: \n");
                                String materieToAddGrade = scanner.next();
                                System.out.println("Alegeti disciplina la care doriti introducerea notei: \n \tMatematica -- 10 \n \t Informatica -- 11 \n \t Biologie -- 12 \n \t Inginerie -- 13\n");
                                String codMaterie = scanner.next();
                                System.out.println("Nota pe care doriti sa o introduceti: ");
                                int notaMaterie = scanner.nextInt();
                                System.out.println("Ziua notarii: \n");
                                String ziua = scanner.next();
                                System.out.println("Luna notarii: \n");
                                String luna = scanner.next();
                                System.out.println("Anul notarii: \n");
                                String anul = scanner.next();
                                System.out.println("Taxa de reexaminare platita( 1 sau 0 ): ");
                                int taxaPlatita = scanner.nextInt();

                                db.insertNotaForStudent(codMaterie, materieToAddGrade, notaMaterie, studentIdToAddMarkInDB, ziua, luna, anul, taxaPlatita, command);
                            }
                            break;
                        case 2:
                            if (!studentIDAfterValidation.equals("")) {      //1 punctele a + b
                                System.out.println("Alegeti disciplina la care doriti introducerea notei: \n \tMatematica -- 10 \n \t Informatica -- 11 \n \t Biologie -- 12 \n \t Inginerie -- 13\n");
                                String disciplina = scanner.next();
                                System.out.println("Nota de pus pe card: \n");
                                Integer nota = scanner.nextInt();
                                System.out.println("Ziua notarii: \n");
                                String ziua = scanner.next();
                                System.out.println("Luna notarii: \n");
                                String luna = scanner.next();
                                System.out.println("Anul notarii: \n");
                                String anul = scanner.next();
                                command.AddMarkToCard(disciplina, nota, ziua, luna, anul);
                            } else System.out.println("Niciun student nu s-a prezentat pentru primirea notei.");
                            break;
                        case 3:
                            if (!studentIDAfterValidation.equals("")) {
                                System.out.println("Alegeti disciplina la care doriti afisarea notelor de pe card: \n \tMatematica -- 10 \n \t Informatica -- 11 \n \t Biologie -- 12 \n \t Inginerie -- 13\n");
                                String disciplina = scanner.next();

                                db.fillArraysOfNoteFromSCA(command);
                                if (disciplina.equals("10"))
                                    System.out.println(db.mateSCA);
                                if (disciplina.equals("11"))
                                    System.out.println(db.infoSCA);
                                if (disciplina.equals("12"))
                                    System.out.println(db.bioSCA);
                                if (disciplina.equals("13"))
                                    System.out.println(db.inginerieSCA);
                            } else System.out.println("Studentul nu a fost selectat.\n");
                            break;

                        case 4:
                            System.out.println("Puneti o nota unui student care nu s-a prezentat:\n");
                            db.showAllStudentsAndIDs();
                            System.out.println("Introduceti ID-ul studentului:\n");
                            String studID = scanner.next();
                            //command.SelectCard(studID);
                            System.out.println("Denumirea materiei la care introduceti nota: \n");
                            String materieToAddGrade = scanner.next();
                            System.out.println("Alegeti disciplina la care doriti introducerea notei: \n \tMatematica -- 10 \n \t Informatica -- 11 \n \t Biologie -- 12 \n \t Inginerie -- 13\n");
                            String codMaterie = scanner.next();
                            System.out.println("Nota pe care doriti sa o introduceti: ");
                            int notaMaterie = scanner.nextInt();
                            System.out.println("Ziua notarii: \n");
                            String ziua = scanner.next();
                            System.out.println("Luna notarii: \n");
                            String luna = scanner.next();
                            System.out.println("Anul notarii: \n");
                            String anul = scanner.next();
                            System.out.println("Taxa de reexaminare platita( 1 sau 0 ): ");
                            int taxaPlatita = scanner.nextInt();

                            db.insertNotaForStudent(codMaterie, materieToAddGrade, notaMaterie, studID, ziua, luna, anul, taxaPlatita, command);
                        case 5:
                            if (!studentIDAfterValidation.equals("")) {
                                db.getNoteForEachMaterieFromDB(studentIDAfterValidation, command);
                                System.out.println("Selectati disciplinele pentru care doriti sa verificati notele:\n ");
                                Boolean doneSelecting = false;
                                ArrayList<String> selection = new ArrayList<>();
                                while (!doneSelecting) {
                                    System.out.println("Alegeti una din materii:\n  \n \tMatematica -- 10 \n \t Informatica -- 11 \n \t Biologie -- 12 \n \t Inginerie -- 13\n");
                                    String temp = scanner.next();
                                    if (temp.equals("quit"))
                                        doneSelecting = true;
                                    if (!temp.equals("quit"))
                                        selection.add(temp);
                                }
                                Boolean concursMate = true;
                                Boolean concursInfo = true;
                                Boolean concursBio = true;
                                Boolean concursInginerie = true;
                                for (String i : selection) {
                                    if (i.equals("10")) {
                                        for (int j = 0; j < db.mateSCA.size(); j += 4) {
                                            if (!db.mateSCA.get(j).equals("A"))
                                                if (Integer.parseInt(db.mateSCA.get(j)) <= 4)
                                                    concursMate = false;

                                        }
                                    }
                                    if (i.equals("11")) {
                                        for (int j = 0; j < db.infoSCA.size(); j += 4) {
                                            if (!db.infoSCA.get(j).equals("A"))
                                                if (Integer.parseInt(db.infoSCA.get(j)) <= 4)
                                                    concursInfo = false;

                                        }
                                    }
                                    if (i.equals("12")) {
                                        for (int j = 0; j < db.bioSCA.size(); j += 4) {
                                            if (!db.bioSCA.get(j).equals("A"))
                                                if (Integer.parseInt(db.bioSCA.get(j)) <= 4)
                                                    concursBio = false;

                                        }
                                    }
                                    if (i.equals("13")) {
                                        for (int j = 0; j < db.inginerieSCA.size(); j += 4) {
                                            if (!db.inginerieSCA.get(j).equals("A"))
                                                if (Integer.parseInt(db.inginerieSCA.get(j)) <= 4)
                                                    concursInginerie = false;

                                        }
                                    }
                                }
                                if (db.mateSCA.isEmpty()) concursMate = false;
                                if (db.infoSCA.isEmpty()) concursInfo = false;
                                if (db.bioSCA.isEmpty()) concursBio = false;
                                if (db.inginerieSCA.isEmpty()) concursInginerie = false;
                                if (selection.contains("10"))
                                    if (concursMate) System.out.println("Poate participa la MATE");
                                    else System.out.println("Nu poate participa la MATE");
                                if (selection.contains("11"))
                                    if (concursInfo) System.out.println("Poate participa la INFO");
                                    else System.out.println("Nu poate participa la INFO");
                                if (selection.contains("12"))
                                    if (concursBio) System.out.println("Poate participa la BIO");
                                    else System.out.println("Nu poate participa la BIO");
                                if (selection.contains("13"))
                                    if (concursInginerie) System.out.println("Poate participa la INGINERIE");
                                    else System.out.println("Nu poate participa la INGINERIE");


                            } else System.out.println("Studentul nu a validat pin-ul inca.");


                            break;

                        case 6:
                            if (!studentIDAfterValidation.equals("")) {
                                db.getNoteForEachMaterieFromDB(studentIDAfterValidation, command);
                                Boolean doneSelecting = false;
                                ArrayList<String> selection = new ArrayList<>();

                                for (int i = 0; i < db.mateDB.size() - 3; i += 4) {
                                    String var = db.mateDB.get(i);
                                    String ziua_t = db.mateDB.get(i + 1);
                                    String luna_t = db.mateDB.get(i + 2);
                                    String anul_t = db.mateDB.get(i + 3);
                                    Boolean isInCard = false;
                                    if (db.mateSCA.size() > 0) {
                                        for (int j = 0; j < db.mateSCA.size() - 3; j += 4) {
                                            String var2 = String.valueOf(Integer.parseInt(db.mateSCA.get(j).trim(), 16));
                                            String ziua_t2 = String.valueOf(Integer.parseInt(db.mateSCA.get(j+1).trim(), 16));
                                            String luna_t2 = String.valueOf(Integer.parseInt(db.mateSCA.get(j+2).trim(), 16));
                                            String anul_t2 = String.valueOf(Integer.parseInt(db.mateSCA.get(j+3).trim(), 16));
                                            System.out.println(var + var2);
                                            if (var.equals(var2)&&ziua_t.equals(ziua_t2)&& luna_t.equals(luna_t2) && anul_t.equals(anul_t2)) {
                                                isInCard = true;
                                            }
                                        }
                                        if (isInCard == false) {
                                            System.out.println("Nota " + var + "nu e pe card.");
                                            command.AddMarkToCard("10",Integer.parseInt(var),ziua_t,luna_t,anul_t);
                                        }
                                    }
                                    else{
                                        System.out.println("Nota " + var + "nu e pe card.");
                                        command.AddMarkToCard("10",Integer.parseInt(var),ziua_t,luna_t,anul_t);
                                    }
                                }
                                for (int i = 0; i < db.infoDB.size() - 3; i += 4) {
                                    String var = db.infoDB.get(i);
                                    String ziua_t = db.infoDB.get(i + 1);
                                    String luna_t = db.infoDB.get(i + 2);
                                    String anul_t = db.infoDB.get(i + 3);
                                    Boolean isInCard = false;
                                    if (db.infoSCA.size() > 0) {
                                        for (int j = 0; j < db.infoSCA.size() - 3; j += 4) {
                                            String var2 = String.valueOf(Integer.parseInt(db.infoSCA.get(j).trim(), 16));
                                            String ziua_t2 = String.valueOf(Integer.parseInt(db.infoSCA.get(j+1).trim(), 16));
                                            String luna_t2 = String.valueOf(Integer.parseInt(db.infoSCA.get(j+2).trim(), 16));
                                            String anul_t2 = String.valueOf(Integer.parseInt(db.infoSCA.get(j+3).trim(), 16));
                                            System.out.println(var + var2);
                                            if (var.equals(var2)&&ziua_t.equals(ziua_t2)&& luna_t.equals(luna_t2) && anul_t.equals(anul_t2)) {
                                                isInCard = true;
                                            }
                                        }
                                        if (isInCard == false) {
                                            System.out.println("Nota " + var + "nu e pe card.");
                                            command.AddMarkToCard("10",Integer.parseInt(var),ziua_t,luna_t,anul_t);
                                        }
                                    }
                                    else{
                                        System.out.println("Nota " + var + "nu e pe card.");
                                        command.AddMarkToCard("10",Integer.parseInt(var),ziua_t,luna_t,anul_t);
                                    }
                                }
                                for (int i = 0; i < db.bioDB.size() - 3; i += 4) {
                                    String var = db.bioDB.get(i);
                                    String ziua_t = db.bioDB.get(i + 1);
                                    String luna_t = db.bioDB.get(i + 2);
                                    String anul_t = db.bioDB.get(i + 3);
                                    Boolean isInCard = false;
                                    if (db.bioSCA.size() > 0) {
                                        for (int j = 0; j < db.bioSCA.size() - 3; j += 4) {
                                            String var2 = String.valueOf(Integer.parseInt(db.bioSCA.get(j).trim(), 16));
                                            String ziua_t2 = String.valueOf(Integer.parseInt(db.bioSCA.get(j+1).trim(), 16));
                                            String luna_t2 = String.valueOf(Integer.parseInt(db.bioSCA.get(j+2).trim(), 16));
                                            String anul_t2 = String.valueOf(Integer.parseInt(db.bioSCA.get(j+3).trim(), 16));
                                            System.out.println(var + var2);
                                            if (var.equals(var2)&&ziua_t.equals(ziua_t2)&& luna_t.equals(luna_t2) && anul_t.equals(anul_t2)) {
                                                isInCard = true;
                                            }
                                        }
                                        if (isInCard == false) {
                                            System.out.println("Nota " + var + "nu e pe card.");
                                            command.AddMarkToCard("10",Integer.parseInt(var),ziua_t,luna_t,anul_t);
                                        }
                                    }
                                    else{
                                        System.out.println("Nota " + var + "nu e pe card.");
                                        command.AddMarkToCard("10",Integer.parseInt(var),ziua_t,luna_t,anul_t);
                                    }
                                }
                                for (int i = 0; i < db.inginerieDB.size() - 3; i += 4) {
                                    String var = db.inginerieDB.get(i);
                                    String ziua_t = db.inginerieDB.get(i + 1);
                                    String luna_t = db.inginerieDB.get(i + 2);
                                    String anul_t = db.inginerieDB.get(i + 3);
                                    Boolean isInCard = false;
                                    if (db.inginerieSCA.size() > 0) {
                                        for (int j = 0; j < db.inginerieSCA.size() - 3; j += 4) {
                                            String var2 = String.valueOf(Integer.parseInt(db.inginerieSCA.get(j).trim(), 16));
                                            String ziua_t2 = String.valueOf(Integer.parseInt(db.inginerieSCA.get(j+1).trim(), 16));
                                            String luna_t2 = String.valueOf(Integer.parseInt(db.inginerieSCA.get(j+2).trim(), 16));
                                            String anul_t2 = String.valueOf(Integer.parseInt(db.inginerieSCA.get(j+3).trim(), 16));
                                            System.out.println(var + var2);
                                            if (var.equals(var2)&&ziua_t.equals(ziua_t2)&& luna_t.equals(luna_t2) && anul_t.equals(anul_t2)) {
                                                isInCard = true;
                                            }
                                        }
                                        if (isInCard == false) {
                                            System.out.println("Nota " + var + "nu e pe card.");
                                            command.AddMarkToCard("10",Integer.parseInt(var),ziua_t,luna_t,anul_t);
                                        }
                                    }
                                    else{
                                        System.out.println("Nota " + var + "nu e pe card.");
                                        command.AddMarkToCard("10",Integer.parseInt(var),ziua_t,luna_t,anul_t);
                                    }
                                }


                            } else System.out.println("Studentul nu a validat pin-ul inca.");

                            break;

                        default:
                            break;
                    }
                    break;

                case 2:
                    System.out.println("STUDENT");
                    String pin = "";
                    String studentID = "";
                    boolean cardSelected = false;
                    while (!cardSelected) {
                        System.out.println("Insert student ID: ");
                        studentID = scanner.next();
                        if (studentID.equals("6000") || studentID.equals("5778")) {
                            cardSelected = command.SelectCard(studentID);
                        }
                    }
                    if (cardSelected) {
                        studentIDAfterValidation = studentID;
                        int correctPin = 0;
                        while (correctPin == 0) {
                            System.out.println("student\n Please insert PIN.");
                            pin = scanner.next();
                            if (command.PinVerification(pin)) {
                                correctPin = 1;
                            }

                        }
                    }


                    break;

                case 10:
                    System.out.println("Bye!");
                    quit = true;
                    break;

                default:
                    System.out.println("Not a good command");
                    break;
            }
        }


    }
}


