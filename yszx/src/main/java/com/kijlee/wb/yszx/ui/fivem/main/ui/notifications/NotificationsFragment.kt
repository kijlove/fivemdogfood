package com.kijlee.wb.yszx.ui.fivem.main.ui.notifications

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobInstallationManager
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UploadBatchListener
import com.google.android.material.snackbar.Snackbar
import com.kijlee.wb.yszx.R
import com.kijlee.wb.yszx.adapter.EditImageListAdapter
import com.kijlee.wb.yszx.entity.fivem.FiveMUserBean
import com.kijlee.wb.yszx.entity.fivem.ImageViewEditBean
import com.kijlee.wb.yszx.utils.PicassoImageLoader
import com.vise.log.ViseLog
import com.yancy.gallerypick.config.GalleryConfig
import com.yancy.gallerypick.config.GalleryPick
import com.yancy.gallerypick.inter.IHandlerCallBack
import kotlinx.android.synthetic.main.fg_add_fivem_user.*
import java.io.File

/**
 *
 */
class NotificationsFragment : Fragment() {
    var viewLayout: View? = null
    lateinit var iHandlerCallBack: IHandlerCallBack
    var userBean = FiveMUserBean()
    lateinit var galleryConfig: GalleryConfig
    val PERMISSIONS_REQUEST_READ_CONTACTS = 8
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.fg_add_fivem_user, container, false)
        initGallery()
        galleryConfig = GalleryConfig.Builder()
            .imageLoader(PicassoImageLoader())    // ImageLoader 加载框架（必填）
            .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
            .provider("com.sphd.kij.systemmanger.provider")   // provider(必填)
//                .pathList(path)                         // 记录已选的图片
            .multiSelect(true, 9)// 配置是否多选的同时 配置多选数量   默认：false ， 9
            .crop(false)// 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
            .isShowCamera(true)// 是否现实相机按钮  默认：false
            .filePath(context!!.getExternalFilesDir(null)!!.absolutePath + "/Gallery/Pictures")          // 图片存放路径
            .build()
        carImageViewAdapter = EditImageListAdapter(R.layout.re_item_image_edit, imageViewEditList)

        //添加图片
        viewLayout!!.findViewById<ImageView>(R.id.addPhoto).setOnClickListener { view ->
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
//                Log.e(TAG, "需要授权 ");
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity!!,
                        Manifest.permission.CAMERA
                    )
                ) {
//                    Log.e(TAG, "拒绝过了");
//                    toast("请在 设置-应用管理 中开启此应用的储存授权。");
                } else {
//                    Log.e(TAG, "进行授权");
                    ActivityCompat.requestPermissions(
                        activity!!,
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

    var user = FiveMUserBean()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //上传用户资料
        addUserBtn.setOnClickListener {
            signUp()
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

    fun signUp() {

        userBean.local = local.selectedItem.toString()
        userBean.age = age.text.toString().toInt()
        when (attributeGroup.checkedRadioButtonId) {
            R.id.sRadio -> {
                userBean.attribute = "S"
            }
            R.id.mRadio -> {
                userBean.attribute = "M"
            }
            R.id.doubleRadio -> {
                userBean.attribute = "双"
            }
        }
        userBean.evaluate = evaluate.text.toString()
        userBean.objective = objective.text.toString()
        userBean.occupation = occupation.text.toString()
        userBean.degree = degree.text.toString()
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
        if (imageViewEditList.size > 0) {//有图片
            var array: Array<String> = Array(imageViewEditList.size, { "" })
            for (item in 0..imageViewEditList.size - 1) {
                array[item] = File(imageViewEditList[item].imagePath!!).absolutePath
            }
            BmobFile.uploadBatch(array, object : UploadBatchListener {
                override fun onSuccess(p0: MutableList<BmobFile>?, p1: MutableList<String>?) {
                    if (p1!!.size == array.size) {
                        userBean.userImages = p0
                        ViseLog.e("山川完成")
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
    }


}