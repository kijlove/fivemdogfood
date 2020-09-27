package com.kijlee.wb.yszx.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.dueeeke.videocontroller.StandardVideoController
import com.dueeeke.videocontroller.component.*
import com.dueeeke.videoplayer.player.VideoView
import com.dueeeke.videoplayer.player.VideoView.OnStateChangeListener
import com.dueeeke.videoplayer.player.VideoView.SimpleOnStateChangeListener
import com.dueeeke.videoplayer.util.L
import com.kijlee.wb.yszx.R
import com.bumptech.glide.Glide

/**
 * 视频播放
 */
class Fg_VideosTest  : Fragment() {
    var viewLayout: View? = null
    var player: VideoView<*>? = null
    var  THUMB :String= "https://cms-bucket.nosdn.127.net/eb411c2810f04ffa8aaafc42052b233820180418095416.jpeg";
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.layout_videos, container, false)
        player = viewLayout!!.findViewById(R.id.player)
        val controller = StandardVideoController(context!!)
        //根据屏幕方向自动进入/退出全屏
        //根据屏幕方向自动进入/退出全屏
        controller.setEnableOrientation(true)

        val prepareView = PrepareView(context!!) //准备播放界面

        val thumb =
            prepareView.findViewById<ImageView>(R.id.thumb) //封面图

        Glide.with(this).load(THUMB).into(thumb)
        controller.addControlComponent(prepareView)

        controller.addControlComponent(CompleteView(context!!)) //自动完成播放界面


        controller.addControlComponent(ErrorView(context!!)) //错误界面


        val titleView = TitleView(context!!) //标题栏

        controller.addControlComponent(titleView)

        //根据是否为直播设置不同的底部控制条

        //根据是否为直播设置不同的底部控制条
        val isLive: Boolean = false
        if (isLive) {
            controller.addControlComponent(LiveControlView(context!!)) //直播控制条
        } else {
            val vodControlView = VodControlView(context!!) //点播控制条
            //是否显示底部进度条。默认显示
//                vodControlView.showBottomProgress(false);
            controller.addControlComponent(vodControlView)
        }

        val gestureControlView = GestureView(context!!) //滑动控制视图

        controller.addControlComponent(gestureControlView)
        //根据是否为直播决定是否需要滑动调节进度
        //根据是否为直播决定是否需要滑动调节进度
        controller.setCanChangePosition(!isLive)

        //设置标题

        //设置标题
        val title: String = "点播"
        titleView.setTitle(title)

        //注意：以上组件如果你想单独定制，我推荐你把源码复制一份出来，然后改成你想要的样子。
        //改完之后再通过addControlComponent添加上去
        //你也可以通过addControlComponent添加一些你自己的组件，具体实现方式参考现有组件的实现。
        //这个组件不一定是View，请发挥你的想象力😃

        //如果你不需要单独配置各个组件，可以直接调用此方法快速添加以上组件
//            controller.addDefaultControlComponent(title, isLive);

        //竖屏也开启手势操作，默认关闭
//            controller.setEnableInNormal(true);
        //滑动调节亮度，音量，进度，默认开启
//            controller.setGestureEnabled(false);
        //适配刘海屏，默认开启
//            controller.setAdaptCutout(false);

        //在控制器上显示调试信息

        //注意：以上组件如果你想单独定制，我推荐你把源码复制一份出来，然后改成你想要的样子。
        //改完之后再通过addControlComponent添加上去
        //你也可以通过addControlComponent添加一些你自己的组件，具体实现方式参考现有组件的实现。
        //这个组件不一定是View，请发挥你的想象力😃

        //如果你不需要单独配置各个组件，可以直接调用此方法快速添加以上组件
//            controller.addDefaultControlComponent(title, isLive);

        //竖屏也开启手势操作，默认关闭
//            controller.setEnableInNormal(true);
        //滑动调节亮度，音量，进度，默认开启
//            controller.setGestureEnabled(false);
        //适配刘海屏，默认开启
//            controller.setAdaptCutout(false);

        //在控制器上显示调试信息
        controller.addControlComponent(DebugInfoView(context!!))
        //在LogCat显示调试信息
        //在LogCat显示调试信息
        controller.addControlComponent(PlayerMonitor())

        //如果你不想要UI，不要设置控制器即可

        //如果你不想要UI，不要设置控制器即可
        player!!.setVideoController(controller)

        player!!.setUrl("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4")

        //保存播放进度
//            mVideoView.setProgressManager(new ProgressManagerImpl());
        //播放状态监听

        //保存播放进度
//            mVideoView.setProgressManager(new ProgressManagerImpl());
        //播放状态监听
        player!!.addOnStateChangeListener(mOnStateChangeListener)

        //临时切换播放核心，如需全局请通过VideoConfig配置，详见MyApplication
        //使用IjkPlayer解码
//            mVideoView.setPlayerFactory(IjkPlayerFactory.create());
        //使用ExoPlayer解码
//            mVideoView.setPlayerFactory(ExoMediaPlayerFactory.create());
        //使用MediaPlayer解码
//            mVideoView.setPlayerFactory(AndroidMediaPlayerFactory.create());


        //临时切换播放核心，如需全局请通过VideoConfig配置，详见MyApplication
        //使用IjkPlayer解码
//            mVideoView.setPlayerFactory(IjkPlayerFactory.create());
        //使用ExoPlayer解码
//            mVideoView.setPlayerFactory(ExoMediaPlayerFactory.create());
        //使用MediaPlayer解码
//            mVideoView.setPlayerFactory(AndroidMediaPlayerFactory.create());
        player!!.start()
        return viewLayout
    }


    private val mOnStateChangeListener: OnStateChangeListener =
        object : SimpleOnStateChangeListener() {
            override fun onPlayerStateChanged(playerState: Int) {
                when (playerState) {
                    VideoView.PLAYER_NORMAL -> {
                    }
                    VideoView.PLAYER_FULL_SCREEN -> {
                    }
                }
            }

            override fun onPlayStateChanged(playState: Int) {
                when (playState) {
                    VideoView.STATE_IDLE -> {
                    }
                    VideoView.STATE_PREPARING -> {
                    }
                    VideoView.STATE_PREPARED -> {
                    }
                    VideoView.STATE_PLAYING -> {
                        //需在此时获取视频宽高
                        val videoSize: IntArray = player!!.getVideoSize()
                        L.d("视频宽：" + videoSize[0])
                        L.d("视频高：" + videoSize[1])
                    }
                    VideoView.STATE_PAUSED -> {
                    }
                    VideoView.STATE_BUFFERING -> {
                    }
                    VideoView.STATE_BUFFERED -> {
                    }
                    VideoView.STATE_PLAYBACK_COMPLETED -> {
                    }
                    VideoView.STATE_ERROR -> {
                    }
                }
            }
        }

}