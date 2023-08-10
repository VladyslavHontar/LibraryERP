package library.v2_0.repository.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceFactory {

  public DataSource create() throws IOException {
    Properties dbProperties = new Properties();
    try (InputStream inputStream = DataSourceFactory.class.getResourceAsStream("/db.properties")) {
      dbProperties.load(inputStream);
    }
    String url = dbProperties.getProperty("url");
    String user = dbProperties.getProperty("user");
    String password = dbProperties.getProperty("password");

    return new DataSource(url, user, password);
  }
}
