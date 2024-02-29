/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package co.com.alprosoft.eraiep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author leand
 * Hecho con el JDK 17
 */
public class EraiEP {
    
    public static final String ROOT_DIR = System.getProperty("user.dir");    
    public static final String EXERCISES_DIR = ROOT_DIR + "\\" + "ejercicios";
    public static final String EXERCISES_DIR_NAME = "ejercicios";
    public static final String EXERCISE_DESCRIPTION_FILE_NAME = "descripcion.txt";
    public static final String EXERCISE_TEST_CASE_VISUALIZATION_FILE_NAME = "visibilidad.txt";
    public static final String EXERCISE_TEST_CASE_INPUT_FILE_NAME_PART = "entrada";
    public static final String EXERCISE_TEST_CASE_OUTPUT_FILE_NAME_PART = "salida";    
    // Para hacer la prueba de la aplicación Java:
    public static final String EXERCISE1_DIR = EXERCISES_DIR + "\\" + "ejercicio1";
    public static final String EXERCISE1_DIR_NAME = "ejercicio1";
    // Para hacer la prueba de funcionamiento de variantes aleatorias en un ejercicio de programación
    public static final String EXERCISE1_OUTPUT_VARIANTS_DIR = EXERCISE1_DIR + "\\" + "var_salidas_cp";
    public static final String EXERCISE1_OUTPUT_VARIANTS_DIR_NAME = "var_salidas_cp";
    public static final String EXERCISE_OUTPUT_VARIANT_NAME_PART = "var";
    public static final String EXERCISE_OUTPUT_VARIANT_DESCRIPTION_TAGS_FILE_NAME = "etiquetas.txt";
    public static final String EXERCISE_CHOSEN_OUTPUT_VARIANT_FILE_NAME = "variante.txt";
    
    public static final String EXERCISE_SOLUTION_PROPOSAL_DIRECTORY_NAME = "propuestas";
    
    public static final String SEND_COMMAND = "send";    
    public static final String EXIT_COMMAND = "exit";
    
    public static final String OK_RESULT = "OK";    
    public static final String NOT_OK_RESULT = "NO OK";
    
    // Constante que indica que hubo un error en la ejecución del programa EraiEP
    public static final String ERAIEP_ERROR = "ERROR";
    
    public static final String EXERCISE_FLOW_FILE_NAME = "flujo_ejercicios.txt";
    public static final String EXERCISE_FLOW_FILE_PATH = EXERCISES_DIR + '\\' + EXERCISE_FLOW_FILE_NAME;
    
    public static final String EXERCISE_DIR_NAME_PART = "ejercicio";    
    public static final String EXERCISE_VARIANT_DIR_NAME_PART = "var";
    
    public static final String EXERCISE_VARIANT_DESCRIPTORS_FILE_NAME = "descriptores.txt";
    
    // Nombre de comando para ejecutar el intérprete de Python en Windows (cmd)
    // Nota: Este nombre puede diferir dependiendo de cómo se haya instalado Python en Windows (por ejemplo).
    // Posibles nombres: python, python3, python3.x (siendo x el número menor - minor - de la versión del lenguaje).
    public static final String PYTHON_INTERPRETER_COMMAND_NAME = "python";
    // public static final String PYTHON_INTERPRETER_COMMAND_NAME = "python3";
    
    public EraiEP() {
    }

    public static void main(String[] args){
        // SETUP INICIAL
        // Obtener el directorio de trabajo actual (Current working directory) de esta aplicación Java
        // https://mkyong.com/java/how-to-get-the-current-working-directory-in-java/
        System.out.println(ROOT_DIR);
        
        // 1) Valide que existe el archivo de flujo de ejercicios.
        if (fileExists(EXERCISE_FLOW_FILE_PATH)) {
            // 3) Si el archivo de flujo de ejercicios existe, lea el archivo, línea por línea.
            
            BufferedReader inputStreamReader = null;
            
            try (BufferedReader br = Files.newBufferedReader(FileSystems.getDefault().getPath(
                    EXERCISES_DIR_NAME, EXERCISE_FLOW_FILE_NAME), 
                    StandardCharsets.UTF_8)) {
                
                inputStreamReader = new BufferedReader(new InputStreamReader(System.in));
                        
                for (String line; (line = br.readLine()) != null; ) {
                    // Muestra la línea leída del archivo en pantalla.
                    // System.out.println(line);
                    // 4) Por cada línea leída del archivo:
                    // 5) Extraiga el número del ejercicio y el de la variante (si tiene).
                    String[] exerciseFlowEntryParts = line.split("/");
                    String exerciseNumber;
                    String exerciseVariantNumber = null;
                    if (exerciseFlowEntryParts.length == 2) {
                        exerciseNumber = exerciseFlowEntryParts[0].substring(1, exerciseFlowEntryParts[0].length());
                        exerciseVariantNumber = exerciseFlowEntryParts[1].substring(1, exerciseFlowEntryParts[1].length());
                    } else {
                        exerciseNumber = exerciseFlowEntryParts[0].substring(1, exerciseFlowEntryParts[0].length());
                    }
                    
                    // 6) Valide que exista el directorio del ejercicio.
                    if (directoryExists(EXERCISES_DIR + "\\" + EXERCISE_DIR_NAME_PART + exerciseNumber)) {
                        String exerciseDirPath = EXERCISES_DIR + "\\" + EXERCISE_DIR_NAME_PART + exerciseNumber;
                        // 8) Si existe el directorio del ejercicio, revise si el ejercicio tiene o no variantes.
                        if (exerciseVariantNumber != null) {
                            // 10) Si el ejercicio tiene variantes, valide que exista el directorio de la variante del ejercicio.
                            if (directoryExists(exerciseDirPath + "\\" + EXERCISE_VARIANT_DIR_NAME_PART + exerciseVariantNumber)) {
                                String exerciseVariantDirPath = exerciseDirPath + "\\" + EXERCISE_VARIANT_DIR_NAME_PART + exerciseVariantNumber;
                                // 12) Si el directorio de la variante del ejercicio existe:
                                // 13) Valide si el archivo de descripcion.txt existe en el directorio principal del ejercicio.
                                if (fileExists(exerciseDirPath + "\\" + EXERCISE_DESCRIPTION_FILE_NAME)) {
                                    // 15) Si el archivo de descripcion.txt existe, valide si el archivo descriptores.txt existe en el directorio de variantes.
                                    if (fileExists(exerciseVariantDirPath + "\\" + EXERCISE_VARIANT_DESCRIPTORS_FILE_NAME)) {
                                        // 17) Si el archivo de descriptores.txt existe, entonces:
                                        // 18) Ensamble los textos de cada línea del archivo de descriptores.txt en las etiquetas <genai-desc N> 
                                        //   del archivo descripcion.txt, siendo N el número de la línea del archivo descriptores.txt.
                                        // 19) En seguida, muestre el contenido de la descripción del ejercicio al estudiante.
                                        try {
                                            assembleExerciseDescriptorsAndReadDescriptionFile(exerciseNumber, exerciseVariantNumber);                                            
                                            // 20) Valide si el archivo de visibilidad.txt existe en el directorio de la variante del ejercicio.
                                            if (fileExists(exerciseVariantDirPath + "\\" + EXERCISE_TEST_CASE_VISUALIZATION_FILE_NAME)) {
                                                // 24) Pero si el archivo de visibilidad.txt existe en el directorio de la variante del ejercicio, 
                                                //  entonces lea ese archivo y muestre el contenido de entradaN.txt y salidaN.txt al estudiante, 
                                                //  donde N es el número del caso de prueba mostrado en las líneas del archivo visibilidad.txt, 
                                                //  uno por línea, siempre y cuando existan los dos archivos entradaN.txt y salidaN.txt en el directorio 
                                                //  de variante del ejercicio.
                                                readVisibleTestCaseFiles(exerciseNumber, exerciseVariantNumber, exerciseVariantDirPath);
                                                
                                            } else {
                                                // 21) Si el archivo de visibilidad.txt no existe en el directorio de la variante del ejercicio, 
                                                //  valide si el archivo de visibilidad.txt existe en el directorio principal:
                                                if (fileExists(exerciseDirPath + "\\" + EXERCISE_TEST_CASE_VISUALIZATION_FILE_NAME)) {
                                                    // 22) Si el archivo de visibilidad.txt existe en el directorio principal, entonces lea ese archivo y muestre el 
                                                    //  contenido de entradaN.txt y salidaN.txt al estudiante, donde N es el número del caso de prueba mostrado en las 
                                                    //  líneas del archivo visibilidad.txt, uno por línea, siempre y cuando existan los dos archivos entradaN.txt y 
                                                    //  salidaN.txt en el directorio de variante del ejercicio.
                                                    readVisibleTestCaseFiles(exerciseNumber, exerciseDirPath);
                                                }                                                
                                                // 23) Si el archivo de visibilidad.txt no existe en el directorio principal, no muestre casos de prueba al estudiante.
                                            }
                                            
                                        } catch (IOException ex) {
                                            System.out.println("Error al mostrar el ejercicio: " + ex);
                                            // Logger.getLogger(EraiEP.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } else {
                                        // 16) Si el archivo de descriptores.txt no existe, entonces muestre el contenido de descripcion.txt al estudiante (tal cual).
                                        try {
                                            readDescriptionFile(exerciseNumber);
                                        } catch (IOException ex) {
                                            System.out.println("Error al mostrar el ejercicio " + exerciseNumber + ":" + ex);
                                            // Logger.getLogger(EraiEP.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                    
                                } else {
                                    // 14) Si el archivo de descripcion.txt no existe, pase al paso 20.
                                    // 20) Valide si el archivo de visibilidad.txt existe en el directorio de la variante del ejercicio.
                                    if (fileExists(exerciseVariantDirPath + "\\" + EXERCISE_TEST_CASE_VISUALIZATION_FILE_NAME)) {
                                        // 24) Pero si el archivo de visibilidad.txt existe en el directorio de la variante del ejercicio, 
                                        //  entonces lea ese archivo y muestre el contenido de entradaN.txt y salidaN.txt al estudiante, 
                                        //  donde N es el número del caso de prueba mostrado en las líneas del archivo visibilidad.txt, 
                                        //  uno por línea, siempre y cuando existan los dos archivos entradaN.txt y salidaN.txt en el directorio 
                                        //  de variante del ejercicio.
                                        readVisibleTestCaseFiles(exerciseNumber, exerciseVariantNumber, exerciseVariantDirPath);                                                
                                    } else {
                                        // 21) Si el archivo de visibilidad.txt no existe en el directorio de la variante del ejercicio, 
                                        //  valide si el archivo de visibilidad.txt existe en el directorio principal:
                                        if (fileExists(exerciseDirPath + "\\" + EXERCISE_TEST_CASE_VISUALIZATION_FILE_NAME)) {
                                            // 22) Si el archivo de visibilidad.txt existe en el directorio principal, entonces lea ese archivo y muestre el 
                                            //  contenido de entradaN.txt y salidaN.txt al estudiante, donde N es el número del caso de prueba mostrado en las 
                                            //  líneas del archivo visibilidad.txt, uno por línea, siempre y cuando existan los dos archivos entradaN.txt y 
                                            //  salidaN.txt en el directorio de variante del ejercicio.
                                            readVisibleTestCaseFiles(exerciseNumber, exerciseDirPath);                                            
                                        }                                                
                                        // 23) Si el archivo de visibilidad.txt no existe en el directorio principal, no muestre casos de prueba al estudiante.
                                    }
                                }
                                
                                // 25) Muestre un mensaje al estudiante, indicando los comandos que puede usar para salir del programa de 
                                //   evaluador automático, y para enviar un código Python al evaluador automático para evaluar el ejercicio.
                                // 26) (…) : Muchas cosas ocurren aquí que no son relevantes ahora.
                                try {
                                    String interactionResult = showWaitingForUserInputMessage(inputStreamReader, exerciseNumber, 
                                            exerciseVariantNumber, exerciseVariantDirPath);
                                    if (interactionResult.equals(EXIT_COMMAND) || interactionResult.equals(ERAIEP_ERROR)) {
                                        break; // Esto provoca la terminación del ciclo, y por ende, del programa.
                                    }
                                } catch (IOException ex) {
                                    System.out.println("Error durante la ejecución de la aplicación: " + ex);
                                    // Logger.getLogger(EraiEP.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                }
                                
                            }
                            // 11) Si el directorio de la variante del ejercicio no existe, lea la siguiente línea del archivo de flujo de ejercicios.
                        } else {
                            // 9) Si el ejercicio no tiene variantes, proceda al caso de excepción 1.
                            // ¿Qué ocurre si un ejercicio no tiene variantes?
                            // 1) Valide si el archivo de descripcion.txt existe en el directorio principal del ejercicio.
                            if (fileExists(exerciseDirPath + "\\" + EXERCISE_DESCRIPTION_FILE_NAME)) {
                                // 3) Si el archivo de descripcion.txt existe, entonces muestre el contenido de descripcion.txt al estudiante, tal cual como está escrito allí.
                                try {
                                    readDescriptionFile(exerciseNumber);
                                } catch (IOException ex) {
                                    System.out.println("Error al mostrar el ejercicio " + exerciseNumber + ":" + ex);
                                    // Logger.getLogger(EraiEP.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            // 2) Si el archivo de descripcion.txt no existe, pase al paso 4.                            
                            // 4) Valide si el archivo de visibilidad.txt existe en el directorio principal.
                            if (fileExists(exerciseDirPath + "\\" + EXERCISE_TEST_CASE_VISUALIZATION_FILE_NAME)) {
                                // 5) Si el archivo de visibilidad.txt existe en el directorio principal, entonces lea ese archivo y muestre el contenido 
                                    // de entradaN.txt y salidaN.txt al estudiante, donde N es el número del caso de prueba mostrado en las líneas del archivo 
                                    // visibilidad.txt, uno por línea, siempre y cuando existan los dos archivos entradaN.txt y salidaN.txt en el directorio de 
                                    // variante del ejercicio.
                                readVisibleTestCaseFiles(exerciseNumber, exerciseDirPath);                                
                            }
                            // 6) Si el archivo de visibilidad.txt no existe en el directorio principal, no muestre casos de prueba al estudiante.
                            
                            // 7) Vaya al paso 25, que está en el algoritmo, por fuera de este caso de excepción.
                            // 25) Muestre un mensaje al estudiante, indicando los comandos que puede usar para salir del programa de 
                            //   evaluador automático, y para enviar un código Python al evaluador automático para evaluar el ejercicio.
                            // 26) (…) : Muchas cosas ocurren aquí que no son relevantes ahora.
                            try {
                                showWaitingForUserInputMessage(inputStreamReader, exerciseNumber, exerciseDirPath);
                            } catch (IOException ex) {
                                System.out.println("Error durante la ejecución de la aplicación: " + ex);
                                // Logger.getLogger(EraiEP.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                    // 7) Si no existe el directorio del ejercicio, lea la siguiente línea del archivo de flujo de ejercicios.                    
                    
                }                
            } catch (IOException ex) {
                System.out.println("Error al tratar de leer el archivo de flujo de ejercicios: " + ex);
                // Logger.getLogger(EraiEP.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException ex) {
                        System.out.println("Error al tratar de cerrar el flujo de datos de entrada: " + ex);
                    }
                }
            }
        }        
        // 2) Si no existe este archivo, no muestre ejercicio alguno en pantalla.
        
        /*
        try {
            ArrayList<Integer> availableOutputVariantNumbers = getAvailableOutputVariantNumbers();
            int selectedVariant = chooseRandomOutputVariant(availableOutputVariantNumbers);
            saveSelectedVariantInFile(EXERCISE1_DIR + "\\" + EXERCISE_CHOSEN_OUTPUT_VARIANT_FILE_NAME, 
                    selectedVariant);
            readDescriptionFile();
            readVisibleTestCaseFiles();
        } catch (IOException ex) {
            System.out.println("Error al mostrar el ejercicio: " + ex);
            // Logger.getLogger(EraiEP.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            showWaitingForUserInputMessage();
        } catch (IOException ex) {
            System.out.println("Error durante la ejecución de la aplicación: " + ex);
            // Logger.getLogger(EraiEP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        */
    }
    
    public static ArrayList<Integer> getAvailableOutputVariantNumbers() throws IOException {
        ArrayList<Integer> outputVariantNumbers = new ArrayList<>();
        /*
        Para que existan variantes de función, proceso u operación que puedan usarse en un ejercicio de programación,
        se deben cumplir con las siguientes condiciones:
        1) Debe existir un directorio llamado var_salidas_cp en el directorio del ejercicio (e.g., ejercicio1)
        2) Debe existir al menos un directorio en el directorio var_salidas_cp que tenga como nombre "var<num>",
            donde <num> es un número entero positivo (1, 2, ..., n).
        3) Para cada archivo llamado entrada<num>.txt en el directorio del ejercicio (e.g., ejercicio1), debe existir 
            un archivo de salida en el directorio var_salidas_cp/var<num> que se llame salida<num>.txt. Si hay un solo
            archivo de salida que no existe para un archivo de entrada existente en un directorio var_salidas_cp/var<num>,
            entonces el número <num> será desestimado de los números de variantes existentes para el ejercicio. Es decir,
            no es una variante válida.
            En contraste, si para cada archivo de entrada existe un archivo de salida en el directorio var_salidas_cp/var<num>,
            entonces el número <num> será tenido en cuenta como un número de variante válida para el ejercicio.
            En este caso, esta función debe devolver true.
        */
        if (directoryExists(EXERCISE1_OUTPUT_VARIANTS_DIR)) {
            // El directorio llamado var_salidas_cp en el directorio del ejercicio (e.g., ejercicio1) existe.
            ArrayList<String> outputVariantPathResults = new ArrayList<>();
            Stream<Path> outputVariantDirSearchResults = Files.find(Paths.get(EXERCISE1_OUTPUT_VARIANTS_DIR), 1, 
                    (path, basicFileAttributes) -> path.toFile().isDirectory() && path.toFile().getName().matches("var\\d+"));
            outputVariantDirSearchResults.forEach(path -> outputVariantPathResults.add(path.getFileName().toString()));
            if (!outputVariantPathResults.isEmpty()) {
                // Existe al menos un directorio que tenga como nombre var<num> en el directorio var_salidas_cp.
                ArrayList<String> inputDataSetFilePathResults = new ArrayList<>();                
                // Encontrar archivos de entrada cuyo nombre sea entrada<num>.txt en el directorio del ejercicio (e.g., ejercicio1).
                Stream<Path> inputDataSetFileSearchResults = Files.find(Paths.get(EXERCISE1_DIR), 1, 
                    (path, basicFileAttributes) -> path.toFile().isFile() && path.toFile().getName().matches("entrada\\d+\\.txt"));
                inputDataSetFileSearchResults.forEach(path -> inputDataSetFilePathResults.add(path.getFileName().toString()));
                // Por cada directorio encontrado asociado a una variante, encontrar archivos de salida cuyo nombre sea salida<num>.txt 
                //  en el directorio var_salidas_cp/var<num>
                for (int i = 0; i < outputVariantPathResults.size(); i++) {
                    ArrayList<String> outputDataSetFilePathResults = new ArrayList<>();
                    String p = outputVariantPathResults.get(i);
                    int varNum = Integer.parseInt(p.substring(3,p.length()));
                    Stream<Path> outputDataSetFileSearchResults = Files.find(Paths.get(
                            EXERCISE1_OUTPUT_VARIANTS_DIR + "\\" + EXERCISE_OUTPUT_VARIANT_NAME_PART + varNum), 1, 
                    (path, basicFileAttributes) -> path.toFile().isFile() && path.toFile().getName().matches("salida\\d+\\.txt"));
                    outputDataSetFileSearchResults.forEach(path -> outputDataSetFilePathResults.add(path.getFileName().toString()));
                    
                    // Comparar el número de archivos encontrados con nombre entrada<num>.txt, con los que tienen nombre salida<num>.txt.
                    // Si estos números son iguales, entonces este es una variante de salida válida para el ejercicio de programación.
                    if (inputDataSetFilePathResults.size() == outputDataSetFilePathResults.size()) {
                        outputVariantNumbers.add(varNum);
                    }
                }                
            }
        }
        return outputVariantNumbers;
    }
    
    public static int chooseRandomOutputVariant(ArrayList<Integer> availableOutputVariantNumbers) {
        if (availableOutputVariantNumbers != null && availableOutputVariantNumbers.size() >= 1) {
            Random rand = new Random();
            // Obtener un número entre 0 y el tamaño del arreglo que contiene los números de variantes disponibles menos 1.
            int selectedVariant = rand.nextInt(availableOutputVariantNumbers.size());        
            // Añade uno al resultado para obtener un número en el rango requerido [1; número de variantes disponibles]:
            selectedVariant += 1;
            return selectedVariant;
        } else {
            return 0; // En este caso, asúmase que no se usarán variantes en el ejercicio de programación, y la salida
            // de cada entrada en un caso de prueba está en el directorio del ejercicio (e.g., ejercicio1).
        }        
    }
    
    public static boolean saveSelectedVariantInFile (String selectedVariantSavedFilePath, int selectedVariant) throws IOException {
        // Sólo guarde una nueva variante si no existe el archivo variante.txt, o si este existe pero no es un número.
        if (!fileExists(selectedVariantSavedFilePath) || !fileHasOnlyOneIntegerNumberWrittenInFirstLine(selectedVariantSavedFilePath)){
            // El archivo no existe, o si este existe pero no contiene sólo un número entero en la primera línea : Guárdelo.
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedVariantSavedFilePath))) {
                writer.write(""+selectedVariant);
                return true;
            }
        } else {
            // El archivo existe: No lo guarde.
            return false;
        }        
    }
    
    public static boolean readDescriptionFile() throws FileNotFoundException, IOException {
        // https://stackoverflow.com/questions/5868369/how-can-i-read-a-large-text-file-line-by-line-using-java
        // Lectura del archivo de descripción del ejercicio de prueba, línea por línea:
        // Con este patrón, no es necesario cerrar explícitamente las instancias de BufferedReader y FileReader
        // Este patrón evita que el alcance (scope) de la línea incluya a otras partes de la implementación del programa,
        //  sino que sólo es visible dentro del ciclo for.
        
        // Para leer archivos con codificación UTF-8 (encoding) - Para mostrar correctamente acentos y otros caracteres del español:
        /*
        https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html
        Accessing Files
        Paths may be used with the Files class to operate on files, directories, and other types of files. 
        For example, suppose we want a BufferedReader to read text from a file "access.log". 
        The file is located in a directory "logs" relative to the current working directory and is UTF-8 encoded.
             Path path = FileSystems.getDefault().getPath("logs", "access.log");
             BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        */
        try (BufferedReader br = Files.newBufferedReader(FileSystems.getDefault().getPath(
                EXERCISES_DIR_NAME, EXERCISE1_DIR_NAME, EXERCISE_DESCRIPTION_FILE_NAME), 
                StandardCharsets.UTF_8)) {
            for (String line; (line = br.readLine()) != null; ) {
                // Muestra la línea leída del archivo en pantalla.
                System.out.println(line);
            }
            // La línea del archivo no es visible aquí.
            return true;
        }
    }
    
    public static boolean readDescriptionFile(String exerciseNumber) throws FileNotFoundException, IOException {
        // https://stackoverflow.com/questions/5868369/how-can-i-read-a-large-text-file-line-by-line-using-java
        // Lectura del archivo de descripción del ejercicio de prueba, línea por línea:
        // Con este patrón, no es necesario cerrar explícitamente las instancias de BufferedReader y FileReader
        // Este patrón evita que el alcance (scope) de la línea incluya a otras partes de la implementación del programa,
        //  sino que sólo es visible dentro del ciclo for.
        
        // Para leer archivos con codificación UTF-8 (encoding) - Para mostrar correctamente acentos y otros caracteres del español:
        /*
        https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html
        Accessing Files
        Paths may be used with the Files class to operate on files, directories, and other types of files. 
        For example, suppose we want a BufferedReader to read text from a file "access.log". 
        The file is located in a directory "logs" relative to the current working directory and is UTF-8 encoded.
             Path path = FileSystems.getDefault().getPath("logs", "access.log");
             BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        */
        try (BufferedReader br = Files.newBufferedReader(FileSystems.getDefault().getPath(
                EXERCISES_DIR_NAME, EXERCISE_DIR_NAME_PART + exerciseNumber, EXERCISE_DESCRIPTION_FILE_NAME), 
                StandardCharsets.UTF_8)) {
            for (String line; (line = br.readLine()) != null; ) {
                // Muestra la línea leída del archivo en pantalla.
                System.out.println(line);
            }
            // La línea del archivo no es visible aquí.
            return true;
        }
    }
    
    public static boolean assembleExerciseDescriptorsAndReadDescriptionFile(
            String exerciseNumber, String exerciseVariantNumber) throws FileNotFoundException, IOException {
        // https://stackoverflow.com/questions/5868369/how-can-i-read-a-large-text-file-line-by-line-using-java
        // Lectura del archivo de descripción del ejercicio de prueba, línea por línea:
        // Con este patrón, no es necesario cerrar explícitamente las instancias de BufferedReader y FileReader
        // Este patrón evita que el alcance (scope) de la línea incluya a otras partes de la implementación del programa,
        //  sino que sólo es visible dentro del ciclo for.
        
        // Para leer archivos con codificación UTF-8 (encoding) - Para mostrar correctamente acentos y otros caracteres del español:
        /*
        https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html
        Accessing Files
        Paths may be used with the Files class to operate on files, directories, and other types of files. 
        For example, suppose we want a BufferedReader to read text from a file "access.log". 
        The file is located in a directory "logs" relative to the current working directory and is UTF-8 encoded.
             Path path = FileSystems.getDefault().getPath("logs", "access.log");
             BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        */
        
        // Almacene cada línea de descriptores en un ArrayList
        ArrayList<String> exerciseDescriptors = new ArrayList<>();
        
        // Obtenga el contenido de los descriptores del ejercicio
        try (BufferedReader br = Files.newBufferedReader(FileSystems.getDefault().getPath(
                EXERCISES_DIR_NAME, EXERCISE_DIR_NAME_PART + exerciseNumber, 
                EXERCISE_VARIANT_DIR_NAME_PART + exerciseVariantNumber, EXERCISE_VARIANT_DESCRIPTORS_FILE_NAME), 
                StandardCharsets.UTF_8)) {
            for (String line; (line = br.readLine()) != null; ) {
                // Obtiene la línea del archivo de descriptores.txt de la VE
                exerciseDescriptors.add(line);
            }            
        }
        
        try (BufferedReader br = Files.newBufferedReader(FileSystems.getDefault().getPath(
                EXERCISES_DIR_NAME, EXERCISE_DIR_NAME_PART + exerciseNumber, EXERCISE_DESCRIPTION_FILE_NAME), 
                StandardCharsets.UTF_8)) {
            for (String line; (line = br.readLine()) != null; ) {
                // Obtiene la línea del archivo de descripcion.txt
                // System.out.println(line);
                // Por cada línea leída del archivo de descripcion.txt, busque la etiqueta <genai-desc 1> 
                //   y reemplácelo con un descriptor, de acuerdo al número de línea indicado en la etiqueta.
                
                boolean matchFound = true;
                while (matchFound) {
                    // https://www.w3schools.com/java/java_regex.asp
                    // Prueba de búsqueda de expresiones en una línea de cadena de caracteres con expresiones regulares 
                    Pattern pattern = Pattern.compile("<genai-desc\\s+\\d+>");
                    Matcher matcher = pattern.matcher(line);
                    matchFound = matcher.find();
                    if (matchFound) {
                        // System.out.println("Match found");
                        // https://stackoverflow.com/questions/6020384/create-array-of-regex-matches
                        String result = matcher.group();
                        // System.out.println(result);
                        // Obtenga el número de línea de la etiqueta
                        String[] resultSubstrings = result.split("<genai-desc");
                        // System.out.println(resultSubstrings[1].trim());
                        resultSubstrings = resultSubstrings[1].trim().split(">");
                        // System.out.println(resultSubstrings[0].trim());
                        int descriptorToFind = Integer.parseInt(resultSubstrings[0].trim()) - 1;

                        line = line.replaceFirst("<genai-desc\\s+\\d+>", exerciseDescriptors.get(descriptorToFind));
                        // System.out.println(line);
                    }
                }
                // Muestra la línea leída del archivo en pantalla.
                System.out.println(line);                
            }
            // La línea del archivo no es visible aquí.
            return true;
        }
    }
    
    public static boolean readDescriptionFileByChosenVariant(int variantNumber, ArrayList<Integer> possibleVariantNumberList) throws FileNotFoundException, IOException {
        /*
        Si la variante seleccionada es 0, entonces se sobreentiende que no se usarán variantes en el ejercicio, y en este caso,
            el proceso de mostrar la descripción del ejercicio al usuario se debería hacer tal como si no existiesen variantes.
        Pero si el número de variante seleccionado es un número entero positivo que corresponde con alguno de los números de variantes existentes,
            entonces se debe hacer lo siguiente para mostrar la descripción correcta al usuario, de acuerdo con el número de la variante seleccionada:
        1) Se debe leer el archivo de descripción del ejercicio de prueba, línea por línea, pero, mientras se lee línea por línea,
            se debe también hacer el seguimiento de la existencia de etiquetas como esta: <genai-dt 1 capitalize> dentro del archivo de
            descripción.
            El formato de cada etiqueta genai-dt es el siguiente, en este orden:
                a) Un caracter que abre la etiqueta: <genai-dt
                b) Un número que corresponde a la línea de texto del archivo de etiquetas (etiquetas.txt) de cualquier variante a considerar para mostrar la descripción correcta.
                c) 
        */
        
        
        // https://stackoverflow.com/questions/5868369/how-can-i-read-a-large-text-file-line-by-line-using-java
        // Lectura del archivo de descripción del ejercicio de prueba, línea por línea:
        // Con este patrón, no es necesario cerrar explícitamente las instancias de BufferedReader y FileReader
        // Este patrón evita que el alcance (scope) de la línea incluya a otras partes de la implementación del programa,
        //  sino que sólo es visible dentro del ciclo for.
        
        // Para leer archivos con codificación UTF-8 (encoding) - Para mostrar correctamente acentos y otros caracteres del español:
        /*
        https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html
        Accessing Files
        Paths may be used with the Files class to operate on files, directories, and other types of files. 
        For example, suppose we want a BufferedReader to read text from a file "access.log". 
        The file is located in a directory "logs" relative to the current working directory and is UTF-8 encoded.
             Path path = FileSystems.getDefault().getPath("logs", "access.log");
             BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        */
        try (BufferedReader br = Files.newBufferedReader(FileSystems.getDefault().getPath(
                EXERCISES_DIR_NAME, EXERCISE1_DIR_NAME, EXERCISE_DESCRIPTION_FILE_NAME), 
                StandardCharsets.UTF_8)) {
            for (String line; (line = br.readLine()) != null; ) {
                // Muestra la línea leída del archivo en pantalla.
                System.out.println(line);
            }
            // La línea del archivo no es visible aquí.
            return true;
        }
    }
    
    public static boolean readVisibleTestCaseFiles() throws FileNotFoundException, IOException, NumberFormatException {
        // https://stackoverflow.com/questions/5868369/how-can-i-read-a-large-text-file-line-by-line-using-java
        // Lectura del archivo de visibilidad de casos de prueba, línea por línea:
        ArrayList<Integer> numerosCasosPruebaVisibles = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(EXERCISE1_DIR + "\\" + EXERCISE_TEST_CASE_VISUALIZATION_FILE_NAME))) {
            for (String line; (line = br.readLine()) != null; ) {
                // Procesa la línea leída del archivo.
                numerosCasosPruebaVisibles.add(Integer.valueOf(line));
            }
            // La línea del archivo no es visible aquí.
            for (int i = 0; i < numerosCasosPruebaVisibles.size(); i++){
                if (i == 0){
                    System.out.println();
                    System.out.println("Ejemplos de funcionamiento de propuesta de solución:");
                    System.out.println("====================================================");
                } else {
                    System.out.println();
                }
                Integer numeroCasoPruebaVisible = numerosCasosPruebaVisibles.get(i);
                System.out.println("Caso de prueba # " + numeroCasoPruebaVisible);
                // 0-9 (1 caracter): +1 caracter; 10-99 (2 caracteres): +2 caracteres; 100-999 (3 caracteres): +3 caracteres; etc.
                int extraSymbolsNumber = String.valueOf(numeroCasoPruebaVisible).length();
                String extraSymbols = "";
                for (int j = extraSymbolsNumber; j > 0; j--) {
                    extraSymbols += "=";
                }
                System.out.println("=================" + extraSymbols);
                // Leer la entrada del caso de prueba i + 1
                System.out.println("Datos de entrada");
                System.out.println("================");
                readTestCaseInputFile(numeroCasoPruebaVisible);
                // Leer la salida del caso de prueba i + 1
                System.out.println("Datos de salida");
                System.out.println("===============");
                readTestCaseOutputFile(numeroCasoPruebaVisible);
            }
        }
        return false;
    }
    
    public static boolean readVisibleTestCaseFiles(String exerciseNumber, String exerciseDirPath) throws FileNotFoundException, IOException, NumberFormatException {
        // https://stackoverflow.com/questions/5868369/how-can-i-read-a-large-text-file-line-by-line-using-java
        // Lectura del archivo de visibilidad de casos de prueba, línea por línea:
        ArrayList<Integer> numerosCasosPruebaVisibles = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(EXERCISES_DIR + "\\" 
                + EXERCISE_DIR_NAME_PART + exerciseNumber + "\\" + EXERCISE_TEST_CASE_VISUALIZATION_FILE_NAME))) {
            for (String line; (line = br.readLine()) != null; ) {
                // Procesa la línea leída del archivo.
                numerosCasosPruebaVisibles.add(Integer.valueOf(line));
            }
            
            boolean firstVisibleTestCase = true;
            
            // La línea del archivo no es visible aquí.
            for (int i = 0; i < numerosCasosPruebaVisibles.size(); i++){
                Integer numeroCasoPruebaVisible = numerosCasosPruebaVisibles.get(i);
                
                /*
                if (i == 0){
                    System.out.println();
                    System.out.println("Ejemplos de funcionamiento de propuesta de solución:");
                    System.out.println("====================================================");
                } else {
                    System.out.println();
                }
                */
                
                // Validar si los archivos de entrada.txt y salida.txt del caso de prueba existen.
                if (fileExists(exerciseDirPath + "\\" + EXERCISE_TEST_CASE_INPUT_FILE_NAME_PART + numeroCasoPruebaVisible + ".txt")) {
                    if (fileExists(exerciseDirPath + "\\" + EXERCISE_TEST_CASE_OUTPUT_FILE_NAME_PART + numeroCasoPruebaVisible + ".txt")) {
                        if (firstVisibleTestCase) {
                            System.out.println();
                            System.out.println("Ejemplos de funcionamiento de propuesta de solución:");
                            System.out.println("====================================================");
                            firstVisibleTestCase = false;
                        } else {
                            System.out.println();
                        }
                        
                        System.out.println("Caso de prueba # " + numeroCasoPruebaVisible);
                        // 0-9 (1 caracter): +1 caracter; 10-99 (2 caracteres): +2 caracteres; 100-999 (3 caracteres): +3 caracteres; etc.
                        int extraSymbolsNumber = String.valueOf(numeroCasoPruebaVisible).length();
                        String extraSymbols = "";
                        for (int j = extraSymbolsNumber; j > 0; j--) {
                            extraSymbols += "=";
                        }
                        System.out.println("=================" + extraSymbols);
                        // Leer la entrada del caso de prueba i + 1
                        System.out.println("Datos de entrada");
                        System.out.println("================");
                        readTestCaseInputFile(numeroCasoPruebaVisible, exerciseNumber);
                        // Leer la salida del caso de prueba i + 1
                        System.out.println("Datos de salida");
                        System.out.println("===============");
                        readTestCaseOutputFile(numeroCasoPruebaVisible, exerciseNumber);
                    }
                }                
            }
        }
        return false;
    }
    
    public static boolean readVisibleTestCaseFiles(String exerciseNumber, String exerciseVariantNumber, String exerciseVariantDirPath) 
            throws FileNotFoundException, IOException, NumberFormatException {
        // https://stackoverflow.com/questions/5868369/how-can-i-read-a-large-text-file-line-by-line-using-java
        // Lectura del archivo de visibilidad de casos de prueba, línea por línea:
        ArrayList<Integer> numerosCasosPruebaVisibles = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(EXERCISES_DIR + "\\" 
                + EXERCISE_DIR_NAME_PART + exerciseNumber + "\\" 
                + EXERCISE_VARIANT_DIR_NAME_PART + exerciseVariantNumber + "\\" 
                + EXERCISE_TEST_CASE_VISUALIZATION_FILE_NAME))) {
            for (String line; (line = br.readLine()) != null; ) {
                // Procesa la línea leída del archivo.
                numerosCasosPruebaVisibles.add(Integer.valueOf(line));
            }
            
            boolean firstVisibleTestCase = true;
            
            // La línea del archivo no es visible aquí.
            for (int i = 0; i < numerosCasosPruebaVisibles.size(); i++){
                Integer numeroCasoPruebaVisible = numerosCasosPruebaVisibles.get(i);
                
                /*
                if (i == 0){
                    System.out.println();
                    System.out.println("Ejemplos de funcionamiento de propuesta de solución:");
                    System.out.println("====================================================");
                } else {
                    System.out.println();
                }
                */
                
                // Validar si los archivos de entrada.txt y salida.txt del caso de prueba existen.
                if (fileExists(exerciseVariantDirPath + "\\" + EXERCISE_TEST_CASE_INPUT_FILE_NAME_PART + numeroCasoPruebaVisible + ".txt")) {
                    if (fileExists(exerciseVariantDirPath + "\\" + EXERCISE_TEST_CASE_OUTPUT_FILE_NAME_PART + numeroCasoPruebaVisible + ".txt")) {
                        if (firstVisibleTestCase) {
                            System.out.println();
                            System.out.println("Ejemplos de funcionamiento de propuesta de solución:");
                            System.out.println("====================================================");
                            firstVisibleTestCase = false;
                        } else {
                            System.out.println();
                        }
                        
                        System.out.println("Caso de prueba # " + numeroCasoPruebaVisible);
                        // 0-9 (1 caracter): +1 caracter; 10-99 (2 caracteres): +2 caracteres; 100-999 (3 caracteres): +3 caracteres; etc.
                        int extraSymbolsNumber = String.valueOf(numeroCasoPruebaVisible).length();
                        String extraSymbols = "";
                        for (int j = extraSymbolsNumber; j > 0; j--) {
                            extraSymbols += "=";
                        }
                        System.out.println("=================" + extraSymbols);
                        // Leer la entrada del caso de prueba i + 1
                        System.out.println("Datos de entrada");
                        System.out.println("================");
                        readTestCaseInputFile(numeroCasoPruebaVisible, exerciseNumber, exerciseVariantNumber);
                        // Leer la salida del caso de prueba i + 1
                        System.out.println("Datos de salida");
                        System.out.println("===============");
                        readTestCaseOutputFile(numeroCasoPruebaVisible, exerciseNumber, exerciseVariantNumber);
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean readTestCaseInputFile(int number) throws FileNotFoundException, IOException {
        try (BufferedReader br = Files.newBufferedReader(FileSystems.getDefault().getPath(
                EXERCISES_DIR_NAME, EXERCISE1_DIR_NAME, EXERCISE_TEST_CASE_INPUT_FILE_NAME_PART + number + ".txt"), 
                StandardCharsets.UTF_8)) {
            for (String line; (line = br.readLine()) != null; ) {
                // Muestra la línea leída del archivo en pantalla.
                System.out.println(line);
            }
            return true;
        }
    }
    
    public static boolean readTestCaseInputFile(int number, String exerciseNumber) throws FileNotFoundException, IOException {
        try (BufferedReader br = Files.newBufferedReader(FileSystems.getDefault().getPath(
                EXERCISES_DIR_NAME, EXERCISE_DIR_NAME_PART + exerciseNumber, EXERCISE_TEST_CASE_INPUT_FILE_NAME_PART + number + ".txt"), 
                // StandardCharsets.UTF_8)) {
                StandardCharsets.ISO_8859_1)) {
            for (String line; (line = br.readLine()) != null; ) {
                // Muestra la línea leída del archivo en pantalla.
                System.out.println(line);
            }
            return true;
        }
    }
    
    public static boolean readTestCaseInputFile(int number, String exerciseNumber, String exerciseVariantNumber) 
            throws FileNotFoundException, IOException {
        try (BufferedReader br = Files.newBufferedReader(FileSystems.getDefault().getPath(
                EXERCISES_DIR_NAME, EXERCISE_DIR_NAME_PART + exerciseNumber, 
                EXERCISE_VARIANT_DIR_NAME_PART + exerciseVariantNumber, EXERCISE_TEST_CASE_INPUT_FILE_NAME_PART + number + ".txt"), 
                // StandardCharsets.UTF_8)) {
                StandardCharsets.ISO_8859_1)) {
            for (String line; (line = br.readLine()) != null; ) {
                // Muestra la línea leída del archivo en pantalla.
                System.out.println(line);
            }
            return true;
        }
    }
    
    public static boolean readTestCaseOutputFile(int number) throws FileNotFoundException, IOException {
        try (BufferedReader br = Files.newBufferedReader(FileSystems.getDefault().getPath(
                EXERCISES_DIR_NAME, EXERCISE1_DIR_NAME, EXERCISE_TEST_CASE_OUTPUT_FILE_NAME_PART + number + ".txt"), 
                StandardCharsets.UTF_8)) {
            for (String line; (line = br.readLine()) != null; ) {
                // Muestra la línea leída del archivo en pantalla.
                System.out.println(line);
            }
            return true;
        }
    }
    
     public static boolean readTestCaseOutputFile(int number, String exerciseNumber) throws FileNotFoundException, IOException {
        try (BufferedReader br = Files.newBufferedReader(FileSystems.getDefault().getPath(
                EXERCISES_DIR_NAME, EXERCISE_DIR_NAME_PART + exerciseNumber, EXERCISE_TEST_CASE_OUTPUT_FILE_NAME_PART + number + ".txt"), 
                // StandardCharsets.UTF_8)) {
                StandardCharsets.ISO_8859_1)) {
            for (String line; (line = br.readLine()) != null; ) {
                // Muestra la línea leída del archivo en pantalla.
                System.out.println(line);
            }
            return true;
        }
    }
    
    public static boolean readTestCaseOutputFile(int number, String exerciseNumber, String exerciseVariantNumber) 
            throws FileNotFoundException, IOException {
        try (BufferedReader br = Files.newBufferedReader(FileSystems.getDefault().getPath(
                EXERCISES_DIR_NAME, EXERCISE_DIR_NAME_PART + exerciseNumber, 
                EXERCISE_VARIANT_DIR_NAME_PART + exerciseVariantNumber, EXERCISE_TEST_CASE_OUTPUT_FILE_NAME_PART + number + ".txt"), 
                //StandardCharsets.UTF_8)) {
                StandardCharsets.ISO_8859_1)) {
            for (String line; (line = br.readLine()) != null; ) {
                // Muestra la línea leída del archivo en pantalla.
                System.out.println(line);
            }
            return true;
        }
    }
    
    // NOTA: Esta subrutina no se usa en este momento.
    public static String showWaitingForUserInputMessage() throws IOException, Exception {
        System.out.println();
        System.out.println("Para enviar un código en Python (.py) como propuesta para resolver este ejercicio, escriba la palabra send y luego presione la tecla Enter:");
        System.out.println("Para salir de este programa, escriba la palabra exit y luego presione la tecla Enter:");
        String command = null;
        // Enter data using BufferReader
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            File userCodeFile = null;
            while (command == null || !command.equalsIgnoreCase(EXIT_COMMAND)) {
                // Reading data using readLine
                command = reader.readLine();
                // Printing the read line
                // System.out.println(command);
                if (command.equalsIgnoreCase(SEND_COMMAND)) {
                    System.out.println("Por favor elija un archivo .py a enviar.");
                    // GUI para elegir un archivo:                
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Programas de Python 3", "py");
                    fileChooser.setFileFilter(extensionFilter);
                    // Estas dos líneas se usan para que el GUI para elegir un archivo se asocie a un JDialog que siempre aparezca encima de cualquier ventana abierta:
                    JDialog auxiliaryFileChooserDialog = new JDialog();
                    auxiliaryFileChooserDialog.setAlwaysOnTop(true);
                    int returnVal = fileChooser.showOpenDialog(auxiliaryFileChooserDialog);
                    // Al terminar de elegir el archivo, se deben eliminar todos los recursos de este JDialog temporal para que el programa cierre normalmente.
                    auxiliaryFileChooserDialog.dispose();
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        userCodeFile = fileChooser.getSelectedFile();
                        System.out.println("Has elegido este archivo: " + userCodeFile.getName());
                        String newSolutionProposalDirectoryName = generateDirectoryNameByCurrentDateTime();
                        checkAndCreateNewExerciseSolutionProposalDirectory(EXERCISE1_DIR, 
                                newSolutionProposalDirectoryName);
                        System.out.println("El resultado de la evaluación de la propuesta enviada es: " + assessSolutionProposal(userCodeFile, EXERCISE1_DIR, 
                                EXERCISE1_DIR + "\\" + EXERCISE_SOLUTION_PROPOSAL_DIRECTORY_NAME + "\\" + newSolutionProposalDirectoryName));
                        System.out.println();
                        System.out.println("Para enviar otro código en Python (.py) como propuesta para resolver este ejercicio, escriba la palabra send y luego presione la tecla Enter:");
                        System.out.println("Para salir de este programa, escriba la palabra exit y luego presione la tecla Enter:");
                    } else {
                        System.out.println("No se eligió ningún archivo. Por favor, escriba send o exit para continuar.");
                        System.out.println("Para enviar un código en Python (.py) como propuesta para resolver este ejercicio, escriba la palabra send y luego presione la tecla Enter:");
                        System.out.println("Para salir de este programa, escriba la palabra exit y luego presione la tecla Enter:");
                    }                
                } else if (command.equalsIgnoreCase(EXIT_COMMAND)){
                    System.out.println("Saliendo de la aplicación... Gracias por usarla.");
                    return EXIT_COMMAND;
                } else {
                    System.out.println("Comando no reconocido. Por favor, escriba send o exit para continuar.");
                    System.out.println("Para enviar un código en Python (.py) como propuesta para resolver este ejercicio, escriba la palabra send y luego presione la tecla Enter:");
                    System.out.println("Para salir de este programa, escriba la palabra exit y luego presione la tecla Enter:");
                }
            }
        } catch (Exception e) {
            System.out.println("Hubo un problema del programa mientras se esperaba la acción del usuario: " 
                    + e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return ERAIEP_ERROR;
    }
    
    public static String showWaitingForUserInputMessage(BufferedReader inputStreamReader, String exerciseNumber, String exerciseDirPath) throws IOException, Exception {
        System.out.println();
        System.out.println("Para enviar un código en Python (.py) como propuesta para resolver este ejercicio, escriba la palabra send y luego presione la tecla Enter:");
        System.out.println("Para salir de este programa, escriba la palabra exit y luego presione la tecla Enter:");
        String command = null;
        // Enter data using BufferReader
        // BufferedReader reader = null;
        try {
            // reader = new BufferedReader(new InputStreamReader(System.in));
            File userCodeFile = null;
            while (command == null || !command.equalsIgnoreCase(EXIT_COMMAND)) {
                // Reading data using readLine
                // command = reader.readLine();
                command = inputStreamReader.readLine();
                // Printing the read line
                // System.out.println(command);
                if (command.equalsIgnoreCase(SEND_COMMAND)) {
                    System.out.println("Por favor elija un archivo .py a enviar.");
                    // GUI para elegir un archivo:                
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Programas de Python 3", "py");
                    fileChooser.setFileFilter(extensionFilter);
                    // Estas dos líneas se usan para que el GUI para elegir un archivo se asocie a un JDialog que siempre aparezca encima de cualquier ventana abierta:
                    JDialog auxiliaryFileChooserDialog = new JDialog();
                    auxiliaryFileChooserDialog.setAlwaysOnTop(true);
                    int returnVal = fileChooser.showOpenDialog(auxiliaryFileChooserDialog);
                    // Al terminar de elegir el archivo, se deben eliminar todos los recursos de este JDialog temporal para que el programa cierre normalmente.
                    auxiliaryFileChooserDialog.dispose();
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        userCodeFile = fileChooser.getSelectedFile();
                        System.out.println("Has elegido este archivo: " + userCodeFile.getName());
                        String newSolutionProposalDirectoryName = generateDirectoryNameByCurrentDateTime();
                        checkAndCreateNewExerciseSolutionProposalDirectory(exerciseDirPath, 
                                newSolutionProposalDirectoryName);
                        String assessmentResult = assessSolutionProposal(userCodeFile, exerciseDirPath, 
                                exerciseDirPath + "\\" + EXERCISE_SOLUTION_PROPOSAL_DIRECTORY_NAME + "\\" + newSolutionProposalDirectoryName, exerciseNumber);
                        System.out.println("El resultado de la evaluación de la propuesta enviada es: " + assessmentResult);
                        System.out.println();

                        if (assessmentResult.equals(OK_RESULT)) {
                            // return;
                            return OK_RESULT;
                        }

                        System.out.println("Para enviar otro código en Python (.py) como propuesta para resolver este ejercicio, escriba la palabra send y luego presione la tecla Enter:");
                        System.out.println("Para salir de este programa, escriba la palabra exit y luego presione la tecla Enter:");                        
                    } else {
                        System.out.println("No se eligió ningún archivo. Por favor, escriba send o exit para continuar.");
                        System.out.println("Para enviar un código en Python (.py) como propuesta para resolver este ejercicio, escriba la palabra send y luego presione la tecla Enter:");
                        System.out.println("Para salir de este programa, escriba la palabra exit y luego presione la tecla Enter:");
                    }                
                } else if (command.equalsIgnoreCase(EXIT_COMMAND)){
                    System.out.println("Saliendo de la aplicación... Gracias por usarla.");
                    return EXIT_COMMAND;
                } else {
                    System.out.println("Comando no reconocido. Por favor, escriba send o exit para continuar.");
                    System.out.println("Para enviar un código en Python (.py) como propuesta para resolver este ejercicio, escriba la palabra send y luego presione la tecla Enter:");
                    System.out.println("Para salir de este programa, escriba la palabra exit y luego presione la tecla Enter:");
                }
            }
        } catch (Exception e) {
            System.out.println("Hubo un problema del programa mientras se esperaba la acción del usuario: " 
                    + e.getMessage());
        } finally {
            /*
            if (reader != null) {
                reader.close();
            }
            */
        }
        return ERAIEP_ERROR;
    }
    
    public static String showWaitingForUserInputMessage(BufferedReader inputStreamReader, String exerciseNumber, String exerciseVariantNumber, String exerciseVariantDirPath) throws IOException, Exception {
        System.out.println();
        System.out.println("Para enviar un código en Python (.py) como propuesta para resolver este ejercicio, escriba la palabra send y luego presione la tecla Enter.");
        System.out.println("Para salir de este programa, escriba la palabra exit y luego presione la tecla Enter:");
        String command = null;
        // Enter data using BufferReader     
        // BufferedReader reader = null;
        try {
            // reader = new BufferedReader(new InputStreamReader(System.in));
            File userCodeFile = null;
            while (command == null || !command.equalsIgnoreCase(EXIT_COMMAND)) {
                // Reading data using readLine
                // command = reader.readLine();
                command = inputStreamReader.readLine();
                // Printing the read line
                // System.out.println(command);
                if (command.equalsIgnoreCase(SEND_COMMAND)) {
                    System.out.println("Por favor elija un archivo .py a enviar.");
                    // GUI para elegir un archivo:                
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Programas de Python 3", "py");
                    fileChooser.setFileFilter(extensionFilter);
                    // Estas dos líneas se usan para que el GUI para elegir un archivo se asocie a un JDialog que siempre aparezca encima de cualquier ventana abierta:
                    JDialog auxiliaryFileChooserDialog = new JDialog();
                    auxiliaryFileChooserDialog.setAlwaysOnTop(true);
                    int returnVal = fileChooser.showOpenDialog(auxiliaryFileChooserDialog);
                    // Al terminar de elegir el archivo, se deben eliminar todos los recursos de este JDialog temporal para que el programa cierre normalmente.
                    auxiliaryFileChooserDialog.dispose();
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        userCodeFile = fileChooser.getSelectedFile();
                        System.out.println("Has elegido este archivo: " + userCodeFile.getName());
                        String newSolutionProposalDirectoryName = generateDirectoryNameByCurrentDateTime();
                        checkAndCreateNewExerciseSolutionProposalDirectory(exerciseVariantDirPath, 
                                newSolutionProposalDirectoryName);
                        String assessmentResult = assessSolutionProposal(userCodeFile, 
                                exerciseVariantDirPath, 
                                exerciseVariantDirPath + "\\" + EXERCISE_SOLUTION_PROPOSAL_DIRECTORY_NAME + "\\" + newSolutionProposalDirectoryName,
                                exerciseNumber, exerciseVariantNumber);
                        System.out.println("El resultado de la evaluación de la propuesta enviada es: " + assessmentResult);
                        System.out.println();

                        if (assessmentResult.equals(OK_RESULT)) {
                            //return;
                            return OK_RESULT;
                        }

                        System.out.println("Para enviar otro código en Python (.py) como propuesta para resolver este ejercicio, escriba la palabra send y luego presione la tecla Enter.");
                        System.out.println("Para salir de este programa, escriba la palabra exit y luego presione la tecla Enter:");
                    } else {
                        System.out.println("No se eligió ningún archivo. Por favor, escriba send o exit para continuar.");
                        System.out.println("Para enviar un código en Python (.py) como propuesta para resolver este ejercicio, escriba la palabra send y luego presione la tecla Enter.");
                        System.out.println("Para salir de este programa, escriba la palabra exit y luego presione la tecla Enter:");
                    }
                } else if (command.equalsIgnoreCase(EXIT_COMMAND)){
                    System.out.println("Saliendo de la aplicación... Gracias por usarla.");
                    return EXIT_COMMAND;
                } else {
                    System.out.println("Comando no reconocido. Por favor, escriba send o exit para continuar.");
                    System.out.println("Para enviar un código en Python (.py) como propuesta para resolver este ejercicio, escriba la palabra send y luego presione la tecla Enter.");
                    System.out.println("Para salir de este programa, escriba la palabra exit y luego presione la tecla Enter:");
                }
            }
        } catch (Exception e) {
            System.out.println("Hubo un problema del programa mientras se esperaba la acción del usuario: " 
                    + e.getMessage());
        } finally {
            /*
            if (reader != null) {
                reader.close();
            }
            */
        }
        return ERAIEP_ERROR;
    }
    
    public static boolean checkAndCreateExerciseSolutionProposalsDirectory(String exerciseDirectoryPath) throws Exception {
        // Verificar la existencia del directorio principal de ejercicios.
        if (directoryExists(exerciseDirectoryPath + "\\" + EXERCISE_SOLUTION_PROPOSAL_DIRECTORY_NAME)) {
            // El directorio raíz para todas las propuestas de solución del ejercicio existe.
            return true;
        } else {
            // Crea el directorio raíz de todas las propuestas de solución del ejercicio.
            if (createDirectory(exerciseDirectoryPath + "\\" + EXERCISE_SOLUTION_PROPOSAL_DIRECTORY_NAME)) {
                // Crea el directorio raíz de la propuesta de solución que envió el usuario.
                return true;
            } else {
                throw new Exception("Hubo un error en la aplicación al intentar crear el directorio de propuestas de solución para el ejercicio en la ubicación " 
                        + exerciseDirectoryPath);
            }
        }
    }
    
    public static boolean checkAndCreateNewExerciseSolutionProposalDirectory(String exerciseDirectoryPath, 
            String solutionProposalDirectoryName) throws Exception {
        // Verificar la existencia del directorio principal de ejercicios. Crearla, si no existe.
        if (checkAndCreateExerciseSolutionProposalsDirectory(exerciseDirectoryPath)){
            // Verificar la existencia del directorio de la nueva propuesta de solución al ejercicio dado, conforme al nombre dado para ese directorio.
            if (directoryExists(exerciseDirectoryPath + "\\" + EXERCISE_SOLUTION_PROPOSAL_DIRECTORY_NAME + "\\" + solutionProposalDirectoryName)) {
                // El directorio raíz para la nueva propuesta de solución del ejercicio existe.
                return true;
            } else {
                // Crea el directorio raíz de la nueva propuesta de solución del ejercicio.
                if (createDirectory(exerciseDirectoryPath + "\\" + EXERCISE_SOLUTION_PROPOSAL_DIRECTORY_NAME + "\\" + solutionProposalDirectoryName)) {
                    // Crea el directorio raíz para la nueva propuesta de solución que envió el usuario.
                    return true;
                } else {
                    throw new Exception("Hubo un error en la aplicación al intentar crear el directorio de la nueva propuesta de solución hecha por el usuario "
                            + "para el ejercicio en la ubicación " + exerciseDirectoryPath + "\\" + EXERCISE_SOLUTION_PROPOSAL_DIRECTORY_NAME);
                }
            }
        } else {
            return false;
        }
    }
    
    public static String generateDirectoryNameByCurrentDateTime() {
        // Ahora, se debe calcular la fecha/hora del sistema para colocarlo como nombre a la propuesta de solución.
        /*
        System.out.println(Calendar.getInstance().getTime().toString());        
        System.out.println(Calendar.getInstance().get(Calendar.DATE)); // Día del mes
        System.out.println(Calendar.getInstance().get(Calendar.MONTH)); // Mes (El primer mes es 0)
        System.out.println(Calendar.getInstance().get(Calendar.YEAR)); // Año
        System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)); // Hora del día
        System.out.println(Calendar.getInstance().get(Calendar.MINUTE)); // Minuto
        System.out.println(Calendar.getInstance().get(Calendar.SECOND)); // Segundo
        System.out.println(Calendar.getInstance().get(Calendar.MILLISECOND)); // Milisegundo
        */
        int day = Calendar.getInstance().get(Calendar.DATE);
        String dayString = "";
        if (day / 10 == 0) {
            dayString += "0";
        }
        dayString += String.valueOf(day);

        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        String monthString = "";
        if (month / 10 == 0) {
            monthString += "0";
        }
        monthString += String.valueOf(month);

        String yearString = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        String hourString = "";
        if (hour / 10 == 0) {
            hourString += "0";
        }
        hourString += String.valueOf(hour);

        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        String minuteString = "";
        if (minute / 10 == 0) {
            minuteString += "0";
        }
        minuteString += String.valueOf(minute);

        int second = Calendar.getInstance().get(Calendar.SECOND);
        String secondString = "";
        if (second / 10 == 0) {
            secondString += "0";
        }
        secondString += String.valueOf(second);

        return dayString + "_" + monthString + "_" + yearString 
                + "_" + hourString + "_" + minuteString + "_" + secondString;
    }
    
    public static String assessSolutionProposal(File userCodeFile, String exerciseDirectoryPath, 
            String solutionProposalDirectoryPath) throws FileNotFoundException, Exception {
        // Objetivo: Evaluar todos los casos de prueba del ejercicio (Incluso los visibles) y dar retroalimentación inmediata al usuario.
        Map<Integer, String> retroalimentacionesCasosPrueba = new HashMap<>();
        File testCaseDirectory = new File(EXERCISE1_DIR);
        File[] testCaseFiles = testCaseDirectory.listFiles(); // Obtiene el listado de archivos y directorios del directorio de prueba (ejercicio1).
        // Buscar todos los casos de prueba del ejercicio, desde el primero (1)
        // Ver si los archivos entrada<num>.txt y salida<num>.txt existen.
        // Si no existe ningún archivo de caso de prueba, devuelva error.
        // Si existe un archivo de caso de prueba (entrada o salida), pero no su otro archivo correspondiente, entonces devuelva error.
        // ArrayList<File> testCaseFileArrayList = new ArrayList<>();
        for (File file : testCaseFiles) {
            File inputDataFile;
            File expectedOutputFile;
            // testCaseFileArrayList.add(file);            
            if (file.getName().startsWith(EXERCISE_TEST_CASE_INPUT_FILE_NAME_PART)) {
                // Es un conjunto de datos de entrada para un caso de prueba.
                // entrada<numero>.txt
                
                int number = Integer.parseInt(file.getName().substring(7, file.getName().length() - 4));
                System.out.println("Comienza la evaluación del caso de prueba # " + number);
                // Buscar si el archivo con este nombre existe, para verificar que el caso de prueba completo existe.
                String expectedOutputFileName = EXERCISE_TEST_CASE_OUTPUT_FILE_NAME_PART + number + ".txt";
                if (!new File(testCaseDirectory + "\\" + expectedOutputFileName).exists()) {
                    throw new FileNotFoundException("No se encontró el archivo " + expectedOutputFileName + " en el directorio de casos de prueba del ejercicio, "
                            + "pese a que existe el archivo entrada" + number + ".txt. Por lo tanto, no se hará evaluación automática del código enviado hasta corregir esto.");
                }
                inputDataFile = file;
                expectedOutputFile = new File(testCaseDirectory + "\\" + expectedOutputFileName);
                // System.out.println(inputDataFile);
                // System.out.println(expectedOutputFile);
                // Ejecutar programa de Python 3 con los datos de entrada dados.     
                
                // Generar los archivos de salida estándar y error estándar del programa propuesto con base en todos los casos de prueba.
                // System.out.println(exerciseDirectoryPath);
                // System.out.println(solutionProposalDirectoryPath);
                ProcessBuilder pb = new ProcessBuilder(PYTHON_INTERPRETER_COMMAND_NAME, userCodeFile.getAbsolutePath());
                File stdOutputFile = new File(solutionProposalDirectoryPath + "\\" + "salida" + number + ".txt");
                File stdErrorFile = new File(solutionProposalDirectoryPath + "\\" + "error" + number + ".txt");
                pb.redirectInput(inputDataFile);
                pb.redirectOutput(stdOutputFile);
                pb.redirectError(stdErrorFile);
                Process p = pb.start();
                if (p.waitFor() == 0) { // Espera a que termine el subproceso.
                    // El subproceso se terminó normalmente:
                    System.out.println("El programa enviado se ha terminado de ejecutar con el caso de prueba # " + number);
                    // Ahora es momento de comparar archivos, y determinar la retroalimentación al usuario.
                    if (areIdenticalFiles(stdOutputFile.getAbsolutePath(), expectedOutputFile.getAbsolutePath())) {
                        retroalimentacionesCasosPrueba.put(number, OK_RESULT);
                    } else {
                        retroalimentacionesCasosPrueba.put(number, NOT_OK_RESULT);
                    }
                } else {
                    throw new Exception("Hubo un error al hacer la ejecución del programa enviado con el caso de prueba # " + number);
                }
                
                /*
                if (SystemUtils.IS_OS_WINDOWS) {
                } else {
                    throw new UnsupportedOperationException("Aún no se ha implementado la evaluación automática para ser usada en otros "
                            + "sistemas operativos diferentes a Windows. Si desea, notifique esto al administrador de esta aplicación.");
                }
                */
                
            }
        }
        boolean allOK = true;
        for (int key: retroalimentacionesCasosPrueba.keySet()) {
            System.out.println("Caso de prueba # " + key + ": " + retroalimentacionesCasosPrueba.get(key));
            if (retroalimentacionesCasosPrueba.get(key).equals(NOT_OK_RESULT)) {
                allOK = false;
            }
        }
        if (allOK) {
            return OK_RESULT;
        } else {
            return NOT_OK_RESULT;
        }
    }
    
    public static String assessSolutionProposal(File userCodeFile, String exerciseDirectoryPath, 
            String solutionProposalDirectoryPath, String exerciseNumber) throws FileNotFoundException, Exception {
        // Objetivo: Evaluar todos los casos de prueba del ejercicio (Incluso los visibles) y dar retroalimentación inmediata al usuario.
        Map<Integer, String> retroalimentacionesCasosPrueba = new HashMap<>();
        File testCaseDirectory = new File(exerciseDirectoryPath);
        File[] testCaseFiles = testCaseDirectory.listFiles(); // Obtiene el listado de archivos y directorios del directorio de prueba (ejercicio1).
        // Buscar todos los casos de prueba del ejercicio, desde el primero (1)
        // Ver si los archivos entrada<num>.txt y salida<num>.txt existen.
        // Si no existe ningún archivo de caso de prueba, devuelva error.
        // Si existe un archivo de caso de prueba (entrada o salida), pero no su otro archivo correspondiente, entonces devuelva error.
        // ArrayList<File> testCaseFileArrayList = new ArrayList<>();
        for (File file : testCaseFiles) {
            File inputDataFile;
            File expectedOutputFile;
            // testCaseFileArrayList.add(file);            
            if (file.getName().startsWith(EXERCISE_TEST_CASE_INPUT_FILE_NAME_PART)) {
                // Es un conjunto de datos de entrada para un caso de prueba.
                // entrada<numero>.txt
                
                int number = Integer.parseInt(file.getName().substring(7, file.getName().length() - 4));
                System.out.println("Comienza la evaluación del caso de prueba # " + number);
                // Buscar si el archivo con este nombre existe, para verificar que el caso de prueba completo existe.
                String expectedOutputFileName = EXERCISE_TEST_CASE_OUTPUT_FILE_NAME_PART + number + ".txt";
                if (!new File(testCaseDirectory + "\\" + expectedOutputFileName).exists()) {
                    throw new FileNotFoundException("No se encontró el archivo " + expectedOutputFileName + " en el directorio de casos de prueba del ejercicio, "
                            + "pese a que existe el archivo entrada" + number + ".txt. Por lo tanto, no se hará evaluación automática del código enviado hasta corregir esto.");
                }
                inputDataFile = file;
                expectedOutputFile = new File(testCaseDirectory + "\\" + expectedOutputFileName);
                // System.out.println(inputDataFile);
                // System.out.println(expectedOutputFile);
                // Ejecutar programa de Python 3 con los datos de entrada dados.     
                
                // Generar los archivos de salida estándar y error estándar del programa propuesto con base en todos los casos de prueba.
                // System.out.println(exerciseDirectoryPath);
                // System.out.println(solutionProposalDirectoryPath);
                ProcessBuilder pb = new ProcessBuilder(PYTHON_INTERPRETER_COMMAND_NAME, 
                        userCodeFile.getAbsolutePath());
                File stdOutputFile = new File(solutionProposalDirectoryPath + "\\" + "salida" + number + ".txt");
                File stdErrorFile = new File(solutionProposalDirectoryPath + "\\" + "error" + number + ".txt");
                pb.redirectInput(inputDataFile);
                pb.redirectOutput(stdOutputFile);
                pb.redirectError(stdErrorFile);
                Process p = pb.start();
                if (p.waitFor() == 0) { // Espera a que termine el subproceso.
                    // El subproceso se terminó normalmente:
                    System.out.println("El programa enviado se ha terminado de ejecutar con el caso de prueba # " + number);
                    // Ahora es momento de comparar archivos, y determinar la retroalimentación al usuario.
                    if (areIdenticalFiles(stdOutputFile.getAbsolutePath(), expectedOutputFile.getAbsolutePath())) {
                        retroalimentacionesCasosPrueba.put(number, OK_RESULT);
                    } else {
                        retroalimentacionesCasosPrueba.put(number, NOT_OK_RESULT);
                    }
                } else {
                    throw new Exception("Hubo un error al hacer la ejecución del programa enviado con el caso de prueba # " + number);
                }
                
                /*
                if (SystemUtils.IS_OS_WINDOWS) {
                } else {
                    throw new UnsupportedOperationException("Aún no se ha implementado la evaluación automática para ser usada en otros "
                            + "sistemas operativos diferentes a Windows. Si desea, notifique esto al administrador de esta aplicación.");
                }
                */
                
            }
        }
        boolean allOK = true;
        for (int key: retroalimentacionesCasosPrueba.keySet()) {
            System.out.println("Caso de prueba # " + key + ": " + retroalimentacionesCasosPrueba.get(key));
            if (retroalimentacionesCasosPrueba.get(key).equals(NOT_OK_RESULT)) {
                allOK = false;
            }
        }
        if (allOK) {
            return OK_RESULT;
        } else {
            return NOT_OK_RESULT;
        }
    }
    
    public static String assessSolutionProposal(File userCodeFile, String exerciseDirectoryPath, 
            String solutionProposalDirectoryPath, String exerciseNumber, String exerciseVariantNumber) throws FileNotFoundException, Exception {
        // Objetivo: Evaluar todos los casos de prueba del ejercicio (Incluso los visibles) y dar retroalimentación inmediata al usuario.
        Map<Integer, String> retroalimentacionesCasosPrueba = new HashMap<>();
        File testCaseDirectory = new File(exerciseDirectoryPath);
        File[] testCaseFiles = testCaseDirectory.listFiles(); // Obtiene el listado de archivos y directorios del directorio de prueba (ejercicio1).
        // Buscar todos los casos de prueba del ejercicio, desde el primero (1)
        // Ver si los archivos entrada<num>.txt y salida<num>.txt existen.
        // Si no existe ningún archivo de caso de prueba, devuelva error.
        // Si existe un archivo de caso de prueba (entrada o salida), pero no su otro archivo correspondiente, entonces devuelva error.
        // ArrayList<File> testCaseFileArrayList = new ArrayList<>();
        for (File file : testCaseFiles) {
            File inputDataFile;
            File expectedOutputFile;
            // testCaseFileArrayList.add(file);            
            if (file.getName().startsWith(EXERCISE_TEST_CASE_INPUT_FILE_NAME_PART)) {
                // Es un conjunto de datos de entrada para un caso de prueba.
                // entrada<numero>.txt
                
                int number = Integer.parseInt(file.getName().substring(7, file.getName().length() - 4));
                System.out.println("Comienza la evaluación del caso de prueba # " + number);
                // Buscar si el archivo con este nombre existe, para verificar que el caso de prueba completo existe.
                String expectedOutputFileName = EXERCISE_TEST_CASE_OUTPUT_FILE_NAME_PART + number + ".txt";
                if (!new File(testCaseDirectory + "\\" + expectedOutputFileName).exists()) {
                    throw new FileNotFoundException("No se encontró el archivo " + expectedOutputFileName + " en el directorio de casos de prueba del ejercicio, "
                            + "pese a que existe el archivo entrada" + number + ".txt. Por lo tanto, no se hará evaluación automática del código enviado hasta corregir esto.");
                }
                inputDataFile = file;
                expectedOutputFile = new File(testCaseDirectory + "\\" + expectedOutputFileName);
                // System.out.println(inputDataFile);
                // System.out.println(expectedOutputFile);
                // Ejecutar programa de Python 3 con los datos de entrada dados.     
                
                // Generar los archivos de salida estándar y error estándar del programa propuesto con base en todos los casos de prueba.
                // System.out.println(exerciseDirectoryPath);
                // System.out.println(solutionProposalDirectoryPath);
                ProcessBuilder pb = new ProcessBuilder(PYTHON_INTERPRETER_COMMAND_NAME, userCodeFile.getAbsolutePath());
                File stdOutputFile = new File(solutionProposalDirectoryPath + "\\" + "salida" + number + ".txt");
                File stdErrorFile = new File(solutionProposalDirectoryPath + "\\" + "error" + number + ".txt");
                pb.redirectInput(inputDataFile);
                pb.redirectOutput(stdOutputFile);
                pb.redirectError(stdErrorFile);
                Process p = pb.start();
                if (p.waitFor() == 0) { // Espera a que termine el subproceso.
                    // El subproceso se terminó normalmente:
                    System.out.println("El programa enviado se ha terminado de ejecutar con el caso de prueba # " + number);
                    // Ahora es momento de comparar archivos, y determinar la retroalimentación al usuario.
                    if (areIdenticalFiles(stdOutputFile.getAbsolutePath(), expectedOutputFile.getAbsolutePath())) {
                        retroalimentacionesCasosPrueba.put(number, OK_RESULT);
                    } else {
                        retroalimentacionesCasosPrueba.put(number, NOT_OK_RESULT);
                    }
                } else {
                    throw new Exception("Hubo un error al hacer la ejecución del programa enviado con el caso de prueba # " + number);
                }
                
                /*
                if (SystemUtils.IS_OS_WINDOWS) {
                } else {
                    throw new UnsupportedOperationException("Aún no se ha implementado la evaluación automática para ser usada en otros "
                            + "sistemas operativos diferentes a Windows. Si desea, notifique esto al administrador de esta aplicación.");
                }
                */
                
            }
        }
        boolean allOK = true;
        for (int key: retroalimentacionesCasosPrueba.keySet()) {
            System.out.println("Caso de prueba # " + key + ": " + retroalimentacionesCasosPrueba.get(key));
            if (retroalimentacionesCasosPrueba.get(key).equals(NOT_OK_RESULT)) {
                allOK = false;
            }
        }
        if (allOK) {
            return OK_RESULT;
        } else {
            return NOT_OK_RESULT;
        }
    }
    
    public static boolean directoryExists (String directoryStringPath) {
        File f = new File(directoryStringPath);
        return f.isDirectory(); // Esta función también revisa si el directorio existe o no.           
    }
    
    public static boolean createDirectory (String directoryStringPath) {
        File directoryFile = new File(directoryStringPath);  
        // Crear un directorio, usando el método mkdir()
        return directoryFile.mkdir();
    }
    
    public static boolean fileExists (String directoryStringPath) {
        File f = new File(directoryStringPath);
        return f.isFile(); // Esta función también revisa si el directorio existe o no.           
    }
    
    public static boolean fileHasOnlyOneIntegerNumberWrittenInFirstLine (String directoryStringPath) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(
                FileSystems.getDefault().getPath(directoryStringPath),
                StandardCharsets.UTF_8)) {
            int lineCount = 0;
            int linesWithOnlyIntegerNumbers = 0;
            boolean numberInFirstLine = false;
            for (String line; (line = br.readLine()) != null; ) {
                lineCount++;                
                // Muestra la línea leída del archivo en pantalla.
                if (line.matches("\\d+")){
                    // Está escrito un número entero positivo, sin espacios en blanco, en la línea.
                    linesWithOnlyIntegerNumbers++;
                    if (lineCount == 1){
                        numberInFirstLine = true;
                    }
                } else {
                    // No está escrito un número entero positivo, sin espacios en blanco, en la línea.
                    return false;
                }
            }
            return linesWithOnlyIntegerNumbers == 1 && numberInFirstLine == true;
        }
    }
    
    public static boolean areIdenticalFiles (String file1, String file2) throws FileNotFoundException, IOException {
        FileInputStream in = null;
        FileInputStream in2 = null;
        try {
            in = new FileInputStream(file1);
            in2 = new FileInputStream(file2);
            int byteChunk1;
            int byteChunk2;
            
            while ((byteChunk1 = in.read()) != -1) {
                if ((byteChunk2 = in2.read()) != -1) {
                    if (byteChunk1 != byteChunk2) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return true;
    }
}
