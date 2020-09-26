package com.kijlee.wb.fivemdogfood.ui.showapi.tnl


import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kijlee.wb.fivemdogfood.R
import com.kijlee.wb.fivemdogfood.adapter.ImageAdapter
import com.kijlee.wb.fivemdogfood.entity.showapi.TnlModleBean
import com.squareup.picasso.Picasso
import com.vise.log.ViseLog


class Fg_TnlInfo : Fragment() {
    var viewLayout: View? = null
    var adapter: ImageAdapter? = null

    //    val imgList: MutableList<TnlModleBean>? = ArrayList()
    var tnlModleBean: TnlModleBean? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewLayout = inflater!!.inflate(R.layout.layout_recyclerview, container, false)
        tnlModleBean = arguments!!.getSerializable("TnlModleBean") as TnlModleBean
        adapter = ImageAdapter(R.layout.layout_image_name, tnlModleBean!!.imgList!!.toMutableList())
        viewLayout!!.findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
        viewLayout!!.findViewById<RecyclerView>(R.id.recyclerView).layoutManager =
            StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
        adapter!!.setOnItemClickListener { adapter, view, position ->
            bigImageLoader(adapter.getItem(position) as String)
        }
        return viewLayout
    }

    fun bigImageLoader(imageUrl: String) {
        ViseLog.e("地址=="+imageUrl)
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
        imageView.setOnClickListener{ dialog.cancel() }
    }
}