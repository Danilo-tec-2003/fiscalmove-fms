package br.com.fiscalmove.nucleo;

import br.com.fiscalmove.nucleo.utils.ConexaoUtil;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/** Inicializa o pool de conexões DBCP2 ao subir a aplicação. */
@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(AppContextListener.class.getName());

    private BasicDataSource bds;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        try {
            Properties props = new Properties();
            loadPropertiesIfPresent(ctx, props);

            String dbUrl = required(
                firstNonBlank(System.getenv("DB_URL"), props.getProperty("db.url")),
                "DB_URL ou db.url"
            );
            String dbUser = required(
                firstNonBlank(System.getenv("DB_USER"), props.getProperty("db.user")),
                "DB_USER ou db.user"
            );
            String dbPassword = required(
                firstNonBlank(System.getenv("DB_PASSWORD"), props.getProperty("db.password")),
                "DB_PASSWORD ou db.password"
            );

            bds = new BasicDataSource();
            bds.setDriverClassName(firstNonBlank(
                System.getenv("DB_DRIVER"),
                props.getProperty("db.driver"),
                "org.postgresql.Driver"
            ));
            bds.setUrl(dbUrl);
            bds.setUsername(dbUser);
            bds.setPassword(dbPassword);
            bds.setMinIdle(parseInt(firstNonBlank(
                System.getenv("DB_POOL_MIN"),
                props.getProperty("db.pool.min"),
                "2"
            ), 2));
            bds.setMaxTotal(parseInt(firstNonBlank(
                System.getenv("DB_POOL_MAX"),
                props.getProperty("db.pool.max"),
                "10"
            ), 10));
            bds.setTestOnBorrow(true);
            bds.setValidationQuery("SELECT 1");

            ConexaoUtil.setDataSource(bds);
            LOG.info("Pool DBCP2 inicializado com sucesso. URL: " + bds.getUrl());

        } catch (Exception e) {
            LOG.severe("FALHA ao inicializar pool de conexões: " + e.getMessage());
            throw new RuntimeException("Falha ao inicializar DataSource", e);
        }
    }

    private void loadPropertiesIfPresent(ServletContext ctx, Properties props) throws Exception {
        try (InputStream runtimeProps = ctx.getResourceAsStream("/WEB-INF/classes/db.properties")) {
            if (runtimeProps != null) {
                props.load(runtimeProps);
                return;
            }
        }

        try (InputStream classpathProps = getClass().getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (classpathProps != null) {
                props.load(classpathProps);
            }
        }
    }

    private String required(String value, String name) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Configuração obrigatória ausente: " + name);
        }
        return value.trim();
    }

    private String firstNonBlank(String first, String second) {
        return firstNonBlank(first, second, null);
    }

    private String firstNonBlank(String first, String second, String fallback) {
        if (first != null && !first.trim().isEmpty()) return first.trim();
        if (second != null && !second.trim().isEmpty()) return second.trim();
        return fallback;
    }

    private int parseInt(String value, int fallback) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return fallback;
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            if (bds != null) {
                bds.close();
                LOG.info("Pool DBCP2 encerrado.");
            }
        } catch (Exception e) {
            LOG.warning("Erro ao fechar pool: " + e.getMessage());
        }
    }
}
