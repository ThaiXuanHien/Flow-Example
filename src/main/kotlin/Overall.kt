import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.callbackFlow


private val emittingScope = CoroutineScope(Dispatchers.IO)
private val collectingScope = CoroutineScope(Dispatchers.Default)

private val sharedFlow = MutableSharedFlow<Int>()
private val stateFlow = MutableStateFlow(0)

var sendData: (data: Int) -> Unit = { }
var closeChannel: () -> Unit = { }

val callbackFlow = callbackFlow {
    sendData = { data -> println("callback send $data"); trySend(data) }
    closeChannel = { close() }
    awaitClose {
        sendData = {}
        closeChannel = {}
        println("Close CallbackFlow")
    }
}


fun main(): Unit = runBlocking {
    println("--------------------------------")
    println("-----------StateFlow------------")
    println("--------------------------------")
    state()
    println("--------------------------------")
    println("----------SharedFlow------------")
    println("--------------------------------")
    share()
    println("--------------------------------")
    println("---------CallbackFlow-----------")
    println("--------------------------------")
    callback()
    println("--------------------------------")
}

fun share(): Unit = runBlocking {
    delay(100)
    println("Emit 1 before collect")
    emitSharedData(1)
    delay(100)
    println("Collect started")
    val job1 = collectSharedData()
    delay(100)
    println("Emit 2 after collect")
    emitSharedData(2)
    delay(100)
    println("Emit same value 2 again")
    emitSharedData(2)
    delay(100)
    println("Emit a new value 3")
    emitSharedData(3)
    delay(100)
    println("Collect again")
    val job2 = collectSharedData()
    delay(100)
    println("Emit 3 after collect again")
    emitSharedData(3)
    delay(100)
    println("Emit 4 after collect again")
    emitSharedData(4)
    delay(100)
    job1.cancel()
    println("Cancel Job 1")
    delay(100)
    println("Emit 5 after cancel job1")
    emitSharedData(5)
    delay(100)
    job2.cancel()
    println("Cancel Job 2")
    delay(100)
    println("Emit 6 after cancel job12")
    emitSharedData(6)
    delay(100)
}

fun state(): Unit = runBlocking {
    delay(100)
    println("Emit 1 before collect")
    emitStateData(1)
    delay(100)
    println("Collect started")
    val job1 = collectStateData()
    delay(100)
    println("Emit 2 after collect")
    emitStateData(2)
    delay(100)
    println("Emit same value 2 again")
    emitStateData(2)
    delay(100)
    println("Emit a new value 3")
    emitStateData(3)
    delay(100)
    println("Collect again")
    val job2 = collectStateData()
    delay(100)
    println("Emit 3 after collect again")
    emitStateData(3)
    delay(100)
    println("Emit 4 after collect again")
    emitStateData(4)
    delay(100)
    job1.cancel()
    println("Cancel Job 1")
    delay(100)
    println("Emit 5 after cancel job1")
    emitStateData(5)
    delay(100)
    job2.cancel()
    println("Cancel Job 2")
    delay(100)
    println("Emit 6 after cancel job2")
    emitStateData(6)
    delay(100)
}

fun callback(): Unit = runBlocking {
    delay(100)
    println("Emit 1 before collect")
    sendData(1)
    delay(100)
    println("Collect started")
    val job1 = collectCallbackData()
    delay(100)
    println("Emit 2 after collect")
    sendData(2)
    delay(100)
    println("Emit same value 2 again")
    sendData(2)
    delay(100)
    println("Emit a new value 3")
    sendData(3)
    delay(100)
    println("Collect again")
    val job2 = collectCallbackData()
    delay(100)
    println("Emit 3 after collect again")
    sendData(3)
    delay(100)
    println("Emit 4 after collect again")
    sendData(4)
    delay(100)
    job1.cancel()
    println("Cancel Job 1")
    delay(100)
    println("Emit 5 after cancel job1")
    sendData(5)
    delay(100)
    job2.cancel()
    println("Cancel Job 2")
    delay(100)
    println("Emit 6 after cancel job2")
    sendData(6)
    delay(100)
    println("Close Channel")
    closeChannel()
    delay(100)
}


private fun emitSharedData(value: Int) {
    emittingScope.launch {
        println("emitted $value")
        sharedFlow.emit(value)
    }
}

private fun emitStateData(value: Int) {
    emittingScope.launch {
        println("emitted $value")
        stateFlow.emit(value)
    }
}

private fun collectSharedData() =
    collectingScope.launch {
        sharedFlow.collect {
            println("collected $it")
        }
    }

private fun collectStateData() =
    collectingScope.launch {
        stateFlow.collect {
            println("collected $it")
        }
    }

fun collectCallbackData() =
    collectingScope.launch {
        callbackFlow.collect {
            println("callback collect $it")
        }
    }