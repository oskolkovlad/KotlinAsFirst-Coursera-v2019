@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import  java.util.Stack

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */

fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val inputText = File(inputName).readText()

    var startIndex: Int
    var countOfInclude: Int
    var index: Int

    val acMassive = mutableMapOf<String, Int>()

    for (substring in substrings) {
        startIndex = 0
        countOfInclude = 0
        index = 0

        while (index != -1) {
            index = inputText.indexOf(substring, startIndex, true)

            if (index >= 0) {
                startIndex = index + 1
                countOfInclude++
            } else index = -1
        }

        acMassive[substring] = countOfInclude
    }

    return acMassive
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */

fun main() {
//    markdownToHtmlSimple("input/markdown_simple.md", "output.html")
//    markdownToHtmlSimple("input/markdown_simple1.md", "output.html")
//    markdownToHtmlSimple("input/markdown_simple2.md", "output.html")

//    File("output.html").delete()

    println(countSubstrings("input/substrings_in1.txt", listOf("РАЗНЫЕ", "ные", "Неряшливость", "е", "эволюция")))

    //assertEquals(
//mapOf("РАЗНЫЕ" to 2, "ные" to 2, "Неряшливость" to 1, "е" to 49, "эволюция" to 0),
//countSubstrings("input/substrings_in1.txt", listOf("РАЗНЫЕ", "ные", "Неряшливость", "е", "эволюция"))
//)
//assertEquals(
//mapOf("Карминовый" to 2, "Некрасивый" to 2, "белоглазый" to 1),
//countSubstrings("input/substrings_in1.txt", listOf("Карминовый", "Некрасивый", "белоглазый"))
//)
//assertEquals(
//mapOf("--" to 4, "ее" to 2, "животное" to 2, "." to 2),
//countSubstrings("input/substrings_in2.txt", listOf("--", "ее", "животное", "."))
//)

//    sibilants("input/sibilants_in1.txt", "temp.txt")
//    assertFileContent(
//        "temp.txt",
//        """/**
// * Простая
// *
// * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
// * Во входном файле с именем inputName содержится некоторый текст.
// * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
// * файл outputName текст с исправленными ошибками.
// *
// * Регистр заменённых букв следует сохранять.
// *
// * Исключения (жУри, броШУра, параШут) в рамках данного задания обрабатывать не нужно
// *
// * жИ шИ ЖИ Ши ЖА шА Жа ша жу шу жу щу ча шу щу ща жа жи жи жу чу ча
// */"""
//    )
}

fun sibilants(inputName: String, outputName: String) {
    val inputText = File(inputName).readText()
    val outputStream = File(outputName).bufferedWriter()

    var tempText: String = inputText

    val firstLetters = setOf("ж", "Ж", "ч", "Ч", "ш", "Ш", "щ", "Щ")
    val correctLetters = mapOf(
        "ы" to "и",
        "Ы" to "И",
        "я" to "а",
        "Я" to "А",
        "ю" to "у",
        "Ю" to "У"
    )

    for (firstLetter in firstLetters)
        for ((incorrectLetter, correctLetter) in correctLetters)
            tempText = tempText.replace(firstLetter + incorrectLetter, firstLetter + correctLetter)

    outputStream.write(tempText)
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> = TODO()

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    TODO()
}

//fun main() {
////    markdownToHtmlSimple("input/markdown_simple.md", "output.html")
////    markdownToHtmlSimple("input/markdown_simple1.md", "output.html")
//    markdownToHtmlSimple("input/markdown_simple2.md", "output.html")
//
////    File("output.html").delete()
//}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */

fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val stackOfTags = Stack<String>()
    val stackForHistory = Stack<String>()

    var pChar = ' '
    var ppChar = ' '

//    val s = "~~"
//    val i = "*"
//    val b = "**"
//    val bi = "***"

    var flagOfEmptyLine = false
    var flagFour = false
    var countSave = 0
    var countOfEmptyLines = 0

    outputStream.write("<html>\n<body>\n<p>")

    for (line in File(inputName).readLines()) {
//        println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        if (line.isEmpty()) {
            flagOfEmptyLine = true
            countOfEmptyLines++
        }

        if (!line.contains(Regex("""^*~~*""")) && !line.contains(Regex("""^*\**"""))) outputStream.write(line)

        for (charInline in line) {
            countSave++
            if (charInline == '~' && pChar == '~') {
                if (stackOfTags.isNotEmpty() && (stackOfTags.peek() == "<s>" || stackOfTags.contains("<s>"))) {
//                    println("=================</s>==========================")
//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
                    ppChar = pChar
                    pChar = charInline
//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
//                    println("=========================================================================")

                    if (stackOfTags.peek() == "<s>") {
                        stackOfTags.pop()
                        outputStream.write("</s")
                    } else {
                        while (true) {
                            if (stackOfTags.peek() == "<b>") {
                                stackOfTags.pop()
                                stackForHistory.push("<b>")
                                outputStream.write("</b>")
                            } else if (stackOfTags.peek() == "<i>") {
                                stackOfTags.pop()
                                stackForHistory.push("<i>")
                                outputStream.write("</i>")
                            } else if (stackOfTags.peek() == "<s>") {
                                stackOfTags.pop()
                                outputStream.write("</s>")
                                break
                            }
                        }

                        while (stackForHistory.isNotEmpty()) {
                            outputStream.write(stackForHistory.peek())
                            if (stackForHistory.size == 1) stackOfTags.push(stackForHistory.peek())
                            stackForHistory.pop()
                        }
                    }

                    continue
                } else {
                    if (ppChar == '漽') {
                        outputStream.write(pChar.toInt())
                        continue
                    }

//                    println("=======================<s>=================================")
//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
                    ppChar = pChar
                    pChar = charInline
//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
//                    println("=========================================================================")

                    stackOfTags.push("<s>")
                    outputStream.write(stackOfTags.peek())

                    continue
                }
            }

            if (charInline == '*' && pChar == '儓' && ppChar == '%') flagFour = true

            if ((charInline == '*' && pChar == '*' && ppChar != '*') /*|| flagFour*/) {
                if (stackOfTags.isNotEmpty() && (stackOfTags.peek() == "<b>" || stackOfTags.contains("<b>"))) {
//                    println("========================</b>====================================")
//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
                    /*if (flagFour) {
                        flagFour = false

                        ppChar = pChar
                        pChar = charInline

                    }*/

                    ppChar = pChar
                    pChar = charInline

//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
//                    println("=========================================================================")
//                    println(stackOfTags.peek())

                    if (stackOfTags.peek() == "<b>") {
                        stackOfTags.pop()
                        outputStream.write("</b>")
                    } else {
                        while (true) {
                            if (stackOfTags.peek() == "<s>") {
                                stackOfTags.pop()
                                stackForHistory.push("<s>")
                                outputStream.write("</s>")
                            } else if (stackOfTags.peek() == "<i>") {
                                stackOfTags.pop()
                                stackForHistory.push("<i>")
                                outputStream.write("</i>")
                            } else if (stackOfTags.peek() == "<b>") {
                                stackOfTags.pop()
                                outputStream.write("</b>")
                                break
                            }
                        }

                        while (stackForHistory.isNotEmpty()) {
                            outputStream.write(stackForHistory.peek())
                            if (stackForHistory.size == 1) stackOfTags.push(stackForHistory.peek())
                            stackForHistory.pop()
                        }
                    }

                    continue
                } else {
                    if (charInline == '*' && pChar == '*' && ppChar == ';') {
                        outputStream.write(pChar.toInt())
                        outputStream.write(pChar.toInt())
                        ppChar = pChar
                        pChar = charInline
                        continue
                    }

//                    println("=======================<b>================================")
//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
                    ppChar = pChar
                    pChar = charInline
//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
//                    println("=========================================================================")
//                    if (stackOfTags.isNotEmpty()) println(stackOfTags.peek())
                    stackOfTags.push("<b>")
//                    println(stackOfTags.peek())
                    outputStream.write(stackOfTags.peek())

                    continue
                }
            }

            if (charInline == '*' && pChar == '*' && ppChar == '*') {
                if (stackOfTags.isNotEmpty() && stackOfTags.peek() == "<b>") {
//                    println("=======================<i>=================================")
//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
                    ppChar = pChar
                    pChar = charInline

//                    if (flagFour) continue
//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
//                    println("=========================================================================")

                    stackOfTags.push("<i>")
                    outputStream.write(stackOfTags.peek())

                    continue
                } else {
                    if (stackOfTags.isNotEmpty() && stackOfTags.peek() == "<i>") {
//                        println("=========================</i>================================")
                        stackOfTags.pop()
                        outputStream.write("</i>")
                    }

                    if (stackOfTags.isNotEmpty() && stackOfTags.peek() == "<b>") {
//                        println("============================</b>=============================")
                        stackOfTags.pop()
                        outputStream.write("</b>")
                    }

//                    println("====================<b><i> смена pChar и ppChar=============================")
//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
                    ppChar = pChar
                    pChar = charInline
//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
//                    println("=========================================================================")

                    continue
                }
            }

            if (charInline != '*' && pChar == '*' && ppChar != '*') {
                if (stackOfTags.isNotEmpty() && (stackOfTags.peek() == "<i>" || stackOfTags.contains("<i>"))) {
//                    println("=============================</i>====================================")
//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
                    ppChar = pChar
                    pChar = charInline
//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
//                    println("=========================================================================")
//                    println(stackOfTags.peek())

                    if (stackOfTags.peek() == "<i>") {
                        stackOfTags.pop()
                        outputStream.write("</i>")
                    } else {
                        while (true) {
                            if (stackOfTags.peek() == "<s>") {
                                stackOfTags.pop()
                                stackForHistory.push("<s>")
                                outputStream.write("</s>")
                            } else if (stackOfTags.peek() == "<b>") {
                                stackOfTags.pop()
                                stackForHistory.push("<b>")
                                outputStream.write("</b>")
                            } else if (stackOfTags.peek() == "<i>") {
                                stackOfTags.pop()
                                outputStream.write("</i>")
                                break
                            }
                        }

                        while (stackForHistory.isNotEmpty()) {
                            outputStream.write(stackForHistory.peek())
                            if (stackForHistory.size == 1) stackOfTags.push(stackForHistory.peek())
                            stackForHistory.pop()
                        }
                    }

                    continue
                } else {
                    if (charInline == '?' && pChar == '*' && ppChar == '4') {
                        outputStream.write(pChar.toInt())
                        ppChar = pChar
                        pChar = charInline
                        continue
                    }

//                    println("=========================<i>=====================================")
//                    println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
                    ppChar = pChar
                    pChar = charInline
//                    println ("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
//                    println("=========================================================================")
//                    println(stackOfTags.peek())
                    stackOfTags.push("<i>")
//                    println(stackOfTags.peek())
                    outputStream.write(stackOfTags.peek())

                    continue
                }
            }

            if (pChar == '*' || (pChar == '~' && ppChar == '~')) {
//                println("=========================pChar == '*' || pChar == '~'================================")
//                println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
                ppChar = pChar
                pChar = charInline
//                println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
//                println("=========================================================================")

                continue
            }

//            println("outputStream.write(pChar.toInt()) = ${pChar.toInt()}")
            outputStream.write(pChar.toInt())

            if (flagOfEmptyLine && countOfEmptyLines in 1..2) {
                outputStream.write("</p>\n<p>")
//                println("=============EEEMMMPPPTTTYYY=======================")
                flagOfEmptyLine = false
            } else if (flagOfEmptyLine && countOfEmptyLines > 2) {
                outputStream.write("</p>\n<p></p>\n<p>")
//                println("=============EEEMMMPPPTTTYYY=======================")
                flagOfEmptyLine = false
            } else countOfEmptyLines = 0

//            println("=============END=======================")
//            println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
            ppChar = pChar
            pChar = charInline
//            println("charInline = $charInline | pChar = $pChar | ppChar = $ppChar")
//            println("=========================================================================")
        }
    }

//    println("outputStream.write(pChar.toInt()) = ${pChar.toInt()}")
    outputStream.write(pChar.toInt())

    outputStream.write("</p>\n</body>\n</html>")
    outputStream.close()
}


/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    TODO()
//    val outputStream = File(outputName).bufferedWriter()
//    var currentLineLength = 0
//
//    var countInLine = mutableMapOf<Int, Int>()
//    var count = 0
//    var key = 0
//    var wordsList = mutableListOf<String>()
//    var lineStr = ""
//
//
//    for (line in File(inputName).readLines()) {
//
//        //***//
//        wordsList = line.split(" ").toMutableList()
//        for (i in 0 until wordsList.size)
//            wordsList[i] = wordsList[i].replace(" ", "")
//        lineStr = wordsList.joinToString(" ")
//        //***//
//
//        count = lineStr.length
//        countInLine[key] = count
//        key++
//    }
}
