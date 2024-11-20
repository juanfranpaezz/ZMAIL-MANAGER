package USERS;

import java.util.Random;


public class UserValidator {

    public static UserMail validateUserMail(String name, UsersManager uM_G) {
        boolean value = false;
        Random random = new Random();
        int randomPro;
        int i = 0;
        UserMail createMail = null;
        do {
            randomPro = random.nextInt(999);
            createMail = new UserMail(name + randomPro);
            i++;
            value = uM_G.validateUserExists(createMail);
        } while (value && i < 2000);
        return createMail;
    }

    /**
     * verifyNamePerson verifica que el nombre este en el formato admitido.
     * @param dataName
     * @return true si, y solo si el nombre es menor a 25 caracteres y no contiene caracteres especiales.
     */
    public static boolean verifyDataName(String dataName) {
        if (dataName.length() <= 1) {
            return false;
        }
        if (dataName.length() > 25) {
            return false;
        }
        if (!dataName.matches("^[a-zA-Z\\s']*$")) {
            return false;
        }
        return true;
    }

}
