package language.interpreter;

public class StatementRuntimeTest extends ValueRuntimeTest {
  @Override
  protected Runtime factory() {
    return new StatementRuntime();
  }
}
