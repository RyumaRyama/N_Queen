set terminal svg
set ylabel 'time(sec)'
set xlabel 'N'
set title 'ExecutionSpeed'
set output "output_java_c.svg"
plot "calcTimeCandJava/java_ave.txt" with lp,"calcTimeCandJava/c_ave.txt" with lp,"calcTimeCandJava/c_opt_ave.txt" with lp
