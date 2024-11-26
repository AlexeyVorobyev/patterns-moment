a. Используйте делегацию вместо наследования

Утверждение "Используйте делегацию вместо наследования" подразумевает, что в некоторых случаях лучше использовать делегирование для достижения повторного использования кода и расширяемости, чем полагаться на наследование. Делегация позволяет объекту использовать функциональность другого объекта, не создавая жесткой связи между классами, как это происходит при наследовании.

Тривиальный пример:

Предположим, у нас есть класс Printer, который отвечает за печать документов:

class Printer {
fun print(document: String) {
println("Printing: $document")
}
}


Вместо того чтобы создавать подкласс DocumentPrinter, который наследует от Printer, мы можем создать класс Document, который будет делегировать печать объекту Printer:

class Document(private val printer: Printer) {
fun printContent(content: String) {
printer.print(content)
}
}


Таким образом, Document использует Printer для выполнения задачи печати, что позволяет нам легко изменять реализацию печати, не меняя сам класс Document.

▎b. Отношение ассоциации в ОПП

Ассоциация — это отношение между двумя классами, которое показывает, как объекты одного класса могут взаимодействовать с объектами другого класса. Ассоциация может быть однонаправленной или двунаправленной.

Разновидности ассоциации:

1. Однонаправленная ассоциация: Один класс знает о другом, но не наоборот.

   class Driver(val name: String)
   class Car(val driver: Driver)


2. Двунаправленная ассоциация: Оба класса знают друг о друге.

   class Driver(val name: String) {
   var car: Car? = null
   }
   class Car(val driver: Driver)


3. Композиция: Это сильная форма ассоциации, где один объект является частью другого и не может существовать без него.

   class Engine
   class Car(val engine: Engine)


4. Агрегация: Это более слабая форма ассоциации, где один объект может существовать независимо от другого.

   class Team(val name: String)
   class Player(val team: Team)


▎c. Проблема и место применения паттерна Стратегия

Паттерн Стратегия используется, когда необходимо выбирать алгоритм поведения на этапе выполнения программы. Он позволяет определить группу взаимозаменяемых алгоритмов и инкапсулировать их, позволяя клиенту выбирать нужный алгоритм.

Проблема: Если у вас есть несколько способов выполнения одной и той же задачи (например, сортировка), и вы хотите иметь возможность менять алгоритм в зависимости от условий.

Пример делегации: Вместо того чтобы создавать множество подклассов для каждого алгоритма сортировки, можно создать интерфейс SortingStrategy и реализовать различные стратегии:

interface SortingStrategy {
fun sort(data: List<Int>): List<Int>
}

class QuickSort : SortingStrategy {
override fun sort(data: List<Int>): List<Int> {
// Реализация быстрой сортировки
return data.sorted() // Пример упрощенной реализации
}
}

class BubbleSort : SortingStrategy {
override fun sort(data: List<Int>): List<Int> {
// Реализация пузырьковой сортировки
return data.sorted() // Пример упрощенной реализации
}
}

class Sorter(private var strategy: SortingStrategy) {
fun setStrategy(strategy: SortingStrategy) {
this.strategy = strategy
}

    fun sort(data: List<Int>): List<Int> {
        return strategy.sort(data)
    }
}


▎d. Тривиальный пример наследования и реализации паттерна Стратегия

Пример наследования:

open class Animal {
open fun makeSound() {
println("Animal sound")
}
}

class Dog : Animal() {
override fun makeSound() {
println("Bark")
}
}


Пример реализации паттерна Стратегия:

interface SoundStrategy {
fun makeSound()
}

class Bark : SoundStrategy {
override fun makeSound() {
println("Bark")
}
}

class Meow : SoundStrategy {
override fun makeSound() {
println("Meow")
}
}

class Animal(private var soundStrategy: SoundStrategy) {
fun setSoundStrategy(soundStrategy: SoundStrategy) {
this.soundStrategy = soundStrategy
}

    fun makeSound() {
        soundStrategy.makeSound()
    }
}


Разница: В первом случае мы используем наследование для создания различных типов животных с разными звуками. Это приводит к жесткой связи между классами. Во втором случае мы используем паттерн Стратегия, чтобы динамически изменять поведение звука животного без создания новых подклассов.

▎e. Пример ситуации, когда нет необходимости в применении паттерна

Паттерн не всегда необходим, особенно в простых ситуациях. Например, если у вас есть небольшое приложение с единственным способом выполнения задачи, например, простой калькулятор:

class Calculator {
fun add(a: Int, b: Int): Int {
return a + b
}

    fun subtract(a: Int, b: Int): Int {
        return a - b
    }
}


В этом случае не имеет смысла применять паттерн Стратегия для добавления или вычитания, так как у нас нет множества алгоритмов для выбора. Простота реализации делает паттерн излишним и усложняет код без реальной необходимости.