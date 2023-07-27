package library.v2_0.view;

import library.v2_0.infrastructure.Model;

public interface View {

  String update(Model inModel, Model outModel);
}
