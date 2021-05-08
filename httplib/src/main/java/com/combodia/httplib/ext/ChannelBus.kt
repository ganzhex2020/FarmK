package com.combodia.httplib.ext

import kotlinx.coroutines.channels.Channel

/**
 *Author:ganzhe
 *时间:2021/4/15 17:53
 *描述:This is ChannelBus
 */
class ChannelBus private constructor() {

   // private var channel: Channel<Events> = Channel(Channel.BUFFERED)

    companion object {
        val instance: ChannelBus by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ChannelBus()
        }
    }
}

