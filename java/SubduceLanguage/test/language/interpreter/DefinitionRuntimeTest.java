package language.interpreter;

public class DefinitionRuntimeTest extends ValueRuntimeTest {
  @Override
  protected Runtime factory() {
    return new DefinitionRuntime();
  }
}
