;;"C:\Program Files\Java\jdk-22\bin\java" Main
(print "-------- SUMAS --------")
(defun suma (a b)
    (setq resultado (+ a b))
    (print (format nil "Suma: ~A " resultado))
    resultado)

(defun operacion-matematica (x y)
  (defun imprimir-numeros (n)
    (if (>= n 0)
        (progn
          (imprimir-numeros (- n 1))
          (print n))))
  
  (setq total (suma x y))
  (imprimir-numeros total)
  total)
(write "Ingrese un numero: ")
(setq num1 (read))
(write "Ingrese otro numero: ")
(setq num2 (read))
(print (format nil "Resultado final: ~A" (operacion-matematica num1 num2)))
(print "")

(print "-------- HANOI --------")
(defun hanoi (n a b c)
  (defun imprimir (string) 
    (format t string))
  (if (= n 1)
      (imprimir (format nil "Mover de ~A a ~A ~%" a c ))  
      (progn (hanoi (- n 1) a c b)
        (hanoi 1 a b c) 
        (hanoi (- n 1) b a c))))

(write "Ingrese la cantidad de discos: ")
(hanoi (+ (read) 1) "Torre 1" "Torre 2" "Torre 3")
(print "")

(print "-------- QUICK SORT --------")
(defun filtrar-menores (lista pivote)
  (if (not lista)
      nil
      (append 
        (if (<= (car lista) pivote)
            (list (car lista))
            '())
        (filtrar-menores (cdr lista) pivote))))

(defun filtrar-mayores (lista pivote)
  (if (not lista)
      nil
      (append 
        (if (> (car lista) pivote)
            (list (car lista))
            '())
        (filtrar-mayores (cdr lista) pivote))))

(defun quicksort (lista)
  (if (or (not lista) (not (cdr lista)))  ; caso base si esta vacio o solo tiene un elemento
      lista
      (progn
        (setq pivote (car lista))
        (setq menores (filtrar-menores (cdr lista) pivote))
        (setq mayores (filtrar-mayores (cdr lista) pivote))
        (append (quicksort menores) (list pivote) (quicksort mayores)))))

(write "ingrese una lista '(1 2 3 4...): ")
(print (format nil "Lista ordenada: ~A" (quicksort (eval (read)))))





