// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.lattice.model.projection;

import java.util.Collection;

import io.vlingo.symbio.Entry;
import io.vlingo.symbio.State;

public class TextProjectable extends AbstractProjectable {
  public TextProjectable(final State<String> state, final Collection<Entry<?>> entries, final String projectionId) {
    super(state, entries, projectionId);
  }

  @Override
  public String dataAsText() {
    return textState().data;
  }
}
