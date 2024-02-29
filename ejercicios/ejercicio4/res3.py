# Importación del módulo de funciones generadoras - NO MODIFICAR
from pydoc import importfile
idg = importfile('C:/Users/leand/Documents/Python3Projects/PythonInputInstrToFunctConverter/input_data_generators.py')
sequential_idg_list = []
for i in range(1): sequential_idg_list.append([0])
# Fin de la importación del módulo de funciones generadoras - NO MODIFICAR
n = idg.random_int_from_closed_interval_data_gen(-5, 10)
if (n <= 0):
    print('ERROR')
else:
    preant = 1
    ant = 2
    if (n == 1):
        print(preant)
    if (n == 2):
        print(ant)
    if (n >= 3):
        for j in range(3, n + 1, 1):
            sig = ant + preant
            print(sig)
            preant = ant
            ant = sig