package library.v2_0.controller;

import library.v2_0.domain.User;
import library.v2_0.infrastructure.Model;
import library.v2_0.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class AdminController implements Controller {

  private final UserService userService;

  @Override
  public String handle(Model inModel, Model outModel) {
    User admin = inModel.get("user");
    String action = inModel.get("action");

    if ("logout".equals(action)) {
      return "login_view";
    }

    switch (action) {
      case "showUsers":
        outModel.put("response", "showUsers");
        outModel.put("users", userService.getUsers());
        break;

      case "addUser":
        String username = inModel.get("username");
        char[] password = inModel.get("password");
        User.Type type = User.Type.valueOf(inModel.get("type"));

        try {
          User newUser = userService.add(admin, username, password, type);
          outModel.put("newUser", newUser);
        } catch (IllegalStateException ex) {
          outModel.put("error", "Username already taken by another client!");
        }
        outModel.put("response", "addUser");
        break;

      case "deleteUser":
        Long userId = inModel.get("userId");
        try {
          User deletedUser = userService.delete(admin, userId);
          outModel.put("deletedUser", deletedUser);
        } catch (NoSuchElementException ex) {
          outModel.put("error", "incorrect UserId!");
        } catch (IllegalArgumentException ex) {
          outModel.put("error", "admin cannot delete himself!");
        }
        outModel.put("response", "deleteUser");
    }
    return "admin_view";
  }


}
