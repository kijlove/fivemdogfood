package com.kijlee.wb.loveuser.ui.love.main.ui.adddate


import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobInstallationManager
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UploadBatchListener
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.adapter.EditImageListAdapter
import com.kijlee.wb.loveuser.entity.loveuser.Areas
import com.kijlee.wb.loveuser.entity.loveuser.ImageViewEditBean
import com.kijlee.wb.loveuser.entity.loveuser.LoveUserBean
import com.kijlee.wb.loveuser.entity.loveuser.ManagerUser
import com.kijlee.wb.loveuser.flag.Flag
import com.kijlee.wb.loveuser.flag.FragmentName
import com.kijlee.wb.loveuser.utils.PicassoImageLoader
import com.vise.log.ViseLog
import com.yancy.gallerypick.config.GalleryConfig
import com.yancy.gallerypick.config.GalleryPick
import com.yancy.gallerypick.inter.IHandlerCallBack
import kotlinx.android.synthetic.main.fg_add_fivem_user.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 */
class FgAddLoveUser : Fragment() {
    var viewLayout: View? = null
    lateinit var iHandlerCallBack: IHandlerCallBack
    var userBean = LoveUserBean()
    lateinit var galleryConfig: GalleryConfig
    val PERMISSIONS_REQUEST_READ_CONTACTS = 8
    var cityArray:MutableList<Areas> =ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.fg_add_fivem_user, container, false)
        cityArray = getStates(requireContext())!!
        initGallery()
        galleryConfig = GalleryConfig.Builder()
            .imageLoader(PicassoImageLoader())    // ImageLoader 加载框架（必填）
            .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
            .provider("com.sphd.kij.systemmanger.provider")   // provider(必填)
//                .pathList(path)                         // 记录已选的图片
            .multiSelect(true, 3)// 配置是否多选的同时 配置多选数量   默认：false ， 9
            .crop(false)// 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
            .isShowCamera(true)// 是否现实相机按钮  默认：false
            .filePath(requireContext().getExternalFilesDir(null)!!.absolutePath + "/Gallery/Pictures")          // 图片存放路径
            .build()
        carImageViewAdapter = EditImageListAdapter(R.layout.re_item_image_edit, imageViewEditList)

        viewLayout!!.findViewById<TextView>(R.id.birthdayText).setOnClickListener { view ->
            ViseLog.e("点击了出生日期")
            DatePickerDialog(
                requireContext(), DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    //更新EditText控件日期 小于10加0
                    viewLayout!!.findViewById<TextView>(R.id.birthdayText).setText(
                        StringBuilder().append(year).append("-")
                            .append(if (month + 1 < 10) "0" + (month + 1) else month + 1)
                            .append("-")
                            .append(if (day < 10) "0" + day else day)
                    )
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        //添加图片
        viewLayout!!.findViewById<ImageView>(R.id.addPhoto).setOnClickListener { view ->
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
//                Log.e(TAG, "需要授权 ");
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.CAMERA
                    )
                ) {
//                    Log.e(TAG, "拒绝过了");
//                    toast("请在 设置-应用管理 中开启此应用的储存授权。");
                } else {
//                    Log.e(TAG, "进行授权");
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.CAMERA),
                        PERMISSIONS_REQUEST_READ_CONTACTS
                    )
                }
            } else {
//                Log.e(TAG, "不需要授权 ");
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(activity)
            }
        }
        viewLayout!!.findViewById<RecyclerView>(R.id.impage_recyclerview).adapter =
            carImageViewAdapter
        return viewLayout
    }
    var calendar: Calendar = Calendar.getInstance()

    var user = LoveUserBean()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var stringlist = ArrayList<String>()
        for(item in cityArray){
            stringlist.add(item.name!!)
        }
        province.adapter  = ArrayAdapter<String>(requireContext(),R.layout.layout_defaut_text,stringlist)
        //上传用户资料
        addUserBtn.setOnClickListener {
            if (!TextUtils.isEmpty(numCode.text)) {

                var bmobUser = BmobQuery<LoveUserBean>()
                bmobUser.addWhereEqualTo(
                    "numCode",
                    numCode.text.toString()
                )
                bmobUser.findObjects(object : FindListener<LoveUserBean>() {
                    override fun done(p0: MutableList<LoveUserBean>?, p1: BmobException?) {
                        if (p1 == null) {
                            ViseLog.e("查询成功" + p0!!.size)
                            if (p0!!.size == 0) {
                                signUp()
                            } else {
                                Snackbar.make(
                                    addUserBtn, "编号相同",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            signUp()
                        }
                    }
                })
            }else{

                Snackbar.make(
                    addUserBtn, "编号不能为空",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    var path: MutableList<String> = java.util.ArrayList()

    var imageViewEditList: MutableList<ImageViewEditBean> = ArrayList()
    lateinit var carImageViewAdapter: EditImageListAdapter

    //图片上传用到
    fun initGallery() {
        iHandlerCallBack = object : IHandlerCallBack {
            override fun onStart() {
            }

            override fun onSuccess(photoList: List<String>) {
                path.clear()
                imageViewEditList.clear()

                for (s in photoList) {
                    path.add(s)
                    var imageViewEditBean = ImageViewEditBean()
                    imageViewEditBean.imagePath = s
                    imageViewEditBean.isShowDelIcon = true
                    imageViewEditList.add(imageViewEditBean)
                }

                carImageViewAdapter.setList(imageViewEditList)
            }

            override fun onCancel() {
            }

            override fun onFinish() {
            }

            override fun onError() {
            }
        }
    }
    fun getStates(context: Context): MutableList<Areas>? {
        var inputStream: InputStream? = null
        var bos: ByteArrayOutputStream? = null
        try {
            inputStream = context.getAssets().open("china_city_data.json")
            bos = ByteArrayOutputStream()
            val bytes = ByteArray(4 * 1024)
            var len = 0
            while (inputStream.read(bytes).also({ len = it }) != -1) {
                bos.write(bytes, 0, len)
            }
            val json = String(bos.toByteArray())
            val jsonArray: JsonArray = Gson().fromJson(json, JsonArray::class.java)
            var areas :MutableList<Areas> = ArrayList()
            for(item in jsonArray){
                var area =  Gson().fromJson(item, Areas::class.java)
                areas.add(area)
                ViseLog.e("json===="+item.toString())
            }

            return areas
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (inputStream != null) inputStream.close()
                if (bos != null) bos.close()
            } catch (e: IOException) {
                ViseLog.e("getStates")
            }
        }
        return null
    }
    fun signUp() {

        if (BmobUser.isLogin()) {
            val user: ManagerUser = BmobUser.getCurrentUser(ManagerUser::class.java)
            userBean.managerId = user.orgId
            userBean.city = city.selectedItem.toString()
            userBean.academic = academic.selectedItem.toString()
            userBean.attribute = attribute.text.toString()
            userBean.income = income.text.toString()
            userBean.hobby = hobby.text.toString()
            userBean.maritalStatus = maritalStatus.text.toString()
            userBean.profession = profession.text.toString()
            userBean.family = family.text.toString()
            userBean.other = other.text.toString()
            userBean.profession = profession.text.toString()

            when (sexGroup.checkedRadioButtonId) {
                R.id.manRadio -> {
                    userBean.sex = "男"
                }
                R.id.womanRadio -> {
                    userBean.sex = "女"
                }
                else -> {
                    userBean.sex = "未知"
                }
            }
            when (vipLevelGroup.checkedRadioButtonId) {
                R.id.copper -> {
                    userBean.vipLevel = "青铜"
                }
                R.id.silver -> {
                    userBean.vipLevel = "白银"
                }
                R.id.gold -> {
                    userBean.vipLevel = "黄金"
                }
                R.id.diamond -> {
                    userBean.vipLevel = "钻石"
                }
                else -> {
                    userBean.vipLevel = "未知"
                }
            }

            userBean.birthday = SimpleDateFormat("yyyy-MM-dd").parse(birthdayText.text.toString()).time


            if (imageViewEditList.size > 0) {//有图片
                var array: Array<String> = Array(imageViewEditList.size, { "" })
                for (item in 0..imageViewEditList.size - 1) {
                    array[item] = File(imageViewEditList[item].imagePath!!).absolutePath
                }
                BmobFile.uploadBatch(array, object : UploadBatchListener {
                    override fun onSuccess(p0: MutableList<BmobFile>?, p1: MutableList<String>?) {
                        if (p1!!.size == array.size) {
                            userBean.userImages = p0
                            ViseLog.e("上传完成")
                            userBean.numCode = numCode.text.toString()//编号

                            userBean.save(object : SaveListener<String>() {
                                override fun done(p0: String?, p1: BmobException?) {
                                    Snackbar.make(
                                        addUserBtn,
                                        "上传完成 " + BmobInstallationManager.getInstallationId(),
                                        Snackbar.LENGTH_LONG
                                    ).show()

                                }
                            })
                        }
                    }

                    override fun onProgress(p0: Int, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onError(p0: Int, p1: String?) {
                    }
                })
            } else {

                userBean.numCode = numCode.text.toString()//编号

                userBean.save(object : SaveListener<String>() {
                    override fun done(p0: String?, p1: BmobException?) {
                        Snackbar.make(
                            addUserBtn,
                            "上传完成 " + BmobInstallationManager.getInstallationId(),
                            Snackbar.LENGTH_LONG
                        ).show()

                    }
                })
            }
        } else {
            //跳转到下一级
            var intent = Intent(context, MineSwitchActivity::class.java)
            intent.putExtra(Flag.FragmentSwitch, FragmentName.FgLogin)
            startActivity(intent)
        }
    }


}