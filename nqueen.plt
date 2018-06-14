set terminal aqua
set ylabel 'time(sec)'
set xlabel 'gene'
set title 'ExecutionSpeed'
plot "java_ave.txt" with lp,"py_ave.txt" with lp
