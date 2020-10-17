package com.kijlee.wb.fivemdogfood.ui.fivem.main.ui.adddate


import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
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
import cn.bmob.v3.listener.UpdateListener
import cn.bmob.v3.listener.UploadBatchListener
import com.google.android.material.snackbar.Snackbar
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.adapter.EditImageListAdapter
import com.kijlee.wb.fivemdogfood.entity.fivem.FiveMUserBean
import com.kijlee.wb.fivemdogfood.entity.fivem.ImageViewEditBean
import com.kijlee.wb.fivemdogfood.entity.fivem.ManagerUser
import com.kijlee.wb.fivemdogfood.flag.Flag
import com.kijlee.wb.fivemdogfood.flag.FragmentName
import com.kijlee.wb.fivemdogfood.utils.PicassoImageLoader
import com.squareup.picasso.Picasso
import com.vise.log.ViseLog
import com.yancy.gallerypick.config.GalleryConfig
import com.yancy.gallerypick.config.GalleryPick
import com.yancy.gallerypick.inter.IHandlerCallBack
import kotlinx.android.synthetic.main.fg_add_fivem_user.*
import java.io.File

/**
 *编辑信息
 */
class FgEditDateSm : Fragment() {
    var viewLayout: View? = null
    lateinit var iHandlerCallBack: IHandlerCallBack
    var fiveMUserBean: FiveMUserBean? = null
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
        fiveMUserBean = requireArguments().getSerializable(Flag.GetLastBean) as FiveMUserBean
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
        viewLayout!!.findViewById<RecyclerView>(R.id.impage_recyclerview).adapter =
            carImageViewAdapter
        return viewLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserMessage()
        carImageViewAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.delImageView -> {

                    for (i in 0..fiveMUserBean!!.userImages!!.size - 1) {
                        if ((adapter.getItem(position) as ImageViewEditBean).imagePath!!
                                .equals(fiveMUserBean!!.userImages!![i].fileUrl)
                        ) {
                            var bmobFile = BmobFile()
                            bmobFile.url = fiveMUserBean!!.userImages!![i].fileUrl
                            bmobFile.delete(object : UpdateListener() {
                                override fun done(p0: BmobException?) {

                                    if (p0 == null) {
                                        imageViewEditList.removeAt(position)
                                        fiveMUserBean!!.userImages!!.removeAt(i)
                                        fiveMUserBean!!.update(fiveMUserBean!!.objectId,
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
            if (BmobUser.isLogin() && BmobUser.getCurrentUser(ManagerUser::class.java).username.equals(
                    fiveMUserBean!!.managerUserName
                )
            ) {

                if (fiveMUserBean!!.userImages != null && fiveMUserBean!!.userImages!!.size > 0) {

                    for (i in fiveMUserBean!!.userImages!!) {

                        var bmobFile = BmobFile()
                        bmobFile.url = i.fileUrl
                        bmobFile.delete(object : UpdateListener() {
                            override fun done(p0: BmobException?) {
                                if (p0 == null) {
                                    fiveMUserBean!!.userImages!!.remove(i)
                                    fiveMUserBean!!.update(fiveMUserBean!!.objectId,
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
                fiveMUserBean!!.delete(fiveMUserBean!!.objectId, object : UpdateListener() {
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

        if (BmobUser.isLogin() && BmobUser.getCurrentUser(ManagerUser::class.java).username.equals(
                fiveMUserBean!!.managerUserName
            )
        ) {
            fiveMUserBean!!.local = local.selectedItem.toString()
            fiveMUserBean!!.age = age.text.toString().toInt()
            when (attributeGroup.checkedRadioButtonId) {
                R.id.sRadio -> {
                    fiveMUserBean!!.attribute = "S"
                }
                R.id.mRadio -> {
                    fiveMUserBean!!.attribute = "M"
                }
                R.id.doubleRadio -> {
                    fiveMUserBean!!.attribute = "双"
                }
            }
            fiveMUserBean!!.evaluate = evaluate.text.toString()
            fiveMUserBean!!.objective = objective.text.toString()
            fiveMUserBean!!.occupation = occupation.text.toString()
            fiveMUserBean!!.degree = degree.text.toString()
            fiveMUserBean!!.numCode = numCode.text.toString()//编号
            when (sexGroup.checkedRadioButtonId) {
                R.id.manRadio -> {
                    fiveMUserBean!!.sex = "男"
                }
                R.id.womanRadio -> {
                    fiveMUserBean!!.sex = "女"
                }
                else -> {
                    fiveMUserBean!!.sex = "未知"
                }
            }
            if (imageViewEditList.size > 0) {//有图片
                var array: Array<String> = Array(imageViewEditList.size, { "" })
                for (item in 0..imageViewEditList.size - 1) {
                    array[item] = File(imageViewEditList[item].imagePath!!).absolutePath
                }
                BmobFile.uploadBatch(array, object : UploadBatchListener {
                    override fun onSuccess(p0: MutableList<BmobFile>?, p1: MutableList<String>?) {
                        if (p1!!.size == array.size) {
                            fiveMUserBean!!.userImages = p0
                            ViseLog.e("上传完成")

                            fiveMUserBean!!.update(
                                fiveMUserBean!!.objectId,
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

                fiveMUserBean!!.update(fiveMUserBean!!.objectId, object : UpdateListener() {
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
        delUserBtn.visibility = View.VISIBLE
        age.setText("" + fiveMUserBean!!.age)
        numCode.setText(fiveMUserBean!!.numCode)
        evaluate.setText(fiveMUserBean!!.evaluate)
        objective.setText(fiveMUserBean!!.objective)
        occupation.setText(fiveMUserBean!!.occupation)
        degree.setText(fiveMUserBean!!.degree)
        for (i in 0..resources.getStringArray(R.array.local).size - 1) {
            if (fiveMUserBean!!.local == resources.getStringArray(R.array.local)[i]) {
                local.setSelection(i)
            }
        }

        when (fiveMUserBean!!.attribute) {
            "S" -> {
                attributeGroup.clearCheck()
                sRadio.isChecked = true
            }
            "M" -> {
                attributeGroup.clearCheck()
                mRadio.isChecked = true
            }
            "双" -> {
                attributeGroup.clearCheck()
                doubleRadio.isChecked = true
            }
            else -> {
                attributeGroup.clearCheck()
            }
        }
        when (fiveMUserBean!!.sex) {
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
        for (s in fiveMUserBean!!.userImages!!) {
            var imageViewEditBean = ImageViewEditBean()
            imageViewEditBean.imagePath = s.fileUrl
            imageViewEditBean.isShowDelIcon = false
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