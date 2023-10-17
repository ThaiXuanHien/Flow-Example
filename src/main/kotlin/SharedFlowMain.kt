import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow

fun main() = runBlocking {

    // Emitting before Collection
//    delay(1000)
//    println("Emit 1 before collect")
//    emitSharedData(1)
//    delay(1000)
//    println("Collect started")
//    val job1 = collectSharedData()
//    delay(1000)
    // Result
    /*
     Emit 1 before collect
     emitted 1
     Collect started
     collected 1 (if replay > 0)
    */
    // Emitting after Collection
//    delay(1000)
//    println("Collect started")
//    val job1 = collectSharedData()
//    delay(1000)
//    println("Emit 2 after collect")
//    emitSharedData(2)
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
//    val job1 = collectSharedData()
//    delay(1000)
//    println("Emit 2 after collect")
//    emitSharedData(2)
//    delay(1000)
//    println("Emit same value 2 again")
//    emitSharedData(2)
//    delay(1000)
    // Result
    /*
    Collect started
    Emit 2 after collect
    emitted 2
    collected 2
    Emit same value 2 again
    emitted 2
    collected 2
    */
    // Collect-Emit and Collect-Emit Again
    delay(1000)
    println("Collect started")
    val job1 = collectSharedData()
    delay(1000)
    println("Emit a new value 3")
    emitSharedData(3)
    delay(1000)
    println("Collect again")
    val job2 = collectSharedData()
    delay(1000)
    println("Emit 3 after collect again")
    emitSharedData(3)
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
private val sharedFlow = MutableSharedFlow<Int>()

private fun emitSharedData(value: Int) {
    emittingScope.launch {
        println("emitted $value")
        sharedFlow.emit(value)
    }
}

private fun collectSharedData() =
    collectingScope.launch {
        sharedFlow.collect {
            println("collected $it")
        }
    }