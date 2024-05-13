package com.eep.android.gestionifema.api

import com.eep.android.gestionifema.api.Service.ApiServiceCenters
import com.eep.android.gestionifema.api.Service.ApiServiceLogin
import com.eep.android.gestionifema.api.Service.ApiServiceUser

object ApiClientCenters {
    val retrofitService: ApiServiceCenters by lazy {
        retrofit.create(ApiServiceCenters::class.java)
    }
}

object ApiClientUsers {
    val retrofitService: ApiServiceUser by lazy {
        retrofit.create(ApiServiceUser::class.java)
    }
}

object ApiClientLogin {
    val retrofitService: ApiServiceLogin by lazy {
        retrofit.create(ApiServiceLogin::class.java)
    }
}