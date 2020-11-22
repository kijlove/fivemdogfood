package com.kijlee.wb.loveuser.ui.love.main.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.entity.loveuser.LoveUserBean
import com.kijlee.wb.loveuser.entity.loveuser.ManagerUser
import com.kijlee.wb.loveuser.flag.Flag
import com.kijlee.wb.loveuser.flag.FragmentName
import com.kijlee.wb.loveuser.ui.love.main.ui.adddate.MineSwitchActivity
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.squareup.picasso.Picasso
import com.vise.log.ViseLog
import kotlinx.android.synthetic.main.fragment_home.*

/***
 * 主页
 */
class HomeFragment : Fragment(), AdapterView.OnItemSelectedListener {

    var mView: View? = null
    var userList: MutableList<LoveUserBean> = ArrayList<LoveUserBean>()
    lateinit var fiveMUserAdapter: FiveMUserAdapter
    var managerId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_home, container, false)
        fiveMUserAdapter = FiveMUserAdapter(R.layout.fivem_re_item_user, userList)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewUser.adapter = fiveMUserAdapter

        if (managerId == null) {
            val builder = QMUIDialog.EditTextDialogBuilder(requireContext())
            builder.setTitle("输入管理员账号")
                .addAction(
                    0,
                    "取消",
                    QMUIDialogAction.ACTION_PROP_POSITIVE
                ) { dialog, index -> dialog.dismiss() }
                .addAction(0, "确定", QMUIDialogAction.ACTION_PROP_NEGATIVE) { dialog, index ->
                    val text = builder.editText.text
                    if (text != null && text.length > 0) {
                        dialog.dismiss()
                        getManagerId(text.toString())
                    } else {
                        Toast.makeText(activity, "输入管理员账号", Toast.LENGTH_SHORT).show()
                    }
                }
                .create().show()
        }
        fiveMUserAdapter.setOnItemClickListener { adapter, view, position ->
                //编辑用户信息
                var intent = Intent(context, MineSwitchActivity::class.java)
                intent.putExtra(Flag.FragmentSwitch, FragmentName.FgEditDateSm)
                intent.putExtra(Flag.ToNextBean, adapter.getItem(position) as LoveUserBean)
                startActivity(intent)
        }
        fiveMUserAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.image1 -> {
                    bigImageLoader((adapter.getItem(position) as LoveUserBean).userImages!![0].url)
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
        city.onItemSelectedListener = this
        refreshDate.setOnRefreshListener {
            if (managerId != null) {
                getUserList(managerId!!)
            }
        }
    }

    fun getManagerId(string: String) {

        //最后组装完整的and条件
        var bmobUser2 = BmobQuery<ManagerUser>()
        bmobUser2.addWhereEqualTo(
            "managerId",
            string
        )
        bmobUser2.findObjects(object : FindListener<ManagerUser>() {
            override fun done(p0: MutableList<ManagerUser>?, p1: BmobException?) {
                ViseLog.e("查询结果=====")
                if (p1 == null) {
                    managerId = p0!!.get(0)!!.objectId
                    ViseLog.e("查询成功"+managerId)
                    getUserList(managerId!!)
                } else {
                    ViseLog.e("查询失败")
                }
            }
        })
    }

    fun getUserList(managerId: String) {
        //最后组装完整的and条件
        val andQuerys: MutableList<BmobQuery<LoveUserBean>> =
            ArrayList<BmobQuery<LoveUserBean>>()
        var bmobUser1 = BmobQuery<LoveUserBean>()
        var bmobUser = BmobQuery<LoveUserBean>()
        bmobUser1.addWhereEqualTo(
            "managerId",
            managerId
        )
        bmobUser.addWhereEqualTo(
            "city",
            mView!!.findViewById<Spinner>(R.id.city).selectedItem.toString()
        )
        andQuerys.add(bmobUser1)
        andQuerys.add(bmobUser)
        var bmobUser2 = BmobQuery<LoveUserBean>()
        bmobUser2.and(andQuerys)
        bmobUser2.findObjects(object : FindListener<LoveUserBean>() {
            override fun done(p0: MutableList<LoveUserBean>?, p1: BmobException?) {
                ViseLog.e("查询结果====="+managerId)
                refreshDate.isRefreshing = false
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
        val dialog = Dialog(requireContext())
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
      if(managerId!=null){
          getUserList(managerId!!)
      }
    }
}