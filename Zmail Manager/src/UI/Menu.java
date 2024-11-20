package UI;

import EXCEPTIONS.PasswordException;
import JSON.JSONUtiles;
import MESSAGES.Mail;
import MESSAGES.MailBox;
import MESSAGES.MailType;
import USERS.*;
import org.json.JSONArray;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Menu {

    public static void startCode(){
        boolean fileExists = Files.exists(Paths.get("Users.json"));
        boolean fileMailExists = Files.exists(Paths.get("Mails.json"));
        MailBox<? extends User> mailBox = new MailBox<>();
        Scanner scanner = new Scanner(System.in);
        UsersManager<Person> uMPerson = new UsersManager<>();
        UsersManager<Business> uMBusiness = new UsersManager<>();
        UsersManager<Pro> uMPro = new UsersManager<>();
        UsersManager<? extends User> uM_G=new UsersManager<>();

        Random random = new Random();
        String[] companyTypes = {
                "1- TECNOLOGÍA",
                "2- FINANZAS",
                "3- SALUD",
                "4- COMERCIO",
                "5- ENERGÍA",
                "6- LOGÍSTICA",
                "7- ENTRETENIMIENTO",
                "8- EDUCACIÓN"
        };


        boolean menuIsActive = true;

        System.out.println("\u001B[34m\nBienvenido a ZMAIL.\u001B[0m");
        int mailScanner=0;
        while(menuIsActive){ //Variable que controla el menu
            System.out.println("\u001B[34m1-\u001B[0m Iniciar sesión");
            System.out.println("\u001B[34m2-\u001B[0m Registrarse");
            System.out.print("\u001B[34m3-\u001B[0m Cerrar ZMAIL\n--> ");
            try {
                mailScanner= scanner.nextInt();
                scanner.nextLine();
            }catch (InputMismatchException e){// Control de excepcion
                scanner.nextLine();
            }

            switch (mailScanner){// Variable que controla el menu inicio de sesion
                case 1:

                    System.out.println("Ingrese su zmail sin la extensión (zmail.com): ");
                    String zmailScanner = scanner.nextLine();
                    System.out.println("Ingrese su contraseña: ");
                    String password = scanner.nextLine();

                    UserMail zmail = new UserMail(zmailScanner);

                    if(uMPerson.loginUser(zmail, password)){// Verifica que el usuario exista
                        boolean userLogued = true;
                        System.out.println("\u001B[34mBienvenido, tu zmail es: "+zmail+"\u001B[0m");
                        while (userLogued){
                            System.out.println("\u001B[34mMenú de ZMAIL\u001B[0m");
                            System.out.println("\u001B[34m1-\u001B[0m Enviar un mensaje");
                            System.out.println("\u001B[34m2-\u001B[0m Ver mensajes recibidos");
                            System.out.println("\u001B[34m3-\u001B[0m Ver mensajes enviados por mí");
                            if(UsersManager.isUserPro(zmail)){// Devuelve la clase del usuario
                                System.out.println("\u001B[34m4-\u001B[0m Modificar lista de contactos");
                                System.out.print("\u001B[34m5-\u001B[0m Volver\n--> ");
                            }
                            else{
                                System.out.print("\u001B[34m4-\u001B[0m Volver\n--> ");
                            }

                            int userLoguedOption =0;
                            try {
                                userLoguedOption= scanner.nextInt();
                                scanner.nextLine();// Limpia el buffer
                            }catch (InputMismatchException e){// Control de excepcion
                                scanner.nextLine();
                            }
                            switch (userLoguedOption){
                                case 1:
                                    boolean inMenu = true;
                                    int contactListOption = 0;
                                    while(inMenu){// Variable que controla el menu de los usuarios una vez en sesion
                                        boolean validator = true;
                                        if(UsersManager.isUserPro(zmail)){// Devuelve true si el usuario es pro
                                            boolean validOption = false;
                                            do {
                                                System.out.println("\u001B[34m¿Qué deseas hacer?\u001B[0m");
                                                System.out.println("\u001B[34m1-\u001B[0m Enviar el mensaje a mi lista de contactos");
                                                System.out.println("\u001B[34m2-\u001B[0m Enviar el mensaje a un usuario");
                                                System.out.print("\u001B[34m3-\u001B[0m Volver\n--> ");
                                                try {
                                                    contactListOption = scanner.nextInt();
                                                    scanner.nextLine();
                                                    if (contactListOption >= 1 && contactListOption <= 3) {
                                                        validOption = true;// Termina el bucle
                                                    } else {
                                                        System.out.println("Por favor, selecciona una opción válida entre 1 y 3.");
                                                    }
                                                } catch (InputMismatchException e) {// Captura la excepcion
                                                    System.out.println("Entrada inválida. Por favor, ingresa un número.");
                                                    scanner.nextLine();
                                                }
                                            } while (!validOption);// Se ejecuta mientras valid option sea flase

                                        }else contactListOption=2;// Si no es pro, va al caso 2
                                        switch (contactListOption){// Variable que controla el menu de mensajes de usario pro
                                            case 1:
                                                HashSet<UserMail> contact_list=new Pro().getContactList(zmail);// Trae la contact list del json
                                                if(contact_list.isEmpty()){// Si esta vacia imprime un mensaje, y ejecuta un break
                                                    System.out.println("Tu lista esta vacia, volveras al menú");
                                                    break;
                                                }
                                                System.out.println("Ingrese el asunto del zmail:");
                                                String matter = scanner.nextLine();
                                                String msg="";
                                                while(msg.isBlank()){// Mientras que el mensaje este vacio, se ejecuta el bucle
                                                    System.out.println("Ingrese el mensaje: ");
                                                    msg = scanner.nextLine();
                                                }
                                                MailType mt = null;
                                                int mailTypeOption = 0;
                                                boolean validOption = false;

                                                while (!validOption) {
                                                    try {
                                                        System.out.println("\u001B[34mSelecciona el tipo de notificación:\u001B[0m");
                                                        System.out.println("\u001B[34m1.\u001B[0m NOTIFICACION");
                                                        System.out.println("\u001B[34m2.\u001B[0m PROMOCION");
                                                        System.out.println("\u001B[34m3.\u001B[0m PRINCIPAL");
                                                        mailTypeOption = scanner.nextInt();
                                                        scanner.nextLine();
                                                        switch (mailTypeOption) {
                                                            case 1:
                                                                mt = MailType.NOTIFICATION;
                                                                validOption = true;
                                                                break;
                                                            case 2:
                                                                mt = MailType.PROMOTION;
                                                                validOption = true;
                                                                break;
                                                            case 3:
                                                                mt = MailType.PRINCIPAL;
                                                                validOption = true;
                                                                break;
                                                            default:
                                                                System.out.println("Elige una opción válida entre 1 y 3.");
                                                        }
                                                    } catch (InputMismatchException e) {
                                                        scanner.nextLine();
                                                        System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
                                                    }
                                                }
                                                if(!validOption){
                                                    break;
                                                }

                                                Mail sendMail = new Mail(contact_list,msg,zmail,mt,matter);
                                                System.out.println((MailManager.cargarAJSON(sendMail)) ? "Mensaje cargado con exito " : "No se pudo cargar.");
                                                if(!fileMailExists){
                                                    System.out.println("\u001B[34m¡Felicidades! Eres el primer usuario en enviar un Mail\u001B[0m");
                                                    System.out.println("\u001B[34mEl programa se cerrará para actualizar de forma correcta los datos.\u001B[0m");
                                                    System.out.println("\u001B[34mAl finalizar, puedes volver a entrar. Este mensaje no volverá a aparecer.\u001B[0m");
                                                    JSONUtiles.uploadToFile(new JSONArray(),"Mails.json");
                                                    System.exit(0);
                                                }
                                                break;

                                            case 2:
                                                System.out.println("Ingrese el zmail del usuario receptor (sin extensión zmail)");
                                                String zmailReceptor = scanner.next();
                                                scanner.nextLine();
                                                UserMail uMZR = new UserMail(zmailReceptor);


                                                if(!uM_G.validateUserExists(uMZR)){
                                                    System.out.println("El zmail es inexistente.");
                                                    break;
                                                }

                                                System.out.println("Ingrese el asunto del zmail:");

                                                String matter_list = scanner.nextLine();

                                                String msg_list = "";
                                                while(msg_list.isBlank()){
                                                    System.out.println("Ingrese el mensaje: ");
                                                    msg_list = scanner.nextLine();
                                                }

                                                MailType mailType=MailType.PRINCIPAL;
                                                HashSet<UserMail> hUM= new HashSet<>();
                                                hUM.add(new UserMail(zmailReceptor));
                                                Mail proMail = new Mail(hUM,msg_list,zmail,mailType,matter_list);
                                                System.out.println((MailManager.cargarAJSON(proMail)) ? "Mensaje cargado con exito " : "No se pudo cargar.");
                                                if(!fileMailExists && validator){
                                                    System.out.println("\u001B[34m¡Felicidades! Eres el primer usuario en enviar un Mail\u001B[0m");
                                                    System.out.println("\u001B[34mEl programa se cerrará para actualizar de forma correcta los datos.\u001B[0m");
                                                    System.out.println("\u001B[34mAl finalizar, puedes volver a entrar. Este mensaje no volverá a aparecer.\u001B[0m");
                                                    JSONUtiles.uploadToFile(new JSONArray(),"Mails.json");
                                                    System.exit(0);
                                                }
                                                inMenu=false;
                                                break;
                                            case 3:
                                                inMenu = false;
                                                break;
                                            default:
                                                System.out.println("Elige una opción válida.");
                                                break;
                                        }

                                    }
                                    break;

                                case 2:

                                    if(mailBox.recibedMessages(zmail).isEmpty()){
                                        System.out.println("Usted no tiene correos recibidos");
                                    }else {
                                        System.out.println("Correos recibidos: ");
                                        for(Mail mails: mailBox.recibedMessages(zmail)){
                                            System.out.println(mails.recibedToString());

                                        }
                                    }

                                    break;
                                case 3:
                                        if(mailBox.sendedMessages(zmail).isEmpty()){
                                            System.out.println("Usted no tiene correos enviados");
                                        }else {
                                            System.out.println("Correos enviados: ");
                                            for(Mail mails: mailBox.sendedMessages(zmail)){
                                                System.out.println(mails.sendsToString());


                                            }
                                            mailBox.removeFromBox();

                                        }
                                        break;

                                case 4:

                                    if(UsersManager.isUserPro(zmail)){
                                        HashSet<UserMail> contactList=new Pro().getContactList(zmail);
                                        Optional<Pro> usersFound = uMPro.getUsersList().stream()
                                                .filter(usuario -> usuario.getUserMail().equals(zmail))
                                                .findFirst();

                                        if(contactList.isEmpty()){
                                            System.out.println("Tu lista de contactos esta vacia");
                                            break;
                                        }else{
                                            System.out.println("Esta es tu lista de contactos: "+contactList.toString());
                                        }
                                        int addDeleteUsers = 0;
                                        boolean deleteUsers = true;

                                        do {
                                            System.out.println("Ingrese 1 si desea eliminar un usuario, 2 si desea agregar:");
                                            boolean inputValid = false;
                                            do {
                                                try {
                                                    addDeleteUsers = scanner.nextInt();
                                                    scanner.nextLine(); // Limpiar el buffer
                                                    inputValid = addDeleteUsers >= 1 && addDeleteUsers <= 2; // Validar rango
                                                    if (!inputValid) {
                                                        System.out.println("El número debe ser 1 o 2. Inténtelo de nuevo.");
                                                    }
                                                } catch (InputMismatchException e) {
                                                    System.out.println("Ingrese un número válido por favor.");
                                                    scanner.nextLine(); // Limpiar el buffer tras la excepción
                                                }
                                            } while (!inputValid);

                                            if (addDeleteUsers == 1) { // Eliminar usuarios
                                                while (deleteUsers) {
                                                    System.out.println("Ingrese el zmail de un receptor (sin extensión zmail):");
                                                    String zmailRec = scanner.nextLine();

                                                    UserMail userMailZ = new UserMail(zmailRec);
                                                    if (uM_G.validateUserExists(userMailZ)) {
                                                        contactList.remove(userMailZ);
                                                        System.out.println("¿Desea continuar eliminando receptores? 1 Sí - 0 No");
                                                        int option = -1;
                                                        boolean optionValid = false;
                                                        do {
                                                            try {
                                                                option = scanner.nextInt();
                                                                scanner.nextLine(); // Limpiar el buffer
                                                                optionValid = option == 1 || option == 0; // Validar opción
                                                                if (!optionValid) {
                                                                    System.out.println("El número debe ser 1 o 0. Inténtelo de nuevo.");
                                                                }
                                                            } catch (InputMismatchException e) {
                                                                System.out.println("Ingrese un número válido (1 o 0).");
                                                                scanner.nextLine(); // Limpiar el buffer tras la excepción
                                                            }
                                                        } while (!optionValid);

                                                        if (option == 0) {
                                                            System.out.println(Pro.deleteUsersFromContactList(contactList, zmail));
                                                            deleteUsers = false;
                                                        }
                                                    } else {
                                                        System.out.println("El zmail es inexistente.");
                                                    }
                                                }
                                            } else if (addDeleteUsers == 2) { // Agregar usuarios
                                                boolean addUsers = true;
                                                while (addUsers) {
                                                    System.out.println("Ingrese el zmail del usuario que desea agregar (sin extensión zmail):");
                                                    String zmailRec = scanner.nextLine();

                                                    UserMail userMailZ = new UserMail(zmailRec);
                                                    if (uM_G.validateUserExists(userMailZ)) {
                                                        contactList.add(userMailZ);
                                                        System.out.println("¿Desea continuar agregando usuarios? \u001B[34m1\u001B[0m Sí - \u001B[34m0\u001B[0m No");
                                                        int option = -1;
                                                        boolean optionValid = false;
                                                        do {
                                                            try {
                                                                option = scanner.nextInt();
                                                                scanner.nextLine(); // Limpiar el buffer
                                                                optionValid = option == 1 || option == 0; // Validar opción
                                                                if (!optionValid) {
                                                                    System.out.println("El número debe ser 1 o 0. Inténtelo de nuevo.");
                                                                }
                                                            } catch (InputMismatchException e) {
                                                                System.out.println("Ingrese un número válido (1 o 0).");
                                                                scanner.nextLine(); // Limpiar el buffer tras la excepción
                                                            }
                                                        } while (!optionValid);

                                                        if (option == 0) {
                                                            System.out.println(Pro.addUsersToContactList(contactList, zmail));
                                                            addUsers = false;
                                                        }
                                                    } else {
                                                        System.out.println("El zmail es inexistente.");
                                                    }
                                                }
                                            }
                                        } while (addDeleteUsers >= 1 && addDeleteUsers <= 2);

                                    }else{
                                        System.out.println("Volviendo...");
                                        userLogued=false;
                                        break;
                                    }


                                    break;
                                case 5:
                                    if(UsersManager.isUserPro(zmail)){
                                        System.out.println("Volviendo...");
                                        userLogued=false;
                                        break;
                                    }else{
                                        System.out.println("Elige una opción válida.");
                                        break;
                                    }


                                default:
                                    System.out.println("Elige una opción válida.");
                                    break;
                            }
                        }

                    }else{
                        System.out.println("Los datos que ingresaste son incorrectos.\n");
                    }

                    break;
                case 2:
                    boolean menuAccountType = true;
                    while(menuAccountType){
                        System.out.println("\u001B[34mIngrese el tipo de cuenta: \u001B[0m");
                        System.out.println("\u001B[34m1-\u001B[0m Estandar");
                        System.out.println("\u001B[34m2-\u001B[0m Business");
                        System.out.print("\u001B[34m3-\u001B[0m Pro\n--> ");
                        int registerOption=0;
                        int accountOption=0;
                        boolean accountOptions=accountOption<1||accountOption>3;
                        while(accountOptions){
                            try {
                                registerOption= scanner.nextInt();
                                scanner.nextLine();
                                accountOptions=false;
                            }catch (InputMismatchException e){
                                scanner.nextLine();
                                System.out.println("Ingrese una opcion valida por favor");
                            }
                        }
                        switch (registerOption) {
                            case 1:

                                String name = "";
                                String lastName = "";
                                String passwordScanner = "";
                                String secondPassword = "";

                                System.out.println("Recuerde que su nombre no puede contener caracteres especiales ni numeros, ni tener más de 25 caracteres");

                                do {
                                    System.out.println("Ingrese su nombre: ");
                                    name = scanner.nextLine();
                                }while (!UserValidator.verifyDataName(name));

                                System.out.println("Recuerde que su apellido no puede contener caracteres especiales, numeros, ni tener más de 25 caracteres");

                                do{
                                    System.out.println("Ingrese su apellido: ");
                                    lastName = scanner.nextLine();
                                }while (!UserValidator.verifyDataName(lastName));
                                System.out.println("Recuerde que su contraseña debe tener 8 caracteres minimo, 1 minuscula y una mayuscula");
                                boolean passwordVerifier=true;
                                do{
                                    try {
                                        System.out.println("Ingrese la contraseña: ");
                                        passwordScanner=scanner.nextLine();
                                        uMPerson.verifyPassword(passwordScanner);
                                        passwordVerifier=false;
                                    } catch (PasswordException e) {
                                        System.out.println(e.getMessage());

                                    }
                                }while(passwordVerifier);
                                boolean passwordEqualsVerifier=true;

                                do {
                                    try{
                                        System.out.println("Ingrese la contraseña nuevamente");
                                        secondPassword=scanner.nextLine();
                                        uMPerson.comparePasswords(passwordScanner,secondPassword);
                                        passwordEqualsVerifier=false;
                                    }catch (PasswordException.PasswordDoesntMatch e){
                                        System.out.println(e.getMessage());
                                    }
                                }while(passwordEqualsVerifier);

                                //int randomNumber;
                                UserMail createUserMail;
                                do {
                                    int randomNumber = random.nextInt(999);
                                    createUserMail = new UserMail(name + lastName + String.valueOf(randomNumber));
                                } while (uMPerson.validateUserExists(createUserMail));


                                Person personUser = new Person(createUserMail, passwordScanner, name, lastName);



                                if(uMPerson.registerUser(personUser)){
                                    if(!fileExists){
                                        System.out.println("\u001B[34m¡Felicidades! Eres el primer usuario en registrarse. Tu ZMAIL es "+ createUserMail.toString()+"\u001B[0m");
                                        System.out.println("\u001B[34mEl programa se cerrará para actualizar de forma correcta los datos.\u001B[0m");
                                        System.out.println("\u001B[34mAl finalizar, puedes volver a entrar. Este mensaje no volverá a aparecer.\u001B[0m");

                                        System.exit(0);
                                    }else{
                                        System.out.println("\u001B[34mUsuario registrado con éxito. Tu ZMAIL es " + createUserMail.toString()+"\u001B[0m");
                                        menuAccountType=false;
                                    }
                                }else{
                                    System.out.println("Tu usuario no pudo ser registrado.");
                                }
                                break;
                            case 2:
                                String companyName = "";
                                UserMail createBusinessMail = null;
                                do {
                                    System.out.println("Ingrese el nombre de la companía: ");
                                    companyName = scanner.nextLine();
                                    createBusinessMail = UserValidator.validateUserMail(companyName, uM_G);
                                } while (createBusinessMail == null || !UserValidator.verifyDataName(companyName));
                                System.out.println("Ingrese el tipo de compañía:");
                                int companyProTypeOption = 0;
                                for (String type : companyTypes) {
                                    System.out.println(type);
                                }

                                boolean validInput = false;

                                while (!validInput) {
                                    try {
                                        System.out.println("Ingrese un número entre 1 y 8:");
                                        companyProTypeOption = scanner.nextInt();
                                        scanner.nextLine();
                                        if (companyProTypeOption >= 1 && companyProTypeOption <= 8) {
                                            validInput = true;
                                        } else {
                                            System.out.println("El número debe estar entre 1 y 8. Intente nuevamente.");
                                        }
                                    } catch (InputMismatchException e) {
                                        scanner.nextLine();
                                        System.out.println("Entrada no válida. Por favor, ingrese un número entre 1 y 8.");
                                    }
                                }
                                CompanyType cp = CompanyType.values()[companyProTypeOption - 1];
                                String businessPassword = "";
                                boolean value2;
                                do {
                                    value2 = false;
                                    System.out.println("Ingrese la contraseña deseada, recuerde que debe ser mayor a 8 caracteres, menor a 25 y con tener un numero, mayuscula, y minuscula ");
                                    businessPassword = scanner.nextLine();
                                    if (!uM_G.verifyThePassword(businessPassword)) {
                                        value2 = true;
                                        System.out.println("la contraseña no es valida");
                                    }
                                }while(value2);
                                boolean value3;
                                String businessPasswordAgain = "";
                                do{
                                    value3 = false;
                                    System.out.println("Ingrese la contraseña nuevamente: ");
                                    businessPasswordAgain = scanner.nextLine();
                                    if(businessPasswordAgain.equals(businessPassword)){
                                        value3 = true;
                                    }
                                }while(!businessPasswordAgain.equals(businessPassword));
                                Business b = new Business(createBusinessMail, businessPassword, companyName, cp);

                                if (uMBusiness.registerUser(b)) {
                                    if (!fileExists) {
                                        System.out.println("\u001B[34m¡Felicidades! Eres el primer usuario en registrarse. Tu ZMAIL es " + createBusinessMail.toString()+"\u001B[0m");
                                        System.out.println("\u001B[34mEl programa se cerrará para actualizar de forma correcta los datos.\u001B[0m");
                                        System.out.println("\u001B[34mAl finalizar, puedes volver a entrar. Este mensaje no volverá a aparecer.\u001B[0m");

                                        System.exit(0);
                                    } else {
                                        System.out.println("\u001B[34mCuenta Business registrado con éxito. Tu ZMAIL es " + createBusinessMail.toString()+"\u001B[0m");
                                        menuAccountType=false;
                                    }
                                } else {
                                    System.out.println("\"Tu cuenta Business no pudo ser registrada.");
                                }

                                break;
                            case 3:
                                String companyName1 = "";
                                UserMail createProMail = null;
                                do {
                                    System.out.println("Ingrese el nombre de la companía: ");
                                    companyName1 = scanner.nextLine();
                                    createBusinessMail = UserValidator.validateUserMail(companyName1, uM_G);
                                } while (createBusinessMail == null || !UserValidator.verifyDataName(companyName1));

                                do {
                                    int randomNumber = random.nextInt(999);
                                    createProMail = new UserMail(companyName1 + String.valueOf(randomNumber));
                                } while (uMPerson.validateUserExists(createProMail));
                                System.out.println("Ingrese el tipo de compañía:");
                                for (String type : companyTypes) {
                                    System.out.println(type);
                                }
                                int companyTypeOptionP = 0;
                                boolean validInput2 = false;

                                do {
                                    System.out.println("Ingrese un número entre 1 y 8:");
                                    try {
                                        companyTypeOptionP = scanner.nextInt();
                                        scanner.nextLine();
                                        if (companyTypeOptionP >= 1 && companyTypeOptionP <= 8) {
                                            validInput2 = true;
                                        } else {
                                            System.out.println("El número debe estar entre 1 y 8. Intente nuevamente.");
                                        }
                                    } catch (InputMismatchException e) {
                                        scanner.nextLine();
                                        System.out.println("Entrada no válida. Por favor, ingrese un número entre 1 y 8.");
                                    }
                                } while (!validInput2);

                                CompanyType cpPro = CompanyType.values()[companyTypeOptionP - 1];

                                String proPassword = "";
                                boolean value5;
                                do {
                                    value5 = false;
                                    System.out.println("Ingrese la contraseña deseada, recuerde que debe ser mayor a 8 caracteres, menor a 25 y con tener un numero, mayuscula, y minuscula: ");
                                    proPassword = scanner.nextLine();
                                    if (!uM_G.verifyThePassword(proPassword)) {
                                        value5 = true;
                                        System.out.println("La contraseña no es valida");
                                    }
                                }while(value5);
                                String proPasswordAgain = "";
                                do{
                                    value5 = false;
                                    System.out.println("Ingrese la contraseña nuevamente: ");
                                    proPasswordAgain = scanner.nextLine();
                                    if(!proPasswordAgain.equals(proPassword)){
                                        value5 = true;
                                        System.out.println("La contraseña no es valida");
                                    }
                                }while(value5);

                                Pro p = new Pro(createProMail, proPassword, companyName1, cpPro);
                                System.out.println("¿Deseas cargar una lista de contactos? \u001B[34m1\u001B[0m Sí -- \u001B[34m0\u001B[0m No");
                                System.out.println("Con una lista de contactos podrás enviar un zmail a varios receptores a la vez, de forma muy simple!");
                                int bContactList = 2;

                                boolean inputValid = false;
                                do {
                                    try {
                                        bContactList = scanner.nextInt();
                                        scanner.nextLine();
                                        inputValid = bContactList >= 0 && bContactList <= 1;
                                        if (!inputValid) {
                                            System.out.println("El número ingresado debe ser 0 o 1. Inténtelo de nuevo.");
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("Ingrese un número válido por favor.");
                                        scanner.nextLine();
                                    }
                                } while (!inputValid);
                                boolean next = true;
                                if (bContactList == 1) {

                                    while (next) {
                                        System.out.println("Ingrese el zmail (sin extensión zmail.com): ");

                                        String zmailContact = scanner.nextLine();

                                        if (uMPro.validateUserExists(new UserMail(zmailContact))) {
                                            System.out.println((p.addMailToList(new UserMail(zmailContact))) ? "Agregado con éxito." : "No se pudo agregar el contacto.");
                                        } else {
                                            System.out.println("El zmail no pertenece a ningun usuario.");
                                        }
                                        System.out.print("¿Quiere continuar agregando contactos en la lista? \u001B[34m1\u001B[0m Sí -- \u001B[34m0\u001B[0m No");
                                        int optionContact = 2;
                                        boolean inputValid2 = false;
                                        do {
                                            System.out.println("Ingrese un número (0 o 1): ");
                                            try {
                                                optionContact = scanner.nextInt();
                                                scanner.nextLine();
                                                inputValid2 = optionContact >= 0 && optionContact <= 1;
                                                if (!inputValid2) {
                                                    System.out.println("El número ingresado debe ser 0 o 1. Inténtelo de nuevo.");
                                                }
                                            } catch (InputMismatchException e) {
                                                System.out.println("Ingrese un número válido por favor.");
                                                scanner.nextLine();
                                            }
                                        } while (!inputValid2);
                                        if (optionContact == 0) {

                                            next = false;


                                        }

                                    }

                                }

                                if (uMPro.registerUser(p)) {
                                    if (!fileExists) {
                                        System.out.println("\u001B[34m¡Felicidades! Eres el primer usuario en registrarse. Tu ZMAIL es " + createProMail.toString()+"\u001B[0m");
                                        System.out.println("\u001B[34mEl programa se cerrará para actualizar de forma correcta los datos.\u001B[0m");
                                        System.out.println("\u001B[34mAl finalizar, puedes volver a entrar. Este mensaje no volverá a aparecer.\u001B[0m");

                                        System.exit(0);
                                    } else {
                                        System.out.println("\u001B[34mCuenta Business Pro registrada con éxito. Tu ZMAIL es " + createProMail.toString()+"\u001B[0m");
                                        menuAccountType=false;
                                    }
                                } else {
                                    System.out.println("\"Tu cuenta Business Pro no pudo ser registrada.");
                                }

                                break;

                            default:
                                System.out.println("Opción inválida.");
                        }
                    }
                    break;
                case 3:

                    System.out.println("Cerrando...");
                    menuIsActive=false;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");

            }
        }
    }



}

