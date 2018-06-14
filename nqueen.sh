#!/usr/bin/env sh

if ls *.txt > /dev/null 2>&1 ;
    then rm *.txt
fi

#変数宣言
gene=0;

#コンパイル
#clang $1
#clang -o Prime_opt -O $1
javac $1

# 10 ~ gene 世代まで測定
for i in `seq 1 3`
do 
    for j in `seq 1 5` # 5回測定した平均
    do  
        gene=`expr $i \* 10`
        
        #(./a.out $gene) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> c_$gene.txt
        #(./Prime_opt $gene) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> c_opt_$gene.txt
        (java NQueen $gene) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> java_$gene.txt 
        (python3 nqueen.py $gene) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> py_$gene.txt
        
    done
done

#初期化
gene=0

#平均を求める
for i in `seq 1 3` #世代
do
    gene=`expr $i \* 10`
    
    #/bin/echo -n $gene" " >> c.txt
    #cat c_$gene.txt | awk '{x++;sum+=$1}END {print sum/x}' >> c_ave.txt
    
    #/bin/echo -n $gene" " >> c_opt_ave.txt
    #cat c_opt_$gene.txt | awk '{x++;sum+=$1}END {print sum/x}' >> c_opt_ave.txt
    
    /bin/echo -n $gene" " >> java_ave.txt
    cat java_$gene.txt | awk '{x++;sum+=$1}END {print sum/x}' >> java_ave.txt
    
    /bin/echo -n $gene" " >> py_ave.txt
    cat py_$gene.txt | awk '{x++;sum+=$1}END {print sum/x}' >> py_ave.txt
done

