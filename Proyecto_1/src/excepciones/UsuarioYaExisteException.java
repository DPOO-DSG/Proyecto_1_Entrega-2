package excepciones;

public class UsuarioYaExisteException extends Exception {
    public UsuarioYaExisteException(String msg) { super(msg); }
}