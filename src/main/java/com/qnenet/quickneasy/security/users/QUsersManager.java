package com.qnenet.quickneasy.security.users;


import org.springframework.stereotype.Service;

import com.qnenet.qneobjects.api.QNEObjects;
import com.qnenet.qneobjects.classes.QLoginUser;
import com.qnenet.quickneasy.QNEPaths;
import com.qnenet.quickneasy.system.QSystem;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class QUsersManager {

//    private final List<RouteData> allAppRoutes;
    private QSystem system;
    private final QNEPaths qnePaths;
    private final QNEObjects qobjs;
    private Path usersMapFilePath;

    ConcurrentHashMap<String, QLoginUser> users;

    public QUsersManager(QNEPaths qnePaths, QNEObjects qobjs) {
//        this.system = system;
        this.qnePaths = qnePaths;
        this.qobjs = qobjs;
    }

    public void init(QSystem system) {
        this.system = system;
        usersMapFilePath = Paths.get(qnePaths.getSystemPath().toString(), "users.map");
        if (Files.notExists(usersMapFilePath)) {
            newSystem();
        } else {
            restart();
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////
/////////// New System ////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

    private void newSystem() {
        users = new ConcurrentHashMap<>();

        QLoginUser newUser = new QLoginUser();
        newUser.setUserName("paul");
        newUser.setPwd(new char[]{'p','a','u','l'});
        newUser.addRoles("ADMIN");
        addUser(newUser, false);

        newUser = new QLoginUser();
        newUser.setUserName("barb");
        newUser.setPwd(new char[]{'b','a','r','b'});
        newUser.addRoles("ADMIN");
        addUser(newUser, true);

        saveUsers();

    }

    private void addUser(QLoginUser newUser, boolean save) {
        users.put(newUser.getUsername(), newUser);
        if (save) saveUsers();
    }

///////////////////////////////////////////////////////////////////////////////////////////////////
/////////// Restart ///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

    private void restart() {
        loadUsers();
    }


///////////////////////////////////////////////////////////////////////////////////////////////////
/////////// Getters & Setters /////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

    public QLoginUser addUser(String userName, char[] password, boolean save, String... roles) {
        QLoginUser user = new QLoginUser();
        user.setUserName(userName);
        user.setPwd(password);
        user.setAccountIsCurrent(true);
        user.setCredentialIsCurrent(true);
        user.setAccountIsEnabled(true);
        user.setAccountIsNotLocked(true);

        user.addRoles(roles);

        users.put(userName, user);
        if (save) saveUsers();
        return user;
    }


    public QLoginUser findByUsername(String userName) {
        return users.get(userName);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////
/////////// Persistence ///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

    // private void saveUsers() {
    //     qobjs.saveObjToEncFile(usersMapFilePath, users, system.getBigEncryptor());
    // }

    // private void loadUsers() {
    //     users = (ConcurrentHashMap<String, QLoginUser>)
    //             qobjs.loadObjFromEncFile(usersMapFilePath, system.getBigEncryptor());
    // }

    private void saveUsers() {
        qobjs.saveObjectToFile(users, usersMapFilePath);
    }

    @SuppressWarnings("unchecked")
    private void loadUsers() {
        users = (ConcurrentHashMap<String, QLoginUser>)
                qobjs.loadObjectFromFile(usersMapFilePath);
    }



///////////////////////////////////////////////////////////////////////////////////////////////////
} /////////// End Class ///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////


