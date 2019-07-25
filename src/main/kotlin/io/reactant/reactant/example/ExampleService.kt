package io.reactant.reactant.example

import io.reactant.reactant.core.component.Component
import io.reactant.reactant.core.component.lifecycle.LifeCycleHook
import io.reactant.reactant.core.dependency.injection.Inject
import io.reactant.reactant.service.spec.config.Config
import io.reactant.reactant.service.spec.dsl.register
import io.reactant.reactant.service.spec.server.EventService
import io.reactant.reactant.ui.ReactantUIService
import org.bukkit.Bukkit
import java.util.logging.Level
import java.util.logging.Logger

@Component
class ExampleService(
        private val helloService: io.reactant.reactant.example.HelloService,
        @Inject("plugins/ReactantExample/testers.json") private val testersConfig: Config<io.reactant.reactant.example.TesterList>,
        private val eventService: EventService,
        private val uiService: ReactantUIService
) : LifeCycleHook {
    private val logger = Logger.getLogger(this.javaClass.name)

    override fun onEnable() {
        val testers = testersConfig.content.testers
        testers.map {
            """

                ==============================
                Name: ${it.name},
                Age: ${it.age},
                Address: ${it.address},
                FavouriteFoods: [${(it.favouriteFoods ?: listOf()).joinToString(",")}]
                ==============================

            """.trimIndent()
        }.forEach { logger.log(Level.INFO, it) }

        helloService.sayHello(Bukkit.getServer().name)

        register(eventService) {
            //            PlayerJoinEvent::class.observable().subscribe { showWelcome(it.player) }
        }

    }

//    fun showWelcome(player: Player) {
//        val colors = setOf(
//                createItemStack(Material.BLUE_STAINED_GLASS_PANE), createItemStack(Material.RED_STAINED_GLASS_PANE),
//                createItemStack(Material.GRAY_STAINED_GLASS_PANE), createItemStack(Material.GREEN_STAINED_GLASS_PANE)
//        )
//        uiService.createUI(player) {
//            view {
//                scheduler.interval(10).subscribe {
//                    val choose = colors.random()
//                    getElementById<ReactantUIDivElement>("main")?.fillPattern = { _, _ -> choose.clone() }
//                    render()
//                }
//            }
//            click.subscribe { it.isCancelled = true }
//            div {
//                id = "main"
//                margin(1)
//                padding(1)
//                height = MATCH_PARENT
//                fill(createItemStack(Material.RED_STAINED_GLASS_PANE))
//                div {
//                    id = "wool"
//                    height = MATCH_PARENT
//                    fill(createItemStack(Material.GREEN_WOOL))
//                }
//            }
//        }
//    }
//
//    fun showAnotherView(player: Player) {
//        uiService.createUI(player) {
//            div {
//                click.subscribe { it.isCancelled = true }
//                padding = listOf(1)
//                height = MATCH_PARENT
//                fill(createItemStack(Material.RED_STAINED_GLASS_PANE))
//                div {
//                    height = MATCH_PARENT
//                    fill(createItemStack(Material.GREEN_WOOL))
//                }
//            }
//        }
//    }
}
