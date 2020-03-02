// Copyright © 2012-2020 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.lattice.model.process;

import io.vlingo.common.Completes;

public class FiveStepEmittingStatefulProcess extends StatefulProcess<StepCountState> implements FiveStepProcess {
  private final Chronicle<StepCountState> chronicle;
  private StepCountState state;

  public FiveStepEmittingStatefulProcess() {
    this.state = new StepCountState();
    this.chronicle = new Chronicle<>(state);
  }

  @Override
  public Chronicle<StepCountState> chronicle() {
    return chronicle;
  }

  @Override
  public Completes<Integer> queryStepCount() {
    return completes().with(state.stepCount());
  }

  @Override
  public void stepOneHappened() {
    state.countStep();
    process(new DoStepTwo());
  }

  @Override
  public void stepTwoHappened() {
    state.countStep();
    process(new DoStepThree());
  }

  @Override
  public void stepThreeHappened() {
    state.countStep();
    process(new DoStepFour());
  }

  @Override
  public void stepFourHappened() {
    state.countStep();
    process(new DoStepFive());
  }

  @Override
  public void stepFiveHappened() {
    state.countStep();
  }

  @Override
  public String id() {
    return "12345";
  }

  @Override
  protected void state(final StepCountState state) {
    this.state = state;
  }

  @Override
  protected Class<StepCountState> stateType() {
    return StepCountState.class;
  }

  @Override
  public String provideRelocationSnapshot() {
    return String.valueOf(state.stepCount());
  }

  @Override
  public void applyRelocationSnapshot(String snapshot) {
    state(new StepCountState(Integer.parseInt(snapshot)));
  }
}
