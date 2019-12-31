package language.interpreter;

import java.util.Objects;

public class SubduceError extends RuntimeException {
  public final String message;

  public SubduceError(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SubduceError that = (SubduceError) o;
    return message.equals(that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message);
  }

  @Override
  public String getMessage() {
    return message;
  }
}