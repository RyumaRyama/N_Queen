# N_Queen 考察
SoftComputing

- 実行時間を見ると、c_ave.txtが$n^{2}$的に増加していて、java_ave.txtとc_opt_ave.txtが$n \log n$的に増加している。
- 上記の増加の差が視覚的に見え始めるのは、N=150を超える時。
- すなわち、N=150を超える実験の場合は、java_ave.txt, c_opt_ave.txtで行う方が良い。
- したがって、大規模なデータ量を扱うならば、java, cを用いたほうが良く、小規模なデータ量を扱うならば、ライブラリが豊富なpythonを用いた方が良い。
