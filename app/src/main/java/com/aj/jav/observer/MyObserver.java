package com.aj.jav.observer;

import com.aj.jav.constant.Constant;
import com.aj.jav.helper.ApiHelper;
import com.aj.jav.helper.IvHelper;
import com.aj.jav.utils.EncodeUtility;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import net.idik.lib.cipher.so.CipherClient;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/**
 * 實作iv解密
 * class <T> 定義回傳
 * 建構子 Class<T> t 定義gson parse
 */

public abstract class MyObserver<T extends GsonStatusInterface> implements Observer<Response<String>> {
    private Gson mGson = new Gson();
    private Class<T> mGsonClass;

    public MyObserver(Class<T> gsonClass) {
        mGsonClass = gsonClass;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Response<String> stringResponse) {
        String encdoeType = stringResponse.headers().get(Constant.HEADER_IS_ENCRYPT_KEY);
        if (encdoeType != null) {
            switch (encdoeType) {
                case Constant.HEADER_IS_ENCRYPT_VALUE:
                    try {
                        String iv = IvHelper.decode(stringResponse.headers().get(Constant.HEADER_KEY));
                        String decodeStr = EncodeUtility.decryptAES(CipherClient.decodeKey(), iv, stringResponse.body());
                        completeWithResponse(decodeStr);
                    }catch (Exception e){
                        //decryptAES 失敗
                        onComplete(stringResponse.body());
                        onError(new Throwable("decryptIvError"));
                    }
                    break;

                default:
                   // 其他加密方式
                    break;
            }
        }else {
            // 沒有加密
            completeWithResponse(stringResponse.body());
        }
    }

    private void completeWithResponse(String responseStr){
        onComplete(responseStr);

        T gson = getGsonData(responseStr);
        try {
            assert gson != null;
            completeWithCheckStatus(gson.getStatusCode() , gson.getStatusMessage() , gson);
        }catch (NullPointerException e){
            //包含decryptAES & 解析gson 失敗
            onError(new Throwable("parseGsonError"));
        }
    }

    private void completeWithCheckStatus(int statusCode , String statusMsg , T gson){
        if (ApiHelper.isSuccess(statusCode)) {
            onComplete(gson);
        } else if (ApiHelper.isTokenChange(statusCode)) {
            onError(new Throwable("tokenChange"));
            //todo
//            loadChangeTokenApi(networkStatus);
        } else if (ApiHelper.isTokenFail(statusCode)) {
            onError(new Throwable("tokenError"));
        } else {
            onError(new Throwable("status code : "+statusCode + " / status msg : "+statusMsg));
        }
    }

    @Override
    public void onComplete() {

    }

    private T getGsonData(String response) {
        try {
            return mGson.fromJson(response, mGsonClass);
        } catch (JsonParseException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 確認回傳文字
     */
    public abstract void onComplete(String stringResponse);

    /**
     * 回傳文字後 轉成gson
     */
    public abstract void onComplete(T gson);
}
