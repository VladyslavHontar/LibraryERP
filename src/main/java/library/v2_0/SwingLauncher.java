package library.v2_0;

import library.v2_0.controller.AdminController;
import library.v2_0.controller.LoginController;
import library.v2_0.infrastructure.Dispatcher;
import library.v2_0.infrastructure.Model;
import library.v2_0.repository.jdbc.DataSourceFactory;
import library.v2_0.repository.jdbc.DataSource;
import library.v2_0.repository.jdbc.JdbcUserRepository;
import library.v2_0.service.UserService;
import library.v2_0.view.swing.SwingAdminView;
import library.v2_0.view.swing.SwingLoginView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SwingLauncher {

  public static void main(String[] args) throws IOException {
    DataSourceFactory dataSourceFactory = new DataSourceFactory();
    DataSource dataSource = dataSourceFactory.create();
    JdbcUserRepository userRepository = new JdbcUserRepository(dataSource);

    UserService userService = new UserService(userRepository);

    Dispatcher dispatcher = new Dispatcher();
    dispatcher.addController("login_controller", new LoginController(userService));
    dispatcher.addController("admin_controller", new AdminController(userService));

    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Library ERP");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      dispatcher.addView("login_view", new SwingLoginView(frame, dispatcher));
      dispatcher.addView("admin_view", new SwingAdminView(frame, dispatcher));

      frame.setPreferredSize(new Dimension(1000, 800));

      frame.pack();
      frame.setVisible(true);

      dispatcher.dispatchResponse("login_view", new Model());
    });
  }
}
