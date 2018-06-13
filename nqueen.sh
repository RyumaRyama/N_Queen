#!/usr/bin/env sh

if ls *.txt > /dev/null 2>&1 ;
    then rm *.txt
fi

echo "C言語(最適化なし)実行速度計測結果(10回)" >> result_c.txt 
#echo "C言語(最適化あり)実行速度計測結果(10回)" >> result_c_opt.txt
#echo "Java言語実行速度計測結果(10回)" >> result_java.txt
#echo "Python言語実行速度計測結果(10回)" >> result_py.txt

# コンパイル
clang $1
#clang -o Prime_opt -O $1
javac $2

for i in `seq 1 10`
do   
    (time -p ./a.out $3) 2>> result_c.txt 
    echo "------------" >> result_c.txt
    
    (time -p java NQueen $3) >> result_java.txt 2>&1 
    echo "------------" >> result_java.txt
    
<< COMMENTOUT
    (time -p ./Prime_opt $4) >> result_c_opt.txt 2>&1 
    echo "------------" >> result_c_opt.txt
    (time -p python3 $3 $4) >> result_py.txt 2>&1 
    echo "------------" >> result_py.txt 
COMMENTOUT

done
