package persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class DBServer {
    private final String HOST;
    private ServerSocket serverSocket;
    private static final Logger log = LoggerFactory.getLogger(DBServer.class);

    public DBServer(String host) {
        HOST = host;
    }

    public void run() {
        log.info("DBServer 실행");
        try {
            log.debug("서버 소켓 연결 시작");
            InetAddress ir = InetAddress.getByName(HOST);
            serverSocket = new ServerSocket(5000, 50, ir);
            log.debug("서버 소켓 연결 성공");

            while(true) {
                log.info("클라이언트 접속 대기 중");
                Socket socket = serverSocket.accept();
                DBServerThread runnableObj = new DBServerThread(socket);
                Thread DBThread = new Thread(runnableObj);
                DBThread.start();
                log.info("클라이언트 접속 감지");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
