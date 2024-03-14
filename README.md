# EraiEP
### EraiEP: Evaluador y Retroalimentador Automático e Inmediato de Ejercicios de Programación
Este es un evaluador automático de ejercicios de programación que funciona con un enfoque de análisis dinámico basado en casos de prueba. El código de este repositorio fue escrito en Java, y fue diseñado especialmente para la evaluación de ejercicios de programación en Python, así que este programa requiere de un intérprete de Python para poder ser utilizado. Este es un trabajo en progreso.

### Algunos aspectos por corregir

Se ha comprobado recientemente que este programa funciona bien cuando el usuario (estudiante) envía código que puede ser ejecutado por el intérprete de Python, independientemente de que la lógica de programación en el código sea correcta o incorrecta en relación con las salidas esperadas según los casos de prueba definidos para los ejercicios. Sin embargo, el programa termina abruptamente su ejecución cuando el código enviado por el estudiante no puede ejecutarse en el intérprete; esto se debe a la forma en que el programa trata este tipo de casos. Este es un problema que debe corregirse.

Por otro lado, también es prioritario hacer correcciones en el código de este repositorio para que la práctica en el programa continúe cuando se detecte que el código enviado contiene ciclos infinitos.

## Editores, Lenguajes Usados y Detalles del Proyecto en Java

Este programa fue desarrollado principalmente en Apache Netbeans IDE 16, en Windows 10, usando el JDK 17.0.6 (javac 17.0.6) como un proyecto de Java Application con Maven. El nombre de este proyecto Java es EraiEP, el group id asignado fue co.com.alprosoft, y el nombre de paquete asignado, co.com.alprosoft.eraiep.

Para la ejecución de programas de Python (.py) en este evaluador automático, se empleó el intérprete de Python 3.11.1, instalado en Windows 10.

El directorio relativo en donde se albergan los archivos .java de este proyecto es:
EraiEP\src\main\java\co\com\alprosoft\eraiep

Además, note que el proyecto por defecto usa un directorio llamado **ejercicios**, en donde se albergan los ejercicios disponibles a ser evaluados automáticamente. Note que cada directorio en **ejercicios** (e.g., ejercicio1, ejercicio2, etc.) no corresponde a un solo ejercicio, sino a un conjunto de ejercicios resultantes provenientes de un problema raíz. En este momento, el flujo de ejercicios que usa este evaluador automático es fijo, y está determinado por el contenido de **flujo_ejercicios.txt**, ubicado en el directorio **ejercicios**.

## Componentes (Clases)

Este sistema sólo cuenta con una clase llamada **EraiEP** (EraiEP), que corresponde al programa principal.

## Cómo usar este proyecto Java

Estos proyecto se puede ejecutar en el IDE sugerido inicialmente, pero debería poderse ejecutar bien con un compilador de Java. En general, para usar o probar este proyecto, se debe ejecutar la clase principal; es decir, EraiEP.java.

Sin embargo, antes de poder utilizar este programa, es necesario tener en cuenta tres aspectos:

1. Se debe haber instalado un intérprete de Python en el sistema operativo en el que se está utilizando este sistema. En este caso, se recomienda el intérprete de Python 3.11.1, pero podría ser posible usar una versión más actualizada. Sin embargo, entre versiones de Python podrían haber diferencias leves o significativas, así que esto debe tenerse en cuenta al momento de desarrollar o usar el sistema para la evaluación automática. 
2. Este programa no se ha probado en otro sistema operativo diferente al de Windows 10.
3. Dependiendo del ambiente de desarrollo o de producción que se considere con este programa, se debe verificar cuidadosamente las constantes (parámetros) empleados para el funcionamiento de este sistema. Entre otras constantes, se debe verificar en especial:
    1. El directorio raíz del proyecto y el directorio en donde están ubicados los ejercicios.
    2. El nombre del intérprete de Python que se emplea para su ejecución en una terminal, teniendo en cuenta que en este sistema se realizan llamados al intérprete de Python como un subproceso. Esto puede realizarse en Windows mediante el comando cmd, pero también se puede realizar de forma dinámica durante la ejecución de este sistema, para la evaluación automática de ejercicios. Este aspecto es especialmente importante, teniendo en cuenta que el nombre del intérprete de Python no es necesariamente **python**.

Para verificar el directorio raíz del proyecto y el directorio en donde están ubicados los ejercicios, se puede revisar el siguiente código en EraiEP.java:
```
    public static final String ROOT_DIR = System.getProperty("user.dir");    
    public static final String EXERCISES_DIR = ROOT_DIR + "\\" + "ejercicios";
    public static final String EXERCISES_DIR_NAME = "ejercicios";
```
Para verificar el nombre del intérprete de Python, se puede revisar el siguiente código:
```
    // Nombre de comando para ejecutar el intérprete de Python en Windows (cmd)
    // Nota: Este nombre puede diferir dependiendo de cómo se haya instalado Python en Windows (por ejemplo).
    // Posibles nombres: python, python3, python3.x (siendo x el número menor - minor - de la versión del lenguaje).
    public static final String PYTHON_INTERPRETER_COMMAND_NAME = "python";
    // public static final String PYTHON_INTERPRETER_COMMAND_NAME = "python3";
```

Finalmente, como referencia, las respuestas correctas a 11 de los ejercicios de ejemplo se encuentran en el directorio del repositorio llamado **prueba_codigos_python**. Estos archivos pueden ser útiles para probar el funcionamiento del evaluador automático con estos 11 ejercicios que también son mostrados en el archivo **flujo_ejercicios.txt**.

## Licencia

MIT.

## Autor

Leandro Alejandro Niebles Carbonó (GitHub: carbonol).
