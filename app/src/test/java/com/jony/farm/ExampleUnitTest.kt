package com.jony.farm


import com.jony.farm.util.DateUtil
import org.junit.Assert.*
import org.junit.Test
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testEnum(){
     //   val resId = CommonUtil.getImgByAnimalId(2)
     //   println(resId)
       /* val list = mutableListOf<Int>()
        for (i in 0..34){
            list.add(0)
        }

        var x =0
        while (x<5){
            val random = (2..34).random()
            if (list[random]==0){
                list[random] = 1
                x++
            }

        }
        println(list)*/

//        var list = listOf<Int>(1,2,3,4,5,6,7,8)
//        list = list.subList(0,3)
//        println(list)
        /*val list = listOf(1,2,3,4,5,6,7,8)
        for (i in list.indices){
            println(list[i])
            if (i ==4){
                return
            }

        }*/
    //    val tradType = TradType.values()
    //    println(tradType)
      /*  val dateStr = "2017/03/01 15:30:00"
        val date: Date = DateUtil.str2date(dateStr)
        val dateOff = DateUtil.dateOff(date,3)
        println(dateOff)*/
       /* val list = listOf(1,2,3,4,5)
        val l = list.takeLast(list.size-3)
        println(l)*/

      //  val date = DateUtil.str2date("2021/05/07 14:29:00")
      //  println(date.time)
        //val str = "jjwchat.com/#/?rc=LC33309"
        val str = "04-19 05:07"
        println(str.substring(6))


    }

    //map函数最后一个参数是一个函数
    fun <T, R> List<T>.map(transform: (T) -> R): List<R> {
        val result = arrayListOf<R>()
        for (item in this)
            result.add(transform(item))
        return result
    }
    @Test
    fun test(){
//        val list = listOf<String>("1","2","3","4","5","6","7")
//        //使用
//        val dataList = list.map{
//            it.toInt()*2
//        }
//        println(dataList)
        val str = " "
        println(str.isBlank())
    }

}