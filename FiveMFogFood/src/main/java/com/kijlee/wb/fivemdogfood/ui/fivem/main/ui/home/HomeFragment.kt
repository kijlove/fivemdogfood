package com.kijlee.wb.fivemdogfood.ui.fivem.main.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.view.get
import androidx.fragment.app.Fragment
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.sharesdk.onekeyshare.OnekeyShare
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.entity.fivem.FiveMUserBean
import com.mob.MobSDK
import com.mob.wrappers.ShareSDKWrapper.share
import com.squareup.picasso.Picasso
import com.vise.log.ViseLog
import kotlinx.android.synthetic.main.fragment_home.*

/***
 * 主页
 */
class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {

    var mView: View? = null
    var userList: MutableList<FiveMUserBean> = ArrayList<FiveMUserBean>()
    lateinit var fiveMUserAdapter: FiveMUserAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_home, container, false)
        fiveMUserAdapter = FiveMUserAdapter(R.layout.fivem_re_item_user, userList)
        getUserList()
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewUser.adapter = fiveMUserAdapter
        fiveMUserAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.image1 -> {
                    bigImageLoader(fiveMUserAdapter.array[0])
                }
                R.id.image2 -> {
                    bigImageLoader(fiveMUserAdapter.array[1])
                }
                R.id.image3 -> {
                    bigImageLoader(fiveMUserAdapter.array[2])
                }
                R.id.shareImage -> {//分享
//                    val oks = OnekeyShare()
//                    // title标题，微信、QQ和QQ空间等平台使用
//                    oks.setTitle("分享信息")
//                        // titleUrl QQ和QQ空间跳转链接
//                    oks.setTitleUrl("http://sharesdk.cn")
//                    // text是分享文本，所有平台都需要这个字段
//                    oks.text = "我是分享文本"
//                    // setImageUrl是网络图片的url
//                    oks.setImageUrl("https://img.tupianzj.com/uploads/allimg/202009/9999/984c2cbc21.jpg")
//                    // url在微信、Facebook等平台中使用
//                    oks.setUrl("http://sharesdk.cn")
//                    // 启动分享GUI
//                    oks.show(MobSDK.getContext())
                }
            }
        }
        local.onItemSelectedListener = this

    }

    fun getUserList() {
        var bmobUser = BmobQuery<FiveMUserBean>()
        ViseLog.e("local=====" + mView!!.findViewById<Spinner>(R.id.local).selectedItem.toString())
        bmobUser.addWhereEqualTo(
            "local",
            mView!!.findViewById<Spinner>(R.id.local).selectedItem.toString()
        )
        bmobUser.findObjects(object : FindListener<FiveMUserBean>() {
            override fun done(p0: MutableList<FiveMUserBean>?, p1: BmobException?) {
                if (p1 == null) {
                    fiveMUserAdapter.setList(p0)
                    ViseLog.e("查询成功" + p0!!.size)
                } else {
                    ViseLog.e("查询失败" + p1.message)
                }
            }
        })
    }

    fun bigImageLoader(imageUrl: String) {
        ViseLog.e("地址==" + imageUrl)
//bitmap: Bitmap
        val imageView = ImageView(context)
        Picasso.with(context).load(imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(imageView)
        val dialog = Dialog(context!!)
//        image.setImageBitmap(bitmap)
        dialog.setContentView(imageView)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
        imageView.setOnClickListener { dialog.cancel() }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        ViseLog.e("local=====" + p0!!.getItemAtPosition(p2).toString())
        getUserList()
    }
}