import java.util.ArrayList;
import java.util.Arrays;

public class CardFunctions {

    JavaCardHostApp host = new JavaCardHostApp();
    Database db = new Database();

    final static byte Wallet_CLA =(byte)0x80;
    final static byte VERIFY = (byte) 0x20;
    final static byte ADD_MARK = (byte) 0x30;
    final static byte GET_MARK = (byte) 0x40;
    final static byte DEBIT = (byte) 0x40;
    final static byte GET_BALANCE = (byte) 0x50;
    final static byte UNBLOCK = (byte) 0x60;
    static byte counter;

    public void ConnectToCard(){
        host.establishConnectionToSimulator();
        host.pwrUp();
    }


    public boolean SelectCard(String studentID){
        byte lengthDataField = 10;
        byte maxDataBytesExpected = 0;
        byte instParamtr1 = 0x04;
        byte instParamtr2 = 0;
        boolean cardSelected = false;
        //select
        byte[] cmnds = {(byte)0x00, (byte)0xA4, instParamtr1, instParamtr2};
        host.setTheAPDUCommands(cmnds);
        host.setTheDataLength(lengthDataField);
        if(studentID.equals("6000")) {
            byte[] data = {(byte) 0xa0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x62, (byte) 0x03, (byte) 0x01, (byte) 0xc, (byte) 0x6, (byte) 0x01};
            host.setTheDataIn(data);
            host.setExpctdByteLength(maxDataBytesExpected);
            host.exchangeTheAPDUWithSimulator();
            byte[] temp = host.decodeStatus();
            if(temp[0] == (byte) 0x90) {
                System.out.println("Conn Done!- stud 6000(1792)");
                return true;
            }
            else return false;
        }
        if(studentID.equals("5778")) {
            byte[] data = {(byte) 0xa0, (byte) 0x0, (byte) 0x0, (byte) 0x0, (byte) 0x62, (byte) 0x03, (byte) 0x01, (byte) 0xc, (byte) 0x6, (byte) 0x20};
            host.setTheDataIn(data);
            host.setExpctdByteLength(maxDataBytesExpected);
            host.exchangeTheAPDUWithSimulator();
            byte[] temp = host.decodeStatus();
            if(temp[0] == (byte) 0x90) {
                System.out.println("Conn Done- stud 5778(1692)");
                return true;

            }
            else return false;
        }
        return false;
    }


    public void AddMarkToCard(String disc, int not, String zi, String lu, String an){
        int temp = Integer.parseInt(disc);
        byte disciplina = (byte) temp;
        byte nota = (byte) not;
        temp = Integer.parseInt(zi);
        byte ziua = (byte) temp;
        temp = Integer.parseInt(lu);
        byte luna = (byte) temp;
        temp = Integer.parseInt(an);
        byte anul = (byte) temp;


        byte lengthDataField = 5;
        byte maxDataBytesExpected = 2;
        byte instParamtr1 = 0;
        byte instParamtr2 = 0;
        byte[] cmnds_c = {Wallet_CLA, ADD_MARK, instParamtr1, instParamtr2};
        host.setTheAPDUCommands(cmnds_c);
        host.setTheDataLength(lengthDataField);
        byte[] data_c = {disciplina, nota, ziua, luna, anul};
        host.setTheDataIn(data_c);
        host.setExpctdByteLength(maxDataBytesExpected);
        host.exchangeTheAPDUWithSimulator();
        host.decodeStatus();
        //host.getResponseFromAPDU();

    }

    public ArrayList<String> GetMarkFromCard(String codDisc){
        int temp = Integer.parseInt(codDisc);
        byte codDisciplina = (byte) temp;

        byte lengthDataField = 1;
        byte maxDataBytesExpected = 4;
        byte instParamtr1 = 0;
        byte instParamtr2 = 0;
        byte[] cmnds_c = {Wallet_CLA, GET_MARK, instParamtr1, instParamtr2};
        host.setTheAPDUCommands(cmnds_c);
        host.setTheDataLength(lengthDataField);
        byte[] data_c = {codDisciplina};
        host.setTheDataIn(data_c);
        host.setExpctdByteLength(maxDataBytesExpected);
        host.exchangeTheAPDUWithSimulator();
        host.decodeStatus();
        byte[] resp = host.getResponseFromAPDU(codDisc);
        return host.note;


    }

    public boolean PinVerification(String pin){
        byte lengthDataField = (byte) pin.length();
        byte maxDataBytesExpected = 0;
        byte instParamtr1 = 0;
        byte instParamtr2 = 0;

        char[] ip = pin.toCharArray();
        byte[] data_v = new byte[ip.length];


        byte[] cmnds_v = {Wallet_CLA, VERIFY, instParamtr1, instParamtr2};
        host.setTheAPDUCommands(cmnds_v);
        host.setTheDataLength(lengthDataField);

        for(int ipIndx = 0; ipIndx<ip.length;ipIndx++){
            data_v[ipIndx] = (byte)(ip[ipIndx]&0x0F);
        }
        host.setTheDataIn(data_v);
        host.setExpctdByteLength(maxDataBytesExpected);
        host.exchangeTheAPDUWithSimulator();
        byte[] stat = host.decodeStatus();
        if(stat[0] == (byte) 0x90){
            return true;
        }
        else return false;

    }
}
