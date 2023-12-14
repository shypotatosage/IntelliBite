package com.imtuc.intellibite.repository

import com.imtuc.intellibite.retrofit.EndPointAPI
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: EndPointAPI
) {
}