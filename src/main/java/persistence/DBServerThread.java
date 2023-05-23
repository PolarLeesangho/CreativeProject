package persistence;

import Control.DBControl;
import com.opencsv.exceptions.CsvException;
import persistence.DTO.DTO;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class DBServerThread implements Runnable {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public DBServerThread(Socket socket) {
        this.socket = socket;
        try {
            oos = new ObjectOutputStream(this.socket.getOutputStream());
            ois = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Protocol<File> protocol;

        try {
            while(true) {
                protocol = (Protocol<File>) ois.readObject();
                execute(protocol);
            }
        } catch (SocketException e) {
            //pass
        } catch (IOException | ClassNotFoundException | InvalidNameFormatException | CsvException e) {
            e.printStackTrace();
        }

    }

    private void execute(Protocol<File> protocol) throws InvalidNameFormatException, IOException, CsvException {
        DBControl control = new DBControl();

        if (protocol.getQUERY() == ProtocolQuery.insert) {
            control.DBUpdateRequest(protocol.getDATA());
        } else {
            Protocol<List<DTO>> responseProtocol;
            List<DTO> data = null;

            if (protocol.getQUERY() == ProtocolQuery.selectAll) {
                data = control.selectAllRequest(protocol);
            } else if (protocol.getQUERY() == ProtocolQuery.selectOrderByMonth) {
                data = control.selectOrderByMonthRequest(protocol);
            } else if (protocol.getQUERY() == ProtocolQuery.selectOrderByDongName) {
                data = control.selectOrderByDongNameRequest(protocol);
            } else if (protocol.getQUERY() == ProtocolQuery.selectOrderByIndustryCode) {
                data = control.selectOrderByIndustryCodeRequest(protocol);
            } else if (protocol.getQUERY() == ProtocolQuery.selectOrderByAmount) {
                data = control.selectOrderByAmountRequest(protocol);
            } else if (protocol.getQUERY() == ProtocolQuery.selectOrderByDFP) {
                data = control.selectOrderByDFPRequest();
            }

            responseProtocol = new Protocol<>(ProtocolQuery.response, ProtocolType.response, data);
            oos.writeObject(responseProtocol);
        }
    }
}
