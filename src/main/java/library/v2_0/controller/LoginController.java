package library.v2_0.controller;

import library.v2_0.domain.User;
import library.v2_0.exceptions.AuthenticationException;
import library.v2_0.infrastructure.Model;
import library.v2_0.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class LoginController implements Controller {

  private final UserService userService;

  @Override
  public String handle(Model inModel, Model outModel) {
    String username = inModel.get("username");
    String password = inModel.get("password");

    try {
      User authenticatedUser = userService.authenticate(username, password);
      outModel.put("user", authenticatedUser);
      switch (authenticatedUser.getType()) {
        case ADMIN:
          return "admin_view";

        case CLIENT:
          return "user_view";

        case MANAGER:
          return "manager_view";
      }
    } catch (NoSuchElementException | AuthenticationException ex) {
      outModel.put("failedAuthentication", true);
    }
    return "login_view";
  }
}
