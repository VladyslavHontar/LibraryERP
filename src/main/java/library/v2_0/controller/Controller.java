package library.v2_0.controller;

import library.v2_0.infrastructure.Model;

public interface Controller {

  String handle(Model inModel, Model outModel);
}
