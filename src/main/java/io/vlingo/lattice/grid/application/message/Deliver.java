package io.vlingo.lattice.grid.application.message;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

import io.vlingo.actors.Address;
import io.vlingo.actors.Definition;
import io.vlingo.actors.LocalMessage;
import io.vlingo.actors.Returns;
import io.vlingo.common.SerializableConsumer;
import io.vlingo.wire.node.Id;

public class Deliver<T> implements Serializable, Message {
  private static final long serialVersionUID = 591702431591762704L;

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static Function<io.vlingo.actors.Message, Deliver<?>> from(BiConsumer<UUID, Returns<?>> correlation) {
    return (message) -> {
      final LocalMessage<?> __message = (LocalMessage<?>) message;
      final Optional<Returns<?>> returns = Optional.ofNullable(__message.returns());

      final UUID answerCorrelationId = returns.map(_return -> {
        final UUID correlationId = UUID.randomUUID();
        correlation.accept(correlationId, _return);
        return correlationId;
      }).orElse(null);

      return new Deliver(
          __message.protocol(),
          __message.actor().address(),
          Definition.SerializationProxy.from(__message.actor().definition()),
          __message.consumer(),
          answerCorrelationId,
          __message.representation());
    };
  }

  public final Class<T> protocol;
  public final Address address;
  public final Definition.SerializationProxy definition;
  public final SerializableConsumer<T> consumer;
  public final UUID answerCorrelationId;
  public final String representation;

  public Deliver(final Class<T> protocol,
                 final Address address,
                 final Definition.SerializationProxy definition,
                 final SerializableConsumer<T> consumer,
                 final String representation) {
    this(protocol, address, definition, consumer, null, representation);
  }

  public Deliver(final Class<T> protocol,
                 final Address address,
                 final Definition.SerializationProxy definition,
                 final SerializableConsumer<T> consumer,
                 final UUID answerCorrelationId,
                 final String representation) {
    this.protocol = protocol;
    this.address = address;
    this.definition = definition;
    this.consumer = consumer;
    this.answerCorrelationId = answerCorrelationId;
    this.representation = representation;
  }

  @Override
  public void accept(Id receiver, Id sender, Visitor visitor) {
    visitor.visit(receiver, sender, this);
  }

  @Override
  public String toString() {
    return String.format(
        "Deliver(protocol='%s', address='%s', definitionProxy='%s', consumer='%s', representation='%s')",
        protocol, address, definition, consumer, representation);
  }
}
