import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.sun.javacard.apduio.*;

import javax.xml.crypto.Data;


public class JavaCardHostApp {

    private Socket sock;
    private OutputStream os;
    private InputStream is;
    private Apdu apdu;
    private CadClientInterface cad;
    public ArrayList<String> note = new ArrayList<>();
    //Database db = new Database();




    public JavaCardHostApp() {
        apdu = new Apdu();
    }

    public void establishConnectionToSimulator() {
        try {
            //prgramm socket for the connection with simulator
            sock = new Socket("localhost", 9025);
            os = sock.getOutputStream();
            is = sock.getInputStream();
            //Initialize the instance card acceptance device
            cad = CadDevice.getCadClientInstance(CadDevice.PROTOCOL_T0, is, os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void closeConnection() {
        try {
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pwrUp() {
        try {
            if (cad != null) {
                cad.powerUp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pwrDown() {
        try {
            if (cad != null) {
                //power down the card
                cad.powerDown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTheAPDUCommands(byte[] cmnds) {
        if (cmnds.length > 4 || cmnds.length == 0) {
            System.err.println("inavlid commands");
        } else {
            //set the APDU header
            apdu.command = cmnds;
        }
    }

    public void setTheDataLength(byte ln) {
        //set the length of the data command
        apdu.Lc = ln;
    }

    public void setTheDataIn(byte[] data) {
        if (data.length != apdu.Lc) {
            System.err.println("The number of data in the array are more than expected");
        } else {
            //set the data to be sent to the applets
            apdu.dataIn = data;
        }
    }

    public void setExpctdByteLength(byte ln) {
        //expected length of the data in the response APDU
        apdu.Le = ln;
    }

    public void exchangeTheAPDUWithSimulator() {

        try {
            //Exchange the APDUs
            apdu.setDataIn(apdu.dataIn, apdu.Lc);
            cad.exchangeApdu(apdu);
            System.out.println(apdu);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public byte[] decodeStatus() {
        byte[] statByte = apdu.getSw1Sw2();
        return statByte;
    }

    public byte[] getResponseFromAPDU(String codDisciplina){
        byte[] resp = apdu.getDataOut();
        note.clear();
        for(int i =0; i < resp.length; i++){
            String temporary1 = atrToHex(resp[i]);
            if(!temporary1.equals("0")){
                note.add(temporary1);
            }}
        return  resp;

    }



    public String atrToHex(byte atCode) {
        char hex[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        String str2 = "";
        int num = atCode & 0xff;
        int rem;
        while (num > 0) {
            rem = num % 16;
            str2 = hex[rem] + str2;
            num = num / 16;
        }
        if (str2 != "") {
            return str2;
        } else {
            return "0";
        }
    }

}
