package id.lagu.service

interface LaguManager {
    abstract fun clear()

    abstract fun storeToken(access_token: String)

    abstract fun getToken()
}