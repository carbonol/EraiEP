B = idg.random_float_from_closed_interval_with_fixed_precision_data_gen(0.1, 10.0, 1)
b = idg.random_float_from_closed_interval_with_fixed_precision_data_gen(0.1, 10.0, 1)
h = idg.random_float_from_closed_interval_with_fixed_precision_data_gen(0.1, 10.0, 1)
print(((B + b) / 2.0) * h)