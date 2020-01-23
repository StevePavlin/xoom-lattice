package io.vlingo.lattice.grid.application.message;

import java.io.Serializable;

public class Answer implements Serializable, Message {
  private static final long serialVersionUID = -2796142731077588067L;

  public Answer() { }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
