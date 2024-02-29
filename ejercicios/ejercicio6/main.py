# Importación del módulo de funciones generadoras - NO MODIFICAR
from pydoc import importfile
idg = importfile('C:/Users/leand/Documents/Python3Projects/PythonInputInstrToFunctConverter/input_data_generators.py')
sequential_idg_list = []
for i in range(2): sequential_idg_list.append([0])
# Fin de la importación del módulo de funciones generadoras - NO MODIFICAR
a = idg.random_int_from_closed_interval_data_gen(1, 101)
b = idg.random_int_from_closed_interval_data_gen(1, 101)
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