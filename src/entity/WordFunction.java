package entity;

@FunctionalInterface
public interface WordFunction {
    String process(String str, int N, int L);
}
