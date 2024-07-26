package com.qnenet.quickneasy.system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Security;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.qnenet.quickneasy.QNEPaths;

import jakarta.annotation.PostConstruct;

@Service
public class QSystem {

    private Environment env;

    // @Autowired
    // private ObjectFactory<QKnown> knownFactory;

    // private QKnown known;

    // private QNEObjects objs;

    public static ExecutorService executor;

    // HashMap<String, Object> geoMap;
    private ConcurrentHashMap<String, Object> sysprops;

    // private KeyStore keyStore;

    public static boolean isNew;

    public QSystem(Environment env) throws IOException {
        this.env = env;
        /*
         * objs = QuickNEasyApp.getObjs();
         * known = knownFactory.getObject(); */ 
        Security.addProvider(new BouncyCastleProvider());
        executor = Executors.newVirtualThreadPerTaskExecutor();

        Path pidPath = Paths.get(QNEPaths.getSystemPath().toString(), "qne.pid");
        // Path geoPath = Paths.get(QNEPaths.getSystemPath().toString(), "geo");
        if (Files.exists(pidPath)) {
            isNew = false;
        } else {
            Files.createDirectories(pidPath.getParent());
            isNew = true;
        }
        Files.writeString(pidPath, String.valueOf(ProcessHandle.current().pid()));

    }

    @PostConstruct
    public void init() {

        System.out.println("QSystem.init()");
        if (isNew) {
            newSystem();
        } else {
            restart();
        }
    }

    private void newSystem() {

    /*     sysprops = new ConcurrentHashMap<>();
        sysprops.put(QSysConstants.WAN_IP_ADDRESS, geoMap.get("TraitsIpAddress"));
        sysprops.put(QSysConstants.LAN_IP_ADDRESS, QNetworkUtils.getLanIPAddress());
        sysprops.put(QSysConstants.PORT, env.getProperty(QSysConstants.PORT));

        // objs.saveObjectToFile(sysprops, QNEPaths.getSystemPropsFilePath());

        // 127.0.0.1:11113-0
        String discoveryServerStr = env.getProperty(QSysConstants.DISCOVERY_SERVER);
        QEPAddr discoveryServerEPAddr = new QEPAddr(discoveryServerStr);
 */
    }

    // @SuppressWarnings("unchecked")
    private void restart() {
        // executor.submit(() -> {
        //     geoMap = (HashMap<String, Object>) objs.loadObjectFromFile(QNEPaths.getGeoIPMapFilePath());
        //     sysprops = (ConcurrentHashMap<String, Object>) objs.loadObjectFromFile(QNEPaths.getSystemPropsFilePath());
        // });
    }

    // private void lookForOtherNodesOnLan() {
    // throw new UnsupportedOperationException("Unimplemented method
    // 'lookForOtherNodesOnLan'");
    // }

    

}
