package fr.aubel.music.component

import fr.aubel.music.services.DataService
import fr.aubel.music.services.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class CommandLineTaskExecutor(private val taskService: TaskService) : CommandLineRunner {
    @Autowired
    private lateinit var dataService: DataService

    override fun run(vararg args: String?) {
        taskService.execute("command line runner task: adding data");
        dataService.CreateAll()
        taskService.execute("command line runner task: done");
    }
}