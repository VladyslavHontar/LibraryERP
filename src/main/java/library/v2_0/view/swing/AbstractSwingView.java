package library.v2_0.view.swing;

import library.v2_0.infrastructure.Dispatcher;
import library.v2_0.view.View;
import lombok.RequiredArgsConstructor;
import javax.swing.*;

@RequiredArgsConstructor
public abstract class AbstractSwingView implements View {

  protected final JFrame frame;

  protected final Dispatcher dispatcher;
}
