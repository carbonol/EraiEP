# Importación del módulo de funciones generadoras - NO MODIFICAR
from pydoc import importfile
idg = importfile('C:/Users/leand/Documents/Python3Projects/PythonInputInstrToFunctConverter/input_data_generators.py')
sequential_idg_list = []
for i in range(1): sequential_idg_list.append([0])
# Fin de la importación del módulo de funciones generadoras - NO MODIFICAR
n = idg.random_int_from_closed_interval_data_gen(1, 101)
# x // 1 es equivalente a calcular la parte entera de x. 
# Entonces, (n // 2) es equivalente a calcular la parte entera de n / 2.
if ((n // 2) * 2 != n):
    print('El número es impar')
else:
    print('El número es par')