package net.swamphut.swampium.example

import net.swamphut.swampium.extra.command.SwampiumCommand
import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command(name = "gettester", mixinStandardHelpOptions = true, version = ["0.0.3"])
class GetTesterCommand(val exampleService: ExampleService) : SwampiumCommand(), Runnable {
    @Option(names = ["-a", "--age"], paramLabel = "AGE", description = ["filter by age"])
    var age: Int? = null

    @Option(names = ["-f", "--food"], paramLabel = "FAVOURITE_FOODS", description = ["filter by food"])
    var food: String? = null

    override fun run() {
        exampleService.getTesters().subscribe { it ->
            it.filter {
                age == null || it.age == age
            }.filter {
                food == null || (it.favouriteFoods ?: listOf()).contains(food!!)
            }.forEach {
                stdout.out("${it.name} ${it.age} ${it.favouriteFoods} ${it.address}")
            }
        }
    }
}