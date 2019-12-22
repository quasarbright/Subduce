package language.interpreter.expression.value.listValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import language.interpreter.expression.value.Value;

public class ConsList implements ListValue {
  private final Value first;
  private final ListValue rest;

  public ConsList(Value first, ListValue rest) {
    this.first = first;
    this.rest = rest;
  }

  @Override
  public <R> R accept(ListValueVisitor<R> visitor) {
    return visitor.visitCons(first, rest);
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, rest);
  }

  @Override
  public boolean equals(Object obj) {
    if(this == obj) {
      return true;
    }
    if(obj == null || getClass() != obj.getClass()) {
      return false;
    }
    ConsList other = (ConsList) obj;
    return first.equals(other.first) && rest.equals(other.rest);
  }

  private List<Value> flatten() {
    return this.accept(new ListValueVisitor<>() {
      @Override
      public List<Value> visitEmpty() {
        return Collections.emptyList();
      }
      @Override
      public List<Value> visitCons(Value first, ListValue rest) {
        List<Value> ans = new ArrayList<>(Collections.singletonList(first));
        ans.addAll(visit(rest));
        return ans;
      }
    });
  }

  @Override
  public String toString() {
    List<String> strings = flatten().stream()
            .map(Object::toString)
            .collect(Collectors.toList());
    return "(list "+String.join(" ", strings)+")";
  }
}
