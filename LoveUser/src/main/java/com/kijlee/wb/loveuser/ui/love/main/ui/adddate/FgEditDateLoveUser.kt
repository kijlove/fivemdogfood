package com.kijlee.wb.loveuser.ui.love.main.ui.adddate


import android.Manifest
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobInstallationManager
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import cn.bmob.v3.listener.UploadBatchListener
import com.google.android.material.snackbar.Snackbar
import com.kijlee.wb.loveuser.R
import com.kijlee.wb.loveuser.adapter.EditImageListAdapter
import com.kijlee.wb.loveuser.entity.loveuser.LoveUserBean
import com.kijlee.wb.loveuser.entity.loveuser.ImageViewEditBean
import com.kijlee.wb.loveuser.entity.loveuser.ManagerUser
import com.kijlee.wb.loveuser.flag.Flag
import com.kijlee.wb.loveuser.flag.FragmentName
import com.kijlee.wb.loveuser.utils.PicassoImageLoader
import com.squareup.picasso.Picasso
import com.vise.log.ViseLog
import com.yancy.gallerypick.config.GalleryConfig
import com.yancy.gallerypick.config.GalleryPick
import com.yancy.gallerypick.inter.IHandlerCallBack
import kotlinx.android.synthetic.main.fg_add_fivem_user.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 *编辑信息
 */
class FgEditDateLoveUser : Fragment() {
    var viewLayout: View? = null
    lateinit var iHandlerCallBack: IHandlerCallBack
    var userBean: LoveUserBean? = null
    lateinit var galleryConfig: GalleryConfig
    val PERMISSIONS_REQUEST_READ_CONTACTS = 8

    var path: MutableList<String> = java.util.ArrayList()

    var imageViewEditList: MutableList<ImageViewEditBean> = ArrayList()
    lateinit var carImageViewAdapter: EditImageListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.fg_add_fivem_user, container, false)
        userBean = requireArguments().getSerializable(Flag.GetLastBean) as LoveUserBean
        initGallery()
        galleryConfig = GalleryConfig.Builder()
            .imageLoader(PicassoImageLoader())    // ImageLoader 加载框架（必填）
            .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
            .provider("com.sphd.kij.systemmanger.provider")   // provider(必填)
//                .pathList(path)                         // 记录已选的图片
            .multiSelect(true, 9)// 配置是否多选的同时 配置多选数量   默认：false ， 9
            .crop(false)// 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
            .isShowCamera(true)// 是否现实相机按钮  默认：false
            .filePath(requireContext().getExternalFilesDir(null)!!.absolutePath + "/Gallery/Pictures")          // 图片存放路径
            .build()
        carImageViewAdapter = EditImageListAdapter(R.layout.re_item_image_edit, imageViewEditList)

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
        viewLayout!!.findViewById<RecyclerView>(R.id.impage_recyclerview).adapter =
            carImageViewAdapter
        if (BmobUser.isLogin()) {
            val user: ManagerUser = BmobUser.getCurrentUser(ManagerUser::class.java)
            if (userBean!!.managerId == user.objectId) {
                viewLayout!!.findViewById<Button>(R.id.addUserBtn).visibility = View.VISIBLE
                viewLayout!!.findViewById<Button>(R.id.delUserBtn).visibility = View.VISIBLE
            } else {
                viewLayout!!.findViewById<Button>(R.id.addUserBtn).visibility = View.GONE
                viewLayout!!.findViewById<Button>(R.id.delUserBtn).visibility = View.GONE
            }
        } else {
            viewLayout!!.findViewById<Button>(R.id.addUserBtn).visibility = View.GONE
            viewLayout!!.findViewById<Button>(R.id.delUserBtn).visibility = View.GONE
        }
        return viewLayout
    }

    var calendar: Calendar = Calendar.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserMessage()

        carImageViewAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.imageView -> {
                    bigImageLoader((adapter.getItem(position) as ImageViewEditBean).imagePath!!)
                }
                R.id.delImageView -> {

                    for (i in 0..userBean!!.userImages!!.size - 1) {
                        if ((adapter.getItem(position) as ImageViewEditBean).imagePath!!
                                .equals(userBean!!.userImages!![i].fileUrl)
                        ) {
                            var bmobFile = BmobFile()
                            bmobFile.url = userBean!!.userImages!![i].fileUrl
                            bmobFile.delete(object : UpdateListener() {
                                override fun done(p0: BmobException?) {

                                    if (p0 == null) {
                                        imageViewEditList.removeAt(position)
                                        userBean!!.userImages!!.removeAt(i)
                                        userBean!!.update(userBean!!.objectId,
                                            object : UpdateListener() {
                                                override fun done(p0: BmobException?) {
                                                    if (p0 == null) {
                                                        Snackbar.make(
                                                            addUserBtn,
                                                            "删除完成 " + BmobInstallationManager.getInstallationId(),
                                                            Snackbar.LENGTH_LONG
                                                        ).show()
                                                        carImageViewAdapter.notifyDataSetChanged()
                                                    }
                                                }

                                            })
                                    }
                                }
                            })
                        }
                    }
                }
            }
        }
        //上传用户资料
        addUserBtn.setOnClickListener {
            if (!TextUtils.isEmpty(numCode.text)) {
                signUp()
            } else {

                Snackbar.make(
                    addUserBtn, "编号不能为空",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        //上传用户资料
        delUserBtn.setOnClickListener {
            if (BmobUser.isLogin() && BmobUser.getCurrentUser(ManagerUser::class.java).objectId.equals(
                    userBean!!.managerId
                )
            ) {
                if (userBean!!.userImages != null && userBean!!.userImages!!.size > 0) {
                    for (i in userBean!!.userImages!!) {

                        var bmobFile = BmobFile()
                        bmobFile.url = i.fileUrl
                        bmobFile.delete(object : UpdateListener() {
                            override fun done(p0: BmobException?) {
                                if (p0 == null) {
                                    userBean!!.userImages!!.remove(i)
                                    userBean!!.update(userBean!!.objectId,
                                        object : UpdateListener() {
                                            override fun done(p0: BmobException?) {
                                                if (p0 == null) {
                                                    Snackbar.make(
                                                        addUserBtn,
                                                        "删除完成 " + BmobInstallationManager.getInstallationId(),
                                                        Snackbar.LENGTH_LONG
                                                    ).show()
                                                    carImageViewAdapter.notifyDataSetChanged()
                                                }
                                            }

                                        })
                                }
                            }
                        })

                    }
                }
                userBean!!.delete(userBean!!.objectId, object : UpdateListener() {
                    override fun done(p0: BmobException?) {

                        if (p0 == null) {

                            Snackbar.make(
                                addUserBtn,
                                "删除完成" + BmobInstallationManager.getInstallationId(),
                                Snackbar.LENGTH_LONG
                            ).show()
                            requireActivity().finish()
                        }
                    }
                })
            }
        }


    }

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

    fun signUp() {

        if (BmobUser.isLogin() && BmobUser.getCurrentUser(ManagerUser::class.java).objectId.equals(
                userBean!!.managerId
            )
        ) {
            userBean!!.city = city.selectedItem.toString()


            userBean!!.city = city.selectedItem.toString()
            userBean!!.academic = academic.selectedItem.toString()
            userBean!!.attribute = attribute.text.toString()
            userBean!!.income = income.text.toString()
            userBean!!.hobby = hobby.text.toString()
            userBean!!.maritalStatus = maritalStatus.text.toString()
            userBean!!.profession = profession.text.toString()
            userBean!!.family = family.text.toString()
            userBean!!.other = other.text.toString()
            userBean!!.profession = profession.text.toString()

            when (sexGroup.checkedRadioButtonId) {
                R.id.manRadio -> {
                    userBean!!.sex = "男"
                }
                R.id.womanRadio -> {
                    userBean!!.sex = "女"
                }
                else -> {
                    userBean!!.sex = "未知"
                }
            }
            when (vipLevelGroup.checkedRadioButtonId) {
                R.id.copper -> {
                    userBean!!.vipLevel = "青铜"
                }
                R.id.silver -> {
                    userBean!!.vipLevel = "白银"
                }
                R.id.gold -> {
                    userBean!!.vipLevel = "黄金"
                }
                R.id.diamond -> {
                    userBean!!.vipLevel = "钻石"
                }
                else -> {
                    userBean!!.vipLevel = "未知"
                }
            }

            userBean!!.birthday =
                SimpleDateFormat("yyyy-MM-dd").parse(birthdayText.text.toString()).time

            if (imageViewEditList.size > 0) {//有图片
                var array: Array<String> = Array(imageViewEditList.size, { "" })
                for (item in 0..imageViewEditList.size - 1) {
                    array[item] = File(imageViewEditList[item].imagePath!!).absolutePath
                }
                BmobFile.uploadBatch(array, object : UploadBatchListener {
                    override fun onSuccess(p0: MutableList<BmobFile>?, p1: MutableList<String>?) {
                        if (p1!!.size == array.size) {
                            userBean!!.userImages = p0
                            ViseLog.e("上传完成")

                            userBean!!.update(
                                userBean!!.objectId,
                                object : UpdateListener() {
                                    override fun done(p1: BmobException?) {
                                        Snackbar.make(
                                            addUserBtn,
                                            "上传完成 " + BmobInstallationManager.getInstallationId(),
                                            Snackbar.LENGTH_LONG
                                        ).show()
                                        requireActivity().finish()
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

                userBean!!.update(userBean!!.objectId, object : UpdateListener() {
                    override fun done(p1: BmobException?) {
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
            var intent = Intent(context, FiveMAddDateActivity::class.java)
            intent.putExtra(Flag.FragmentSwitch, FragmentName.FgLogonIn)
            startActivity(intent)
        }
    }

    fun setUserMessage() {

        numCode.setText(userBean!!.numCode)
        for (i in 0..resources.getStringArray(R.array.local).size - 1) {
            if (userBean!!.city == resources.getStringArray(R.array.local)[i]) {
                city.setSelection(i)
            }
        }

        for (i in 0..resources.getStringArray(R.array.degree_array).size - 1) {
            if (userBean!!.academic == resources.getStringArray(R.array.degree_array)[i]) {
                city.setSelection(i)
            }
        }


        when (userBean!!.sex) {
            "男" -> {
                sexGroup.clearCheck()
                manRadio.isChecked = true
            }
            "女" -> {
                sexGroup.clearCheck()
                womanRadio.isChecked = true
            }
            else -> {
                sexGroup.clearCheck()
            }
        }



        attribute.setText(userBean!!.attribute!!)
        income.setText(userBean!!.income!!)
        hobby.setText(userBean!!.hobby)
        maritalStatus.setText(userBean!!.maritalStatus)
        profession.setText(userBean!!.profession)
        family.setText(userBean!!.family)
        other.setText(userBean!!.other)
        profession.setText(userBean!!.profession)

        when (userBean!!.vipLevel) {
            "青铜" -> {
                copper.isChecked = true
            }
            "白银" -> {
                silver.isChecked = true
            }
            "黄金" -> {
                gold.isChecked = true
            }
            "钻石" -> {
                diamond.isChecked = true
            }
            else -> {
                vipLevelGroup.clearCheck()

            }
        }

        birthdayText.text = SimpleDateFormat("yyyy-MM-dd").format(Date(userBean!!.birthday!!))



        for (s in userBean!!.userImages!!) {
            var imageViewEditBean = ImageViewEditBean()
            imageViewEditBean.imagePath = s.fileUrl
            if (BmobUser.isLogin()) {
                val user: ManagerUser = BmobUser.getCurrentUser(ManagerUser::class.java)
                if (userBean!!.managerId == user.objectId) {
                    imageViewEditBean.isShowDelIcon = false
                } else {
                    imageViewEditBean.isShowDelIcon = true
                }
            } else {
                imageViewEditBean.isShowDelIcon = true
            }
            imageViewEditBean.isLocal = false
            imageViewEditList.add(imageViewEditBean)
        }
        carImageViewAdapter.setList(imageViewEditList)
    }

    fun bigImageLoader(imageUrl: String) {
        ViseLog.e("地址==" + imageUrl)

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
}