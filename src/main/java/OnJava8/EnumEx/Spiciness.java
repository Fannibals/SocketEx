package OnJava8.EnumEx;

public enum Spiciness {
    NOT, MILD, MEDIUM, HOT, FLAMING;

    @Override
    public String toString() {
        return super.toString() + "-Ethan";
    }
}
