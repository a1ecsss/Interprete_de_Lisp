;;"C:\Program Files\Java\jdk-22\bin\java" Main

(defun hanoi (n a b c)
  (defun imprimir (string) 
    (format t string))
  (if (= n 1)
      (imprimir (format nil "Mover de ~A a ~A ~%" a c ))  
      (progn (hanoi (- n 1) a c b)
        (hanoi 1 a b c) 
        (hanoi (- n 1) b a c))))

(write "Ingrese la cantidad de discos: ")
(hanoi (read) "Torre 1" "Torre 2" "Torre 3")