package com.sdssoft.batikindonesia

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Batik(
    var id: Int? = null,
    var nama_batik: String? = null,
    var daerah_batik: String? = null,
    var link_batik: String? = null,
    var makna_batik: String? = null,
    var harga_rendah: Int? = null,
    var harga_tinggi: Int? = null,
) : Parcelable