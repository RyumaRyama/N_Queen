set terminal svg
set ylabel 'time(sec)'
set xlabel 'gene'
set title 'ExecutionSpeed'
set output "output.svg"
plot "java_ave.txt" with lp,"c_ave.txt" with lp,"c_opt_ave.txt" with lp
set output "output_py.svg"
plot "py2_ave.txt" with lp,"py3_ave.txt" with lp
