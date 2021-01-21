package ru.job4j.dream.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Connection, which rollback all commits.
 * It is used for integration test.
 */
final class ConnectionRollback {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionRollback.class);
    private static String fileDb = "db.properties";

    /**
     * Create connection with autocommit=false mode and rollback call, when conneciton is closed.
     *
     * @param connection connection.
     * @return Connection object.
     */
    public static Connection create(final Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        return (Connection) Proxy.newProxyInstance(
                ConnectionRollback.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    Object rsl = null;
                    //LOG.info("Connection ......{}", method.getName());
                    if ("close".equals(method.getName())) {
                        return rsl;
                        //connection.rollback();
                        //rsl = method.invoke(connection, args);
                    } else if ("commit".equals(method.getName())) {
                        LOG.info("***    NOT commit !!! ***");
                    } else if ("prepareStatement".equals(method.getName())) {
                        rsl = method.invoke(connection, args);
                    } else {
                        rsl = method.invoke(connection, args);
                    }
                    return rsl;
                }
        );
    }

    public static Connection init() {
        Connection cn = null;
        try (FileInputStream in = new FileInputStream(fileDb)) {
            Properties cfg = new Properties();
            cfg.load(in);
            Class.forName(cfg.getProperty("jdbc.driver"));
            cn = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password"));
        } catch (IOException | ClassNotFoundException | SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return cn;
    }
}
