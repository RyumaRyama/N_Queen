set terminal svg
set ylabel 'time(sec)'
set xlabel 'N'
set title 'ExecutionSpeed'
set output "output_java_c.svg"
plot "java_ave.txt" with lp,"c_ave.txt" with lp,"c_opt_ave.txt" with lp
set output "output_py.svg"
plot "py2_ave.txt" with lp,"py3_ave.txt" with lp
set output "output.svg"
plot "py2_ave.txt" with lp,"py3_ave.txt" with lp,"java_ave.txt" with lp,"c_ave.txt" with lp,"c_opt_ave.txt" with lp
