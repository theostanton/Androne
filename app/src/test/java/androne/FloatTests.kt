package androne

import com.theostanton.androne.util.map
import org.amshove.kluent.shouldEqual
import org.junit.Test

class FloatTests {

  companion object {
    const val THROTTLE_LOW = 1000.toFloat()
    const val THROTTLE_HIGH = 2000.toFloat()
    const val RECEIVER_LOW = 0f
    const val RECEIVER_HIGH = 1f
  }


  @Test
  fun testMap(){
    val value = 1250.toFloat()
    val mapped = value.map(
        THROTTLE_LOW,
        THROTTLE_HIGH,
        RECEIVER_LOW,
        RECEIVER_HIGH
    )
    mapped shouldEqual 0.25f
  }

  @Test
  fun testClip(){
    val value = 750.toFloat()
    val mapped = value.map(
        THROTTLE_LOW,
        THROTTLE_HIGH,
        RECEIVER_LOW,
        RECEIVER_HIGH,
        true
    )
    mapped shouldEqual 0f
  }

  @Test
  fun testDontClip(){
    val value = 750.toFloat()
    val mapped = value.map(
        THROTTLE_LOW,
        THROTTLE_HIGH,
        RECEIVER_LOW,
        RECEIVER_HIGH,
        false
    )
    mapped shouldEqual -0.25f
  }

}