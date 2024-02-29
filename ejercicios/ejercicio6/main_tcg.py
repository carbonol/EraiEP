# Configuración inicial para generar archivos de entrada de este programa - NO MODIFICAR
import sys
input_data_list = []
input_data_file_route = None
if (len(sys.argv) == 2):
	input_data_file_route = sys.argv[1]
# Fin de configuración inicial para generar archivos de entrada de este programa - NO MODIFICAR
# Importación del módulo de funciones generadoras - NO MODIFICAR
from pydoc import importfile
idg = importfile('C:/Users/leand/Documents/Python3Projects/PythonInputInstrToFunctConverter/input_data_generators.py')
sequential_idg_list = []
for i in range(2): sequential_idg_list.append([0])
# Fin de la importación del módulo de funciones generadoras - NO MODIFICAR
a = idg.random_int_from_closed_interval_data_gen(1, 101)
input_data_list.append(a)
b = idg.random_int_from_closed_interval_data_gen(1, 101)
input_data_list.append(b)
if (a < b):
    temp = a
    a = b
    b = temp
q1 = a // b
r1 = a % b
if (r1 != 0):
    q2 = b // r1
    r2 = b % r1
    if (r2 != 0):
        q = r1 // r2
        r3 = r1 % r2
        while (r3 != 0):
            r1 = r2
            r2 = r3
            q = r1 // r2
            r3 = r1 % r2
        print(r2)
    else:
        print(r1)
else:
    print(b)
# Configuración final para generar archivos de entrada de este programa - NO MODIFICAR
if (input_data_file_route != None and input_data_file_route.endswith('.txt')):
	with open(input_data_file_route, mode='w', encoding='utf-8') as input_file:
		counter = len(input_data_list) # Variable auxiliar para no dejar líneas en blanco en el archivo .txt
		for input_data in input_data_list:
			if (counter != 1):
				input_file.write(str(input_data) + '\n')
			else:
				input_file.write(str(input_data))
			counter -= 1
# Fin de configuración final para generar archivos de entrada de este programa - NO MODIFICAR
