package library.v2_0.view;

import library.v2_0.infrastructure.Model;
import library.v2_0.terminal.Terminal;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class TerminalLoginView implements View {

  private final Terminal terminal;

  @Override
  public String update(Model inModel, Model outModel) {
    Optional<Boolean> failedAuthentication = inModel.tryGet("failedAuthentication");
    if (failedAuthentication.orElse(false)) {
      terminal.println("# Failed to login, please try again!");
      terminal.println("# ");
    }

    terminal.println("# Please enter your username: ");
    terminal.print("> ");
    String username = terminal.readLine();
    outModel.put("username", username);

    terminal.println("# Please enter your password: ");
    terminal.print("> ");
    String password = terminal.readLine();
    outModel.put("password", password);

    return "login_controller";
  }
}