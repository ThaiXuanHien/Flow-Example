import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

fun main() = runBlocking {
    //https://medium.com/mobile-app-development-publication/comparing-stateflow-sharedflow-and-callbackflow-2f0d03d48a43
    
    // Emitting before Collection
//    delay(1000)
//    println("Emit 1 before collect")
//    emitStateData(1)
//    delay(1000)
//    println("Collect started")
//    val job1 = collectStateData()
//    delay(1000)
    // Result
    /*
    Emit 1 before collect
    emitted 1
    Collect started
    collected 1
    */

    // Emitting after Collection
//    delay(1000)
//    println("Collect started")
//    val job1 = collectStateData()
//    delay(1000)
//    println("Emit 2 after collect")
//    emitStateData(2)
//    delay(1000)
    // Result
    /*
    Collect started
    collected 0
    Emit 2 after collect
    emitted 2
    collected 2
    */
    // Emitting Two Same Values Again after Collection
//    delay(1000)
//    println("Collect started")
//    val job1 = collectStateData()
//    delay(1000)
//    println("Emit 2 after collect")
//    emitStateData(2)
//    delay(1000)
//    println("Emit same value 2 again")
//    emitStateData(2)
//    delay(1000)
    // Result
    /*
    Collect started
    collected 0
    Emit 2 after collect
    emitted 2
    collected 2
    Emit same value 2 again
    emitted 2
    */
    // Collect-Emit and Collect-Emit Again
//    delay(1000)
//    println("Collect started")
//    val job1 = collectStateData()
//    delay(1000)
//    println("Emit a new value 3")
//    emitStateData(3)
//    delay(1000)
//    println("Collect again")
//    val job2 = collectStateData()
//    delay(1000)
//    println("Emit 3 after collect again")
//    emitStateData(3)
//    delay(1000)
    // Result
    /*Collect started
    collected 0
    Emit a new value 3
    emitted 3
    collected 3
    Collect again
    collected 3
    Emit 3 after collect again
    emitted 3
     */
    delay(1000)
    println("Collect started")
    val job1 = collectStateData()
    delay(1000)
    println("Emit a new value 3")
    emitStateData(3)
    delay(1000)
    println("Collect again")
    val job2 = collectStateData()
    delay(1000)
    println("Emit 4 after collect again")
    emitStateData(4)
    delay(1000)
    // Result
    /*Collect started
    collected 0
    Emit a new value 3
    emitted 3
    collected 3
    Collect again
    collected 3
    Emit 3 after collect again
    emitted 3
     */
}

private val emittingScope = CoroutineScope(Dispatchers.IO)
private val collectingScope = CoroutineScope(Dispatchers.Default)
private val stateFlow = MutableStateFlow(0)

private fun emitStateData(value: Int) {
    emittingScope.launch {
        println("emitted $value")
        stateFlow.emit(value)
    }
}

private fun collectStateData() =
    collectingScope.launch {
        stateFlow.collect {
            println("collected $it")
        }
    }

