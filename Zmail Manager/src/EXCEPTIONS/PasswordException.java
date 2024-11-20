package EXCEPTIONS;

public class PasswordException extends Exception {

  public PasswordException(String mensaje) {
    super(mensaje);
  }

  public static class WrongPassword extends PasswordException {
    public WrongPassword() {
      super("La contraseña es errónea, intente de vuelta por favor");
    }
  }

  public static class PasswordNeedsNumber extends PasswordException {
    public PasswordNeedsNumber() {
      super("La contraseña requiere al menos un número");
    }
  }

  public static class PasswordNeedsLowerCase extends PasswordException {
    public PasswordNeedsLowerCase() {
      super("La contraseña requiere al menos una minúscula");
    }
  }

  public static class PasswordNeedsUpperCase extends PasswordException {
    public PasswordNeedsUpperCase() {
      super("La contraseña requiere al menos una mayúscula");
    }
  }

  public static class PasswordTooShort extends PasswordException {
    public PasswordTooShort() {
      super("La contraseña es muy corta, requiere al menos 8 caracteres");
    }
  }

  public static class PasswordDoesntMatch extends PasswordException {
    public PasswordDoesntMatch() {
      super("La contraseña no coincide con la previamente ingresada");
    }
  }

  public static class PasswordTooLong extends PasswordException {
    public PasswordTooLong() {
      super("La contraseña es muy larga, debe tener menos de 25 caracteres");
    }
  }
}
