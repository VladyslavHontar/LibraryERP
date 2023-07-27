package library.v2_0.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModelAndTarget {

  private final Model model;
  private final String targetName;
}
