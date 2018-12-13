# nti_image_filters
## Само задание: 

### Предварительная подготовка изображения
Важным шагом в алгоритмах распознавания объектов на цифровых изображениях (фотографиях или кадрах видеопотока) является предварительная подготовка. Например, алгоритм определения лица с помощью гистограмм направленных градиентов работает только с изображением в градациях серого.

Существует несколько способов перевести цветное изображение в градации серого цвета. Если исходить, что исходное изображение кодируется интенсивностями трех цветовых каналов: красным, зеленым и синим (R, G и B), то можно предложить следующие преобразования.

### Среднее арифметическое
Яркость итогового серого пикселя вычисляется как среднее арифметическое трех цветовых каналов:

P=R+G+B3Но поскольку данная характеристика не соответствует человеческому восприятия цвета, то используются подходы, описанные ниже.
### Средневзвешенное
Данные способ перевода используется при переводе из цветовой модели RGB в модель YUV. Для получения свечения пикселя (Y) из компонент R, G и B вводятся коэффициенты. Поскольку сама цветовая модель YUV пришла из телевидения, коэффициенты, используемые для расчета свечения, выбраны из соображений коррекции люминисцентов цветного монитора. Дело в том, что из базовых цветов, взятых в одинаковом количестве, человеческих глаз сперва выделяет зеленый, затем красный, а уже потом синий. Подразумевается, что когда зеленый и синий цвета излучаются монитором в одинаковом количестве, зеленый, тем не менее, выглядит ярче. Поэтому преобразование в градации серого цвета путем вычисления среднего арифметического цветовых компонент не отражает воспринимаемую человеком яркость оригинала. Для этого используется средневзвешенная величина. (http://www.linuxlib.ru/gimp/gimp/node150.html)

Y=0.299⋅R+0.587⋅G+0.114⋅B
### Поиск ближайшей точки на нейтральной оси
Если представить, цветовое пространство, кодируемое компонентами R, G и B в виде куба, то диагональ куба называют нейтральной осью. Тогда можно ввести понятие светимости, для определения которой выбирается ближайшая точка на нейтральной оси, соответствующая конкретным компонентам RGB.

L=max(R,G,B)+min(R,G,B)2
### Определение величины яркости
В цветовой модели HSV компонента V соответствует величине яркости. Величина яркости определяется как максимальная интенсивость одной из компонент RGB:

V=max(R,G,B)Еще одним преобразованием, которое может использоваться перед применением алгоримтов распознавания, является уменьшение цифрового шума изображения. Для подавления шумов применяют различные алгоритмы, также именуемые ''фильтры''. При этом следует помнить, что применение шумоподавляющих фильтров всегда негативно сказывает на детализации изображения. Поэтому фильтры параметры фильтров необходимо тщательно подбирать под ту цель, которую необходимо достичь с их помощью.
Наиболее простыми фильтрами являются:

фильтр, основанный на вычислении среднего геометрического
медианный фильтр
Оба этих фильтра оперируют плавающим окном - область пикселей вокруг вычисляемого на основе алгоритма фильтрации пикселя (включая его самого). Окно итеративно перемещается по кадру, обходя тем самым все пиксели кадра и вычисляя их новые значения.
### Фильтр с вычислением среднего геометрического
Алгоритм фильтра перемножает все значения пикселей плавающего окна и затем вычисляет корень от полученного числа.
Степень корня определяется размером плавающего окна.

Например, для окна размером 3×3 с содержимым, представленном на таблице ниже (значения яркости соответствующих пикселей), будет использоваться 9ая степень (3⋅3=9).



Таким образом, значение яркости пикселя, находящегося по центру окна, заменяется на полученное значение.

При сдвиге окно продолжает оперировать изначальными, а не вычисленными заново значениями яркости пикселей. Вычисление значений крайних пикселей по периметру изображения не происходит.

При работе с цветным изображением закодированном с помощью модели RGB фильтр применяется по-отдельности к каждому каналу.
### Медианный фильтр
В данном фильтре значения яркостей пикселей из плавающего окна сортируются, после чего берется значение из середины списка, полученного после сортировки.

Для значений пикселей, представленных в таблице выше, значение пикселя Px,y будет определяться следующим образом:

Image: https://ucarecdn.com/0fd73113-b66e-43ff-b6bc-3ec1af380553/﻿﻿

Px,y=145Напишите программу, которая бы подавляла шумы на изображении и после чего переводила бы его в градации серого.﻿﻿
### Формат входных данных
В первой строке входного файла содержится два числа W и H - размер изображение по горизонтали и вертикали (16≤W,H≤64).

Последующая строка содержит W×H наборов шестнадцатиричных символов. Каждый набор - пиксель, описанный в модели RGB, где первые (старшие) два символа кодируют красную компоненту пикселя, следующие два символа кодируют зеленую компоненту, а последние (младшие) два символа кодируют синюю компоненту. Первые W наборов - это первая строка изображения, следующие W наборов - вторая строка и т.д.

Следующая строка определяет алгоритм подавления шума F, который должен быть применен к изображению. F может принимать следующие значения:

1 - фильтр, основанный на вычислении среднего геометрического;
2 - медианный фильтр.
Последняя строка задает алгоритм ''обесцвечивания'' изображение D. D может принимать следующие значения:

1 - использование cреднего арифметического;
2 - использование средневзвешенного;
3 - использование ближайшей точки на нейтральной оси;
4 - определение величины яркости.
### Формат выходных данных
Выведите в одной строке два целых числа - максимально темное и максимально светлое значения пикселей после проведенных преобразований - подавления шума и ''обесцвечивания''.

### Примечание
При реализации ﻿подавления шума используйте плавающее окно 3×3. При получении не целых результатов - отбрасывайте дробную часть.﻿

### Sample Input:

16 16
11211e 102015 132411 101d09 273126 323635 333439 363437 3f3b38 38342b 2f2822 1e1516 1f161b 302b28 333021 35331c 18211e 1b251d 182217 1a2215 1b2117 21201b 3e3936 4c413f 4e3f3c 63504c 4d3a36 402e2e 433535 312926 322f26 2e2f21 1a1915 1d1c1a 1c1a1b 21201e 35302a 443a30 756357 ae938a d6b5b0 d9b3b2 c49e9d a78885 8d7972 524741 35312e 2f3032 291e18 312527 32252e 2e1f26 564442 baa497 d3b6a4 f3ccbd ffd6d1 ffd3d6 fbc9c8 eabfb9 c2a49a 685750 221c1e 1f1d28 2f1e17 5d494a 8c777e 624851 cfb1b1 d1afa3 c79e8c d0a092 c28b86 edb5b4 f1bbb9 e6bab1 d6b4a8 816861 261619 392b38 29160f 99847f bea2a1 9b797a deb6b4 d1a49e b18177 a7756c 9e6d68 ab7b77 e3b8b1 b79188 927066 84665e 482a28 694c4e 2d1e19 746057 bea297 d7b1a6 eabbb5 e8b5b4 e6b2b4 b88886 ad847e c2a097 dabcb2 795a55 7a5954 7d564f 582e22 b18373 261b19 837069 dabfae e8c2af f1bfb4 ffd0d1 ffcfd5 f0c2c5 e2bfbb edd5cb e1cac2 947b77 876564 6b4037 93614a c28c6a 2e2329 7a6b66 cbb1a2 e3bda8 f1c0b1 ffc8c3 f4bdc0 cc9b9e d9b3b2 cfb3af dcc4c2 a98f90 ae8c8b 956b5f 9b6a4c ba875c 4a3f4d c2b3b8 d2bbb5 e2c1b2 e8baab e5b1a4 d49d96 d29f9c e8baba bc9397 896569 937172 ba9992 9a7764 94724f 9a7549 3e3344 bbadbc c8b3bc bfa3a0 e4beb1 e5b8a3 ce9b88 cf968d d09597 bc848f 88565f 946c6c 9d8072 8a765b 8e805b 7a7148 32293e 5a4e64 d9cadd ae99a2 c2a7a0 cea996 d3a58e f1bcae e7abab bd818b 966368 936e66 88775d 898965 808f68 627750 373445 2d283c 776f86 c1b4c5 998688 ab9085 ba9686 ebbfb4 f4c1be d6a4a7 b48d88 85705b 6e704b 758c60 6d9769 619167 1f2630 2b2f3b 323443 84808f b3a9b4 7a696f 876f6f 987b77 ab8c87 ac9186 8b7f69 616743 6b8558 6a9965 5a9964 4c925e 243336 243235 283138 363a46 9996a9 aca2ba 736678 594c53 4f493d 515437 576740 5e7e4f 689866 54955d 439155 4ba05f 1b2f2d 1c2f2b 263434 242d36 3e3e56 b6b0d2 bab3d4 7c7a88 4c5447 495d38 6d8f5c 6b9c65 52915c 509b62 449958 439f56
2
1
### Sample Output:

18 207
